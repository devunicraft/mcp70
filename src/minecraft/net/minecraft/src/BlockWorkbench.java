package net.minecraft.src;

public class BlockWorkbench extends Block
{
    protected BlockWorkbench(int par1)
    {
        super(par1, Material.wood);
        blockIndexInTexture = 59;
        setCreativeTab(CreativeTabs.tabDeco);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 == 1)
        {
            return blockIndexInTexture - 16;
        }

        if (par1 == 0)
        {
            return Block.planks.getBlockTextureFromSide(0);
        }

        if (par1 == 2 || par1 == 4)
        {
            return blockIndexInTexture + 1;
        }
        else
        {
            return blockIndexInTexture;
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            par5EntityPlayer.displayGUIWorkbench(par2, par3, par4);
            return true;
        }
    }
}
