package net.minecraft.src;

public class EntityBodyHelper
{
    private EntityLiving field_75668_a;
    private int field_75666_b;
    private float field_75667_c;

    public EntityBodyHelper(EntityLiving par1EntityLiving)
    {
        field_75666_b = 0;
        field_75667_c = 0.0F;
        field_75668_a = par1EntityLiving;
    }

    public void func_75664_a()
    {
        double d = field_75668_a.posX - field_75668_a.prevPosX;
        double d1 = field_75668_a.posZ - field_75668_a.prevPosZ;

        if (d * d + d1 * d1 > 2.5000002779052011E-007D)
        {
            field_75668_a.renderYawOffset = field_75668_a.rotationYaw;
            field_75668_a.rotationYawHead = func_75665_a(field_75668_a.renderYawOffset, field_75668_a.rotationYawHead, 75F);
            field_75667_c = field_75668_a.rotationYawHead;
            field_75666_b = 0;
            return;
        }

        float f = 75F;

        if (Math.abs(field_75668_a.rotationYawHead - field_75667_c) > 15F)
        {
            field_75666_b = 0;
            field_75667_c = field_75668_a.rotationYawHead;
        }
        else
        {
            field_75666_b++;

            if (field_75666_b > 10)
            {
                f = Math.max(1.0F - (float)(field_75666_b - 10) / 10F, 0.0F) * 75F;
            }
        }

        field_75668_a.renderYawOffset = func_75665_a(field_75668_a.rotationYawHead, field_75668_a.renderYawOffset, f);
    }

    private float func_75665_a(float par1, float par2, float par3)
    {
        float f = MathHelper.func_76142_g(par1 - par2);

        if (f < -par3)
        {
            f = -par3;
        }

        if (f >= par3)
        {
            f = par3;
        }

        return par1 - f;
    }
}
