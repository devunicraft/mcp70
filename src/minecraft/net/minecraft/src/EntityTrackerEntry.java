package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;

public class EntityTrackerEntry
{
    public Entity field_73132_a;
    public int field_73130_b;
    public int field_73131_c;
    public int field_73128_d;
    public int field_73129_e;
    public int field_73126_f;
    public int field_73127_g;
    public int field_73139_h;
    public int field_73140_i;
    public double field_73137_j;
    public double field_73138_k;
    public double field_73135_l;
    public int field_73136_m;
    private double field_73147_p;
    private double field_73146_q;
    private double field_73145_r;
    private boolean field_73144_s;
    private boolean field_73143_t;
    private int field_73142_u;
    private Entity field_73141_v;
    public boolean field_73133_n;
    public Set field_73134_o;

    public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
    {
        field_73136_m = 0;
        field_73144_s = false;
        field_73142_u = 0;
        field_73133_n = false;
        field_73134_o = new HashSet();
        field_73132_a = par1Entity;
        field_73130_b = par2;
        field_73131_c = par3;
        field_73143_t = par4;
        field_73128_d = MathHelper.floor_double(par1Entity.posX * 32D);
        field_73129_e = MathHelper.floor_double(par1Entity.posY * 32D);
        field_73126_f = MathHelper.floor_double(par1Entity.posZ * 32D);
        field_73127_g = MathHelper.floor_float((par1Entity.rotationYaw * 256F) / 360F);
        field_73139_h = MathHelper.floor_float((par1Entity.rotationPitch * 256F) / 360F);
        field_73140_i = MathHelper.floor_float((par1Entity.func_70079_am() * 256F) / 360F);
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj instanceof EntityTrackerEntry)
        {
            return ((EntityTrackerEntry)par1Obj).field_73132_a.entityId == field_73132_a.entityId;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_73132_a.entityId;
    }

