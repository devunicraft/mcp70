package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;

public class CommandServerBanIp extends CommandBase
{
    public static final Pattern field_71545_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public CommandServerBanIp()
    {
    }

    public String getCommandName()
    {
        return "ban-ip";
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().isListActive() && super.func_71519_b(par1ICommandSender);
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.banip.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = field_71545_a.matcher(par2ArrayOfStr[0]);
            String s = null;

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_71520_a(par2ArrayOfStr, 1);
            }

            if (matcher.matches())
            {
                func_71544_a(par1ICommandSender, par2ArrayOfStr[0], s);
            }
            else
            {
                EntityPlayerMP entityplayermp = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }

                func_71544_a(par1ICommandSender, entityplayermp.func_71114_r(), s);
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.banip.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z());
        }
        else
        {
            return null;
        }
    }

    protected void func_71544_a(ICommandSender par1ICommandSender, String par2Str, String par3Str)
    {
        BanEntry banentry = new BanEntry(par2Str);
        banentry.setBannedBy(par1ICommandSender.func_70005_c_());

        if (par3Str != null)
        {
            banentry.setBanReason(par3Str);
        }

        MinecraftServer.func_71276_C().func_71203_ab().getBannedIPs().put(banentry);
        List list = MinecraftServer.func_71276_C().func_71203_ab().func_72382_j(par2Str);
        String as[] = new String[list.size()];
        int i = 0;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.serverForThisPlayer.kickPlayerFromServer("You have been IP banned.");
            as[i++] = entityplayermp.func_70023_ak();
        }

        if (list.isEmpty())
        {
            func_71522_a(par1ICommandSender, "commands.banip.success", new Object[]
                    {
                        par2Str
                    });
        }
        else
        {
            func_71522_a(par1ICommandSender, "commands.banip.success.players", new Object[]
                    {
                        par2Str, func_71527_a(as)
                    });
        }
    }
}
