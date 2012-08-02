package net.minecraft.src;

import java.io.*;

public class Packet15Place extends Packet
{
    private int xPosition;
    private int yPosition;
    private int zPosition;

    /** The offset to use for block/item placement. */
    private int direction;
    private ItemStack itemStack;

    /** The offset from xPosition where the actual click took place */
    private float xOffset;

    /** The offset from yPosition where the actual click took place */
    private float yOffset;

    /** The offset from zPosition where the actual click took place */
    private float zOffset;

    public Packet15Place()
    {
    }

    public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, float par6, float par7, float par8)
    {
        xPosition = par1;
        yPosition = par2;
        zPosition = par3;
        direction = par4;
        itemStack = par5ItemStack;
        xOffset = par6;
        yOffset = par7;
        zOffset = par8;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.read();
        zPosition = par1DataInputStream.readInt();
        direction = par1DataInputStream.read();
        itemStack = readItemStack(par1DataInputStream);
        xOffset = (float)par1DataInputStream.read() / 16F;
        yOffset = (float)par1DataInputStream.read() / 16F;
        zOffset = (float)par1DataInputStream.read() / 16F;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.write(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.write(direction);
        writeItemStack(itemStack, par1DataOutputStream);
        par1DataOutputStream.write((int)(xOffset * 16F));
        par1DataOutputStream.write((int)(yOffset * 16F));
        par1DataOutputStream.write((int)(zOffset * 16F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlace(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 19;
    }

    public int getXPosition()
    {
        return xPosition;
    }

    public int getYPosition()
    {
        return yPosition;
    }

    public int getZPosition()
    {
        return zPosition;
    }

    public int getDirection()
    {
        return direction;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }

    /**
     * Returns the offset from xPosition where the actual click took place
     */
    public float getXOffset()
    {
        return xOffset;
    }

    /**
     * Returns the offset from yPosition where the actual click took place
     */
    public float getYOffset()
    {
        return yOffset;
    }

    /**
     * Returns the offset from zPosition where the actual click took place
     */
    public float getZOffset()
    {
        return zOffset;
    }
}
