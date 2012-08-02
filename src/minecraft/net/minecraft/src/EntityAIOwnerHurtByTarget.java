package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable theDefendingTameable;
    EntityLiving theOwnerAttacker;

    public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        theDefendingTameable = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theDefendingTameable.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = theDefendingTameable.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            theOwnerAttacker = entityliving.getAITarget();
            return isSuitableTarget(theOwnerAttacker, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(theOwnerAttacker);
        super.startExecuting();
    }
}
