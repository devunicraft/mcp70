package net.minecraft.src;

import java.util.List;

public class BlockSandStone extends Block
{
    public static final String field_72189_a[] =
    {
        "default", "chiseled", "smooth"
    };

    public BlockSandStone(int par1)
    {
        super(par1, 192, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 1 || par1 == 0 && (par2 == 1 || par2 == 2))
        {
            return 176;
        }

        if (par1 == 0)
        {
            return 208;
        }

        if (par2 == 1)
        {
            return 229;
        }

        return par2 != 2 ? 192 : 230;
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
            return blockIndexInTexture + 16;
        }
        else
        {
            return blockIndexInTexture;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }
}
