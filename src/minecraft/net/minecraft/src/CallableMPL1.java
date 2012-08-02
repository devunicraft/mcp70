package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL1 implements Callable
{
    /** Initialises the WorldClient for CallableMPL1. */
    final WorldClient worldClientMPL1;

    CallableMPL1(WorldClient par1WorldClient)
    {
        worldClientMPL1 = par1WorldClient;
    }

    public String func_78832_a()
    {
        return (new StringBuilder()).append(WorldClient.func_73026_a(worldClientMPL1).size()).append(" total; ").append(WorldClient.func_73026_a(worldClientMPL1).toString()).toString();
    }

    public Object call()
    {
        return func_78832_a();
    }
}
