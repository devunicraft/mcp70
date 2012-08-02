package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase
{
    /** The child that is following its parent. */
    EntityAnimal childAnimal;
    EntityAnimal parentAnimal;
    float field_75347_c;
    private int field_75345_d;

    public EntityAIFollowParent(EntityAnimal par1EntityAnimal, float par2)
    {
        childAnimal = par1EntityAnimal;
        field_75347_c = par2;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (childAnimal.getGrowingAge() >= 0)
        {
            return false;
        }

        List list = childAnimal.worldObj.getEntitiesWithinAABB(childAnimal.getClass(), childAnimal.boundingBox.expand(8D, 4D, 8D));
        EntityAnimal entityanimal = null;
        double d = Double.MAX_VALUE;
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityAnimal entityanimal1 = (EntityAnimal)iterator.next();

            if (entityanimal1.getGrowingAge() >= 0)
            {
                double d1 = childAnimal.getDistanceSqToEntity(entityanimal1);

                if (d1 <= d)
                {
                    d = d1;
                    entityanimal = entityanimal1;
                }
            }
        }
        while (true);

        if (entityanimal == null)
        {
            return false;
        }

        if (d < 9D)
        {
            return false;
        }
        else
        {
            parentAnimal = entityanimal;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!parentAnimal.isEntityAlive())
        {
            return false;
        }

        double d = childAnimal.getDistanceSqToEntity(parentAnimal);
        return d >= 9D && d <= 256D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_75345_d = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        parentAnimal = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (--field_75345_d > 0)
        {
            return;
        }
        else
        {
            field_75345_d = 10;
            childAnimal.getNavigator().tryMoveToEntityLiving(parentAnimal, field_75347_c);
            return;
        }
    }
}