    public void func_73122_a(List par1List)
    {
        field_73133_n = false;

        if (!field_73144_s || field_73132_a.getDistanceSq(field_73147_p, field_73146_q, field_73145_r) > 16D)
        {
            field_73147_p = field_73132_a.posX;
            field_73146_q = field_73132_a.posY;
            field_73145_r = field_73132_a.posZ;
            field_73144_s = true;
            field_73133_n = true;
            func_73125_b(par1List);
        }

        if (field_73141_v != field_73132_a.ridingEntity)
        {
            field_73141_v = field_73132_a.ridingEntity;
            func_73120_a(new Packet39AttachEntity(field_73132_a, field_73132_a.ridingEntity));
        }

        if (field_73132_a.ridingEntity == null)
        {
            field_73142_u++;

            if (field_73136_m++ % field_73131_c == 0 || field_73132_a.isAirBorne)
            {
                int i = field_73132_a.field_70168_am.func_75630_a(field_73132_a.posX);
                int j = MathHelper.floor_double(field_73132_a.posY * 32D);
                int k = field_73132_a.field_70168_am.func_75630_a(field_73132_a.posZ);
                int l = MathHelper.floor_float((field_73132_a.rotationYaw * 256F) / 360F);
                int i1 = MathHelper.floor_float((field_73132_a.rotationPitch * 256F) / 360F);
                int j1 = i - field_73128_d;
                int k1 = j - field_73129_e;
                int l1 = k - field_73126_f;
                Object obj = null;
                boolean flag = Math.abs(j1) >= 4 || Math.abs(k1) >= 4 || Math.abs(l1) >= 4;
                boolean flag1 = Math.abs(l - field_73127_g) >= 4 || Math.abs(i1 - field_73139_h) >= 4;

                if (j1 < -128 || j1 >= 128 || k1 < -128 || k1 >= 128 || l1 < -128 || l1 >= 128 || field_73142_u > 400)
                {
                    field_73142_u = 0;
                    obj = new Packet34EntityTeleport(field_73132_a.entityId, i, j, k, (byte)l, (byte)i1);
                }
                else if (flag && flag1)
                {
                    obj = new Packet33RelEntityMoveLook(field_73132_a.entityId, (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
                }
                else if (flag)
                {
                    obj = new Packet31RelEntityMove(field_73132_a.entityId, (byte)j1, (byte)k1, (byte)l1);
                }
                else if (flag1)
                {
                    obj = new Packet32EntityLook(field_73132_a.entityId, (byte)l, (byte)i1);
                }

                if (field_73143_t)
                {
                    double d = field_73132_a.motionX - field_73137_j;
                    double d1 = field_73132_a.motionY - field_73138_k;
                    double d2 = field_73132_a.motionZ - field_73135_l;
                    double d3 = 0.02D;
                    double d4 = d * d + d1 * d1 + d2 * d2;

                    if (d4 > d3 * d3 || d4 > 0.0D && field_73132_a.motionX == 0.0D && field_73132_a.motionY == 0.0D && field_73132_a.motionZ == 0.0D)
                    {
                        field_73137_j = field_73132_a.motionX;
                        field_73138_k = field_73132_a.motionY;
                        field_73135_l = field_73132_a.motionZ;
                        func_73120_a(new Packet28EntityVelocity(field_73132_a.entityId, field_73137_j, field_73138_k, field_73135_l));
                    }
                }

                if (obj != null)
                {
                    func_73120_a(((Packet)(obj)));
                }

                DataWatcher datawatcher = field_73132_a.getDataWatcher();

                if (datawatcher.func_75684_a())
                {
                    func_73116_b(new Packet40EntityMetadata(field_73132_a.entityId, datawatcher));
                }

                int i2 = MathHelper.floor_float((field_73132_a.func_70079_am() * 256F) / 360F);

                if (Math.abs(i2 - field_73140_i) >= 4)
                {
                    func_73120_a(new Packet35EntityHeadRotation(field_73132_a.entityId, (byte)i2));
                    field_73140_i = i2;
                }

                if (flag)
                {
                    field_73128_d = i;
                    field_73129_e = j;
                    field_73126_f = k;
                }

                if (flag1)
                {
                    field_73127_g = l;
                    field_73139_h = i1;
                }
            }

            field_73132_a.isAirBorne = false;
        }

        if (field_73132_a.velocityChanged)
        {
            func_73116_b(new Packet28EntityVelocity(field_73132_a));
            field_73132_a.velocityChanged = false;
        }
    }

    public void func_73120_a(Packet par1Packet)
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_73134_o.iterator(); iterator.hasNext(); entityplayermp.serverForThisPlayer.sendPacketToPlayer(par1Packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_73116_b(Packet par1Packet)
    {
        func_73120_a(par1Packet);

        if (field_73132_a instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)field_73132_a).serverForThisPlayer.sendPacketToPlayer(par1Packet);
        }
    }

    public void func_73119_a()
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_73134_o.iterator(); iterator.hasNext(); entityplayermp.destroyedItemsNetCache.add(Integer.valueOf(field_73132_a.entityId)))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_73118_a(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_73134_o.contains(par1EntityPlayerMP))
        {
            field_73134_o.remove(par1EntityPlayerMP);
        }
    }

    public void func_73117_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP == field_73132_a)
        {
            return;
        }

        double d = par1EntityPlayerMP.posX - (double)(field_73128_d / 32);
        double d1 = par1EntityPlayerMP.posZ - (double)(field_73126_f / 32);

        if (d >= (double)(-field_73130_b) && d <= (double)field_73130_b && d1 >= (double)(-field_73130_b) && d1 <= (double)field_73130_b)
        {
            if (!field_73134_o.contains(par1EntityPlayerMP) && func_73121_d(par1EntityPlayerMP))
            {
                field_73134_o.add(par1EntityPlayerMP);
                Packet packet = func_73124_b();
                par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(packet);
                field_73137_j = field_73132_a.motionX;
                field_73138_k = field_73132_a.motionY;
                field_73135_l = field_73132_a.motionZ;

                if (field_73143_t && !(packet instanceof Packet24MobSpawn))
                {
                    par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet28EntityVelocity(field_73132_a.entityId, field_73132_a.motionX, field_73132_a.motionY, field_73132_a.motionZ));
                }

                if (field_73132_a.ridingEntity != null)
                {
                    par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet39AttachEntity(field_73132_a, field_73132_a.ridingEntity));
                }

                ItemStack aitemstack[] = field_73132_a.func_70035_c();

                if (aitemstack != null)
                {
                    for (int i = 0; i < aitemstack.length; i++)
                    {
                        par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet5PlayerInventory(field_73132_a.entityId, i, aitemstack[i]));
                    }
                }

                if (field_73132_a instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)field_73132_a;

                    if (entityplayer.isPlayerSleeping())
                    {
                        par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet17Sleep(field_73132_a, 0, MathHelper.floor_double(field_73132_a.posX), MathHelper.floor_double(field_73132_a.posY), MathHelper.floor_double(field_73132_a.posZ)));
                    }
                }

                if (field_73132_a instanceof EntityLiving)
                {
                    EntityLiving entityliving = (EntityLiving)field_73132_a;
                    PotionEffect potioneffect;

                    for (Iterator iterator = entityliving.getActivePotionEffects().iterator(); iterator.hasNext(); par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet41EntityEffect(field_73132_a.entityId, potioneffect)))
                    {
                        potioneffect = (PotionEffect)iterator.next();
                    }
                }
            }
        }
        else if (field_73134_o.contains(par1EntityPlayerMP))
        {
            field_73134_o.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(field_73132_a.entityId));
        }
    }

    private boolean func_73121_d(EntityPlayerMP par1EntityPlayerMP)
    {
        return par1EntityPlayerMP.getServerForPlayer().getPlayerManager().func_72694_a(par1EntityPlayerMP, field_73132_a.chunkCoordX, field_73132_a.chunkCoordZ);
    }

    public void func_73125_b(List par1List)
    {
        EntityPlayer entityplayer;

        for (Iterator iterator = par1List.iterator(); iterator.hasNext(); func_73117_b((EntityPlayerMP)entityplayer))
        {
            entityplayer = (EntityPlayer)iterator.next();
        }
    }

    private Packet func_73124_b()
    {
        if (field_73132_a.isDead)
        {
            System.out.println("Fetching addPacket for removed entity");
        }

        if (field_73132_a instanceof EntityItem)
        {
            EntityItem entityitem = (EntityItem)field_73132_a;
            Packet21PickupSpawn packet21pickupspawn = new Packet21PickupSpawn(entityitem);
            entityitem.posX = (double)packet21pickupspawn.xPosition / 32D;
            entityitem.posY = (double)packet21pickupspawn.yPosition / 32D;
            entityitem.posZ = (double)packet21pickupspawn.zPosition / 32D;
            return packet21pickupspawn;
        }

        if (field_73132_a instanceof EntityPlayerMP)
        {
            return new Packet20NamedEntitySpawn((EntityPlayer)field_73132_a);
        }

        if (field_73132_a instanceof EntityMinecart)
        {
            EntityMinecart entityminecart = (EntityMinecart)field_73132_a;

            if (entityminecart.minecartType == 0)
            {
                return new Packet23VehicleSpawn(field_73132_a, 10);
            }

            if (entityminecart.minecartType == 1)
            {
                return new Packet23VehicleSpawn(field_73132_a, 11);
            }

            if (entityminecart.minecartType == 2)
            {
                return new Packet23VehicleSpawn(field_73132_a, 12);
            }
        }

        if (field_73132_a instanceof EntityBoat)
        {
            return new Packet23VehicleSpawn(field_73132_a, 1);
        }

        if ((field_73132_a instanceof IAnimals) || (field_73132_a instanceof EntityDragon))
        {
            field_73140_i = MathHelper.floor_float((field_73132_a.func_70079_am() * 256F) / 360F);
            return new Packet24MobSpawn((EntityLiving)field_73132_a);
        }

        if (field_73132_a instanceof EntityFishHook)
        {
            EntityPlayer entityplayer = ((EntityFishHook)field_73132_a).angler;
            return new Packet23VehicleSpawn(field_73132_a, 90, entityplayer == null ? field_73132_a.entityId : ((Entity)(entityplayer)).entityId);
        }

        if (field_73132_a instanceof EntityArrow)
        {
            Entity entity = ((EntityArrow)field_73132_a).shootingEntity;
            return new Packet23VehicleSpawn(field_73132_a, 60, entity == null ? field_73132_a.entityId : entity.entityId);
        }

        if (field_73132_a instanceof EntitySnowball)
        {
            return new Packet23VehicleSpawn(field_73132_a, 61);
        }

        if (field_73132_a instanceof EntityPotion)
        {
            return new Packet23VehicleSpawn(field_73132_a, 73, ((EntityPotion)field_73132_a).getPotionDamage());
        }

        if (field_73132_a instanceof EntityExpBottle)
        {
            return new Packet23VehicleSpawn(field_73132_a, 75);
        }

        if (field_73132_a instanceof EntityEnderPearl)
        {
            return new Packet23VehicleSpawn(field_73132_a, 65);
        }

        if (field_73132_a instanceof EntityEnderEye)
        {
            return new Packet23VehicleSpawn(field_73132_a, 72);
        }

        if (field_73132_a instanceof EntitySmallFireball)
        {
            EntitySmallFireball entitysmallfireball = (EntitySmallFireball)field_73132_a;
            Packet23VehicleSpawn packet23vehiclespawn = null;

            if (entitysmallfireball.shootingEntity != null)
            {
                packet23vehiclespawn = new Packet23VehicleSpawn(field_73132_a, 64, entitysmallfireball.shootingEntity.entityId);
            }
            else
            {
                packet23vehiclespawn = new Packet23VehicleSpawn(field_73132_a, 64, 0);
            }

            packet23vehiclespawn.speedX = (int)(entitysmallfireball.accelerationX * 8000D);
            packet23vehiclespawn.speedY = (int)(entitysmallfireball.accelerationY * 8000D);
            packet23vehiclespawn.speedZ = (int)(entitysmallfireball.accelerationZ * 8000D);
            return packet23vehiclespawn;
        }

        if (field_73132_a instanceof EntityFireball)
        {
            EntityFireball entityfireball = (EntityFireball)field_73132_a;
            Packet23VehicleSpawn packet23vehiclespawn1 = null;

            if (entityfireball.shootingEntity != null)
            {
                packet23vehiclespawn1 = new Packet23VehicleSpawn(field_73132_a, 63, ((EntityFireball)field_73132_a).shootingEntity.entityId);
            }
            else
            {
                packet23vehiclespawn1 = new Packet23VehicleSpawn(field_73132_a, 63, 0);
            }

            packet23vehiclespawn1.speedX = (int)(entityfireball.accelerationX * 8000D);
            packet23vehiclespawn1.speedY = (int)(entityfireball.accelerationY * 8000D);
            packet23vehiclespawn1.speedZ = (int)(entityfireball.accelerationZ * 8000D);
            return packet23vehiclespawn1;
        }

        if (field_73132_a instanceof EntityEgg)
        {
            return new Packet23VehicleSpawn(field_73132_a, 62);
        }

        if (field_73132_a instanceof EntityTNTPrimed)
        {
            return new Packet23VehicleSpawn(field_73132_a, 50);
        }

        if (field_73132_a instanceof EntityEnderCrystal)
        {
            return new Packet23VehicleSpawn(field_73132_a, 51);
        }

        if (field_73132_a instanceof EntityFallingSand)
        {
            EntityFallingSand entityfallingsand = (EntityFallingSand)field_73132_a;
            return new Packet23VehicleSpawn(field_73132_a, 70, entityfallingsand.blockID | entityfallingsand.field_70285_b << 16);
        }

        if (field_73132_a instanceof EntityPainting)
        {
            return new Packet25EntityPainting((EntityPainting)field_73132_a);
        }

        if (field_73132_a instanceof EntityXPOrb)
        {
            return new Packet26EntityExpOrb((EntityXPOrb)field_73132_a);
        }
        else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Don't know how to add ").append(field_73132_a.getClass()).append("!").toString());
        }
    }

    public void func_73123_c(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_73134_o.contains(par1EntityPlayerMP))
        {
            field_73134_o.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(field_73132_a.entityId));
        }
    }
}
