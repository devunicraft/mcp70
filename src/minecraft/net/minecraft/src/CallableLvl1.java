package net.minecraft.src;

import java.util.List;
import java.util.concurrent.Callable;

class CallableLvl1 implements Callable
{
    /** Gets loaded Entities. */
    final World worldLvl1;

    CallableLvl1(World par1World)
    {
        worldLvl1 = par1World;
    }

    public String func_77484_a()
    {
        return (new StringBuilder()).append(worldLvl1.loadedEntityList.size()).append(" total; ").append(worldLvl1.loadedEntityList.toString()).toString();
    }

    public Object call()
    {
        return func_77484_a();
    }
}
