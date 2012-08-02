package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.security.PrivateKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

public class TcpConnection implements NetworkManager
{
    public static AtomicInteger field_74471_a = new AtomicInteger();
    public static AtomicInteger field_74469_b = new AtomicInteger();

    /** The object used for synchronization on the send queue. */
    private Object sendQueueLock;

    /** The socket used by this network manager. */
    private Socket networkSocket;

    /** The InetSocketAddress of the remote endpoint */
    private final SocketAddress remoteSocketAddress;

    /** The input stream connected to the socket. */
    private volatile DataInputStream socketInputStream;

    /** The output stream connected to the socket. */
    private volatile DataOutputStream socketOutputStream;

    /** Whether the network is currently operational. */
    private volatile boolean isRunning;

    /**
     * Whether this network manager is currently terminating (and should ignore further errors).
     */
    private volatile boolean isTerminating;

    /**
     * Linked list of packets that have been read and are awaiting processing.
     */
    private List readPackets;

    /** Linked list of packets awaiting sending. */
    private List dataPackets;

    /** Linked list of packets with chunk data that are awaiting sending. */
    private List chunkDataPackets;

    /** A reference to the NetHandler object. */
    private NetHandler theNetHandler;

    /**
     * Whether this server is currently terminating. If this is a client, this is always false.
     */
    private boolean isServerTerminating;

    /** The thread used for writing. */
    private Thread writeThread;

    /** The thread used for reading. */
    private Thread readThread;

    /** A String indicating why the network has shutdown. */
    private String terminationReason;
    private Object field_74480_w[];
    private int field_74490_x;

