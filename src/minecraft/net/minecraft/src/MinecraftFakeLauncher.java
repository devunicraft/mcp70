package net.minecraft.src;

import java.applet.Applet;
import java.applet.AppletStub;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MinecraftFakeLauncher extends Applet implements AppletStub
{
    final Map field_74534_a;

    public MinecraftFakeLauncher(Map par1Map)
    {
        field_74534_a = par1Map;
    }

    public void appletResize(int i, int j)
    {
    }

    public boolean isActive()
    {
        return true;
    }

    public URL getDocumentBase()
    {
        try
        {
            return new URL("http://www.minecraft.net/game/");
        }
        catch (MalformedURLException malformedurlexception)
        {
            malformedurlexception.printStackTrace();
        }

        return null;
    }

    public String getParameter(String par1Str)
    {
        if (field_74534_a.containsKey(par1Str))
        {
            return (String)field_74534_a.get(par1Str);
        }
        else
        {
            System.err.println((new StringBuilder()).append("Client asked for parameter: ").append(par1Str).toString());
            return null;
        }
    }
}
