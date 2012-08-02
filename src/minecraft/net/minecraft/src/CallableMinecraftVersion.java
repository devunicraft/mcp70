package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    /** Gets the Crash Rrport current Minecraft version. */
    final CrashReport crashReportMinecraftVersion;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        crashReportMinecraftVersion = par1CrashReport;
    }

    public String func_71493_a()
    {
        return "1.3.1";
    }

    public Object call()
    {
        return func_71493_a();
    }
}
