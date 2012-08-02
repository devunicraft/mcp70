package net.minecraft.src;

public class EntityAIMoveTwardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_75433_e;

    public EntityAIMoveTwardsRestriction(EntityCreature par1EntityCreature, float par2)
    {
        theEntity = par1EntityCreature;
        field_75433_e = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theEntity.isWithinHomeDistanceCurrentPosition())
        {
            return false;
        }

        ChunkCoordinates chunkcoordinates = theEntity.getHomePosition();
        Vec3 vec3 = RandomPositionGenerator.func_75464_a(theEntity, 16, 7, Vec3.func_72437_a().func_72345_a(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            movePosX = vec3.xCoord;
            movePosY = vec3.yCoord;
            movePosZ = vec3.zCoord;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !theEntity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, field_75433_e);
    }
}
