package net.minecraft.src;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;

public class RenderList
{
    private int field_78429_a;
    private int field_78427_b;
    private int field_78428_c;
    private double field_78425_d;
    private double field_78426_e;
    private double field_78423_f;
    private IntBuffer field_78424_g;
    private boolean field_78430_h;
    private boolean field_78431_i;

    public RenderList()
    {
        field_78424_g = GLAllocation.createDirectIntBuffer(0x10000);
        field_78430_h = false;
        field_78431_i = false;
    }

    public void func_78422_a(int par1, int par2, int par3, double par4, double par6, double par8)
    {
        field_78430_h = true;
        field_78424_g.clear();
        field_78429_a = par1;
        field_78427_b = par2;
        field_78428_c = par3;
        field_78425_d = par4;
        field_78426_e = par6;
        field_78423_f = par8;
    }

    public boolean func_78418_a(int par1, int par2, int par3)
    {
        if (!field_78430_h)
        {
            return false;
        }
        else
        {
            return par1 == field_78429_a && par2 == field_78427_b && par3 == field_78428_c;
        }
    }

    public void func_78420_a(int par1)
    {
        field_78424_g.put(par1);

        if (field_78424_g.remaining() == 0)
        {
            func_78419_a();
        }
    }

    public void func_78419_a()
    {
        if (!field_78430_h)
        {
            return;
        }

        if (!field_78431_i)
        {
            field_78424_g.flip();
            field_78431_i = true;
        }

        if (field_78424_g.remaining() > 0)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((double)field_78429_a - field_78425_d), (float)((double)field_78427_b - field_78426_e), (float)((double)field_78428_c - field_78423_f));
            GL11.glCallLists(field_78424_g);
            GL11.glPopMatrix();
        }
    }

    public void func_78421_b()
    {
        field_78430_h = false;
        field_78431_i = false;
    }
}
