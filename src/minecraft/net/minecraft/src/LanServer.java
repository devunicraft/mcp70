package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class LanServer
{
    private String field_77492_a;
    private String field_77490_b;
    private long field_77491_c;

    public LanServer(String par1Str, String par2Str)
    {
        field_77492_a = par1Str;
        field_77490_b = par2Str;
        field_77491_c = Minecraft.func_71386_F();
    }

    public String func_77487_a()
    {
        return field_77492_a;
    }

    public String func_77488_b()
    {
        return field_77490_b;
    }

    public void func_77489_c()
    {
        field_77491_c = Minecraft.func_71386_F();
    }
}
