package net.minecraft.src;

public class ItemSeeds extends Item
{
    /**
     * The type of block this seed turns into (wheat or pumpkin stems for instance)
     */
    private int blockType;

    /** BlockID of the block the seeds can be planted on. */
    private int soilBlockID;

    public ItemSeeds(int par1, int par2, int par3)
    {
        super(par1);
        blockType = par2;
        soilBlockID = par3;
        setTabToDisplayOn(CreativeTabs.tabMaterials);
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6) || !par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6))
        {
            return false;
        }

        int i = par3World.getBlockId(par4, par5, par6);

        if (i == soilBlockID && par3World.isAirBlock(par4, par5 + 1, par6))
        {
            par3World.setBlockWithNotify(par4, par5 + 1, par6, blockType);
            par1ItemStack.stackSize--;
            return true;
        }
        else
        {
            return false;
        }
    }
}
