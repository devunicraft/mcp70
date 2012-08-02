package net.minecraft.src;

import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class ConvertProgressUpdater implements IProgressUpdate
{
    private long field_74266_b;
    final MinecraftServer field_74267_a;

    public ConvertProgressUpdater(MinecraftServer par1MinecraftServer)
    {
        field_74267_a = par1MinecraftServer;
        field_74266_b = System.currentTimeMillis();
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String s)
    {
    }

    public void printText(String s)
    {
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        if (System.currentTimeMillis() - field_74266_b >= 1000L)
        {
            field_74266_b = System.currentTimeMillis();
            MinecraftServer.minecraftLogger.info((new StringBuilder()).append("Converting... ").append(par1).append("%").toString());
        }
    }

    public void func_73717_a()
    {
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String s)
    {
    }
}
