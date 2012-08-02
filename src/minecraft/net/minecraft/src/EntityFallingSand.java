package net.minecraft.src;

public class EntityFallingSand extends Entity
{
    public int blockID;
    public int field_70285_b;

    /** How long the block has been falling for. */
    public int fallTime;
    public boolean field_70284_d;

    public EntityFallingSand(World par1World)
    {
        super(par1World);
        fallTime = 0;
        field_70284_d = true;
    }

    public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World, par2, par4, par6, par8, 0);
    }

    public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8, int par9)
    {
        super(par1World);
        fallTime = 0;
        field_70284_d = true;
        blockID = par8;
        field_70285_b = par9;
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(par2, par4, par6);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = par2;
        prevPosY = par4;
        prevPosZ = par6;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (blockID == 0)
        {
            setDead();
            return;
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        fallTime++;
        motionY -= 0.039999999105930328D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;

        if (!worldObj.isRemote)
        {
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(posY);
            int k = MathHelper.floor_double(posZ);

            if (fallTime == 1)
            {
                if (fallTime == 1 && worldObj.getBlockId(i, j, k) == blockID)
                {
                    worldObj.setBlockWithNotify(i, j, k, 0);
                }
                else
                {
                    setDead();
                }
            }

            if (onGround)
            {
                motionX *= 0.69999998807907104D;
                motionZ *= 0.69999998807907104D;
                motionY *= -0.5D;

                if (worldObj.getBlockId(i, j, k) != Block.pistonMoving.blockID)
                {
                    setDead();

                    if ((!worldObj.canPlaceEntityOnSide(blockID, i, j, k, true, 1, null) || BlockSand.canFallBelow(worldObj, i, j - 1, k) || !worldObj.setBlockAndMetadataWithNotify(i, j, k, blockID, field_70285_b)) && !worldObj.isRemote && field_70284_d)
                    {
                        entityDropItem(new ItemStack(blockID, 1, field_70285_b), 0.0F);
                    }
                }
            }
            else if (fallTime > 100 && !worldObj.isRemote && (j < 1 || j > 256) || fallTime > 600)
            {
                if (field_70284_d)
                {
                    dropItem(blockID, 1);
                }

                setDead();
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Tile", (byte)blockID);
        par1NBTTagCompound.setByte("Data", (byte)field_70285_b);
        par1NBTTagCompound.setByte("Time", (byte)fallTime);
        par1NBTTagCompound.setBoolean("DropItem", field_70284_d);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        blockID = par1NBTTagCompound.getByte("Tile") & 0xff;
        field_70285_b = par1NBTTagCompound.getByte("Data") & 0xff;
        fallTime = par1NBTTagCompound.getByte("Time") & 0xff;

        if (par1NBTTagCompound.hasKey("DropItem"))
        {
            field_70284_d = par1NBTTagCompound.getBoolean("DropItem");
        }

        if (blockID == 0)
        {
            blockID = Block.sand.blockID;
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public World getWorld()
    {
        return worldObj;
    }
}
