package net.minecraft.src;

import java.io.*;

public class Packet204ClientInfo extends Packet
{
    private String language;
    private int renderDistance;
    private int chatVisisble;
    private boolean chatColours;
    private int gameDifficulty;

    public Packet204ClientInfo()
    {
    }

    public Packet204ClientInfo(String par1Str, int par2, int par3, boolean par4, int par5)
    {
        language = par1Str;
        renderDistance = par2;
        chatVisisble = par3;
        chatColours = par4;
        gameDifficulty = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        language = readString(par1DataInputStream, 7);
        renderDistance = par1DataInputStream.readByte();
        byte byte0 = par1DataInputStream.readByte();
        chatVisisble = byte0 & 7;
        chatColours = (byte0 & 8) == 8;
        gameDifficulty = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(language, par1DataOutputStream);
        par1DataOutputStream.writeByte(renderDistance);
        par1DataOutputStream.writeByte(chatVisisble | (chatColours ? 1 : 0) << 3);
        par1DataOutputStream.writeByte(gameDifficulty);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleClientInfo(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 0;
    }

    public String getLanguage()
    {
        return language;
    }

    public int getRenderDistance()
    {
        return renderDistance;
    }

    public int getChatVisibility()
    {
        return chatVisisble;
    }

    public boolean getChatColours()
    {
        return chatColours;
    }

    public int getDifficulty()
    {
        return gameDifficulty;
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean isRealPacket()
    {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean containsSameEntityIDAs(Packet par1Packet)
    {
        return true;
    }
}
