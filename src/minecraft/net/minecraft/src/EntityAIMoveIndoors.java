package net.minecraft.src;

import java.util.Random;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo doorInfo;
    private int insidePosX;
    private int insidePosZ;

    public EntityAIMoveIndoors(EntityCreature par1EntityCreature)
    {
        insidePosX = -1;
        insidePosZ = -1;
        entityObj = par1EntityCreature;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (entityObj.worldObj.isDaytime() && !entityObj.worldObj.isRaining() || entityObj.worldObj.worldProvider.hasNoSky)
        {
            return false;
        }

        if (entityObj.getRNG().nextInt(50) != 0)
        {
            return false;
        }

        if (insidePosX != -1 && entityObj.getDistanceSq(insidePosX, entityObj.posY, insidePosZ) < 4D)
        {
            return false;
        }

        Village village = entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ), 14);

        if (village == null)
        {
            return false;
        }
        else
        {
            doorInfo = village.findNearestDoorUnrestricted(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ));
            return doorInfo != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !entityObj.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        insidePosX = -1;

        if (entityObj.getDistanceSq(doorInfo.getInsidePosX(), doorInfo.posY, doorInfo.getInsidePosZ()) > 256D)
        {
            Vec3 vec3 = RandomPositionGenerator.func_75464_a(entityObj, 14, 3, Vec3.func_72437_a().func_72345_a((double)doorInfo.getInsidePosX() + 0.5D, doorInfo.getInsidePosY(), (double)doorInfo.getInsidePosZ() + 0.5D));

            if (vec3 != null)
            {
                entityObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.3F);
            }
        }
        else
        {
            entityObj.getNavigator().tryMoveToXYZ((double)doorInfo.getInsidePosX() + 0.5D, doorInfo.getInsidePosY(), (double)doorInfo.getInsidePosZ() + 0.5D, 0.3F);
        }
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        insidePosX = doorInfo.getInsidePosX();
        insidePosZ = doorInfo.getInsidePosZ();
        doorInfo = null;
    }
}
