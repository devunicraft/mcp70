package net.minecraft.src;

import java.io.*;

public class Packet203AutoComplete extends Packet
{
    /**
     * Sent by the client containing the text to be autocompleted. Sent by the server with possible completions
     * separated by null (two bytes in UTF-16)
     */
    private String text;

    public Packet203AutoComplete()
    {
    }

    public Packet203AutoComplete(String par1Str)
    {
        text = par1Str;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        text = readString(par1DataInputStream, Packet3Chat.maxChatLength);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(text, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_72461_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + text.length() * 2;
    }

    public String func_73473_d()
    {
        return text;
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