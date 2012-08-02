package net.minecraft.src;

import java.util.*;

public class BlockChest extends BlockContainer
{
    private Random random;

    protected BlockChest(int par1)
    {
        super(par1, Material.wood);
        random = new Random();
        blockIndexInTexture = 26;
        setCreativeTab(CreativeTabs.tabDeco);
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
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 22;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        unifyAdjacentChests(par1World, par2, par3, par4);
        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);

        if (i == blockID)
        {
            unifyAdjacentChests(par1World, par2, par3, par4 - 1);
        }

        if (j == blockID)
        {
            unifyAdjacentChests(par1World, par2, par3, par4 + 1);
        }

        if (k == blockID)
        {
            unifyAdjacentChests(par1World, par2 - 1, par3, par4);
        }

        if (l == blockID)
        {
            unifyAdjacentChests(par1World, par2 + 1, par3, par4);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);
        byte byte0 = 0;
        int i1 = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

        if (i1 == 0)
        {
            byte0 = 2;
        }

        if (i1 == 1)
        {
            byte0 = 5;
        }

        if (i1 == 2)
        {
            byte0 = 3;
        }

        if (i1 == 3)
        {
            byte0 = 4;
        }

        if (i != blockID && j != blockID && k != blockID && l != blockID)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
        }
        else
        {
            if ((i == blockID || j == blockID) && (byte0 == 4 || byte0 == 5))
            {
                if (i == blockID)
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, byte0);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, byte0);
                }

                par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
            }

            if ((k == blockID || l == blockID) && (byte0 == 2 || byte0 == 3))
            {
                if (k == blockID)
                {
                    par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, byte0);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, byte0);
                }

                par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
            }
        }
    }

    /**
     * Turns the adjacent chests to a double chest.
     */
    public void unifyAdjacentChests(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isRemote)
        {
            return;
        }

        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);
        byte byte0 = 4;

        if (i == blockID || j == blockID)
        {
            int i1 = par1World.getBlockId(par2 - 1, par3, i != blockID ? par4 + 1 : par4 - 1);
            int k1 = par1World.getBlockId(par2 + 1, par3, i != blockID ? par4 + 1 : par4 - 1);
            byte0 = 5;
            int i2 = -1;

            if (i == blockID)
            {
                i2 = par1World.getBlockMetadata(par2, par3, par4 - 1);
            }
            else
            {
                i2 = par1World.getBlockMetadata(par2, par3, par4 + 1);
            }

            if (i2 == 4)
            {
                byte0 = 4;
            }

            if ((Block.opaqueCubeLookup[k] || Block.opaqueCubeLookup[i1]) && !Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[k1])
            {
                byte0 = 5;
            }

            if ((Block.opaqueCubeLookup[l] || Block.opaqueCubeLookup[k1]) && !Block.opaqueCubeLookup[k] && !Block.opaqueCubeLookup[i1])
            {
                byte0 = 4;
            }
        }
        else if (k == blockID || l == blockID)
        {
            int j1 = par1World.getBlockId(k != blockID ? par2 + 1 : par2 - 1, par3, par4 - 1);
            int l1 = par1World.getBlockId(k != blockID ? par2 + 1 : par2 - 1, par3, par4 + 1);
            byte0 = 3;
            int j2 = -1;

            if (k == blockID)
            {
                j2 = par1World.getBlockMetadata(par2 - 1, par3, par4);
            }
            else
            {
                j2 = par1World.getBlockMetadata(par2 + 1, par3, par4);
            }

            if (j2 == 2)
            {
                byte0 = 2;
            }

            if ((Block.opaqueCubeLookup[i] || Block.opaqueCubeLookup[j1]) && !Block.opaqueCubeLookup[j] && !Block.opaqueCubeLookup[l1])
            {
                byte0 = 3;
            }

            if ((Block.opaqueCubeLookup[j] || Block.opaqueCubeLookup[l1]) && !Block.opaqueCubeLookup[i] && !Block.opaqueCubeLookup[j1])
            {
                byte0 = 2;
            }
        }
        else
        {
            byte0 = 3;

            if (Block.opaqueCubeLookup[i] && !Block.opaqueCubeLookup[j])
            {
                byte0 = 3;
            }

            if (Block.opaqueCubeLookup[j] && !Block.opaqueCubeLookup[i])
            {
                byte0 = 2;
            }

            if (Block.opaqueCubeLookup[k] && !Block.opaqueCubeLookup[l])
            {
                byte0 = 5;
            }

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[k])
            {
                byte0 = 4;
            }
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int i, int j)
    {
        return 4;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return 4;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int i = 0;

        if (par1World.getBlockId(par2 - 1, par3, par4) == blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par2 + 1, par3, par4) == blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par2, par3, par4 - 1) == blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par2, par3, par4 + 1) == blockID)
        {
            i++;
        }

        if (i > 1)
        {
            return false;
        }

        if (isThereANeighborChest(par1World, par2 - 1, par3, par4))
        {
            return false;
        }

        if (isThereANeighborChest(par1World, par2 + 1, par3, par4))
        {
            return false;
        }

        if (isThereANeighborChest(par1World, par2, par3, par4 - 1))
        {
            return false;
        }

        return !isThereANeighborChest(par1World, par2, par3, par4 + 1);
    }

    /**
     * Checks the neighbor blocks to see if there is a chest there. Args: world, x, y, z
     */
    private boolean isThereANeighborChest(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockId(par2, par3, par4) != blockID)
        {
            return false;
        }

        if (par1World.getBlockId(par2 - 1, par3, par4) == blockID)
        {
            return true;
        }

        if (par1World.getBlockId(par2 + 1, par3, par4) == blockID)
        {
            return true;
        }

        if (par1World.getBlockId(par2, par3, par4 - 1) == blockID)
        {
            return true;
        }

        return par1World.getBlockId(par2, par3, par4 + 1) == blockID;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitychest != null)
        {
            tileentitychest.updateContainingBlockInfo();
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitychest != null)
        {
            for (int i = 0; i < tileentitychest.getSizeInventory(); i++)
            {
                ItemStack itemstack = tileentitychest.getStackInSlot(i);

                if (itemstack == null)
                {
                    continue;
                }

                float f = random.nextFloat() * 0.8F + 0.1F;
                float f1 = random.nextFloat() * 0.8F + 0.1F;
                float f2 = random.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0)
                {
                    int j = random.nextInt(21) + 10;

                    if (j > itemstack.stackSize)
                    {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(par1World, (float)par2 + f, (float)par3 + f1, (float)par4 + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float)random.nextGaussian() * f3;
                    entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)random.nextGaussian() * f3;

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    par1World.spawnEntityInWorld(entityitem);
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        Object obj = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

        if (obj == null)
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2, par3 + 1, par4))
        {
            return true;
        }

        if (isOcelotBlockingChest(par1World, par2, par3, par4))
        {
            return true;
        }

        if (par1World.getBlockId(par2 - 1, par3, par4) == blockID && (par1World.isBlockNormalCube(par2 - 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 - 1, par3, par4)))
        {
            return true;
        }

        if (par1World.getBlockId(par2 + 1, par3, par4) == blockID && (par1World.isBlockNormalCube(par2 + 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 + 1, par3, par4)))
        {
            return true;
        }

        if (par1World.getBlockId(par2, par3, par4 - 1) == blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 - 1) || isOcelotBlockingChest(par1World, par2, par3, par4 - 1)))
        {
            return true;
        }

        if (par1World.getBlockId(par2, par3, par4 + 1) == blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 + 1) || isOcelotBlockingChest(par1World, par2, par3, par4 + 1)))
        {
            return true;
        }

        if (par1World.getBlockId(par2 - 1, par3, par4) == blockID)
        {
            obj = new InventoryLargeChest("container.chestDouble", (TileEntityChest)par1World.getBlockTileEntity(par2 - 1, par3, par4), ((IInventory)(obj)));
        }

        if (par1World.getBlockId(par2 + 1, par3, par4) == blockID)
        {
            obj = new InventoryLargeChest("container.chestDouble", ((IInventory)(obj)), (TileEntityChest)par1World.getBlockTileEntity(par2 + 1, par3, par4));
        }

        if (par1World.getBlockId(par2, par3, par4 - 1) == blockID)
        {
            obj = new InventoryLargeChest("container.chestDouble", (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4 - 1), ((IInventory)(obj)));
        }

        if (par1World.getBlockId(par2, par3, par4 + 1) == blockID)
        {
            obj = new InventoryLargeChest("container.chestDouble", ((IInventory)(obj)), (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4 + 1));
        }

        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            par5EntityPlayer.displayGUIChest(((IInventory)(obj)));
            return true;
        }
    }

    public TileEntity func_72274_a(World par1World)
    {
        return new TileEntityChest();
    }

    /**
     * Looks for a sitting ocelot within certain bounds. Such an ocelot is considered to be blocking access to the
     * chest.
     */
    private static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3)
    {
        for (Iterator iterator = par0World.getEntitiesWithinAABB(net.minecraft.src.EntityOcelot.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par1, par2 + 1, par3, par1 + 1, par2 + 2, par3 + 1)).iterator(); iterator.hasNext();)
        {
            EntityOcelot entityocelot = (EntityOcelot)iterator.next();
            EntityOcelot entityocelot1 = (EntityOcelot)entityocelot;

            if (entityocelot1.isSitting())
            {
                return true;
            }
        }

        return false;
    }
}
