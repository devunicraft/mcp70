package net.minecraft.src;

import java.util.Random;

public class EntityAIFleeSun extends EntityAIBase
{
    private EntityCreature theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private float movementSpeed;
    private World theWorld;

    public EntityAIFleeSun(EntityCreature par1EntityCreature, float par2)
    {
        theCreature = par1EntityCreature;
        movementSpeed = par2;
        theWorld = par1EntityCreature.worldObj;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theWorld.isDaytime())
        {
            return false;
        }

        if (!theCreature.isBurning())
        {
            return false;
        }

        if (!theWorld.canBlockSeeTheSky(MathHelper.floor_double(theCreature.posX), (int)theCreature.boundingBox.minY, MathHelper.floor_double(theCreature.posZ)))
        {
            return false;
        }

        Vec3 vec3 = findPossibleShelter();

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            shelterX = vec3.xCoord;
            shelterY = vec3.yCoord;
            shelterZ = vec3.zCoord;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !theCreature.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theCreature.getNavigator().tryMoveToXYZ(shelterX, shelterY, shelterZ, movementSpeed);
    }

    private Vec3 findPossibleShelter()
    {
        Random random = theCreature.getRNG();

        for (int i = 0; i < 10; i++)
        {
            int j = MathHelper.floor_double((theCreature.posX + (double)random.nextInt(20)) - 10D);
            int k = MathHelper.floor_double((theCreature.boundingBox.minY + (double)random.nextInt(6)) - 3D);
            int l = MathHelper.floor_double((theCreature.posZ + (double)random.nextInt(20)) - 10D);

            if (!theWorld.canBlockSeeTheSky(j, k, l) && theCreature.getBlockPathWeight(j, k, l) < 0.0F)
            {
                return Vec3.func_72437_a().func_72345_a(j, k, l);
            }
        }

        return null;
    }
}
