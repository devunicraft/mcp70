package net.minecraft.src;

public class ItemHoe extends Item
{
    protected EnumToolMaterial field_77843_a;

    public ItemHoe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        field_77843_a = par2EnumToolMaterial;
        maxStackSize = 1;
        setMaxDamage(par2EnumToolMaterial.getMaxUses());
        setTabToDisplayOn(CreativeTabs.tabTools);
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockId(par4, par5 + 1, par6);

        if (par7 != 0 && j == 0 && i == Block.grass.blockID || i == Block.dirt.blockID)
        {
            Block block = Block.tilledField;
            par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

            if (par3World.isRemote)
            {
                return true;
            }
            else
            {
                par3World.setBlockWithNotify(par4, par5, par6, block.blockID);
                par1ItemStack.damageItem(1, par2EntityPlayer);
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    public String func_77842_f()
    {
        return field_77843_a.toString();
    }
}
