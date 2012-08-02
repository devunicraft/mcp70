package net.minecraft.src;

import java.net.SocketAddress;

public interface NetworkManager
{
    public abstract void setNetHandler(NetHandler nethandler);

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public abstract void addToSendQueue(Packet packet);

    /**
     * Wakes reader and writer threads
     */
    public abstract void wakeThreads();

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public abstract void processReadPackets();

    /**
     * Return the InetSocketAddress of the remote endpoint
     */
    public abstract SocketAddress getSocketAddress();

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public abstract void serverShutdown();

    public abstract int func_74426_e();

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public abstract void networkShutdown(String s, Object aobj[]);

    public abstract void func_74431_f();
}
