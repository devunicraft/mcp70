package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableTexturePack implements Callable
{
    final Minecraft field_79002_a;

    public CallableTexturePack(Minecraft par1Minecraft)
    {
        field_79002_a = par1Minecraft;
    }

    public String func_79001_a()
    {
        return field_79002_a.gameSettings.skin;
    }

    public Object call()
    {
        return func_79001_a();
    }
}
