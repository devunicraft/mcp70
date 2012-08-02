package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class EntityTracker
{
    private final WorldServer field_72795_a;
    private Set field_72793_b;
    private IntHashMap field_72794_c;
    private int field_72792_d;

    public EntityTracker(WorldServer par1WorldServer)
    {
        field_72793_b = new HashSet();
        field_72794_c = new IntHashMap();
        field_72795_a = par1WorldServer;
        field_72792_d = par1WorldServer.getMinecraftServer().func_71203_ab().func_72372_a();
    }

    public void func_72786_a(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            func_72791_a(par1Entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            Iterator iterator = field_72793_b.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();

                if (entitytrackerentry.field_73132_a != entityplayermp)
                {
                    entitytrackerentry.func_73117_b(entityplayermp);
                }
            }
            while (true);
        }
        else if (par1Entity instanceof EntityFishHook)
        {
            func_72785_a(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow)
        {
            func_72785_a(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball)
        {
            func_72785_a(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball)
        {
            func_72785_a(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball)
        {
            func_72785_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl)
        {
            func_72785_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderEye)
        {
            func_72785_a(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg)
        {
            func_72785_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion)
        {
            func_72785_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityExpBottle)
        {
            func_72785_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem)
        {
            func_72785_a(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart)
        {
            func_72785_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat)
        {
            func_72785_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid)
        {
            func_72785_a(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof IAnimals)
        {
            func_72785_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityDragon)
        {
            func_72785_a(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed)
        {
            func_72785_a(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingSand)
        {
            func_72785_a(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityPainting)
        {
            func_72785_a(par1Entity, 160, 0x7fffffff, false);
        }
        else if (par1Entity instanceof EntityXPOrb)
        {
            func_72785_a(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal)
        {
            func_72785_a(par1Entity, 256, 0x7fffffff, false);
        }
    }

    public void func_72791_a(Entity par1Entity, int par2, int par3)
    {
        func_72785_a(par1Entity, par2, par3, false);
    }

    public void func_72785_a(Entity par1Entity, int par2, int par3, boolean par4)
    {
        if (par2 > field_72792_d)
        {
            par2 = field_72792_d;
        }

        if (field_72794_c.containsItem(par1Entity.entityId))
        {
            throw new IllegalStateException("Entity is already tracked!");
        }
        else
        {
            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            field_72793_b.add(entitytrackerentry);
            field_72794_c.addKey(par1Entity.entityId, entitytrackerentry);
            entitytrackerentry.func_73125_b(field_72795_a.playerEntities);
            return;
        }
    }

    public void func_72790_b(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            EntityTrackerEntry entitytrackerentry1;

            for (Iterator iterator = field_72793_b.iterator(); iterator.hasNext(); entitytrackerentry1.func_73118_a(entityplayermp))
            {
                entitytrackerentry1 = (EntityTrackerEntry)iterator.next();
            }
        }

        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_72794_c.removeObject(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            field_72793_b.remove(entitytrackerentry);
            entitytrackerentry.func_73119_a();
        }
    }

    public void func_72788_a()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_72793_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.func_73122_a(field_72795_a.playerEntities);

            if (entitytrackerentry.field_73133_n && (entitytrackerentry.field_73132_a instanceof EntityPlayerMP))
            {
                arraylist.add((EntityPlayerMP)entitytrackerentry.field_73132_a);
            }
        }
        while (true);

        for (Iterator iterator1 = arraylist.iterator(); iterator1.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator1.next();
            EntityPlayerMP entityplayermp1 = entityplayermp;
            Iterator iterator2 = field_72793_b.iterator();

            while (iterator2.hasNext())
            {
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator2.next();

                if (entitytrackerentry1.field_73132_a != entityplayermp1)
                {
                    entitytrackerentry1.func_73117_b(entityplayermp1);
                }
            }
        }
    }

    public void func_72784_a(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_72794_c.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.func_73120_a(par2Packet);
        }
    }

    public void func_72789_b(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_72794_c.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.func_73116_b(par2Packet);
        }
    }

    public void func_72787_a(EntityPlayerMP par1EntityPlayerMP)
    {
        EntityTrackerEntry entitytrackerentry;

        for (Iterator iterator = field_72793_b.iterator(); iterator.hasNext(); entitytrackerentry.func_73123_c(par1EntityPlayerMP))
        {
            entitytrackerentry = (EntityTrackerEntry)iterator.next();
        }
    }
}
