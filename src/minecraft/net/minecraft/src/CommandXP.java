package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandXP extends CommandBase
{
    public CommandXP()
    {
    }

    public String getCommandName()
    {
        return "xp";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.xp.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            int i = func_71532_a(par1ICommandSender, par2ArrayOfStr[0], 0, 5000);
            EntityPlayer entityplayer;

            if (par2ArrayOfStr.length > 1)
            {
                entityplayer = func_71543_a(par2ArrayOfStr[1]);
            }
            else
            {
                entityplayer = func_71521_c(par1ICommandSender);
            }

            entityplayer.addExperience(i);
            func_71522_a(par1ICommandSender, "commands.xp.success", new Object[]
                    {
                        Integer.valueOf(i), entityplayer.func_70023_ak()
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 2)
        {
            return func_71530_a(par2ArrayOfStr, func_71542_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_71543_a(String par1Str)
    {
        EntityPlayerMP entityplayermp = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(par1Str);

        if (entityplayermp == null)
        {
            throw new PlayerNotFoundException();
        }
        else
        {
            return entityplayermp;
        }
    }

    protected String[] func_71542_c()
    {
        return MinecraftServer.func_71276_C().func_71213_z();
    }
}
