package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType implements Callable
{
    /** Gets Decitated Server type. */
    final DedicatedServer minecraftServerType;

    CallableType(DedicatedServer par1DedicatedServer)
    {
        minecraftServerType = par1DedicatedServer;
    }

    public String func_71742_a()
    {
        return "Dedicated Server";
    }

    public Object call()
    {
        return func_71742_a();
    }
}
