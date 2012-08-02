package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerPublishLocal extends CommandBase
{
    public CommandServerPublishLocal()
    {
    }

    public String getCommandName()
    {
        return "publish";
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        String s = MinecraftServer.func_71276_C().func_71206_a(EnumGameType.SURVIVAL, false);

        if (s != null)
        {
            func_71522_a(par1ICommandSender, "commands.publish.started", new Object[]
                    {
                        s
                    });
        }
        else
        {
            func_71522_a(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
