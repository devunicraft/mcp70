package net.minecraft.src;

import java.util.Random;

public class EntityAIBeg extends EntityAIBase
{
    private EntityWolf theWolf;
    private EntityPlayer field_75385_b;
    private World worldObject;
    private float minPlayerDistance;
    private int field_75384_e;

    public EntityAIBeg(EntityWolf par1EntityWolf, float par2)
    {
        theWolf = par1EntityWolf;
        worldObject = par1EntityWolf.worldObj;
        minPlayerDistance = par2;
        setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        field_75385_b = worldObject.getClosestPlayerToEntity(theWolf, minPlayerDistance);

        if (field_75385_b == null)
        {
            return false;
        }
        else
        {
            return func_75382_a(field_75385_b);
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!field_75385_b.isEntityAlive())
        {
            return false;
        }

        if (theWolf.getDistanceSqToEntity(field_75385_b) > (double)(minPlayerDistance * minPlayerDistance))
        {
            return false;
        }
        else
        {
            return field_75384_e > 0 && func_75382_a(field_75385_b);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theWolf.func_70918_i(true);
        field_75384_e = 40 + theWolf.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theWolf.func_70918_i(false);
        field_75385_b = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theWolf.getLookHelper().setLookPosition(field_75385_b.posX, field_75385_b.posY + (double)field_75385_b.getEyeHeight(), field_75385_b.posZ, 10F, theWolf.getVerticalFaceSpeed());
        field_75384_e--;
    }

    private boolean func_75382_a(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

        if (itemstack == null)
        {
            return false;
        }

        if (!theWolf.isTamed() && itemstack.itemID == Item.bone.shiftedIndex)
        {
            return true;
        }
        else
        {
            return theWolf.isWheat(itemstack);
        }
    }
}
