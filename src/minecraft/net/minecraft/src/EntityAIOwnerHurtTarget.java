package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLiving theTarget;

    public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        theEntityTameable = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theEntityTameable.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = theEntityTameable.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            theTarget = entityliving.getLastAttackingEntity();
            return isSuitableTarget(theTarget, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(theTarget);
        super.startExecuting();
    }
}
