package net.minecraft.src;

import java.util.Random;

public class BlockCocoa extends BlockDirectional
{
    public BlockCocoa(int par1)
    {
        super(par1, 168, Material.plants);
        setTickRandomly(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!canBlockStay(par1World, par2, par3, par4))
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
        else if (par1World.rand.nextInt(5) == 0)
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);
            int j = func_72219_c(i);

            if (j < 2)
            {
                j++;
                par1World.setBlockMetadataWithNotify(par2, par3, par4, j << 2 | getDirection(i));
            }
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        int i = getDirection(par1World.getBlockMetadata(par2, par3, par4));
        par2 += Direction.offsetX[i];
        par4 += Direction.offsetZ[i];
        int j = par1World.getBlockId(par2, par3, par4);
        return j == Block.wood.blockID && BlockLog.func_72141_e(par1World.getBlockMetadata(par2, par3, par4)) == 3;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 28;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int j = getDirection(i);
        int k = func_72219_c(i);
        int l = 4 + k * 2;
        int i1 = 5 + k * 2;
        float f = (float)l / 2.0F;

        switch (j)
        {
            case 0:
                setBlockBounds((8F - f) / 16F, (12F - (float)i1) / 16F, (15F - (float)l) / 16F, (8F + f) / 16F, 0.75F, 0.9375F);
                break;

            case 2:
                setBlockBounds((8F - f) / 16F, (12F - (float)i1) / 16F, 0.0625F, (8F + f) / 16F, 0.75F, (1.0F + (float)l) / 16F);
                break;

            case 1:
                setBlockBounds(0.0625F, (12F - (float)i1) / 16F, (8F - f) / 16F, (1.0F + (float)l) / 16F, 0.75F, (8F + f) / 16F);
                break;

            case 3:
                setBlockBounds((15F - (float)l) / 16F, (12F - (float)i1) / 16F, (8F - f) / 16F, 0.9375F, 0.75F, (8F + f) / 16F);
                break;
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = ((MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3) + 0) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        if (par5 == 1 || par5 == 0)
        {
            par5 = 2;
        }

        int i = Direction.footInvisibleFaceRemap[Direction.vineGrowth[par5]];
        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!canBlockStay(par1World, par2, par3, par4))
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }

    public static int func_72219_c(int par0)
    {
        return (par0 & 0xc) >> 2;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        int i = func_72219_c(par5);
        byte byte0 = 1;

        if (i >= 2)
        {
            byte0 = 3;
        }

        for (int j = 0; j < byte0; j++)
        {
            dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.dyePowder, 1, 3));
        }
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Item.dyePowder.shiftedIndex;
    }

    public int func_71873_h(World par1World, int par2, int par3, int i)
    {
        return 3;
    }
}
