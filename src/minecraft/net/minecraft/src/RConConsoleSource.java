package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
    public static final RConConsoleSource field_70010_a = new RConConsoleSource();
    private StringBuffer field_70009_b;

    public RConConsoleSource()
    {
        field_70009_b = new StringBuffer();
    }

    public void func_70007_b()
    {
        field_70009_b.setLength(0);
    }

    public String func_70008_c()
    {
        return field_70009_b.toString();
    }

    public String func_70005_c_()
    {
        return "Rcon";
    }

    public void sendChatToPlayer(String par1Str)
    {
        field_70009_b.append(par1Str);
    }

    public boolean func_70003_b(String par1Str)
    {
        return true;
    }

    public String func_70004_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
}
