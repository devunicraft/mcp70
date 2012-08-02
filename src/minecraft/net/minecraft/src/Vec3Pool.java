package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
    private final int field_72351_a;
    private final int field_72349_b;
    private final List field_72350_c = new ArrayList();
    private int field_72347_d;
    private int field_72348_e;
    private int field_72346_f;

    public Vec3Pool(int par1, int par2)
    {
        field_72347_d = 0;
        field_72348_e = 0;
        field_72346_f = 0;
        field_72351_a = par1;
        field_72349_b = par2;
    }

    public Vec3 func_72345_a(double par1, double par3, double par5)
    {
        Vec3 vec3;

        if (field_72347_d >= field_72350_c.size())
        {
            vec3 = new Vec3(par1, par3, par5);
            field_72350_c.add(vec3);
        }
        else
        {
            vec3 = (Vec3)field_72350_c.get(field_72347_d);
            vec3.setComponents(par1, par3, par5);
        }

        field_72347_d++;
        return vec3;
    }

    public void func_72343_a()
    {
        if (field_72347_d > field_72348_e)
        {
            field_72348_e = field_72347_d;
        }

        if (field_72346_f++ == field_72351_a)
        {
            for (int i = Math.max(field_72348_e, field_72350_c.size() - field_72349_b); field_72350_c.size() > i; field_72350_c.remove(i)) { }

            field_72348_e = 0;
            field_72346_f = 0;
        }

        field_72347_d = 0;
    }

    public void func_72344_b()
    {
        field_72347_d = 0;
        field_72350_c.clear();
    }
}
