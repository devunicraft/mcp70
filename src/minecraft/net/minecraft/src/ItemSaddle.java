package net.minecraft.src;

public class ItemSaddle extends Item
{
    public ItemSaddle(int par1)
    {
        super(par1);
        maxStackSize = 1;
        setTabToDisplayOn(CreativeTabs.tabTransport);
    }

    public boolean func_77646_a(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        if (par2EntityLiving instanceof EntityPig)
        {
            EntityPig entitypig = (EntityPig)par2EntityLiving;

            if (!entitypig.getSaddled() && !entitypig.isChild())
            {
                entitypig.setSaddled(true);
                par1ItemStack.stackSize--;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        func_77646_a(par1ItemStack, par2EntityLiving);
        return true;
    }
}
