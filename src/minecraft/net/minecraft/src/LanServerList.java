package net.minecraft.src;

import java.net.InetAddress;
import java.util.*;

public class LanServerList
{
    private ArrayList field_77555_b;
    boolean field_77556_a;

    public LanServerList()
    {
        field_77555_b = new ArrayList();
    }

    public synchronized boolean func_77553_a()
    {
        return field_77556_a;
    }

    public synchronized void func_77552_b()
    {
        field_77556_a = false;
    }

    public synchronized List func_77554_c()
    {
        return Collections.unmodifiableList(field_77555_b);
    }

    public synchronized void func_77551_a(String par1Str, InetAddress par2InetAddress)
    {
        String s = ThreadLanServerPing.func_77524_a(par1Str);
        String s1 = ThreadLanServerPing.func_77523_b(par1Str);

        if (s1 == null)
        {
            return;
        }

        boolean flag = false;
        Iterator iterator = field_77555_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            LanServer lanserver = (LanServer)iterator.next();

            if (!lanserver.func_77488_b().equals(s1))
            {
                continue;
            }

            lanserver.func_77489_c();
            flag = true;
            break;
        }
        while (true);

        if (!flag)
        {
            field_77555_b.add(new LanServer(s, s1));
            field_77556_a = true;
        }
    }
}
