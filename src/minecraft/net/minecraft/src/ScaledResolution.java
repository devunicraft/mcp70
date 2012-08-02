package net.minecraft.src;

public class ScaledResolution
{
    private int scaledWidth;
    private int scaledHeight;
    private double scaledWidthD;
    private double scaledHeightD;
    private int scaleFactor;

    public ScaledResolution(GameSettings par1GameSettings, int par2, int par3)
    {
        scaledWidth = par2;
        scaledHeight = par3;
        scaleFactor = 1;
        int i = par1GameSettings.guiScale;

        if (i == 0)
        {
            i = 1000;
        }

        for (; scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240; scaleFactor++) { }

        scaledWidthD = (double)scaledWidth / (double)scaleFactor;
        scaledHeightD = (double)scaledHeight / (double)scaleFactor;
        scaledWidth = MathHelper.func_76143_f(scaledWidthD);
        scaledHeight = MathHelper.func_76143_f(scaledHeightD);
    }

    public int getScaledWidth()
    {
        return scaledWidth;
    }

    public int getScaledHeight()
    {
        return scaledHeight;
    }

    public double func_78327_c()
    {
        return scaledWidthD;
    }

    public double func_78324_d()
    {
        return scaledHeightD;
    }

    public int func_78325_e()
    {
        return scaleFactor;
    }
}
