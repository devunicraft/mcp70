package net.minecraft.src;

import argo.jdom.*;
import argo.saj.InvalidSyntaxException;
import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class StatFileWriter
{
    private Map field_77457_a;
    private Map field_77455_b;
    private boolean field_77456_c;
    private StatsSyncher statsSyncher;

    public StatFileWriter(Session par1Session, File par2File)
    {
        field_77457_a = new HashMap();
        field_77455_b = new HashMap();
        field_77456_c = false;
        File file = new File(par2File, "stats");

        if (!file.exists())
        {
            file.mkdir();
        }

        File afile[] = par2File.listFiles();
        int i = afile.length;

        for (int j = 0; j < i; j++)
        {
            File file1 = afile[j];

            if (!file1.getName().startsWith("stats_") || !file1.getName().endsWith(".dat"))
            {
                continue;
            }

            File file2 = new File(file, file1.getName());

            if (!file2.exists())
            {
                System.out.println((new StringBuilder()).append("Relocating ").append(file1.getName()).toString());
                file1.renameTo(file2);
            }
        }

        statsSyncher = new StatsSyncher(par1Session, this, file);
    }

    public void readStat(StatBase par1StatBase, int par2)
    {
        writeStatToMap(field_77455_b, par1StatBase, par2);
        writeStatToMap(field_77457_a, par1StatBase, par2);
        field_77456_c = true;
    }

    private void writeStatToMap(Map par1Map, StatBase par2StatBase, int par3)
    {
        Integer integer = (Integer)par1Map.get(par2StatBase);
        int i = integer != null ? integer.intValue() : 0;
        par1Map.put(par2StatBase, Integer.valueOf(i + par3));
    }

    public Map func_77445_b()
    {
        return new HashMap(field_77455_b);
    }

    /**
     * write a whole Map of stats to the statmap
     */
    public void writeStats(Map par1Map)
    {
        if (par1Map == null)
        {
            return;
        }

        field_77456_c = true;
        StatBase statbase;

        for (Iterator iterator = par1Map.keySet().iterator(); iterator.hasNext(); writeStatToMap(field_77457_a, statbase, ((Integer)par1Map.get(statbase)).intValue()))
        {
            statbase = (StatBase)iterator.next();
            writeStatToMap(field_77455_b, statbase, ((Integer)par1Map.get(statbase)).intValue());
        }
    }

    public void func_77452_b(Map par1Map)
    {
        if (par1Map == null)
        {
            return;
        }

        StatBase statbase;
        int i;

        for (Iterator iterator = par1Map.keySet().iterator(); iterator.hasNext(); field_77457_a.put(statbase, Integer.valueOf(((Integer)par1Map.get(statbase)).intValue() + i)))
        {
            statbase = (StatBase)iterator.next();
            Integer integer = (Integer)field_77455_b.get(statbase);
            i = integer != null ? integer.intValue() : 0;
        }
    }

    public void func_77448_c(Map par1Map)
    {
        if (par1Map == null)
        {
            return;
        }

        field_77456_c = true;
        StatBase statbase;

        for (Iterator iterator = par1Map.keySet().iterator(); iterator.hasNext(); writeStatToMap(field_77455_b, statbase, ((Integer)par1Map.get(statbase)).intValue()))
        {
            statbase = (StatBase)iterator.next();
        }
    }

    public static Map func_77453_b(String par0Str)
    {
        HashMap hashmap = new HashMap();

        try
        {
            String s = "local";
            StringBuilder stringbuilder = new StringBuilder();
            JsonRootNode jsonrootnode = (new JdomParser()).parse(par0Str);
            List list = jsonrootnode.getArrayNode(new Object[]
                    {
                        "stats-change"
                    });

            for (Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                JsonNode jsonnode = (JsonNode)iterator.next();
                Map map = jsonnode.getFields();
                java.util.Map.Entry entry = (java.util.Map.Entry)map.entrySet().iterator().next();
                int i = Integer.parseInt(((JsonStringNode)entry.getKey()).getText());
                int j = Integer.parseInt(((JsonNode)entry.getValue()).getText());
                StatBase statbase = StatList.getOneShotStat(i);

                if (statbase == null)
                {
                    System.out.println((new StringBuilder()).append(i).append(" is not a valid stat").toString());
                }
                else
                {
                    stringbuilder.append(StatList.getOneShotStat(i).statGuid).append(",");
                    stringbuilder.append(j).append(",");
                    hashmap.put(statbase, Integer.valueOf(j));
                }
            }

            MD5String md5string = new MD5String(s);
            String s1 = md5string.getMD5String(stringbuilder.toString());

            if (!s1.equals(jsonrootnode.getStringValue(new Object[]
                    {
                        "checksum"
                    })))
            {
                System.out.println("CHECKSUM MISMATCH");
                return null;
            }
        }
        catch (InvalidSyntaxException invalidsyntaxexception)
        {
            invalidsyntaxexception.printStackTrace();
        }

        return hashmap;
    }

    public static String func_77441_a(String par0Str, String par1Str, Map par2Map)
    {
        StringBuilder stringbuilder = new StringBuilder();
        StringBuilder stringbuilder1 = new StringBuilder();
        boolean flag = true;
        stringbuilder.append("{\r\n");

        if (par0Str != null && par1Str != null)
        {
            stringbuilder.append("  \"user\":{\r\n");
            stringbuilder.append("    \"name\":\"").append(par0Str).append("\",\r\n");
            stringbuilder.append("    \"sessionid\":\"").append(par1Str).append("\"\r\n");
            stringbuilder.append("  },\r\n");
        }

        stringbuilder.append("  \"stats-change\":[");
        StatBase statbase;

        for (Iterator iterator = par2Map.keySet().iterator(); iterator.hasNext(); stringbuilder1.append(par2Map.get(statbase)).append(","))
        {
            statbase = (StatBase)iterator.next();

            if (flag)
            {
                flag = false;
            }
            else
            {
                stringbuilder.append("},");
            }

            stringbuilder.append("\r\n    {\"").append(statbase.statId).append("\":").append(par2Map.get(statbase));
            stringbuilder1.append(statbase.statGuid).append(",");
        }

        if (!flag)
        {
            stringbuilder.append("}");
        }

        MD5String md5string = new MD5String(par1Str);
        stringbuilder.append("\r\n  ],\r\n");
        stringbuilder.append("  \"checksum\":\"").append(md5string.getMD5String(stringbuilder1.toString())).append("\"\r\n");
        stringbuilder.append("}");
        return stringbuilder.toString();
    }

    /**
     * Returns true if the achievement has been unlocked.
     */
    public boolean hasAchievementUnlocked(Achievement par1Achievement)
    {
        return field_77457_a.containsKey(par1Achievement);
    }

    /**
     * Returns true if the parent has been unlocked, or there is no parent
     */
    public boolean canUnlockAchievement(Achievement par1Achievement)
    {
        return par1Achievement.parentAchievement == null || hasAchievementUnlocked(par1Achievement.parentAchievement);
    }

    public int writeStat(StatBase par1StatBase)
    {
        Integer integer = (Integer)field_77457_a.get(par1StatBase);
        return integer != null ? integer.intValue() : 0;
    }

    public void syncStats()
    {
        statsSyncher.syncStatsFileWithMap(func_77445_b());
    }

    public void func_77449_e()
    {
        if (field_77456_c && statsSyncher.func_77425_c())
        {
            statsSyncher.beginSendStats(func_77445_b());
        }

        statsSyncher.func_77422_e();
    }
}
