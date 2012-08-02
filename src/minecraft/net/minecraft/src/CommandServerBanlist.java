package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandServerBanlist extends CommandBase
{
    public CommandServerBanlist()
    {
    }

    public String getCommandName()
    {
        return "banlist";
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return (MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().isListActive() || MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().isListActive()) && super.func_71519_b(par1ICommandSender);
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.banlist.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips"))
        {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.func_70004_a("commands.banlist.ips", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().getBannedList().size())
                    }));
            par1ICommandSender.sendChatToPlayer(func_71527_a(MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().getBannedList().keySet().toArray()));
        }
        else
        {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.func_70004_a("commands.banlist.players", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().getBannedList().size())
                    }));
            par1ICommandSender.sendChatToPlayer(func_71527_a(MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().getBannedList().keySet().toArray()));
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "players", "ips"
                    });
        }
        else
        {
            return null;
        }
    }
}
