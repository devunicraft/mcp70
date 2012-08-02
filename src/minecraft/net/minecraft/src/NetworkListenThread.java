package net.minecraft.src;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public abstract class NetworkListenThread
{
    public static Logger field_71751_a = Logger.getLogger("Minecraft");
    private final MinecraftServer field_71750_c;
    private final List field_71748_d = Collections.synchronizedList(new ArrayList());
    public volatile boolean field_71749_b;

    public NetworkListenThread(MinecraftServer par1MinecraftServer) throws IOException
    {
        field_71749_b = false;
        field_71750_c = par1MinecraftServer;
        field_71749_b = true;
    }

    public void func_71745_a(NetServerHandler par1NetServerHandler)
    {
        field_71748_d.add(par1NetServerHandler);
    }

    public void func_71744_a()
    {
        field_71749_b = false;
    }

    public void func_71747_b()
    {
        for (int i = 0; i < field_71748_d.size(); i++)
        {
            NetServerHandler netserverhandler = (NetServerHandler)field_71748_d.get(i);

            try
            {
                netserverhandler.func_72570_d();
            }
            catch (Exception exception)
            {
                field_71751_a.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                netserverhandler.kickPlayerFromServer("Internal server error");
            }

            if (netserverhandler.serverShuttingDown)
            {
                field_71748_d.remove(i--);
            }

            netserverhandler.theNetworkManager.wakeThreads();
        }
    }

    public MinecraftServer func_71746_d()
    {
        return field_71750_c;
    }
}
