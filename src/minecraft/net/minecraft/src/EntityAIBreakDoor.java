package net.minecraft.src;

import java.util.Random;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int field_75358_j;

    public EntityAIBreakDoor(EntityLiving par1EntityLiving)
    {
        super(par1EntityLiving);
        field_75358_j = -1;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!super.shouldExecute())
        {
            return false;
        }
        else
        {
            return !targetDoor.isDoorOpen(theEntity.worldObj, entityPosX, entityPosY, entityPosZ);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        breakingTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        double d = theEntity.getDistanceSq(entityPosX, entityPosY, entityPosZ);
        return breakingTime <= 240 && !targetDoor.isDoorOpen(theEntity.worldObj, entityPosX, entityPosY, entityPosZ) && d < 4D;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
        theEntity.worldObj.func_72888_f(theEntity.entityId, entityPosX, entityPosY, entityPosZ, -1);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        super.updateTask();

        if (theEntity.getRNG().nextInt(20) == 0)
        {
            theEntity.worldObj.playAuxSFX(1010, entityPosX, entityPosY, entityPosZ, 0);
        }

        breakingTime++;
        int i = (int)(((float)breakingTime / 240F) * 10F);

        if (i != field_75358_j)
        {
            theEntity.worldObj.func_72888_f(theEntity.entityId, entityPosX, entityPosY, entityPosZ, i);
            field_75358_j = i;
        }

        if (breakingTime == 240 && theEntity.worldObj.difficultySetting == 3)
        {
            theEntity.worldObj.setBlockWithNotify(entityPosX, entityPosY, entityPosZ, 0);
            theEntity.worldObj.playAuxSFX(1012, entityPosX, entityPosY, entityPosZ, 0);
            theEntity.worldObj.playAuxSFX(2001, entityPosX, entityPosY, entityPosZ, targetDoor.blockID);
        }
    }
}
