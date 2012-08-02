package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerKick extends CommandBase
{
    public CommandServerKick()
    {
    }

    public String getCommandName()
    {
        return "kick";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.kick.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(par2ArrayOfStr[0]);
            String s = "Kicked by an operator.";
            boolean flag = false;

            if (entityplayermp == null)
            {
                throw new PlayerNotFoundException();
            }

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_71520_a(par2ArrayOfStr, 1);
                flag = true;
            }

            entityplayermp.serverForThisPlayer.kickPlayerFromServer(s);

            if (flag)
            {
                func_71522_a(par1ICommandSender, "commands.kick.success.reason", new Object[]
                        {
                            entityplayermp.func_70023_ak(), s
                        });
            }
            else
            {
                func_71522_a(par1ICommandSender, "commands.kick.success", new Object[]
                        {
                            entityplayermp.func_70023_ak()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            return func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z());
        }
        else
        {
            return null;
        }
    }
}
