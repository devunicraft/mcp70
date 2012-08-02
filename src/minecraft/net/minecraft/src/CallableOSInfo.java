package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableOSInfo implements Callable
{
    /** Gets OS Info for Crash Report. */
    final CrashReport crashReportOSInfo;

    CallableOSInfo(CrashReport par1CrashReport)
    {
        crashReportOSInfo = par1CrashReport;
    }

    public String func_71495_a()
    {
        return (new StringBuilder()).append(System.getProperty("os.name")).append(" (").append(System.getProperty("os.arch")).append(") version ").append(System.getProperty("os.version")).toString();
    }

    public Object call()
    {
        return func_71495_a();
    }
}
