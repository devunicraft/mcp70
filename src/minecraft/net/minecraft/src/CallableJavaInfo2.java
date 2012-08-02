package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo2 implements Callable
{
    /** Gets Java Enviroment Info to the Crash Report. */
    final CrashReport crashReportJavaInfo2;

    CallableJavaInfo2(CrashReport par1CrashReport)
    {
        crashReportJavaInfo2 = par1CrashReport;
    }

    public String func_71491_a()
    {
        return (new StringBuilder()).append(System.getProperty("java.vm.name")).append(" (").append(System.getProperty("java.vm.info")).append("), ").append(System.getProperty("java.vm.vendor")).toString();
    }

    public Object call()
    {
        return func_71491_a();
    }
}
