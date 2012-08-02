package net.minecraft.src;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLiving theEntity;
    EntityLiving theVictim;
    int attackCountdown;

    public EntityAIOcelotAttack(EntityLiving par1EntityLiving)
    {
        attackCountdown = 0;
        theEntity = par1EntityLiving;
        theWorld = par1EntityLiving.worldObj;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = theEntity.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            theVictim = entityliving;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!theVictim.isEntityAlive())
        {
            return false;
        }

        if (theEntity.getDistanceSqToEntity(theVictim) > 225D)
        {
            return false;
        }
        else
        {
            return !theEntity.getNavigator().noPath() || shouldExecute();
        }
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theVictim = null;
        theEntity.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theEntity.getLookHelper().setLookPositionWithEntity(theVictim, 30F, 30F);
        double d = theEntity.width * 2.0F * (theEntity.width * 2.0F);
        double d1 = theEntity.getDistanceSq(theVictim.posX, theVictim.boundingBox.minY, theVictim.posZ);
        float f = 0.23F;

        if (d1 > d && d1 < 16D)
        {
            f = 0.4F;
        }
        else if (d1 < 225D)
        {
            f = 0.18F;
        }

        theEntity.getNavigator().tryMoveToEntityLiving(theVictim, f);
        attackCountdown = Math.max(attackCountdown - 1, 0);

        if (d1 > d)
        {
            return;
        }

        if (attackCountdown > 0)
        {
            return;
        }
        else
        {
            attackCountdown = 20;
            theEntity.attackEntityAsMob(theVictim);
            return;
        }
    }
}
