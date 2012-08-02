package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType3 implements Callable
{
    /** Gets Intergated Server type. */
    final IntegratedServer minecraftServerType3;

    CallableType3(IntegratedServer par1IntegratedServer)
    {
        minecraftServerType3 = par1IntegratedServer;
    }

    public String func_76973_a()
    {
        return "Integrated Server";
    }

    public Object call()
    {
        return func_76973_a();
    }
}
