package net.minecraft.src;

import java.io.*;
import java.util.List;
import java.util.zip.*;

public class Packet56MapChunks extends Packet
{
    private int field_73589_c[];
    private int field_73586_d[];
    public int field_73590_a[];
    public int field_73588_b[];
    private byte field_73587_e[];
    private byte field_73584_f[][];
    private int field_73585_g;
    private static byte field_73591_h[] = new byte[0];

    public Packet56MapChunks()
    {
    }

    public Packet56MapChunks(List par1List)
    {
        int i = par1List.size();
        field_73589_c = new int[i];
        field_73586_d = new int[i];
        field_73590_a = new int[i];
        field_73588_b = new int[i];
        field_73584_f = new byte[i][];
        int j = 0;

        for (int k = 0; k < i; k++)
        {
            Chunk chunk = (Chunk)par1List.get(k);
            Packet51MapChunkData packet51mapchunkdata = Packet51MapChunk.func_73594_a(chunk, true, 65535);

            if (field_73591_h.length < j + packet51mapchunkdata.field_74582_a.length)
            {
                byte abyte0[] = new byte[j + packet51mapchunkdata.field_74582_a.length];
                System.arraycopy(field_73591_h, 0, abyte0, 0, field_73591_h.length);
                field_73591_h = abyte0;
            }

            System.arraycopy(packet51mapchunkdata.field_74582_a, 0, field_73591_h, j, packet51mapchunkdata.field_74582_a.length);
            j += packet51mapchunkdata.field_74582_a.length;
            field_73589_c[k] = chunk.xPosition;
            field_73586_d[k] = chunk.zPosition;
            field_73590_a[k] = packet51mapchunkdata.field_74580_b;
            field_73588_b[k] = packet51mapchunkdata.field_74581_c;
            field_73584_f[k] = packet51mapchunkdata.field_74582_a;
        }

        Deflater deflater = new Deflater(-1);

        try
        {
            deflater.setInput(field_73591_h, 0, j);
            deflater.finish();
            field_73587_e = new byte[j];
            field_73585_g = deflater.deflate(field_73587_e);
        }
        finally
        {
            deflater.end();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        short word0 = par1DataInputStream.readShort();
        field_73585_g = par1DataInputStream.readInt();
        field_73589_c = new int[word0];
        field_73586_d = new int[word0];
        field_73590_a = new int[word0];
        field_73588_b = new int[word0];
        field_73584_f = new byte[word0][];

        if (field_73591_h.length < field_73585_g)
        {
            field_73591_h = new byte[field_73585_g];
        }

        par1DataInputStream.readFully(field_73591_h, 0, field_73585_g);
        byte abyte0[] = new byte[0x30100 * word0];
        Inflater inflater = new Inflater();
        inflater.setInput(field_73591_h, 0, field_73585_g);

        try
        {
            inflater.inflate(abyte0);
        }
        catch (DataFormatException dataformatexception)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            inflater.end();
        }

        int i = 0;

        for (int j = 0; j < word0; j++)
        {
            field_73589_c[j] = par1DataInputStream.readInt();
            field_73586_d[j] = par1DataInputStream.readInt();
            field_73590_a[j] = par1DataInputStream.readShort();
            field_73588_b[j] = par1DataInputStream.readShort();
            int k = 0;

            for (int l = 0; l < 16; l++)
            {
                k += field_73590_a[j] >> l & 1;
            }

            int i1 = 2048 * (5 * k) + 256;
            field_73584_f[j] = new byte[i1];
            System.arraycopy(abyte0, i, field_73584_f[j], 0, i1);
            i += i1;
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(field_73589_c.length);
        par1DataOutputStream.writeInt(field_73585_g);
        par1DataOutputStream.write(field_73587_e, 0, field_73585_g);

        for (int i = 0; i < field_73589_c.length; i++)
        {
            par1DataOutputStream.writeInt(field_73589_c[i]);
            par1DataOutputStream.writeInt(field_73586_d[i]);
            par1DataOutputStream.writeShort((short)(field_73590_a[i] & 0xffff));
            par1DataOutputStream.writeShort((short)(field_73588_b[i] & 0xffff));
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMapChunks(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 6 + field_73585_g + 12 * func_73581_d();
    }

    public int func_73582_a(int par1)
    {
        return field_73589_c[par1];
    }

    public int func_73580_b(int par1)
    {
        return field_73586_d[par1];
    }

    public int func_73581_d()
    {
        return field_73589_c.length;
    }

    public byte[] func_73583_c(int par1)
    {
        return field_73584_f[par1];
    }

    public boolean func_73277_a_()
    {
        return true;
    }
}
