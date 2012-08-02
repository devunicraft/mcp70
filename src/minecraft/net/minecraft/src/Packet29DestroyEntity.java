package net.minecraft.src;

import java.io.*;

public class Packet29DestroyEntity extends Packet
{
    public int entityId[];

    public Packet29DestroyEntity()
    {
    }

    public Packet29DestroyEntity(int par1ArrayOfInteger[])
    {
        entityId = par1ArrayOfInteger;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = new int[par1DataInputStream.readByte()];

        for (int i = 0; i < entityId.length; i++)
        {
            entityId[i] = par1DataInputStream.readInt();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(entityId.length);

        for (int i = 0; i < entityId.length; i++)
        {
            par1DataOutputStream.writeInt(entityId[i]);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleDestroyEntity(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 1 + entityId.length * 4;
    }
}
