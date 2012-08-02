package net.minecraft.src;

import java.util.List;

public class EntityAIAvoidEntity extends EntityAIBase
{
    /** The entity we are attached to */
    private EntityCreature theEntity;
    private float field_75378_b;
    private float field_75379_c;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity field_75374_f;

    /** The PathNavigate of our entity */
    private PathNavigate entityPathNavigate;

    /** The class of the entity we should avoid */
    private Class targetEntityClass;

    public EntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, float par4, float par5)
    {
        theEntity = par1EntityCreature;
        targetEntityClass = par2Class;
        distanceFromEntity = par3;
        field_75378_b = par4;
        field_75379_c = par5;
        entityPathNavigate = par1EntityCreature.getNavigator();
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (targetEntityClass == (net.minecraft.src.EntityPlayer.class))
        {
            if ((theEntity instanceof EntityTameable) && ((EntityTameable)theEntity).isTamed())
            {
                return false;
            }

            closestLivingEntity = theEntity.worldObj.getClosestPlayerToEntity(theEntity, distanceFromEntity);

            if (closestLivingEntity == null)
            {
                return false;
            }
        }
        else
        {
            List list = theEntity.worldObj.getEntitiesWithinAABB(targetEntityClass, theEntity.boundingBox.expand(distanceFromEntity, 3D, distanceFromEntity));

            if (list.isEmpty())
            {
                return false;
            }

            closestLivingEntity = (Entity)list.get(0);
        }

        if (!theEntity.getEntitySenses().canSee(closestLivingEntity))
        {
            return false;
        }

        Vec3 vec3 = RandomPositionGenerator.func_75461_b(theEntity, 16, 7, Vec3.func_72437_a().func_72345_a(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));

        if (vec3 == null)
        {
            return false;
        }

        if (closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < closestLivingEntity.getDistanceSqToEntity(theEntity))
        {
            return false;
        }

        field_75374_f = entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);

        if (field_75374_f == null)
        {
            return false;
        }

        return field_75374_f.isDestinationSame(vec3);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        entityPathNavigate.setPath(field_75374_f, field_75378_b);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (theEntity.getDistanceSqToEntity(closestLivingEntity) < 49D)
        {
            theEntity.getNavigator().setSpeed(field_75379_c);
        }
        else
        {
            theEntity.getNavigator().setSpeed(field_75378_b);
        }
    }
}
