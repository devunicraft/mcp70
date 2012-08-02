package net.minecraft.src;

import java.io.File;

public interface ISaveHandler
{
    /**
     * Loads and returns the world info
     */
    public abstract WorldInfo loadWorldInfo();

    /**
     * Checks the session lock to prevent save collisions
     */
    public abstract void checkSessionLock() throws MinecraftException;

    /**
     * Returns the chunk loader with the provided world provider
     */
    public abstract IChunkLoader getChunkLoader(WorldProvider worldprovider);

    public abstract void func_75755_a(WorldInfo worldinfo, NBTTagCompound nbttagcompound);

    /**
     * Saves the passed in world info.
     */
    public abstract void saveWorldInfo(WorldInfo worldinfo);

    /**
     * returns null if no saveHandler is relevent (eg. SMP)
     */
    public abstract IPlayerFileData getSaveHandler();

    public abstract void func_75759_a();

    /**
     * Gets the file location of the given map
     */
    public abstract File getMapFileFromName(String s);

    /**
     * Returns the name of the directory where world information is saved
     */
    public abstract String getSaveDirectoryName();
}
