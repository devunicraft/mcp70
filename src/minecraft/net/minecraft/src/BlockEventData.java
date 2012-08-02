package net.minecraft.src;

public class BlockEventData
{
    private int field_76927_a;
    private int field_76925_b;
    private int field_76926_c;
    private int field_76923_d;
    private int field_76924_e;
    private int field_76922_f;

    public BlockEventData(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        field_76927_a = par1;
        field_76925_b = par2;
        field_76926_c = par3;
        field_76924_e = par5;
        field_76922_f = par6;
        field_76923_d = par4;
    }

    public int func_76919_a()
    {
        return field_76927_a;
    }

    public int func_76921_b()
    {
        return field_76925_b;
    }

    public int func_76920_c()
    {
        return field_76926_c;
    }

    public int func_76918_d()
    {
        return field_76924_e;
    }

    public int func_76917_e()
    {
        return field_76922_f;
    }

    public int func_76916_f()
    {
        return field_76923_d;
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj instanceof BlockEventData)
        {
            BlockEventData blockeventdata = (BlockEventData)par1Obj;
            return field_76927_a == blockeventdata.field_76927_a && field_76925_b == blockeventdata.field_76925_b && field_76926_c == blockeventdata.field_76926_c && field_76924_e == blockeventdata.field_76924_e && field_76922_f == blockeventdata.field_76922_f && field_76923_d == blockeventdata.field_76923_d;
        }
        else
        {
            return false;
        }
    }
}
