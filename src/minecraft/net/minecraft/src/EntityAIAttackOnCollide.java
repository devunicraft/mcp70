package net.minecraft.src;

import java.util.Random;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    EntityLiving attacker;
    EntityLiving entityTarget;

    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int attackTick;
    float field_75440_e;
    boolean field_75437_f;
    PathEntity field_75438_g;
    Class classTarget;
    private int field_75445_i;

    public EntityAIAttackOnCollide(EntityLiving par1EntityLiving, Class par2Class, float par3, boolean par4)
    {
        this(par1EntityLiving, par3, par4);
        classTarget = par2Class;
    }

    public EntityAIAttackOnCollide(EntityLiving par1EntityLiving, float par2, boolean par3)
    {
        attackTick = 0;
        attacker = par1EntityLiving;
        worldObj = par1EntityLiving.worldObj;
        field_75440_e = par2;
        field_75437_f = par3;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = attacker.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }

        if (classTarget != null && !classTarget.isAssignableFrom(entityliving.getClass()))
        {
            return false;
        }
        else
        {
            entityTarget = entityliving;
            field_75438_g = attacker.getNavigator().getPathToEntityLiving(entityTarget);
            return field_75438_g != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        EntityLiving entityliving = attacker.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }

        if (!entityTarget.isEntityAlive())
        {
            return false;
        }

        if (!field_75437_f)
        {
            return !attacker.getNavigator().noPath();
        }

        return attacker.isWithinHomeDistance(MathHelper.floor_double(entityTarget.posX), MathHelper.floor_double(entityTarget.posY), MathHelper.floor_double(entityTarget.posZ));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        attacker.getNavigator().setPath(field_75438_g, field_75440_e);
        field_75445_i = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        entityTarget = null;
        attacker.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30F, 30F);

        if ((field_75437_f || attacker.getEntitySenses().canSee(entityTarget)) && --field_75445_i <= 0)
        {
            field_75445_i = 4 + attacker.getRNG().nextInt(7);
            attacker.getNavigator().tryMoveToEntityLiving(entityTarget, field_75440_e);
        }

        attackTick = Math.max(attackTick - 1, 0);
        double d = attacker.width * 2.0F * (attacker.width * 2.0F);

        if (attacker.getDistanceSq(entityTarget.posX, entityTarget.boundingBox.minY, entityTarget.posZ) > d)
        {
            return;
        }

        if (attackTick > 0)
        {
            return;
        }
        else
        {
            attackTick = 20;
            attacker.attackEntityAsMob(entityTarget);
            return;
        }
    }
}
