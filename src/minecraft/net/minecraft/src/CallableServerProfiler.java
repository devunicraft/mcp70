package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableServerProfiler implements Callable
{
    /** Gets Minecraft Server profile. */
    final MinecraftServer minecraftServerProfiler;

    public CallableServerProfiler(MinecraftServer par1MinecraftServer)
    {
        minecraftServerProfiler = par1MinecraftServer;
    }

    public String func_74271_a()
    {
        return minecraftServerProfiler.field_71304_b.profilingEnabled ? minecraftServerProfiler.field_71304_b.func_76322_c() : "N/A (disabled)";
    }

    public Object call()
    {
        return func_74271_a();
    }
}
