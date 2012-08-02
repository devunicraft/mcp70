package net.minecraft.src;

import java.io.File;
import java.util.*;
import net.minecraft.client.Minecraft;

public class ServerList
{
    private final Minecraft field_78859_a;
    private final List field_78858_b = new ArrayList();

    public ServerList(Minecraft par1Minecraft)
    {
        field_78859_a = par1Minecraft;
        func_78853_a();
    }

    public void func_78853_a()
    {
        try
        {
            NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(field_78859_a.mcDataDir, "servers.dat"));
            NBTTagList nbttaglist = nbttagcompound.getTagList("servers");
            field_78858_b.clear();

            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                field_78858_b.add(ServerData.func_78837_a((NBTTagCompound)nbttaglist.tagAt(i)));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_78855_b()
    {
        try
        {
            NBTTagList nbttaglist = new NBTTagList();
            ServerData serverdata;

            for (Iterator iterator = field_78858_b.iterator(); iterator.hasNext(); nbttaglist.appendTag(serverdata.func_78836_a()))
            {
                serverdata = (ServerData)iterator.next();
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(field_78859_a.mcDataDir, "servers.dat"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public ServerData func_78850_a(int par1)
    {
        return (ServerData)field_78858_b.get(par1);
    }

    public void func_78851_b(int par1)
    {
        field_78858_b.remove(par1);
    }

    public void func_78849_a(ServerData par1ServerData)
    {
        field_78858_b.add(par1ServerData);
    }

    public int func_78856_c()
    {
        return field_78858_b.size();
    }

    public void func_78857_a(int par1, int par2)
    {
        ServerData serverdata = func_78850_a(par1);
        field_78858_b.set(par1, func_78850_a(par2));
        field_78858_b.set(par2, serverdata);
    }

    public void func_78854_a(int par1, ServerData par2ServerData)
    {
        field_78858_b.set(par1, par2ServerData);
    }

    public static void func_78852_b(ServerData par0ServerData)
    {
        ServerList serverlist = new ServerList(Minecraft.getMinecraft());
        serverlist.func_78853_a();
        int i = 0;

        do
        {
            if (i >= serverlist.func_78856_c())
            {
                break;
            }

            ServerData serverdata = serverlist.func_78850_a(i);

            if (serverdata.field_78847_a.equals(par0ServerData.field_78847_a) && serverdata.field_78845_b.equals(par0ServerData.field_78845_b))
            {
                serverlist.func_78854_a(i, par0ServerData);
                break;
            }

            i++;
        }
        while (true);

        serverlist.func_78855_b();
    }
}
