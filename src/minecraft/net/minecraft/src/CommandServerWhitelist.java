package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandServerWhitelist extends CommandBase
{
    public CommandServerWhitelist()
    {
    }

    public String getCommandName()
    {
        return "whitelist";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.whitelist.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            if (par2ArrayOfStr[0].equals("on"))
            {
                MinecraftServer.func_71276_C().func_71203_ab().setWhiteListEnabled(true);
                func_71522_a(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("off"))
            {
                MinecraftServer.func_71276_C().func_71203_ab().setWhiteListEnabled(false);
                func_71522_a(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("list"))
            {
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.func_70004_a("commands.whitelist.list", new Object[]
                        {
                            Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().func_72388_h().size()), Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().func_72373_m().length)
                        }));
                par1ICommandSender.sendChatToPlayer(func_71527_a(MinecraftServer.func_71276_C().func_71203_ab().func_72388_h().toArray(new String[0])));
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_71276_C().func_71203_ab().func_72359_h(par2ArrayOfStr[1]);
                    func_71522_a(par1ICommandSender, "commands.whitelist.add.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_71276_C().func_71203_ab().func_72379_i(par2ArrayOfStr[1]);
                    func_71522_a(par1ICommandSender, "commands.whitelist.remove.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("reload"))
            {
                MinecraftServer.func_71276_C().func_71203_ab().func_72362_j();
                func_71522_a(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }

        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "on", "off", "list", "add", "remove", "reload"
                    });
        }

        if (par2ArrayOfStr.length == 2)
        {
            if (par2ArrayOfStr[0].equals("add"))
            {
                String as[] = MinecraftServer.func_71276_C().func_71203_ab().func_72373_m();
                ArrayList arraylist = new ArrayList();
                String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                String as1[] = as;
                int i = as1.length;

                for (int j = 0; j < i; j++)
                {
                    String s1 = as1[j];

                    if (func_71523_a(s, s1) && !MinecraftServer.func_71276_C().func_71203_ab().func_72388_h().contains(s1))
                    {
                        arraylist.add(s1);
                    }
                }

                return arraylist;
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                return func_71531_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71203_ab().func_72388_h());
            }
        }

        return null;
    }
}
