package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;

public class MemoryConnection implements NetworkManager
{
    private static final SocketAddress field_74444_a = new InetSocketAddress("127.0.0.1", 0);
    private final List field_74442_b = Collections.synchronizedList(new ArrayList());
    private MemoryConnection field_74443_c;
    private NetHandler field_74440_d;
    private boolean field_74441_e;
    private String field_74438_f;
    private Object field_74439_g[];
    private boolean field_74445_h;

    public MemoryConnection(NetHandler par1NetHandler) throws IOException
    {
        field_74441_e = false;
        field_74438_f = "";
        field_74445_h = false;
        field_74440_d = par1NetHandler;
    }

    public void setNetHandler(NetHandler par1NetHandler)
    {
        field_74440_d = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (field_74441_e)
        {
            return;
        }
        else
        {
            field_74443_c.func_74436_b(par1Packet);
            return;
        }
    }

    public void func_74431_f()
    {
        field_74443_c = null;
        field_74440_d = null;
    }

    public boolean func_74435_g()
    {
        return !field_74441_e && field_74443_c != null;
    }

    /**
     * Wakes reader and writer threads
     */
    public void wakeThreads()
    {
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        int i;
        Packet packet;

        for (i = 2500; i-- >= 0 && !field_74442_b.isEmpty(); packet.processPacket(field_74440_d))
        {
            packet = (Packet)field_74442_b.remove(0);
        }

        if (field_74442_b.size() > i)
        {
            System.out.println((new StringBuilder()).append("Memory connection overburdened; after processing 2500 packets, we still have ").append(field_74442_b.size()).append(" to go!").toString());
        }

        if (field_74441_e && field_74442_b.isEmpty())
        {
            field_74440_d.handleErrorMessage(field_74438_f, field_74439_g);
        }
    }

    /**
     * Return the InetSocketAddress of the remote endpoint
     */
    public SocketAddress getSocketAddress()
    {
        return field_74444_a;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        field_74441_e = true;
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object par2ArrayOfObj[])
    {
        field_74441_e = true;
        field_74438_f = par1Str;
        field_74439_g = par2ArrayOfObj;
    }

    public int func_74426_e()
    {
        return 0;
    }

    public void func_74434_a(MemoryConnection par1MemoryConnection)
    {
        field_74443_c = par1MemoryConnection;
        par1MemoryConnection.field_74443_c = this;
    }

    public boolean func_74433_h()
    {
        return field_74445_h;
    }

    public void func_74437_a(boolean par1)
    {
        field_74445_h = par1;
    }

    public MemoryConnection func_74432_i()
    {
        return field_74443_c;
    }

    public void func_74436_b(Packet par1Packet)
    {
        if (par1Packet.func_73277_a_() && field_74440_d.func_72469_b())
        {
            par1Packet.processPacket(field_74440_d);
        }
        else
        {
            field_74442_b.add(par1Packet);
        }
    }
}
