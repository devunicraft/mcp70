package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;

public class Profiler
{
    /** List of parent sections */
    private final List sectionList = new ArrayList();

    /** List of timestamps (System.nanoTime) */
    private final List timestampList = new ArrayList();

    /** Flag profiling enabled */
    public boolean profilingEnabled;

    /** Current profiling section */
    private String profilingSection;

    /** Profiling map */
    private final Map profilingMap = new HashMap();

    public Profiler()
    {
        profilingEnabled = false;
        profilingSection = "";
    }

    /**
     * Clear profiling
     */
    public void clearProfiling()
    {
        profilingMap.clear();
        profilingSection = "";
        sectionList.clear();
    }

    /**
     * Start section
     */
    public void startSection(String par1Str)
    {
        if (!profilingEnabled)
        {
            return;
        }

        if (!(profilingSection.length() <= 0))
        {
            profilingSection += ".";
        }

        profilingSection += par1Str;
        sectionList.add(profilingSection);
        timestampList.add(Long.valueOf(System.nanoTime()));
        return;
    }

    /**
     * End section
     */
    public void endSection()
    {
        if (!profilingEnabled)
        {
            return;
        }

        long l = System.nanoTime();
        long l1 = ((Long)timestampList.remove(timestampList.size() - 1)).longValue();
        sectionList.remove(sectionList.size() - 1);
        long l2 = l - l1;

        if (profilingMap.containsKey(profilingSection))
        {
            profilingMap.put(profilingSection, Long.valueOf(((Long)profilingMap.get(profilingSection)).longValue() + l2));
        }
        else
        {
            profilingMap.put(profilingSection, Long.valueOf(l2));
        }

        if (l2 > 0x5f5e100L)
        {
            System.out.println((new StringBuilder()).append("Something's taking too long! '").append(profilingSection).append("' took aprox ").append((double)l2 / 1000000D).append(" ms").toString());
        }

        profilingSection = sectionList.isEmpty() ? "" : (String)sectionList.get(sectionList.size() - 1);
    }

    /**
     * Get profiling data
     */
    public List getProfilingData(String par1Str)
    {
        if (!profilingEnabled)
        {
            return null;
        }

        String s = par1Str;
        long l = profilingMap.containsKey("root") ? ((Long)profilingMap.get("root")).longValue() : 0L;
        long l1 = profilingMap.containsKey(par1Str) ? ((Long)profilingMap.get(par1Str)).longValue() : -1L;
        ArrayList arraylist = new ArrayList();

        if (par1Str.length() > 0)
        {
            par1Str = (new StringBuilder()).append(par1Str).append(".").toString();
        }

        long l2 = 0L;
        Iterator iterator = profilingMap.keySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s1 = (String)iterator.next();

            if (s1.length() > par1Str.length() && s1.startsWith(par1Str) && s1.indexOf(".", par1Str.length() + 1) < 0)
            {
                l2 += ((Long)profilingMap.get(s1)).longValue();
            }
        }
        while (true);

        float f = l2;

        if (l2 < l1)
        {
            l2 = l1;
        }

        if (l < l2)
        {
            l = l2;
        }

        Iterator iterator1 = profilingMap.keySet().iterator();

        do
        {
            if (!iterator1.hasNext())
            {
                break;
            }

            String s2 = (String)iterator1.next();

            if (s2.length() > par1Str.length() && s2.startsWith(par1Str) && s2.indexOf(".", par1Str.length() + 1) < 0)
            {
                long l3 = ((Long)profilingMap.get(s2)).longValue();
                double d = ((double)l3 * 100D) / (double)l2;
                double d1 = ((double)l3 * 100D) / (double)l;
                String s4 = s2.substring(par1Str.length());
                arraylist.add(new ProfilerResult(s4, d, d1));
            }
        }
        while (true);

        String s3;

        for (Iterator iterator2 = profilingMap.keySet().iterator(); iterator2.hasNext(); profilingMap.put(s3, Long.valueOf((((Long)profilingMap.get(s3)).longValue() * 999L) / 1000L)))
        {
            s3 = (String)iterator2.next();
        }

        if ((float)l2 > f)
        {
            arraylist.add(new ProfilerResult("unspecified", ((double)((float)l2 - f) * 100D) / (double)l2, ((double)((float)l2 - f) * 100D) / (double)l));
        }

        Collections.sort(arraylist);
        arraylist.add(0, new ProfilerResult(s, 100D, ((double)l2 * 100D) / (double)l));
        return arraylist;
    }

    /**
     * End current section and start a new section
     */
    public void endStartSection(String par1Str)
    {
        endSection();
        startSection(par1Str);
    }

    public String func_76322_c()
    {
        return sectionList.size() != 0 ? (String)sectionList.get(sectionList.size() - 1) : "[UNKNOWN]";
    }
}
