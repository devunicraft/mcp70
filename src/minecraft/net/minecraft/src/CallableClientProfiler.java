package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableClientProfiler implements Callable
{
    /** Gets skin of Minecraft player. */
    final Minecraft minecraftClientProfiler;

    public CallableClientProfiler(Minecraft par1Minecraft)
    {
        minecraftClientProfiler = par1Minecraft;
    }

    public String func_74499_a()
    {
        return minecraftClientProfiler.field_71424_I.profilingEnabled ? minecraftClientProfiler.field_71424_I.func_76322_c() : "N/A (disabled)";
    }

    public Object call()
    {
        return func_74499_a();
    }
}
