package net.minecraft.src;

import java.util.List;

public class ContainerMerchant extends Container
{
    private IMerchant field_75178_e;
    private InventoryMerchant field_75176_f;
    private final World field_75177_g;

    public ContainerMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World)
    {
        field_75178_e = par2IMerchant;
        field_75177_g = par3World;
        field_75176_f = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
        func_75146_a(new Slot(field_75176_f, 0, 36, 53));
        func_75146_a(new Slot(field_75176_f, 1, 62, 53));
        func_75146_a(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, field_75176_f, 2, 120, 53));

        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                func_75146_a(new Slot(par1InventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            func_75146_a(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
        }
    }

    public InventoryMerchant func_75174_d()
    {
        return field_75176_f;
    }

    public void func_75132_a(ICrafting par1ICrafting)
    {
        super.func_75132_a(par1ICrafting);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        field_75176_f.func_70470_g();
        super.onCraftMatrixChanged(par1IInventory);
    }

    public void func_75175_c(int par1)
    {
        field_75176_f.func_70471_c(par1);
    }

    public void updateProgressBar(int i, int j)
    {
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return field_75178_e.func_70931_l_() == par1EntityPlayer;
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par1 == 2)
            {
                if (!mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.func_75220_a(itemstack1, itemstack);
            }
            else if (par1 == 0 || par1 == 1)
            {
                if (!mergeItemStack(itemstack1, 3, 39, false))
                {
                    return null;
                }
            }
            else if (par1 >= 3 && par1 < 30)
            {
                if (!mergeItemStack(itemstack1, 30, 39, false))
                {
                    return null;
                }
            }
            else if (par1 >= 30 && par1 < 39 && !mergeItemStack(itemstack1, 3, 30, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(itemstack1);
        }

        return itemstack;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);
        field_75178_e.func_70932_a_(null);
        super.onCraftGuiClosed(par1EntityPlayer);

        if (field_75177_g.isRemote)
        {
            return;
        }

        ItemStack itemstack = field_75176_f.getStackInSlotOnClosing(0);

        if (itemstack != null)
        {
            par1EntityPlayer.dropPlayerItem(itemstack);
        }

        itemstack = field_75176_f.getStackInSlotOnClosing(1);

        if (itemstack != null)
        {
            par1EntityPlayer.dropPlayerItem(itemstack);
        }
    }
}
