package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
    public BlockTripWire(int par1)
    {
        super(par1, 173, Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        setTickRandomly(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i)
    {
        return null;
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 30;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.silk.shiftedIndex;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Item.silk.shiftedIndex;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 2) == 2;
        boolean flag1 = !par1World.func_72797_t(par2, par3 - 1, par4);

        if (flag != flag1)
        {
            dropBlockAsItem(par1World, par2, par3, par4, i, 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 4) == 4;
        boolean flag1 = (i & 2) == 2;

        if (!flag1)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!flag)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        byte byte0 = ((byte)(par1World.func_72797_t(par2, par3 - 1, par4) ? 0 : 2));
        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
        func_72149_e(par1World, par2, par3, par4, byte0);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        func_72149_e(par1World, par2, par3, par4, par6 | 1);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if (par6EntityPlayer.getCurrentEquippedItem() != null && par6EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8);
        }
    }

    private void func_72149_e(World par1World, int par2, int par3, int par4, int par5)
    {
        label0:

        for (int i = 0; i < 2; i++)
        {
            int j = 1;

            do
            {
                if (j >= 42)
                {
                    continue label0;
                }

                int k = par2 + Direction.offsetX[i] * j;
                int l = par4 + Direction.offsetZ[i] * j;
                int i1 = par1World.getBlockId(k, par3, l);

                if (i1 == Block.tripWireSource.blockID)
                {
                    int j1 = par1World.getBlockMetadata(k, par3, l) & 3;

                    if (j1 == Direction.footInvisibleFaceRemap[i])
                    {
                        Block.tripWireSource.func_72143_a(par1World, k, par3, l, i1, par1World.getBlockMetadata(k, par3, l), true, j, par5);
                    }

                    continue label0;
                }

                if (i1 != Block.tripWire.blockID)
                {
                    continue label0;
                }

                j++;
            }
            while (true);
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if ((par1World.getBlockMetadata(par2, par3, par4) & 1) == 1)
        {
            return;
        }
        else
        {
            func_72147_l(par1World, par2, par3, par4);
            return;
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if ((par1World.getBlockMetadata(par2, par3, par4) & 1) != 1)
        {
            return;
        }
        else
        {
            func_72147_l(par1World, par2, par3, par4);
            return;
        }
    }

    private void func_72147_l(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 1) == 1;
        boolean flag1 = false;
        List list = par1World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)par2 + minX, (double)par3 + minY, (double)par4 + minZ, (double)par2 + maxX, (double)par3 + maxY, (double)par4 + maxZ));

        if (!list.isEmpty())
        {
            flag1 = true;
        }

        if (flag1 && !flag)
        {
            i |= 1;
        }

        if (!flag1 && flag)
        {
            i &= -2;
        }

        if (flag1 != flag)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
            func_72149_e(par1World, par2, par3, par4, i);
        }

        if (flag1)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
        }
    }

    public static boolean func_72148_a(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4, int par5)
    {
        int i = par1 + Direction.offsetX[par5];
        int j = par2;
        int k = par3 + Direction.offsetZ[par5];
        int l = par0IBlockAccess.getBlockId(i, j, k);
        boolean flag = (par4 & 2) == 2;

        if (l == Block.tripWireSource.blockID)
        {
            int i1 = par0IBlockAccess.getBlockMetadata(i, j, k);
            int k1 = i1 & 3;
            return k1 == Direction.footInvisibleFaceRemap[par5];
        }

        if (l == Block.tripWire.blockID)
        {
            int j1 = par0IBlockAccess.getBlockMetadata(i, j, k);
            boolean flag1 = (j1 & 2) == 2;
            return flag == flag1;
        }
        else
        {
            return false;
        }
    }
}
