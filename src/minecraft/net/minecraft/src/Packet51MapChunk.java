package net.minecraft.src;

import java.io.*;
import java.util.zip.*;

public class Packet51MapChunk extends Packet
{
    /** The x-position of the transmitted chunk, in chunk coordinates. */
    public int xCh;

    /** The z-position of the transmitted chunk, in chunk coordinates. */
    public int zCh;

    /**
     * The y-position of the lowest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int yChMin;

    /**
     * The y-position of the highest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int yChMax;
    private byte chunkData[];
    private byte field_73596_g[];

    /**
     * Whether to initialize the Chunk before applying the effect of the Packet51MapChunk.
     */
    public boolean includeInitialize;

    /** The length of the compressed chunk data byte array. */
    private int tempLength;
    private static byte temp[] = new byte[0x30100];

    public Packet51MapChunk()
    {
        isChunkDataPacket = true;
    }

    public Packet51MapChunk(Chunk par1Chunk, boolean par2, int par3)
    {
        isChunkDataPacket = true;
        xCh = par1Chunk.xPosition;
        zCh = par1Chunk.zPosition;
        includeInitialize = par2;
        Packet51MapChunkData packet51mapchunkdata = func_73594_a(par1Chunk, par2, par3);
        Deflater deflater = new Deflater(-1);
        yChMax = packet51mapchunkdata.field_74581_c;
        yChMin = packet51mapchunkdata.field_74580_b;

        try
        {
            field_73596_g = packet51mapchunkdata.field_74582_a;
            deflater.setInput(packet51mapchunkdata.field_74582_a, 0, packet51mapchunkdata.field_74582_a.length);
            deflater.finish();
            chunkData = new byte[packet51mapchunkdata.field_74582_a.length];
            tempLength = deflater.deflate(chunkData);
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
        xCh = par1DataInputStream.readInt();
        zCh = par1DataInputStream.readInt();
        includeInitialize = par1DataInputStream.readBoolean();
        yChMin = par1DataInputStream.readShort();
        yChMax = par1DataInputStream.readShort();
        tempLength = par1DataInputStream.readInt();

        if (temp.length < tempLength)
        {
            temp = new byte[tempLength];
        }

        par1DataInputStream.readFully(temp, 0, tempLength);
        int i = 0;

        for (int j = 0; j < 16; j++)
        {
            i += yChMin >> j & 1;
        }

        int k = 12288 * i;

        if (includeInitialize)
        {
            k += 256;
        }

        field_73596_g = new byte[k];
        Inflater inflater = new Inflater();
        inflater.setInput(temp, 0, tempLength);

        try
        {
            inflater.inflate(field_73596_g);
        }
        catch (DataFormatException dataformatexception)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            inflater.end();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xCh);
        par1DataOutputStream.writeInt(zCh);
        par1DataOutputStream.writeBoolean(includeInitialize);
        par1DataOutputStream.writeShort((short)(yChMin & 0xffff));
        par1DataOutputStream.writeShort((short)(yChMax & 0xffff));
        par1DataOutputStream.writeInt(tempLength);
        par1DataOutputStream.write(chunkData, 0, tempLength);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMapChunk(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 17 + tempLength;
    }

    public byte[] func_73593_d()
    {
        return field_73596_g;
    }

    public static Packet51MapChunkData func_73594_a(Chunk par0Chunk, boolean par1, int par2)
    {
        int i = 0;
        ExtendedBlockStorage aextendedblockstorage[] = par0Chunk.getBlockStorageArray();
        int j = 0;
        Packet51MapChunkData packet51mapchunkdata = new Packet51MapChunkData();
        byte abyte0[] = temp;

        if (par1)
        {
            par0Chunk.deferRender = true;
        }

        for (int k = 0; k < aextendedblockstorage.length; k++)
        {
            if (aextendedblockstorage[k] == null || par1 && aextendedblockstorage[k].isEmpty() || (par2 & 1 << k) == 0)
            {
                continue;
            }

            packet51mapchunkdata.field_74580_b |= 1 << k;

            if (aextendedblockstorage[k].getBlockMSBArray() != null)
            {
                packet51mapchunkdata.field_74581_c |= 1 << k;
                j++;
            }
        }

        for (int l = 0; l < aextendedblockstorage.length; l++)
        {
            if (aextendedblockstorage[l] != null && (!par1 || !aextendedblockstorage[l].isEmpty()) && (par2 & 1 << l) != 0)
            {
                byte abyte2[] = aextendedblockstorage[l].func_76658_g();
                System.arraycopy(abyte2, 0, abyte0, i, abyte2.length);
                i += abyte2.length;
            }
        }

        for (int i1 = 0; i1 < aextendedblockstorage.length; i1++)
        {
            if (aextendedblockstorage[i1] != null && (!par1 || !aextendedblockstorage[i1].isEmpty()) && (par2 & 1 << i1) != 0)
            {
                NibbleArray nibblearray = aextendedblockstorage[i1].func_76669_j();
                System.arraycopy(nibblearray.data, 0, abyte0, i, nibblearray.data.length);
                i += nibblearray.data.length;
            }
        }

        for (int j1 = 0; j1 < aextendedblockstorage.length; j1++)
        {
            if (aextendedblockstorage[j1] != null && (!par1 || !aextendedblockstorage[j1].isEmpty()) && (par2 & 1 << j1) != 0)
            {
                NibbleArray nibblearray1 = aextendedblockstorage[j1].getBlocklightArray();
                System.arraycopy(nibblearray1.data, 0, abyte0, i, nibblearray1.data.length);
                i += nibblearray1.data.length;
            }
        }

        for (int k1 = 0; k1 < aextendedblockstorage.length; k1++)
        {
            if (aextendedblockstorage[k1] != null && (!par1 || !aextendedblockstorage[k1].isEmpty()) && (par2 & 1 << k1) != 0)
            {
                NibbleArray nibblearray2 = aextendedblockstorage[k1].getSkylightArray();
                System.arraycopy(nibblearray2.data, 0, abyte0, i, nibblearray2.data.length);
                i += nibblearray2.data.length;
            }
        }

        if (j > 0)
        {
            for (int l1 = 0; l1 < aextendedblockstorage.length; l1++)
            {
                if (aextendedblockstorage[l1] != null && (!par1 || !aextendedblockstorage[l1].isEmpty()) && aextendedblockstorage[l1].getBlockMSBArray() != null && (par2 & 1 << l1) != 0)
                {
                    NibbleArray nibblearray3 = aextendedblockstorage[l1].getBlockMSBArray();
                    System.arraycopy(nibblearray3.data, 0, abyte0, i, nibblearray3.data.length);
                    i += nibblearray3.data.length;
                }
            }
        }

        if (par1)
        {
            byte abyte1[] = par0Chunk.getBiomeArray();
            System.arraycopy(abyte1, 0, abyte0, i, abyte1.length);
            i += abyte1.length;
        }

        packet51mapchunkdata.field_74582_a = new byte[i];
        System.arraycopy(abyte0, 0, packet51mapchunkdata.field_74582_a, 0, i);
        return packet51mapchunkdata;
    }
}
