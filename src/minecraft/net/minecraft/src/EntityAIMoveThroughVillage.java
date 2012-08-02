package net.minecraft.src;

import java.util.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private EntityCreature theEntity;
    private float field_75418_b;
    private PathEntity field_75419_c;
    private VillageDoorInfo doorInfo;
    private boolean field_75417_e;
    private List doorList;

    public EntityAIMoveThroughVillage(EntityCreature par1EntityCreature, float par2, boolean par3)
    {
        doorList = new ArrayList();
        theEntity = par1EntityCreature;
        field_75418_b = par2;
        field_75417_e = par3;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        func_75414_f();

        if (field_75417_e && theEntity.worldObj.isDaytime())
        {
            return false;
        }

        Village village = theEntity.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(theEntity.posX), MathHelper.floor_double(theEntity.posY), MathHelper.floor_double(theEntity.posZ), 0);

        if (village == null)
        {
            return false;
        }

        doorInfo = func_75412_a(village);

        if (doorInfo == null)
        {
            return false;
        }

        boolean flag = theEntity.getNavigator().getCanBreakDoors();
        theEntity.getNavigator().setBreakDoors(false);
        field_75419_c = theEntity.getNavigator().getPathToXYZ(doorInfo.posX, doorInfo.posY, doorInfo.posZ);
        theEntity.getNavigator().setBreakDoors(flag);

        if (field_75419_c != null)
        {
            return true;
        }

        Vec3 vec3 = RandomPositionGenerator.func_75464_a(theEntity, 10, 7, Vec3.func_72437_a().func_72345_a(doorInfo.posX, doorInfo.posY, doorInfo.posZ));

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            theEntity.getNavigator().setBreakDoors(false);
            field_75419_c = theEntity.getNavigator().getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
            theEntity.getNavigator().setBreakDoors(flag);
            return field_75419_c != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (theEntity.getNavigator().noPath())
        {
            return false;
        }
        else
        {
            float f = theEntity.width + 4F;
            return theEntity.getDistanceSq(doorInfo.posX, doorInfo.posY, doorInfo.posZ) > (double)(f * f);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().setPath(field_75419_c, field_75418_b);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        if (theEntity.getNavigator().noPath() || theEntity.getDistanceSq(doorInfo.posX, doorInfo.posY, doorInfo.posZ) < 16D)
        {
            doorList.add(doorInfo);
        }
    }

    private VillageDoorInfo func_75412_a(Village par1Village)
    {
        VillageDoorInfo villagedoorinfo = null;
        int i = 0x7fffffff;
        List list = par1Village.getVillageDoorInfoList();
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor_double(theEntity.posX), MathHelper.floor_double(theEntity.posY), MathHelper.floor_double(theEntity.posZ));

            if (j < i && !func_75413_a(villagedoorinfo1))
            {
                villagedoorinfo = villagedoorinfo1;
                i = j;
            }
        }
        while (true);

        return villagedoorinfo;
    }

    private boolean func_75413_a(VillageDoorInfo par1VillageDoorInfo)
    {
        for (Iterator iterator = doorList.iterator(); iterator.hasNext();)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (par1VillageDoorInfo.posX == villagedoorinfo.posX && par1VillageDoorInfo.posY == villagedoorinfo.posY && par1VillageDoorInfo.posZ == villagedoorinfo.posZ)
            {
                return true;
            }
        }

        return false;
    }

    private void func_75414_f()
    {
        if (doorList.size() > 15)
        {
            doorList.remove(0);
        }
    }
}
