package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;

public class CommandServerPardonIp extends CommandBase
{
    public CommandServerPardonIp()
    {
    }

    public String getCommandName()
    {
        return "pardon-ip";
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().isListActive() && super.func_71519_b(par1ICommandSender);
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.unbanip.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = CommandServerBanIp.field_71545_a.matcher(par2ArrayOfStr[0]);

            if (matcher.matches())
            {
                MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().remove(par2ArrayOfStr[0]);
                func_71522_a(par1ICommandSender, "commands.unbanip.success", new Object[]
                        {
                            par2ArrayOfStr[0]
                        });
                return;
            }
            else
            {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71531_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().getBannedList().keySet());
        }
        else
        {
            return null;
        }
    }
}
