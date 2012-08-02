package net.minecraft.src;

import java.io.*;

public class Packet3Chat extends Packet
{
    /** Maximum number of characters allowed in chat string in each packet. */
    public static int maxChatLength = 119;

    /** The message being sent. */
    public String message;
    private boolean field_73477_c;

    public Packet3Chat()
    {
        field_73477_c = true;
    }

    public Packet3Chat(String par1Str)
    {
        this(par1Str, true);
    }

    public Packet3Chat(String par1Str, boolean par2)
    {
        field_73477_c = true;

        if (par1Str.length() > maxChatLength)
        {
            par1Str = par1Str.substring(0, maxChatLength);
        }

        message = par1Str;
        field_73477_c = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        message = readString(par1DataInputStream, maxChatLength);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(message, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleChat(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + message.length() * 2;
    }

    public boolean func_73475_d()
    {
        return field_73477_c;
    }

    public boolean func_73277_a_()
    {
        return !message.startsWith("/");
    }
}
