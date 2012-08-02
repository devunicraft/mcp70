package net.minecraft.src;

import net.minecraft.client.Minecraft;

public final class ThreadShutdown extends Thread
{
    public ThreadShutdown()
    {
    }

    public void run()
    {
        Minecraft.func_71363_D();
    }
}
