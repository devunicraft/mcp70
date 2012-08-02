package net.minecraft.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.io.PrintStream;
import java.net.URL;
import net.minecraft.src.*;

public class MinecraftApplet extends Applet
{
    /** Reference to the applet canvas. */
    private Canvas mcCanvas;

    /** Reference to the Minecraft object. */
    private Minecraft mc;

    /** Reference to the Minecraft main thread. */
    private Thread mcThread;

    public MinecraftApplet()
    {
        mcThread = null;
    }

    public void init()
    {
        mcCanvas = new CanvasMinecraftApplet(this);
        boolean flag = "true".equalsIgnoreCase(getParameter("fullscreen"));
        mc = new MinecraftAppletImpl(this, mcCanvas, this, getWidth(), getHeight(), flag);
        mc.minecraftUri = getDocumentBase().getHost();

        if (!(getDocumentBase().getPort() <= 0))
        {
            mc.minecraftUri += ":" + getDocumentBase().getPort();
        }

        if (getParameter("username") != null && getParameter("sessionid") != null)
        {
            mc.session = new Session(getParameter("username"), getParameter("sessionid"));
            System.out.println((new StringBuilder()).append("Setting user: ").append(mc.session.username).append(", ").append(mc.session.sessionId).toString());
        }
        else
        {
            mc.session = new Session("Player", "");
        }

        mc.setIsDemo("true".equals(getParameter("demo")));

        if (getParameter("server") != null && getParameter("port") != null)
        {
            mc.setServer(getParameter("server"), Integer.parseInt(getParameter("port")));
        }

        mc.hideQuitButton = !"true".equals(getParameter("stand-alone"));
        setLayout(new BorderLayout());
        add(mcCanvas, "Center");
        mcCanvas.setFocusable(true);
        validate();
        return;
    }

    public void startMainThread()
    {
        if (mcThread != null)
        {
            return;
        }
        else
        {
            mcThread = new Thread(mc, "Minecraft main thread");
            mcThread.start();
            return;
        }
    }

    public void start()
    {
        if (mc != null)
        {
            mc.isGamePaused = false;
        }
    }

    public void stop()
    {
        if (mc != null)
        {
            mc.isGamePaused = true;
        }
    }

    public void destroy()
    {
        shutdown();
    }

    /**
     * Called when the applet window is closed.
     */
    public void shutdown()
    {
        if (mcThread == null)
        {
            return;
        }

        mc.shutdown();

        try
        {
            mcThread.join(10000L);
        }
        catch (InterruptedException interruptedexception)
        {
            try
            {
                mc.shutdownMinecraftApplet();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        mcThread = null;
    }
}
