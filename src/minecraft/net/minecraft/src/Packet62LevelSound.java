package net.minecraft.src;

import java.io.*;

public class Packet62LevelSound extends Packet
{
    /** e.g. step.grass */
    private String soundName;
    private int field_73577_b;
    private int field_73578_c;
    private int field_73575_d;

    /** 1 is 100%. Can be more. */
    private float volume;

    /** 63 is 100%. Can be more. */
    private int pitch;

    public Packet62LevelSound()
    {
        field_73578_c = 0x7fffffff;
    }

    public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        field_73578_c = 0x7fffffff;
        soundName = par1Str;
        field_73577_b = (int)(par2 * 8D);
        field_73578_c = (int)(par4 * 8D);
        field_73575_d = (int)(par6 * 8D);
        volume = par8;
        pitch = (int)(par9 * 63F);

        if (pitch < 0)
        {
            pitch = 0;
        }

        if (pitch > 255)
        {
            pitch = 255;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        soundName = readString(par1DataInputStream, 32);
        field_73577_b = par1DataInputStream.readInt();
        field_73578_c = par1DataInputStream.readInt();
        field_73575_d = par1DataInputStream.readInt();
        volume = par1DataInputStream.readFloat();
        pitch = par1DataInputStream.readUnsignedByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(soundName, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_73577_b);
        par1DataOutputStream.writeInt(field_73578_c);
        par1DataOutputStream.writeInt(field_73575_d);
        par1DataOutputStream.writeFloat(volume);
        par1DataOutputStream.writeByte(pitch);
    }

    public String func_73570_d()
    {
        return soundName;
    }

    public double func_73572_f()
    {
        return (double)((float)field_73577_b / 8F);
    }

    public double func_73568_g()
    {
        return (double)((float)field_73578_c / 8F);
    }

    public double func_73569_h()
    {
        return (double)((float)field_73575_d / 8F);
    }

    public float func_73571_i()
    {
        return volume;
    }

    public float func_73573_j()
    {
        return (float)pitch / 63F;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_72457_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 24;
    }
}
