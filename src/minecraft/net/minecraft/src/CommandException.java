package net.minecraft.src;

public class CommandException extends RuntimeException
{
    private Object field_74845_a[];

    public CommandException(String par1Str, Object par2ArrayOfObj[])
    {
        super(par1Str);
        field_74845_a = par2ArrayOfObj;
    }

    public Object[] func_74844_a()
    {
        return field_74845_a;
    }
}
