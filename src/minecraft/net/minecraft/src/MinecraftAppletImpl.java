package net.minecraft.src;

import java.awt.BorderLayout;
import java.awt.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public class MinecraftAppletImpl extends Minecraft
{
    /** Reference to the main frame, in this case, the applet window itself. */
    final MinecraftApplet mainFrame;

    public MinecraftAppletImpl(MinecraftApplet par1MinecraftApplet, Canvas par2Canvas, MinecraftApplet par3MinecraftApplet, int par4, int par5, boolean par6)
    {
        super(par2Canvas, par3MinecraftApplet, par4, par5, par6);
        mainFrame = par1MinecraftApplet;
    }

    public void func_71406_c(CrashReport par1CrashReport)
    {
        mainFrame.removeAll();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(new PanelCrashReport(par1CrashReport), "Center");
        mainFrame.validate();
    }
}
