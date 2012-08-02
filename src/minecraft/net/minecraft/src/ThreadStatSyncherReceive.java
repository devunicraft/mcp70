package net.minecraft.src;

import java.io.File;

class ThreadStatSyncherReceive extends Thread
{
    final StatsSyncher syncher;

    ThreadStatSyncherReceive(StatsSyncher par1StatsSyncher)
    {
        syncher = par1StatsSyncher;
    }

    public void run()
    {
        try
        {
            if (StatsSyncher.func_77419_a(syncher) != null)
            {
                StatsSyncher.func_77414_a(syncher, StatsSyncher.func_77419_a(syncher), StatsSyncher.func_77408_b(syncher), StatsSyncher.func_77407_c(syncher), StatsSyncher.func_77411_d(syncher));
            }
            else if (StatsSyncher.func_77408_b(syncher).exists())
            {
                StatsSyncher.func_77416_a(syncher, StatsSyncher.func_77410_a(syncher, StatsSyncher.func_77408_b(syncher), StatsSyncher.func_77407_c(syncher), StatsSyncher.func_77411_d(syncher)));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            StatsSyncher.setBusy(syncher, false);
        }
    }
}
