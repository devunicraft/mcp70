package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListenThread extends Thread
{
    private static Logger field_71777_a = Logger.getLogger("Minecraft");
    private final List field_71775_b = Collections.synchronizedList(new ArrayList());
    private final HashMap field_71776_c = new HashMap();
    private int field_71773_d;
    private final ServerSocket field_71774_e;
    private NetworkListenThread field_71771_f;
    private final InetAddress field_71772_g;
    private final int field_71778_h;

    public ServerListenThread(NetworkListenThread par1NetworkListenThread, InetAddress par2InetAddress, int par3) throws IOException
    {
        super("Listen thread");
        field_71773_d = 0;
        field_71771_f = par1NetworkListenThread;
        field_71772_g = par2InetAddress;
        field_71778_h = par3;
        field_71774_e = new ServerSocket(par3, 0, par2InetAddress);
        field_71774_e.setPerformancePreferences(0, 2, 1);
    }

    public void func_71766_a()
    {
        synchronized (field_71775_b)
        {
            for (int i = 0; i < field_71775_b.size(); i++)
            {
                NetLoginHandler netloginhandler = (NetLoginHandler)field_71775_b.get(i);

                try
                {
                    netloginhandler.func_72532_c();
                }
                catch (Exception exception)
                {
                    netloginhandler.func_72527_a("Internal server error");
                    field_71777_a.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                }

                if (netloginhandler.field_72539_c)
                {
                    field_71775_b.remove(i--);
                }

                netloginhandler.field_72538_b.wakeThreads();
            }
        }
    }

    public void run()
    {
        do
        {
            if (!field_71771_f.field_71749_b)
            {
                break;
            }

            try
            {
                Socket socket = field_71774_e.accept();
                InetAddress inetaddress = socket.getInetAddress();
                long l = System.currentTimeMillis();

                synchronized (field_71776_c)
                {
                    if (field_71776_c.containsKey(inetaddress) && !func_71770_b(inetaddress) && l - ((Long)field_71776_c.get(inetaddress)).longValue() < 4000L)
                    {
                        field_71776_c.put(inetaddress, Long.valueOf(l));
                        socket.close();
                        continue;
                    }

                    field_71776_c.put(inetaddress, Long.valueOf(l));
                }

                NetLoginHandler netloginhandler = new NetLoginHandler(field_71771_f.func_71746_d(), socket, (new StringBuilder()).append("Connection #").append(field_71773_d++).toString());
                func_71764_a(netloginhandler);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
        while (true);

        System.out.println("Closing listening thread");
    }

    private void func_71764_a(NetLoginHandler par1NetLoginHandler)
    {
        if (par1NetLoginHandler == null)
        {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }

        synchronized (field_71775_b)
        {
            field_71775_b.add(par1NetLoginHandler);
        }
    }

    private static boolean func_71770_b(InetAddress par0InetAddress)
    {
        return "127.0.0.1".equals(par0InetAddress.getHostAddress());
    }

    public void func_71769_a(InetAddress par1InetAddress)
    {
        if (par1InetAddress != null)
        {
            synchronized (field_71776_c)
            {
                field_71776_c.remove(par1InetAddress);
            }
        }
    }

    public void func_71768_b()
    {
        try
        {
            field_71774_e.close();
        }
        catch (Throwable throwable) { }
    }

    public InetAddress func_71767_c()
    {
        return field_71772_g;
    }

    public int func_71765_d()
    {
        return field_71778_h;
    }
}
