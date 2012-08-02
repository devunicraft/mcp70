package net.minecraft.src;

import java.util.*;

public class VillageSiege
{
    private World worldObj;
    private boolean field_75535_b;
    private int field_75536_c;
    private int field_75533_d;
    private int field_75534_e;
    private Village field_75531_f;
    private int field_75532_g;
    private int field_75538_h;
    private int field_75539_i;

    public VillageSiege(World par1World)
    {
        field_75535_b = false;
        field_75536_c = -1;
        worldObj = par1World;
    }

    /**
     * Runs a single tick for the village siege
     */
    public void tick()
    {
        boolean flag = false;

        if (flag)
        {
            if (field_75536_c == 2)
            {
                field_75533_d = 100;
                return;
            }
        }
        else
        {
            if (worldObj.isDaytime())
            {
                field_75536_c = 0;
                return;
            }

            if (field_75536_c == 2)
            {
                return;
            }

            if (field_75536_c == 0)
            {
                float f = worldObj.getCelestialAngle(0.0F);

                if ((double)f < 0.5D || (double)f > 0.501D)
                {
                    return;
                }

                field_75536_c = worldObj.rand.nextInt(10) != 0 ? 2 : 1;
                field_75535_b = false;

                if (field_75536_c == 2)
                {
                    return;
                }
            }
        }

        if (!field_75535_b)
        {
            if (func_75529_b())
            {
                field_75535_b = true;
            }
            else
            {
                return;
            }
        }

        if (field_75534_e > 0)
        {
            field_75534_e--;
            return;
        }

        field_75534_e = 2;

        if (field_75533_d > 0)
        {
            spawnZombie();
            field_75533_d--;
        }
        else
        {
            field_75536_c = 2;
        }
    }

    private boolean func_75529_b()
    {
        List list = worldObj.playerEntities;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            field_75531_f = worldObj.villageCollectionObj.findNearestVillage((int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ, 1);

            if (field_75531_f != null && field_75531_f.getNumVillageDoors() >= 10 && field_75531_f.getTicksSinceLastDoorAdding() >= 20 && field_75531_f.getNumVillagers() >= 20)
            {
                ChunkCoordinates chunkcoordinates = field_75531_f.getCenter();
                float f = field_75531_f.getVillageRadius();
                boolean flag = false;
                int i = 0;

                do
                {
                    if (i >= 10)
                    {
                        break;
                    }

                    field_75532_g = chunkcoordinates.posX + (int)((double)(MathHelper.cos(worldObj.rand.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.90000000000000002D);
                    field_75538_h = chunkcoordinates.posY;
                    field_75539_i = chunkcoordinates.posZ + (int)((double)(MathHelper.sin(worldObj.rand.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.90000000000000002D);
                    flag = false;
                    Iterator iterator1 = worldObj.villageCollectionObj.func_75540_b().iterator();

                    do
                    {
                        if (!iterator1.hasNext())
                        {
                            break;
                        }

                        Village village = (Village)iterator1.next();

                        if (village == field_75531_f || !village.isInRange(field_75532_g, field_75538_h, field_75539_i))
                        {
                            continue;
                        }

                        flag = true;
                        break;
                    }
                    while (true);

                    if (!flag)
                    {
                        break;
                    }

                    i++;
                }
                while (true);

                if (flag)
                {
                    return false;
                }

                Vec3 vec3 = func_75527_a(field_75532_g, field_75538_h, field_75539_i);

                if (vec3 != null)
                {
                    field_75534_e = 0;
                    field_75533_d = 20;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean spawnZombie()
    {
        Vec3 vec3 = func_75527_a(field_75532_g, field_75538_h, field_75539_i);

        if (vec3 == null)
        {
            return false;
        }

        EntityZombie entityzombie;

        try
        {
            entityzombie = new EntityZombie(worldObj);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }

        entityzombie.setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, worldObj.rand.nextFloat() * 360F, 0.0F);
        worldObj.spawnEntityInWorld(entityzombie);
        ChunkCoordinates chunkcoordinates = field_75531_f.getCenter();
        entityzombie.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, field_75531_f.getVillageRadius());
        return true;
    }

    private Vec3 func_75527_a(int par1, int par2, int par3)
    {
        for (int i = 0; i < 10; i++)
        {
            int j = (par1 + worldObj.rand.nextInt(16)) - 8;
            int k = (par2 + worldObj.rand.nextInt(6)) - 3;
            int l = (par3 + worldObj.rand.nextInt(16)) - 8;

            if (field_75531_f.isInRange(j, k, l) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, worldObj, j, k, l))
            {
                return Vec3.func_72437_a().func_72345_a(j, k, l);
            }
        }

        return null;
    }
}
