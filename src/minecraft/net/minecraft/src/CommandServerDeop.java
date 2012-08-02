package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerDeop extends CommandBase
{
    public CommandServerDeop()
    {
    }

    public String getCommandName()
    {
        return "deop";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.deop.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_71276_C().func_71203_ab().removeNameFromWhitelist(par2ArrayOfStr[0]);
            func_71522_a(par1ICommandSender, "commands.deop.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71531_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71203_ab().func_72376_i());
        }
        else
        {
            return null;
        }
    }
}
