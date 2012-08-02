package net.minecraft.src;

import java.util.*;

class PlayerUsageSnooperThread extends TimerTask
{
    /** The PlayerUsageSnooper object. */
    final PlayerUsageSnooper snooper;

    PlayerUsageSnooperThread(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        snooper = par1PlayerUsageSnooper;
    }

    public void run()
    {
        if (!PlayerUsageSnooper.func_76473_a(snooper).func_70002_Q())
        {
            return;
        }

        HashMap hashmap;

        synchronized (PlayerUsageSnooper.func_76474_b(snooper))
        {
            hashmap = new HashMap(PlayerUsageSnooper.func_76469_c(snooper));
        }

        hashmap.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.func_76466_d(snooper)));
        HttpUtil.sendPost(PlayerUsageSnooper.func_76475_e(snooper), hashmap, true);
    }
}
