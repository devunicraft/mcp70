package net.minecraft.src;

public class SlotFurnace extends Slot
{
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    private int field_75228_b;

    public SlotFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        thePlayer = par1EntityPlayer;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        if (getHasStack())
        {
            field_75228_b += Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        func_75208_c(par1ItemStack);
        super.onPickupFromSlot(par1ItemStack);
    }

    protected void func_75210_a(ItemStack par1ItemStack, int par2)
    {
        field_75228_b += par2;
        func_75208_c(par1ItemStack);
    }

    protected void func_75208_c(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, field_75228_b);

        if (!thePlayer.worldObj.isRemote)
        {
            int i = field_75228_b;
            float f = FurnaceRecipes.smelting().func_77601_c(par1ItemStack.itemID);

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                int j = MathHelper.floor_float((float)i * f);

                if (j < MathHelper.func_76123_f((float)i * f) && (float)Math.random() < (float)i * f - (float)j)
                {
                    j++;
                }

                i = j;
            }

            while (i > 0)
            {
                int k = EntityXPOrb.getXPSplit(i);
                i -= k;
                thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(thePlayer.worldObj, thePlayer.posX, thePlayer.posY + 0.5D, thePlayer.posZ + 0.5D, k));
            }
        }

        field_75228_b = 0;

        if (par1ItemStack.itemID == Item.ingotIron.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.acquireIron, 1);
        }

        if (par1ItemStack.itemID == Item.fishCooked.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.cookFish, 1);
        }
    }
}
