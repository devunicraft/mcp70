package net.minecraft.src;

import java.util.List;
import java.util.Map;
import net.minecraft.server.MinecraftServer;

public class CommandServerPardon extends CommandBase
{
    public CommandServerPardon()
    {
    }

    public String getCommandName()
    {
        return "pardon";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.unban.usage", new Object[0]);
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().isListActive() && super.func_71519_b(par1ICommandSender);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().remove(par2ArrayOfStr[0]);
            func_71522_a(par1ICommandSender, "commands.unban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71531_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71203_ab().getBannedPlayers().getBannedList().keySet());
        }
        else
        {
            return null;
        }
    }
}
