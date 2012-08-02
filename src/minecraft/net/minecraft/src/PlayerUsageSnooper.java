package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Timer;

public class PlayerUsageSnooper
{
    /** String map for report data */
    private Map dataMap;
    private final String field_76480_b = UUID.randomUUID().toString();

    /** URL of the server to send the report to */
    private final URL serverUrl;
    private final IPlayerUsage field_76478_d;
    private final Timer field_76479_e = new Timer("Snooper Timer", true);
    private final Object field_76476_f = new Object();
    private boolean field_76477_g;
    private int field_76483_h;

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage)
    {
        dataMap = new HashMap();
        field_76477_g = false;
        field_76483_h = 0;

        try
        {
            serverUrl = new URL((new StringBuilder()).append("http://snoop.minecraft.net/").append(par1Str).append("?version=").append(1).toString());
        }
        catch (MalformedURLException malformedurlexception)
        {
            throw new IllegalArgumentException();
        }

        field_76478_d = par2IPlayerUsage;
    }

    public void func_76463_a()
    {
        if (field_76477_g)
        {
            return;
        }
        else
        {
            field_76477_g = true;
            func_76464_f();
            field_76479_e.schedule(new PlayerUsageSnooperThread(this), 0L, 0xdbba0L);
            return;
        }
    }

    private void func_76464_f()
    {
        func_76467_g();
        addData("snooper_token", field_76480_b);
        addData("os_name", System.getProperty("os.name"));
        addData("os_version", System.getProperty("os.version"));
        addData("os_architecture", System.getProperty("os.arch"));
        addData("java_version", System.getProperty("java.version"));
        addData("version", "1.3.1");
        field_76478_d.func_70001_b(this);
    }

    private void func_76467_g()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
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
                addData((new StringBuilder()).append("jvm_arg[").append(i++).append("]").toString(), s);
            }
        }
        while (true);

        addData("jvm_args", Integer.valueOf(i));
    }

    public void func_76471_b()
    {
        addData("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        addData("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        addData("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        addData("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        field_76478_d.func_70000_a(this);
    }

    /**
     * Adds information to the report
     */
    public void addData(String par1Str, Object par2Obj)
    {
        synchronized (field_76476_f)
        {
            dataMap.put(par1Str, par2Obj);
        }
    }

    public Map func_76465_c()
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();

        synchronized (field_76476_f)
        {
            func_76471_b();
            java.util.Map.Entry entry;

            for (Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext(); linkedhashmap.put(entry.getKey(), entry.getValue().toString()))
            {
                entry = (java.util.Map.Entry)iterator.next();
            }
        }

        return linkedhashmap;
    }

    public boolean func_76468_d()
    {
        return field_76477_g;
    }

    public void func_76470_e()
    {
        field_76479_e.cancel();
    }

    static IPlayerUsage func_76473_a(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_76478_d;
    }

    static Object func_76474_b(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_76476_f;
    }

    static Map func_76469_c(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.dataMap;
    }

    static int func_76466_d(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_76483_h++;
    }

    static URL func_76475_e(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.serverUrl;
    }
}
