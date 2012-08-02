package net.minecraft.src;

public class ChatLine
{
    private final int field_74543_a;
    private final String field_74541_b;
    private final int field_74542_c;

    public ChatLine(int par1, String par2Str, int par3)
    {
        field_74541_b = par2Str;
        field_74543_a = par1;
        field_74542_c = par3;
    }

    public String func_74538_a()
    {
        return field_74541_b;
    }

    public int func_74540_b()
    {
        return field_74543_a;
    }

    public int func_74539_c()
    {
        return field_74542_c;
    }
}
