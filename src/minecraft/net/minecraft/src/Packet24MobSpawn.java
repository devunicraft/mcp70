package net.minecraft.src;

import java.io.*;
import java.util.List;

public class Packet24MobSpawn extends Packet
{
    /** The entity ID. */
    public int entityId;

    /** The type of mob. */
    public int type;

    /** The X position of the entity. */
    public int xPosition;

    /** The Y position of the entity. */
    public int yPosition;

    /** The Z position of the entity. */
    public int zPosition;
    public int velocityX;
    public int velocityY;
    public int velocityZ;

    /** The yaw of the entity. */
    public byte yaw;

    /** The pitch of the entity. */
    public byte pitch;

    /** The yaw of the entity's head. */
    public byte headYaw;

    /** Indexed metadata for Mob, terminated by 0x7F */
    private DataWatcher metaData;
    private List receivedMetadata;

    public Packet24MobSpawn()
    {
    }

    public Packet24MobSpawn(EntityLiving par1EntityLiving)
    {
        entityId = par1EntityLiving.entityId;
        type = (byte)EntityList.getEntityID(par1EntityLiving);
        xPosition = par1EntityLiving.field_70168_am.func_75630_a(par1EntityLiving.posX);
        yPosition = MathHelper.floor_double(par1EntityLiving.posY * 32D);
        zPosition = par1EntityLiving.field_70168_am.func_75630_a(par1EntityLiving.posZ);
        yaw = (byte)(int)((par1EntityLiving.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((par1EntityLiving.rotationPitch * 256F) / 360F);
        headYaw = (byte)(int)((par1EntityLiving.rotationYawHead * 256F) / 360F);
        double d = 3.8999999999999999D;
        double d1 = par1EntityLiving.motionX;
        double d2 = par1EntityLiving.motionY;
        double d3 = par1EntityLiving.motionZ;

        if (d1 < -d)
        {
            d1 = -d;
        }

        if (d2 < -d)
        {
            d2 = -d;
        }

        if (d3 < -d)
        {
            d3 = -d;
        }

        if (d1 > d)
        {
            d1 = d;
        }

        if (d2 > d)
        {
            d2 = d;
        }

        if (d3 > d)
        {
            d3 = d;
        }

        velocityX = (int)(d1 * 8000D);
        velocityY = (int)(d2 * 8000D);
        velocityZ = (int)(d3 * 8000D);
        metaData = par1EntityLiving.getDataWatcher();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        type = par1DataInputStream.readByte() & 0xff;
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        yaw = par1DataInputStream.readByte();
        pitch = par1DataInputStream.readByte();
        headYaw = par1DataInputStream.readByte();
        velocityX = par1DataInputStream.readShort();
        velocityY = par1DataInputStream.readShort();
        velocityZ = par1DataInputStream.readShort();
        receivedMetadata = DataWatcher.readWatchableObjects(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeByte(type & 0xff);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeByte(yaw);
        par1DataOutputStream.writeByte(pitch);
        par1DataOutputStream.writeByte(headYaw);
        par1DataOutputStream.writeShort(velocityX);
        par1DataOutputStream.writeShort(velocityY);
        par1DataOutputStream.writeShort(velocityZ);
        metaData.writeWatchableObjects(par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMobSpawn(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 26;
    }

    public List getMetadata()
    {
        if (receivedMetadata == null)
        {
            receivedMetadata = metaData.func_75685_c();
        }

        return receivedMetadata;
    }
}
