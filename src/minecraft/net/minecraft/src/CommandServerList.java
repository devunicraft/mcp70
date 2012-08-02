package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerList extends CommandBase
{
    public CommandServerList()
    {
    }

    public String getCommandName()
    {
        return "list";
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        par1ICommandSender.sendChatToPlayer(par1ICommandSender.func_70004_a("commands.players.list", new Object[]
                {
                    Integer.valueOf(MinecraftServer.func_71276_C().func_71233_x()), Integer.valueOf(MinecraftServer.func_71276_C().func_71275_y())
                }));
        par1ICommandSender.sendChatToPlayer(MinecraftServer.func_71276_C().func_71203_ab().func_72398_c());
    }
}
