package net.minecraft.src;

import java.util.*;

public class TileEntityMobSpawner extends TileEntity
{
    /** The stored delay before a new spawn. */
    public int delay;

    /**
     * The string ID of the mobs being spawned from this spawner. Defaults to pig, apparently.
     */
    private String mobID;
    private NBTTagCompound field_70391_e;
    public double yaw;
    public double yaw2;
    private int field_70388_f;
    private int field_70389_g;
    private int field_70395_h;
    private Entity field_70396_i;

    public TileEntityMobSpawner()
    {
        delay = -1;
        field_70391_e = null;
        yaw2 = 0.0D;
        field_70388_f = 200;
        field_70389_g = 800;
        field_70395_h = 4;
        mobID = "Pig";
        delay = 20;
    }

    public String getMobID()
    {
        return mobID;
    }

    public void setMobID(String par1Str)
    {
        mobID = par1Str;
    }

    /**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean anyPlayerInRange()
    {
        return worldObj.getClosestPlayer((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D, 16D) != null;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (!anyPlayerInRange())
        {
            return;
        }

        if (worldObj.isRemote)
        {
            double d = (float)xCoord + worldObj.rand.nextFloat();
            double d1 = (float)yCoord + worldObj.rand.nextFloat();
            double d3 = (float)zCoord + worldObj.rand.nextFloat();
            worldObj.spawnParticle("smoke", d, d1, d3, 0.0D, 0.0D, 0.0D);
            worldObj.spawnParticle("flame", d, d1, d3, 0.0D, 0.0D, 0.0D);
            yaw2 = yaw % 360D;
            yaw += 4.5454545021057129D;
        }
        else
        {
            if (delay == -1)
            {
                updateDelay();
            }

            if (delay > 0)
            {
                delay--;
                return;
            }

            for (int i = 0; i < field_70395_h; i++)
            {
                Entity entity = EntityList.createEntityByName(mobID, worldObj);

                if (entity == null)
                {
                    return;
                }

                int j = worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(8D, 4D, 8D)).size();

                if (j >= 6)
                {
                    updateDelay();
                    return;
                }

                if (entity == null)
                {
                    continue;
                }

                double d2 = (double)xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;
                double d4 = (yCoord + worldObj.rand.nextInt(3)) - 1;
                double d5 = (double)zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;
                EntityLiving entityliving = (entity instanceof EntityLiving) ? (EntityLiving)entity : null;
                entity.setLocationAndAngles(d2, d4, d5, worldObj.rand.nextFloat() * 360F, 0.0F);

                if (entityliving != null && !entityliving.getCanSpawnHere())
                {
                    continue;
                }

                func_70383_a(entity);
                worldObj.spawnEntityInWorld(entity);
                worldObj.playAuxSFX(2004, xCoord, yCoord, zCoord, 0);

                if (entityliving != null)
                {
                    entityliving.spawnExplosionParticle();
                }

                updateDelay();
            }
        }

        super.updateEntity();
    }

    public void func_70383_a(Entity par1Entity)
    {
        if (field_70391_e != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            par1Entity.addEntityID(nbttagcompound);
            NBTBase nbtbase;

            for (Iterator iterator = field_70391_e.getTags().iterator(); iterator.hasNext(); nbttagcompound.setTag(nbtbase.getName(), nbtbase.copy()))
            {
                nbtbase = (NBTBase)iterator.next();
            }

            par1Entity.readFromNBT(nbttagcompound);
        }
    }

    /**
     * Sets the delay before a new spawn (base delay of 200 + random number up to 600).
     */
    private void updateDelay()
    {
        delay = field_70388_f + worldObj.rand.nextInt(field_70389_g - field_70388_f);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        mobID = par1NBTTagCompound.getString("EntityId");
        delay = par1NBTTagCompound.getShort("Delay");

        if (par1NBTTagCompound.hasKey("SpawnData"))
        {
            field_70391_e = par1NBTTagCompound.getCompoundTag("SpawnData");
        }
        else
        {
            field_70391_e = null;
        }

        if (par1NBTTagCompound.hasKey("MinSpawnDelay"))
        {
            field_70388_f = par1NBTTagCompound.getShort("MinSpawnDelay");
            field_70389_g = par1NBTTagCompound.getShort("MaxSpawnDelay");
            field_70395_h = par1NBTTagCompound.getShort("SpawnCount");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("EntityId", mobID);
        par1NBTTagCompound.setShort("Delay", (short)delay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)field_70388_f);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)field_70389_g);
        par1NBTTagCompound.setShort("SpawnCount", (short)field_70395_h);

        if (field_70391_e != null)
        {
            par1NBTTagCompound.setCompoundTag("SpawnData", field_70391_e);
        }
    }

    public Entity func_70382_c()
    {
        if (field_70396_i == null)
        {
            Entity entity = EntityList.createEntityByName(getMobID(), null);
            func_70383_a(entity);
            field_70396_i = entity;
        }

        return field_70396_i;
    }

    public Packet func_70319_e()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }
}
