package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallablePlayers implements Callable
{
    /** Gets Minecraft Server players. */
    final MinecraftServer minecraftServerPlayers;

    public CallablePlayers(MinecraftServer par1MinecraftServer)
    {
        minecraftServerPlayers = par1MinecraftServer;
    }

    public String func_74269_a()
    {
        return (new StringBuilder()).append(MinecraftServer.func_71196_a(minecraftServerPlayers).func_72394_k()).append(" / ").append(MinecraftServer.func_71196_a(minecraftServerPlayers).getMaxPlayers()).append("; ").append(MinecraftServer.func_71196_a(minecraftServerPlayers).playerList).toString();
    }

    public Object call()
    {
        return func_74269_a();
    }
}
