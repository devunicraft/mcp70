package net.minecraft.src;

import java.util.List;
import java.util.concurrent.Callable;

class CallableLvl2 implements Callable
{
    /** Gets loaded Entities. */
    final World worldLvl2;

    CallableLvl2(World par1World)
    {
        worldLvl2 = par1World;
    }

    public String func_77404_a()
    {
        return (new StringBuilder()).append(worldLvl2.playerEntities.size()).append(" total; ").append(worldLvl2.playerEntities.toString()).toString();
    }

    public Object call()
    {
        return func_77404_a();
    }
}
