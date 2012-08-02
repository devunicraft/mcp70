package net.minecraft.src;

import java.util.List;
import java.util.Map;

public interface ICommandManager
{
    public abstract void executeCommand(ICommandSender icommandsender, String s);

    public abstract List func_71558_b(ICommandSender icommandsender, String s);

    public abstract List func_71557_a(ICommandSender icommandsender);

    public abstract Map func_71555_a();
}
