package net.minecraft.src;

import java.util.Random;

public class EntityAIOcelotSit extends EntityAIBase
{
    private final EntityOcelot theOcelot;
    private final float field_75404_b;

    /** Tracks for how long the task has been executing */
    private int currentTick;
    private int field_75402_d;

    /** For how long the Ocelot should be sitting */
    private int maxSittingTicks;

    /** X Coordinate of a nearby sitable block */
    private int sitableBlockX;

    /** Y Coordinate of a nearby sitable block */
    private int sitableBlockY;

    /** Z Coordinate of a nearby sitable block */
    private int sitableBlockZ;

    public EntityAIOcelotSit(EntityOcelot par1EntityOcelot, float par2)
    {
        currentTick = 0;
        field_75402_d = 0;
        maxSittingTicks = 0;
        sitableBlockX = 0;
        sitableBlockY = 0;
        sitableBlockZ = 0;
        theOcelot = par1EntityOcelot;
        field_75404_b = par2;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theOcelot.isTamed() && !theOcelot.isSitting() && theOcelot.getRNG().nextDouble() <= 0.0065000001341104507D && getNearbySitableBlockDistance();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return currentTick <= maxSittingTicks && field_75402_d <= 60 && isSittableBlock(theOcelot.worldObj, sitableBlockX, sitableBlockY, sitableBlockZ);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theOcelot.getNavigator().tryMoveToXYZ((double)(float)sitableBlockX + 0.5D, sitableBlockY + 1, (double)(float)sitableBlockZ + 0.5D, field_75404_b);
        currentTick = 0;
        field_75402_d = 0;
        maxSittingTicks = theOcelot.getRNG().nextInt(theOcelot.getRNG().nextInt(1200) + 1200) + 1200;
        theOcelot.func_70907_r().setSitting(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theOcelot.setSitting(false);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        currentTick++;
        theOcelot.func_70907_r().setSitting(false);

        if (theOcelot.getDistanceSq(sitableBlockX, sitableBlockY + 1, sitableBlockZ) > 1.0D)
        {
            theOcelot.setSitting(false);
            theOcelot.getNavigator().tryMoveToXYZ((double)(float)sitableBlockX + 0.5D, sitableBlockY + 1, (double)(float)sitableBlockZ + 0.5D, field_75404_b);
            field_75402_d++;
        }
        else if (!theOcelot.isSitting())
        {
            theOcelot.setSitting(true);
        }
        else
        {
            field_75402_d--;
        }
    }

    /**
     * Searches for a block to sit on within a 8 block range, returns 0 if none found
     */
    private boolean getNearbySitableBlockDistance()
    {
        int i = (int)theOcelot.posY;
        double d = 2147483647D;

        for (int j = (int)theOcelot.posX - 8; (double)j < theOcelot.posX + 8D; j++)
        {
            for (int k = (int)theOcelot.posZ - 8; (double)k < theOcelot.posZ + 8D; k++)
            {
                if (!isSittableBlock(theOcelot.worldObj, j, i, k) || !theOcelot.worldObj.isAirBlock(j, i + 1, k))
                {
                    continue;
                }

                double d1 = theOcelot.getDistanceSq(j, i, k);

                if (d1 < d)
                {
                    sitableBlockX = j;
                    sitableBlockY = i;
                    sitableBlockZ = k;
                    d = d1;
                }
            }
        }

        return d < 2147483647D;
    }

    /**
     * Determines wheter the Ocelot wants to sit on the block at given coordinate
     */
    private boolean isSittableBlock(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockId(par2, par3, par4);
        int j = par1World.getBlockMetadata(par2, par3, par4);

        if (i == Block.chest.blockID)
        {
            TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentitychest.numUsingPlayers < 1)
            {
                return true;
            }
        }
        else
        {
            if (i == Block.stoneOvenActive.blockID)
            {
                return true;
            }

            if (i == Block.bed.blockID && !BlockBed.isBlockHeadOfBed(j))
            {
                return true;
            }
        }

        return false;
    }
}
