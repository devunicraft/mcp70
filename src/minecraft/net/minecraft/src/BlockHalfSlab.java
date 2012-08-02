package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class BlockHalfSlab extends Block
{
    private final boolean field_72242_a;

    public BlockHalfSlab(int par1, boolean par2, Material par3Material)
    {
        super(par1, 6, par3Material);
        field_72242_a = par2;

        if (par2)
        {
            opaqueCubeLookup[par1] = true;
        }
        else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        setLightOpacity(255);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (field_72242_a)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            boolean flag = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0;

            if (flag)
            {
                setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        if (field_72242_a)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return getBlockTextureFromSideAndMetadata(par1, 0);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return field_72242_a;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        if (field_72242_a)
        {
            return;
        }

        if (par5 == 0 || par5 != 1 && (double)par7 > 0.5D)
        {
            int i = par1World.getBlockMetadata(par2, par3, par4) & 7;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 8);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return !field_72242_a ? 1 : 2;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1 & 7;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return field_72242_a;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (field_72242_a)
        {
            return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
        }

        if (par5 != 1 && par5 != 0 && !super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5))
        {
            return false;
        }

        int i = par2;
        int j = par3;
        int k = par4;
        i += Facing.offsetsXForSide[Facing.faceToSide[par5]];
        j += Facing.offsetsYForSide[Facing.faceToSide[par5]];
        k += Facing.offsetsZForSide[Facing.faceToSide[par5]];
        boolean flag = (par1IBlockAccess.getBlockMetadata(i, j, k) & 8) != 0;

        if (flag)
        {
            if (par5 == 0)
            {
                return true;
            }

            if (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5))
            {
                return true;
            }
            else
            {
                return !func_72241_e(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0;
            }
        }

        if (par5 == 1)
        {
            return true;
        }

        if (par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5))
        {
            return true;
        }
        else
        {
            return !func_72241_e(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0;
        }
    }

    private static boolean func_72241_e(int par0)
    {
        return par0 == Block.stairSingle.blockID || par0 == Block.woodSingleSlab.blockID;
    }

    public abstract String func_72240_d(int i);
}
