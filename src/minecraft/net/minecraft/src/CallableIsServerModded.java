package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableIsServerModded implements Callable
{
    /** For checking if Minecraft Server is modded. */
    final MinecraftServer minecraftServerIsServerModded;

    public CallableIsServerModded(MinecraftServer par1MinecraftServer)
    {
        minecraftServerIsServerModded = par1MinecraftServer;
    }

    public String func_74273_a()
    {
        String s = minecraftServerIsServerModded.getServerModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }
        else
        {
            return "Unknown (can't tell)";
        }
    }

    public Object call()
    {
        return func_74273_a();
    }
}
