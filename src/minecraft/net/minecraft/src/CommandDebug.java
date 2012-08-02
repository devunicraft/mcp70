package net.minecraft.src;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CommandBase
{
    private long field_71551_a;
    private int field_71550_b;

    public CommandDebug()
    {
        field_71551_a = 0L;
        field_71550_b = 0;
    }

    public String getCommandName()
    {
        return "debug";
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            if (par2ArrayOfStr[0].equals("start"))
            {
                func_71522_a(par1ICommandSender, "commands.debug.start", new Object[0]);
                MinecraftServer.func_71276_C().func_71223_ag();
                field_71551_a = System.currentTimeMillis();
                field_71550_b = MinecraftServer.func_71276_C().func_71259_af();
                return;
            }

            if (par2ArrayOfStr[0].equals("stop"))
            {
                if (!MinecraftServer.func_71276_C().field_71304_b.profilingEnabled)
                {
                    throw new CommandException("commands.debug.notStarted", new Object[0]);
                }
                else
                {
                    long l = System.currentTimeMillis();
                    int i = MinecraftServer.func_71276_C().func_71259_af();
                    long l1 = l - field_71551_a;
                    int j = i - field_71550_b;
                    func_71548_a(l1, j);
                    MinecraftServer.func_71276_C().field_71304_b.profilingEnabled = false;
                    func_71522_a(par1ICommandSender, "commands.debug.stop", new Object[]
                            {
                                Float.valueOf((float)l1 / 1000F), Integer.valueOf(j)
                            });
                    return;
                }
            }
        }

        throw new WrongUsageException("commands.debug.usage", new Object[0]);
    }

    private void func_71548_a(long par1, int par3)
    {
        File file = new File(MinecraftServer.func_71276_C().func_71209_f("debug"), (new StringBuilder()).append("profile-results-").append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append(".txt").toString());
        file.getParentFile().mkdirs();

        try
        {
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(func_71547_b(par1, par3));
            filewriter.close();
        }
        catch (Throwable throwable)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, (new StringBuilder()).append("Could not save profiler results to ").append(file).toString(), throwable);
        }
    }

    private String func_71547_b(long par1, int par3)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Profiler Results ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(func_71549_c());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time span: ").append(par1).append(" ms\n");
        stringbuilder.append("Tick span: ").append(par3).append(" ticks\n");
        stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[]
                {
                    Float.valueOf((float)par3 / ((float)par1 / 1000F))
                })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        func_71546_a(0, "root", stringbuilder);
        stringbuilder.append("--- END PROFILE DUMP ---\n\n");
        return stringbuilder.toString();
    }

    private void func_71546_a(int par1, String par2Str, StringBuilder par3StringBuilder)
    {
        List list = MinecraftServer.func_71276_C().field_71304_b.getProfilingData(par2Str);

        if (list == null || list.size() < 3)
        {
            return;
        }

        for (int i = 1; i < list.size(); i++)
        {
            ProfilerResult profilerresult = (ProfilerResult)list.get(i);
            par3StringBuilder.append(String.format("[%02d] ", new Object[]
                    {
                        Integer.valueOf(par1)
                    }));

            for (int j = 0; j < par1; j++)
            {
                par3StringBuilder.append(" ");
            }

            par3StringBuilder.append(profilerresult.field_76331_c);
            par3StringBuilder.append(" - ");
            par3StringBuilder.append(String.format("%.2f", new Object[]
                    {
                        Double.valueOf(profilerresult.field_76332_a)
                    }));
            par3StringBuilder.append("%/");
            par3StringBuilder.append(String.format("%.2f", new Object[]
                    {
                        Double.valueOf(profilerresult.field_76330_b)
                    }));
            par3StringBuilder.append("%\n");

            if (profilerresult.field_76331_c.equals("unspecified"))
            {
                continue;
            }

            try
            {
                func_71546_a(par1 + 1, (new StringBuilder()).append(par2Str).append(".").append(profilerresult.field_76331_c).toString(), par3StringBuilder);
            }
            catch (Exception exception)
            {
                par3StringBuilder.append((new StringBuilder()).append("[[ EXCEPTION ").append(exception).append(" ]]").toString());
            }
        }
    }

    private static String func_71549_c()
    {
        String as[] =
        {
            "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers",
            "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
        };

        try
        {
            return as[(int)(System.nanoTime() % (long)as.length)];
        }
        catch (Throwable throwable)
        {
            return "Witty comment unavailable :(";
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "start", "stop"
                    });
        }
        else
        {
            return null;
        }
    }
}
