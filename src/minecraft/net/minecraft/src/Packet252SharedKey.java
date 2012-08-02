package net.minecraft.src;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class Packet252SharedKey extends Packet
{
    private byte field_73307_a[];
    private byte field_73305_b[];
    private SecretKey field_73306_c;

    public Packet252SharedKey()
    {
        field_73307_a = new byte[0];
        field_73305_b = new byte[0];
    }

    public Packet252SharedKey(SecretKey par1SecretKey, PublicKey par2PublicKey, byte par3ArrayOfByte[])
    {
        field_73307_a = new byte[0];
        field_73305_b = new byte[0];
        field_73306_c = par1SecretKey;
        field_73307_a = CryptManager.func_75894_a(par2PublicKey, par1SecretKey.getEncoded());
        field_73305_b = CryptManager.func_75894_a(par2PublicKey, par3ArrayOfByte);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_73307_a = func_73280_b(par1DataInputStream);
        field_73305_b = func_73280_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        func_73274_a(par1DataOutputStream, field_73307_a);
        func_73274_a(par1DataOutputStream, field_73305_b);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSharedKey(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_73307_a.length + 2 + field_73305_b.length;
    }

    public SecretKey func_73303_a(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_73306_c;
        }
        else
        {
            return field_73306_c = CryptManager.func_75887_a(par1PrivateKey, field_73307_a);
        }
    }

    public SecretKey func_73304_d()
    {
        return func_73303_a(null);
    }

    public byte[] func_73302_b(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_73305_b;
        }
        else
        {
            return CryptManager.func_75889_b(par1PrivateKey, field_73305_b);
        }
    }
}
