package net.minecraft.src;

import java.io.*;
import java.security.PublicKey;

public class Packet253ServerAuthData extends Packet
{
    private String field_73381_a;
    private PublicKey field_73379_b;
    private byte field_73380_c[];

    public Packet253ServerAuthData()
    {
        field_73380_c = new byte[0];
    }

    public Packet253ServerAuthData(String par1Str, PublicKey par2PublicKey, byte par3ArrayOfByte[])
    {
        field_73380_c = new byte[0];
        field_73381_a = par1Str;
        field_73379_b = par2PublicKey;
        field_73380_c = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_73381_a = readString(par1DataInputStream, 20);
        field_73379_b = CryptManager.func_75896_a(func_73280_b(par1DataInputStream));
        field_73380_c = func_73280_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_73381_a, par1DataOutputStream);
        func_73274_a(par1DataOutputStream, field_73379_b.getEncoded());
        func_73274_a(par1DataOutputStream, field_73380_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleServerAuthData(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_73381_a.length() * 2 + 2 + field_73379_b.getEncoded().length + 2 + field_73380_c.length;
    }

    public String func_73377_d()
    {
        return field_73381_a;
    }

    public PublicKey func_73376_f()
    {
        return field_73379_b;
    }

    public byte[] func_73378_g()
    {
        return field_73380_c;
    }
}
