package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandHelp extends CommandBase
{
    public CommandHelp()
    {
    }

    public String getCommandName()
    {
        return "help";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.help.usage", new Object[0]);
    }

    public List getCommandAliases()
    {
        return Arrays.asList(new String[]
                {
                    "?"
                });
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        List list = func_71534_d(par1ICommandSender);
        byte byte0 = 7;
        int i = list.size() / byte0;
        int j = 0;

        try
        {
            j = par2ArrayOfStr.length != 0 ? func_71532_a(par1ICommandSender, par2ArrayOfStr[0], 1, i + 1) - 1 : 0;
        }
        catch (NumberInvalidException numberinvalidexception)
        {
            Map map = func_71535_c();
            ICommand icommand = (ICommand)map.get(par2ArrayOfStr[0]);

            if (icommand != null)
            {
                throw new WrongUsageException(icommand.getCommandUsage(par1ICommandSender), new Object[0]);
            }
            else
            {
                throw new CommandNotFoundException();
            }
        }

        int k = Math.min((j + 1) * byte0, list.size());
        par1ICommandSender.sendChatToPlayer((new StringBuilder()).append("\2472").append(par1ICommandSender.func_70004_a("commands.help.header", new Object[]
                {
                    Integer.valueOf(j + 1), Integer.valueOf(i + 1)
                })).toString());

        for (int l = j * byte0; l < k; l++)
        {
            ICommand icommand1 = (ICommand)list.get(l);
            par1ICommandSender.sendChatToPlayer(icommand1.getCommandUsage(par1ICommandSender));
        }

        if (j == 0)
        {
            par1ICommandSender.sendChatToPlayer((new StringBuilder()).append("\247a").append(par1ICommandSender.func_70004_a("commands.help.footer", new Object[0])).toString());
        }
    }

    protected List func_71534_d(ICommandSender par1ICommandSender)
    {
        List list = MinecraftServer.func_71276_C().func_71187_D().func_71557_a(par1ICommandSender);
        Collections.sort(list);
        return list;
    }

    protected Map func_71535_c()
    {
        return MinecraftServer.func_71276_C().func_71187_D().func_71555_a();
    }
}
