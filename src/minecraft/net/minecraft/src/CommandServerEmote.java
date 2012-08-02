package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerEmote extends CommandBase
{
    public CommandServerEmote()
    {
    }

    public String getCommandName()
    {
        return "me";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.me.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            String s = func_71520_a(par2ArrayOfStr, 0);
            MinecraftServer.func_71276_C().func_71203_ab().sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("* ").append(par1ICommandSender.func_70005_c_()).append(" ").append(s).toString()));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        return func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z());
    }
}
