package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiProgress extends GuiScreen implements IProgressUpdate
{
    private String field_74265_a;
    private String field_74263_b;
    private int field_74264_c;
    private boolean field_74262_d;

    public GuiProgress()
    {
        field_74265_a = "";
        field_74263_b = "";
        field_74264_c = 0;
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String par1Str)
    {
        printText(par1Str);
    }

    public void printText(String par1Str)
    {
        field_74265_a = par1Str;
        displayLoadingString("Working...");
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String par1Str)
    {
        field_74263_b = par1Str;
        setLoadingProgress(0);
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        field_74264_c = par1;
    }

    public void func_73717_a()
    {
        field_74262_d = true;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        if (field_74262_d)
        {
            mc.displayGuiScreen(null);
            return;
        }
        else
        {
            drawDefaultBackground();
            drawCenteredString(fontRenderer, field_74265_a, width / 2, 70, 0xffffff);
            drawCenteredString(fontRenderer, (new StringBuilder()).append(field_74263_b).append(" ").append(field_74264_c).append("%").toString(), width / 2, 90, 0xffffff);
            super.drawScreen(par1, par2, par3);
            return;
        }
    }
}
