package net.minecraft.src;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class PanelCrashReport extends Panel
{
    public PanelCrashReport(CrashReport par1CrashReport)
    {
        setBackground(new Color(0x2e3444));
        setLayout(new BorderLayout());
        StringWriter stringwriter = new StringWriter();
        par1CrashReport.func_71505_b().printStackTrace(new PrintWriter(stringwriter));
        String s = stringwriter.toString();
        String s1 = "";
        String s2 = "";

        try
        {
            s2 = (new StringBuilder()).append(s2).append("Generated ").append((new SimpleDateFormat()).format(new Date())).append("\n").toString();
            s2 = (new StringBuilder()).append(s2).append("\n").toString();
            s2 = (new StringBuilder()).append(s2).append(par1CrashReport.func_71509_c()).toString();
            s1 = GL11.glGetString(GL11.GL_VENDOR);
        }
        catch (Throwable throwable)
        {
            s2 = (new StringBuilder()).append(s2).append("[failed to get system properties (").append(throwable).append(")]\n").toString();
        }

        s2 = (new StringBuilder()).append(s2).append("\n\n").toString();
        s2 = (new StringBuilder()).append(s2).append(s).toString();
        String s3 = "";
        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        s3 = (new StringBuilder()).append(s3).append("\n").toString();

        if (s.contains("Pixel format not accelerated"))
        {
            s3 = (new StringBuilder()).append(s3).append("      Bad video card drivers!      \n").toString();
            s3 = (new StringBuilder()).append(s3).append("      -----------------------      \n").toString();
            s3 = (new StringBuilder()).append(s3).append("\n").toString();
            s3 = (new StringBuilder()).append(s3).append("Minecraft was unable to start because it failed to find an accelerated OpenGL mode.\n").toString();
            s3 = (new StringBuilder()).append(s3).append("This can usually be fixed by updating the video card drivers.\n").toString();

            if (s1.toLowerCase().contains("nvidia"))
            {
                s3 = (new StringBuilder()).append(s3).append("\n").toString();
                s3 = (new StringBuilder()).append(s3).append("You might be able to find drivers for your video card here:\n").toString();
                s3 = (new StringBuilder()).append(s3).append("  http://www.nvidia.com/\n").toString();
            }
            else if (s1.toLowerCase().contains("ati"))
            {
                s3 = (new StringBuilder()).append(s3).append("\n").toString();
                s3 = (new StringBuilder()).append(s3).append("You might be able to find drivers for your video card here:\n").toString();
                s3 = (new StringBuilder()).append(s3).append("  http://www.amd.com/\n").toString();
            }
        }
        else
        {
            s3 = (new StringBuilder()).append(s3).append("      Minecraft has crashed!      \n").toString();
            s3 = (new StringBuilder()).append(s3).append("      ----------------------      \n").toString();
            s3 = (new StringBuilder()).append(s3).append("\n").toString();
            s3 = (new StringBuilder()).append(s3).append("Minecraft has stopped running because it encountered a problem; ").append(par1CrashReport.func_71501_a()).append("\n").toString();
            File file = par1CrashReport.func_71497_f();

            if (file == null)
            {
                par1CrashReport.func_71508_a(new File(new File(Minecraft.getMinecraftDir(), "crash-reports"), (new StringBuilder()).append("crash-").append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-client.txt").toString()));
                file = par1CrashReport.func_71497_f();
            }

            if (file != null)
            {
                s3 = (new StringBuilder()).append(s3).append("This error has been saved to ").append(file.getAbsolutePath()).append(" for your convenience. Please include a copy of this file if you report this crash to anyone.").toString();
            }
            else
            {
                s3 = (new StringBuilder()).append(s3).append("We were unable to save this report to a file.").toString();
            }

            s3 = (new StringBuilder()).append(s3).append("\n").toString();
        }

        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        s3 = (new StringBuilder()).append(s3).append("--- BEGIN ERROR REPORT ").append(Integer.toHexString(s3.hashCode())).append(" --------\n").toString();
        s3 = (new StringBuilder()).append(s3).append(s2).toString();
        s3 = (new StringBuilder()).append(s3).append("--- END ERROR REPORT ").append(Integer.toHexString(s3.hashCode())).append(" ----------\n").toString();
        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        s3 = (new StringBuilder()).append(s3).append("\n").toString();
        TextArea textarea = new TextArea(s3, 0, 0, 1);
        textarea.setFont(new Font("Monospaced", 0, 12));
        add(new CanvasMojangLogo(), "North");
        add(new CanvasCrashReport(80), "East");
        add(new CanvasCrashReport(80), "West");
        add(new CanvasCrashReport(100), "South");
        add(textarea, "Center");
    }
}
