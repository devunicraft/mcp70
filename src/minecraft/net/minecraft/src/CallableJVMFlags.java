package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

class CallableJVMFlags implements Callable
{
    /** Gets additional Java Enviroment info for Crash Report. */
    final CrashReport crashReportJVMFlags;

    CallableJVMFlags(CrashReport par1CrashReport)
    {
        crashReportJVMFlags = par1CrashReport;
    }

    public String func_71487_a()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s = (String)iterator.next();

            if (s.startsWith("-X"))
            {
                if (i++ > 0)
                {
                    stringbuilder.append(" ");
                }

                stringbuilder.append(s);
            }
        }
        while (true);

        return String.format("%d total; %s", new Object[]
                {
                    Integer.valueOf(i), stringbuilder.toString()
                });
    }

    public Object call()
    {
        return func_71487_a();
    }
}
