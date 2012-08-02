package net.minecraft.src;

import java.io.*;

public class Packet2ClientProtocol extends Packet
{
    private int protocolVersion;
    private String username;
    private String serverHost;
    private int serverPort;

    public Packet2ClientProtocol()
    {
    }

    public Packet2ClientProtocol(int par1, String par2Str, String par3Str, int par4)
    {
        protocolVersion = par1;
        username = par2Str;
        serverHost = par3Str;
        serverPort = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        protocolVersion = par1DataInputStream.readByte();
        username = readString(par1DataInputStream, 16);
        serverHost = readString(par1DataInputStream, 255);
        serverPort = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(protocolVersion);
        writeString(username, par1DataOutputStream);
        writeString(serverHost, par1DataOutputStream);
        par1DataOutputStream.writeInt(serverPort);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleClientProtocol(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + 2 * username.length();
    }

    public int getProtocolVersion()
    {
        return protocolVersion;
    }

    public String getUsername()
    {
        return username;
    }
}
