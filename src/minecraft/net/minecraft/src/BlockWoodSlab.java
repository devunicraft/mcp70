package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockWoodSlab extends BlockHalfSlab
{
    public static final String field_72243_a[] =
    {
        "oak", "spruce", "birch", "jungle"
    };

    public BlockWoodSlab(int par1, boolean par2)
    {
        super(par1, par2, Material.wood);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        switch (par2 & 7)
        {
            default:
                return 4;

            case 1:
                return 198;

            case 2:
                return 214;

            case 3:
                return 199;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return getBlockTextureFromSideAndMetadata(par1, 0);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.woodSingleSlab.blockID;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(Block.woodSingleSlab.blockID, 2, par1 & 7);
    }

    public String func_72240_d(int par1)
    {
        if (par1 < 0 || par1 >= field_72243_a.length)
        {
            par1 = 0;
        }

        return (new StringBuilder()).append(super.getBlockName()).append(".").append(field_72243_a[par1]).toString();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (par1 == Block.woodDoubleSlab.blockID)
        {
            return;
        }

        for (int i = 0; i < 4; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
