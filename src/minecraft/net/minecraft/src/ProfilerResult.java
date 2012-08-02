package net.minecraft.src;

public final class ProfilerResult implements Comparable
{
    public double field_76332_a;
    public double field_76330_b;
    public String field_76331_c;

    public ProfilerResult(String par1Str, double par2, double par4)
    {
        field_76331_c = par1Str;
        field_76332_a = par2;
        field_76330_b = par4;
    }

    public int func_76328_a(ProfilerResult par1ProfilerResult)
    {
        if (par1ProfilerResult.field_76332_a < field_76332_a)
        {
            return -1;
        }

        if (par1ProfilerResult.field_76332_a > field_76332_a)
        {
            return 1;
        }
        else
        {
            return par1ProfilerResult.field_76331_c.compareTo(field_76331_c);
        }
    }

    public int func_76329_a()
    {
        return (field_76331_c.hashCode() & 0xaaaaaa) + 0x444444;
    }

    public int compareTo(Object par1Obj)
    {
        return func_76328_a((ProfilerResult)par1Obj);
    }
}
