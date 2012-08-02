package net.minecraft.src;

import java.nio.*;
import java.util.*;
import org.lwjgl.opengl.GL11;

public class GLAllocation
{
    private static final Map field_74531_a = new HashMap();
    private static final List field_74530_b = new ArrayList();

    /**
     * Generates the specified number of display lists and returns the first index.
     */
    public static synchronized int generateDisplayLists(int par0)
    {
        int i = GL11.glGenLists(par0);
        field_74531_a.put(Integer.valueOf(i), Integer.valueOf(par0));
        return i;
    }

    /**
     * Generates texture names and stores them in the specified buffer.
     */
    public static synchronized void generateTextureNames(IntBuffer par0IntBuffer)
    {
        GL11.glGenTextures(par0IntBuffer);

        for (int i = par0IntBuffer.position(); i < par0IntBuffer.limit(); i++)
        {
            field_74530_b.add(Integer.valueOf(par0IntBuffer.get(i)));
        }
    }

    public static synchronized void deleteDisplayLists(int par0)
    {
        GL11.glDeleteLists(par0, ((Integer)field_74531_a.remove(Integer.valueOf(par0))).intValue());
    }

    /**
     * Deletes all textures and display lists. Called when Minecraft is shutdown to free up resources.
     */
    public static synchronized void deleteTexturesAndDisplayLists()
    {
        java.util.Map.Entry entry;

        for (Iterator iterator = field_74531_a.entrySet().iterator(); iterator.hasNext(); GL11.glDeleteLists(((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
        }

        field_74531_a.clear();
        int i;

        for (Iterator iterator1 = field_74530_b.iterator(); iterator1.hasNext(); GL11.glDeleteTextures(i))
        {
            i = ((Integer)iterator1.next()).intValue();
        }

        field_74530_b.clear();
    }

    /**
     * Creates and returns a direct byte buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static synchronized ByteBuffer createDirectByteBuffer(int par0)
    {
        return ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
    }

    /**
     * Creates and returns a direct int buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static IntBuffer createDirectIntBuffer(int par0)
    {
        return createDirectByteBuffer(par0 << 2).asIntBuffer();
    }

    /**
     * Creates and returns a direct float buffer with the specified capacity. Applies native ordering to speed up
     * access.
     */
    public static FloatBuffer createDirectFloatBuffer(int par0)
    {
        return createDirectByteBuffer(par0 << 2).asFloatBuffer();
    }
}
