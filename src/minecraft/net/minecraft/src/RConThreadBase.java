package net.minecraft.src;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.*;

public abstract class RConThreadBase implements Runnable
{
    protected boolean field_72619_a;
    protected IServer field_72617_b;
    protected Thread field_72618_c;
    protected int field_72615_d;
    protected List field_72616_e;
    protected List field_72614_f;

    RConThreadBase(IServer par1IServer)
    {
        field_72619_a = false;
        field_72615_d = 5;
        field_72616_e = new ArrayList();
        field_72614_f = new ArrayList();
        field_72617_b = par1IServer;

        if (field_72617_b.func_71239_B())
        {
            func_72606_c("Debugging is enabled, performance maybe reduced!");
        }
    }

    public synchronized void func_72602_a()
    {
        field_72618_c = new Thread(this);
        field_72618_c.start();
        field_72619_a = true;
    }

    public boolean func_72613_c()
    {
        return field_72619_a;
    }

    protected void func_72607_a(String par1Str)
    {
        field_72617_b.func_71198_k(par1Str);
    }

    protected void func_72609_b(String par1Str)
    {
        field_72617_b.func_71244_g(par1Str);
    }

    protected void func_72606_c(String par1Str)
    {
        field_72617_b.func_71236_h(par1Str);
    }

    protected void func_72610_d(String par1Str)
    {
        field_72617_b.func_71201_j(par1Str);
    }

    protected int func_72603_d()
    {
        return field_72617_b.func_71233_x();
    }

    protected void func_72601_a(DatagramSocket par1DatagramSocket)
    {
        func_72607_a((new StringBuilder()).append("registerSocket: ").append(par1DatagramSocket).toString());
        field_72616_e.add(par1DatagramSocket);
    }

    protected boolean func_72604_a(DatagramSocket par1DatagramSocket, boolean par2)
    {
        func_72607_a((new StringBuilder()).append("closeSocket: ").append(par1DatagramSocket).toString());

        if (null == par1DatagramSocket)
        {
            return false;
        }

        boolean flag = false;

        if (!par1DatagramSocket.isClosed())
        {
            par1DatagramSocket.close();
            flag = true;
        }

        if (par2)
        {
            field_72616_e.remove(par1DatagramSocket);
        }

        return flag;
    }

    protected boolean func_72608_b(ServerSocket par1ServerSocket)
    {
        return func_72605_a(par1ServerSocket, true);
    }

    protected boolean func_72605_a(ServerSocket par1ServerSocket, boolean par2)
    {
        func_72607_a((new StringBuilder()).append("closeSocket: ").append(par1ServerSocket).toString());

        if (null == par1ServerSocket)
        {
            return false;
        }

        boolean flag = false;

        try
        {
            if (!par1ServerSocket.isClosed())
            {
                par1ServerSocket.close();
                flag = true;
            }
        }
        catch (IOException ioexception)
        {
            func_72606_c((new StringBuilder()).append("IO: ").append(ioexception.getMessage()).toString());
        }

        if (par2)
        {
            field_72614_f.remove(par1ServerSocket);
        }

        return flag;
    }

    protected void func_72611_e()
    {
        func_72612_a(false);
    }

    protected void func_72612_a(boolean par1)
    {
        int i = 0;
        Iterator iterator = field_72616_e.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            DatagramSocket datagramsocket = (DatagramSocket)iterator.next();

            if (func_72604_a(datagramsocket, false))
            {
                i++;
            }
        }
        while (true);

        field_72616_e.clear();
        iterator = field_72614_f.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ServerSocket serversocket = (ServerSocket)iterator.next();

            if (func_72605_a(serversocket, false))
            {
                i++;
            }
        }
        while (true);

        field_72614_f.clear();

        if (par1 && 0 < i)
        {
            func_72606_c((new StringBuilder()).append("Force closed ").append(i).append(" sockets").toString());
        }
    }
}
