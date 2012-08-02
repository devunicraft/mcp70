package net.minecraft.src;

import java.util.*;

public class CommandHandler implements ICommandManager
{
    /** Map of Strings to the ICommand objects they represent */
    private final Map commandMap = new HashMap();

    /** The set of ICommand objects currently loaded. */
    private final Set commandSet = new HashSet();

    public CommandHandler()
    {
    }

    public void executeCommand(ICommandSender par1ICommandSender, String par2Str)
    {
        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String as[] = par2Str.split(" ");
        String s = as[0];
        as = func_71559_a(as);
        ICommand icommand = (ICommand)commandMap.get(s);

        try
        {
            if (icommand == null)
            {
                throw new CommandNotFoundException();
            }

            if (icommand.func_71519_b(par1ICommandSender))
            {
                icommand.processCommand(par1ICommandSender, as);
            }
            else
            {
                par1ICommandSender.sendChatToPlayer("\247cYou do not have permission to use this command.");
            }
        }
        catch (WrongUsageException wrongusageexception)
        {
            par1ICommandSender.sendChatToPlayer((new StringBuilder()).append("\247c").append(par1ICommandSender.func_70004_a("commands.generic.usage", new Object[]
                    {
                        par1ICommandSender.func_70004_a(wrongusageexception.getMessage(), wrongusageexception.func_74844_a())
                    })).toString());
        }
        catch (CommandException commandexception)
        {
            par1ICommandSender.sendChatToPlayer((new StringBuilder()).append("\247c").append(par1ICommandSender.func_70004_a(commandexception.getMessage(), commandexception.func_74844_a())).toString());
        }
        catch (Throwable throwable)
        {
            par1ICommandSender.sendChatToPlayer((new StringBuilder()).append("\247c").append(par1ICommandSender.func_70004_a("commands.generic.exception", new Object[0])).toString());
            throwable.printStackTrace();
        }
    }

    public ICommand func_71560_a(ICommand par1ICommand)
    {
        List list = par1ICommand.getCommandAliases();
        commandMap.put(par1ICommand.getCommandName(), par1ICommand);
        commandSet.add(par1ICommand);

        if (list != null)
        {
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                String s = (String)iterator.next();
                ICommand icommand = (ICommand)commandMap.get(s);

                if (icommand == null || !icommand.getCommandName().equals(s))
                {
                    commandMap.put(s, par1ICommand);
                }
            }
            while (true);
        }

        return par1ICommand;
    }

    private static String[] func_71559_a(String par0ArrayOfStr[])
    {
        String as[] = new String[par0ArrayOfStr.length - 1];

        for (int i = 1; i < par0ArrayOfStr.length; i++)
        {
            as[i - 1] = par0ArrayOfStr[i];
        }

        return as;
    }

    public List func_71558_b(ICommandSender par1ICommandSender, String par2Str)
    {
        String as[] = par2Str.split(" ", -1);
        String s = as[0];

        if (as.length == 1)
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = commandMap.entrySet().iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

                if (CommandBase.func_71523_a(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).func_71519_b(par1ICommandSender))
                {
                    arraylist.add(entry.getKey());
                }
            }
            while (true);

            return arraylist;
        }

        if (as.length > 1)
        {
            ICommand icommand = (ICommand)commandMap.get(s);

            if (icommand != null)
            {
                return icommand.func_71516_a(par1ICommandSender, func_71559_a(as));
            }
        }

        return null;
    }

    public List func_71557_a(ICommandSender par1ICommandSender)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = commandSet.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ICommand icommand = (ICommand)iterator.next();

            if (icommand.func_71519_b(par1ICommandSender))
            {
                arraylist.add(icommand);
            }
        }
        while (true);

        return arraylist;
    }

    public Map func_71555_a()
    {
        return commandMap;
    }
}
