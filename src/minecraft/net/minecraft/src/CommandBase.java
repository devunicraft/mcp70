package net.minecraft.src;

import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand field_71533_a = null;

    public CommandBase()
    {
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return (new StringBuilder()).append("/").append(getCommandName()).toString();
    }

    public List getCommandAliases()
    {
        return null;
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70003_b(getCommandName());
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        return null;
    }

    public static int func_71526_a(ICommandSender par0ICommandSender, String par1Str)
    {
        try
        {
            return Integer.parseInt(par1Str);
        }
        catch (NumberFormatException numberformatexception)
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]
                    {
                        par1Str
                    });
        }
    }

    public static int func_71528_a(ICommandSender par0ICommandSender, String par1Str, int par2)
    {
        return func_71532_a(par0ICommandSender, par1Str, par2, 0x7fffffff);
    }

    public static int func_71532_a(ICommandSender par0ICommandSender, String par1Str, int par2, int par3)
    {
        int i = func_71526_a(par0ICommandSender, par1Str);

        if (i < par2)
        {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(par2)
                    });
        }

        if (i > par3)
        {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(par3)
                    });
        }
        else
        {
            return i;
        }
    }

    public static EntityPlayer func_71521_c(ICommandSender par0ICommandSender)
    {
        if (par0ICommandSender instanceof EntityPlayer)
        {
            return (EntityPlayer)par0ICommandSender;
        }
        else
        {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }

    public static String func_71520_a(String par0ArrayOfStr[], int par1)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = par1; i < par0ArrayOfStr.length; i++)
        {
            if (i > par1)
            {
                stringbuilder.append(" ");
            }

            stringbuilder.append(par0ArrayOfStr[i]);
        }

        return stringbuilder.toString();
    }

    public static String func_71527_a(Object par0ArrayOfObj[])
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < par0ArrayOfObj.length; i++)
        {
            String s = par0ArrayOfObj[i].toString();

            if (i > 0)
            {
                if (i == par0ArrayOfObj.length - 1)
                {
                    stringbuilder.append(" and ");
                }
                else
                {
                    stringbuilder.append(", ");
                }
            }

            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static boolean func_71523_a(String par0Str, String par1Str)
    {
        return par1Str.regionMatches(true, 0, par0Str, 0, par0Str.length());
    }

    public static List func_71530_a(String par0ArrayOfStr[], String par1ArrayOfStr[])
    {
        String s = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList arraylist = new ArrayList();
        String as[] = par1ArrayOfStr;
        int i = as.length;

        for (int j = 0; j < i; j++)
        {
            String s1 = as[j];

            if (func_71523_a(s, s1))
            {
                arraylist.add(s1);
            }
        }

        return arraylist;
    }

    public static List func_71531_a(String par0ArrayOfStr[], Iterable par1Iterable)
    {
        String s = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList arraylist = new ArrayList();
        Iterator iterator = par1Iterable.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s1 = (String)iterator.next();

            if (func_71523_a(s, s1))
            {
                arraylist.add(s1);
            }
        }
        while (true);

        return arraylist;
    }

    public static void func_71522_a(ICommandSender par0ICommandSender, String par1Str, Object par2ArrayOfObj[])
    {
        func_71524_a(par0ICommandSender, 0, par1Str, par2ArrayOfObj);
    }

    public static void func_71524_a(ICommandSender par0ICommandSender, int par1, String par2Str, Object par3ArrayOfObj[])
    {
        if (field_71533_a != null)
        {
            field_71533_a.func_71563_a(par0ICommandSender, par1, par2Str, par3ArrayOfObj);
        }
    }

    public static void func_71529_a(IAdminCommand par0IAdminCommand)
    {
        field_71533_a = par0IAdminCommand;
    }

    public int func_71525_a(ICommand par1ICommand)
    {
        return getCommandName().compareTo(par1ICommand.getCommandName());
    }

    public int compareTo(Object par1Obj)
    {
        return func_71525_a((ICommand)par1Obj);
    }
}
