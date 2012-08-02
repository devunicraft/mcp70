package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL2 implements Callable
{
    /** Initialises the WorldClient for CallableMPL2. */
    final WorldClient worldClientMPL2;

    CallableMPL2(WorldClient par1WorldClient)
    {
        worldClientMPL2 = par1WorldClient;
    }

    public String func_78834_a()
    {
        return (new StringBuilder()).append(WorldClient.func_73030_b(worldClientMPL2).size()).append(" total; ").append(WorldClient.func_73030_b(worldClientMPL2).toString()).toString();
    }

    public Object call()
    {
        return func_78834_a();
    }
}
