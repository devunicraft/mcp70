package net.minecraft.src;

import java.util.List;

public interface ICommand extends Comparable
{
    public abstract String getCommandName();

    public abstract String getCommandUsage(ICommandSender icommandsender);

    public abstract List getCommandAliases();

    public abstract void processCommand(ICommandSender icommandsender, String as[]);

    public abstract boolean func_71519_b(ICommandSender icommandsender);

    public abstract List func_71516_a(ICommandSender icommandsender, String as[]);
}
