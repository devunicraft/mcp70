package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMemoryInfo implements Callable
{
    /** Gets Memory Information for Crash Report. */
    final CrashReport crashReportMemoryInfo;

    CallableMemoryInfo(CrashReport par1CrashReport)
    {
        crashReportMemoryInfo = par1CrashReport;
    }

    public String func_71485_a()
    {
        Runtime runtime = Runtime.getRuntime();
        long l = runtime.maxMemory();
        long l1 = runtime.totalMemory();
        long l2 = runtime.freeMemory();
        long l3 = l / 1024L / 1024L;
        long l4 = l1 / 1024L / 1024L;
        long l5 = l2 / 1024L / 1024L;
        return (new StringBuilder()).append(l2).append(" bytes (").append(l5).append(" MB) / ").append(l1).append(" bytes (").append(l4).append(" MB) up to ").append(l).append(" bytes (").append(l3).append(" MB)").toString();
    }

    public Object call()
    {
        return func_71485_a();
    }
}