    /**
     * The length in bytes of the packets in both send queues (data and chunkData).
     */
    private int sendQueueByteLength;
    public static int field_74470_c[] = new int[256];
    public static int field_74467_d[] = new int[256];
    public int field_74468_e;
    boolean field_74465_f;
    boolean field_74466_g;
    private SecretKey field_74488_z;
    private PrivateKey field_74463_A;
    private int field_74464_B;

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler) throws IOException
    {
        this(par1Socket, par2Str, par3NetHandler, null);
    }

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler, PrivateKey par4PrivateKey) throws IOException
    {
        sendQueueLock = new Object();
        isRunning = true;
        isTerminating = false;
        readPackets = Collections.synchronizedList(new ArrayList());
        dataPackets = Collections.synchronizedList(new ArrayList());
        chunkDataPackets = Collections.synchronizedList(new ArrayList());
        isServerTerminating = false;
        terminationReason = "";
        field_74490_x = 0;
        sendQueueByteLength = 0;
        field_74468_e = 0;
        field_74465_f = false;
        field_74466_g = false;
        field_74488_z = null;
        field_74463_A = null;
        field_74464_B = 50;
        field_74463_A = par4PrivateKey;
        networkSocket = par1Socket;
        remoteSocketAddress = par1Socket.getRemoteSocketAddress();
        theNetHandler = par3NetHandler;

        try
        {
            par1Socket.setSoTimeout(30000);
            par1Socket.setTrafficClass(24);
        }
        catch (SocketException socketexception)
        {
            System.err.println(socketexception.getMessage());
        }

        socketInputStream = new DataInputStream(par1Socket.getInputStream());
        socketOutputStream = new DataOutputStream(new BufferedOutputStream(par1Socket.getOutputStream(), 5120));
        readThread = new TcpReaderThread(this, (new StringBuilder()).append(par2Str).append(" read thread").toString());
        writeThread = new TcpWriterThread(this, (new StringBuilder()).append(par2Str).append(" write thread").toString());
        readThread.start();
        writeThread.start();
    }

    public void func_74431_f()
    {
        wakeThreads();
        writeThread = null;
        readThread = null;
    }

    public void setNetHandler(NetHandler par1NetHandler)
    {
        theNetHandler = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (isServerTerminating)
        {
            return;
        }

        synchronized (sendQueueLock)
        {
            sendQueueByteLength += par1Packet.getPacketSize() + 1;

            if (par1Packet.isChunkDataPacket)
            {
                chunkDataPackets.add(par1Packet);
            }
            else
            {
                dataPackets.add(par1Packet);
            }
        }
    }

    /**
     * Sends a data packet if there is one to send, or sends a chunk data packet if there is one and the counter is up,
     * or does nothing.
     */
    private boolean sendPacket()
    {
        boolean flag = false;

        try
        {
            if (field_74468_e == 0 || System.currentTimeMillis() - ((Packet)dataPackets.get(0)).creationTimeMillis >= (long)field_74468_e)
            {
                Packet packet = func_74460_a(false);

                if (packet != null)
                {
                    Packet.writePacket(packet, socketOutputStream);

                    if ((packet instanceof Packet252SharedKey) && !field_74466_g)
                    {
                        if (!theNetHandler.isServerHandler())
                        {
                            field_74488_z = ((Packet252SharedKey)packet).func_73304_d();
                        }

                        func_74446_k();
                    }

                    field_74467_d[packet.getPacketId()] += packet.getPacketSize() + 1;
                    flag = true;
                }
            }

            if (field_74464_B-- <= 0 && (field_74468_e == 0 || System.currentTimeMillis() - ((Packet)chunkDataPackets.get(0)).creationTimeMillis >= (long)field_74468_e))
            {
                Packet packet1 = func_74460_a(true);

                if (packet1 != null)
                {
                    Packet.writePacket(packet1, socketOutputStream);
                    field_74467_d[packet1.getPacketId()] += packet1.getPacketSize() + 1;
                    field_74464_B = 0;
                    flag = true;
                }
            }
        }
        catch (Exception exception)
        {
            if (!isTerminating)
            {
                onNetworkError(exception);
            }

            return false;
        }

        return flag;
    }

    private Packet func_74460_a(boolean par1)
    {
        Packet packet = null;
        List list = par1 ? chunkDataPackets : dataPackets;

        synchronized (sendQueueLock)
        {
            do
            {
                if (list.isEmpty() || packet != null)
                {
                    break;
                }

                packet = (Packet)list.remove(0);
                sendQueueByteLength -= packet.getPacketSize() + 1;

                if (func_74454_a(packet, par1))
                {
                    packet = null;
                }
            }
            while (true);
        }

        return packet;
    }

    private boolean func_74454_a(Packet par1Packet, boolean par2)
    {
        if (!par1Packet.isRealPacket())
        {
            return false;
        }

        List list = par2 ? chunkDataPackets : dataPackets;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            Packet packet = (Packet)iterator.next();

            if (packet.getPacketId() == par1Packet.getPacketId())
            {
                return par1Packet.containsSameEntityIDAs(packet);
            }
        }

        return false;
    }

    /**
     * Wakes reader and writer threads
     */
    public void wakeThreads()
    {
        if (readThread != null)
        {
            readThread.interrupt();
        }

        if (writeThread != null)
        {
            writeThread.interrupt();
        }
    }

    /**
     * Reads a single packet from the input stream and adds it to the read queue. If no packet is read, it shuts down
     * the network.
     */
    private boolean readPacket()
    {
        boolean flag = false;

        try
        {
            Packet packet = Packet.readPacket(socketInputStream, theNetHandler.isServerHandler());

            if (packet != null)
            {
                if ((packet instanceof Packet252SharedKey) && !field_74465_f)
                {
                    if (theNetHandler.isServerHandler())
                    {
                        field_74488_z = ((Packet252SharedKey)packet).func_73303_a(field_74463_A);
                    }

                    func_74448_j();
                }

                field_74470_c[packet.getPacketId()] += packet.getPacketSize() + 1;

                if (!isServerTerminating)
                {
                    if (packet.func_73277_a_() && theNetHandler.func_72469_b())
                    {
                        field_74490_x = 0;
                        packet.processPacket(theNetHandler);
                    }
                    else
                    {
                        readPackets.add(packet);
                    }
                }

                flag = true;
            }
            else
            {
                networkShutdown("disconnect.endOfStream", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            if (!isTerminating)
            {
                onNetworkError(exception);
            }

            return false;
        }

        return flag;
    }

    /**
     * Used to report network errors and causes a network shutdown.
     */
    private void onNetworkError(Exception par1Exception)
    {
        par1Exception.printStackTrace();
        networkShutdown("disconnect.genericReason", new Object[]
                {
                    (new StringBuilder()).append("Internal exception: ").append(par1Exception.toString()).toString()
                });
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object par2ArrayOfObj[])
    {
        if (!isRunning)
        {
            return;
        }

        isTerminating = true;
        terminationReason = par1Str;
        field_74480_w = par2ArrayOfObj;
        isRunning = false;
        (new TcpMasterThread(this)).start();

        try
        {
            socketInputStream.close();
            socketInputStream = null;
            socketOutputStream.close();
            socketOutputStream = null;
            networkSocket.close();
            networkSocket = null;
        }
        catch (Throwable throwable) { }
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        if (sendQueueByteLength > 0x200000)
        {
            networkShutdown("disconnect.overflow", new Object[0]);
        }

        if (readPackets.isEmpty())
        {
            if (field_74490_x++ == 1200)
            {
                networkShutdown("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            field_74490_x = 0;
        }

        Packet packet;

        for (int i = 1000; !readPackets.isEmpty() && i-- >= 0; packet.processPacket(theNetHandler))
        {
            packet = (Packet)readPackets.remove(0);
        }

        wakeThreads();

        if (isTerminating && readPackets.isEmpty())
        {
            theNetHandler.handleErrorMessage(terminationReason, field_74480_w);
        }
    }

    /**
     * Return the InetSocketAddress of the remote endpoint
     */
    public SocketAddress getSocketAddress()
    {
        return remoteSocketAddress;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        if (isServerTerminating)
        {
            return;
        }
        else
        {
            wakeThreads();
            isServerTerminating = true;
            readThread.interrupt();
            (new TcpMonitorThread(this)).start();
            return;
        }
    }

    private void func_74448_j() throws IOException
    {
        field_74465_f = true;
        socketInputStream = new DataInputStream(CryptManager.func_75888_a(field_74488_z, networkSocket.getInputStream()));
    }

    private void func_74446_k() throws IOException
    {
        socketOutputStream.flush();
        field_74466_g = true;
        socketOutputStream = new DataOutputStream(new BufferedOutputStream(CryptManager.func_75897_a(field_74488_z, networkSocket.getOutputStream()), 5120));
    }

    public int func_74426_e()
    {
        return chunkDataPackets.size();
    }

    public Socket func_74452_g()
    {
        return networkSocket;
    }

    /**
     * Whether the network is operational.
     */
    static boolean isRunning(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isRunning;
    }

    /**
     * Is the server terminating? Client side aways returns false.
     */
    static boolean isServerTerminating(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isServerTerminating;
    }

    /**
     * Static accessor to readPacket.
     */
    static boolean readNetworkPacket(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.readPacket();
    }

    /**
     * Static accessor to sendPacket.
     */
    static boolean sendNetworkPacket(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.sendPacket();
    }

    static DataOutputStream getOutputStream(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.socketOutputStream;
    }

    /**
     * Gets whether the Network manager is terminating.
     */
    static boolean isTerminating(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isTerminating;
    }

    /**
     * Sends the network manager an error
     */
    static void sendError(TcpConnection par0TcpConnection, Exception par1Exception)
    {
        par0TcpConnection.onNetworkError(par1Exception);
    }

    /**
     * Returns the read thread.
     */
    static Thread getReadThread(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.readThread;
    }

    /**
     * Returns the write thread.
     */
    static Thread getWriteThread(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.writeThread;
    }
}
