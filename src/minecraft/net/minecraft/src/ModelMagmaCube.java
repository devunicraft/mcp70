package net.minecraft.src;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer field_78109_a[];
    ModelRenderer field_78108_b;

    public ModelMagmaCube()
    {
        field_78109_a = new ModelRenderer[8];

        for (int i = 0; i < field_78109_a.length; i++)
        {
            byte byte0 = 0;
            int j = i;

            if (i == 2)
            {
                byte0 = 24;
                j = 10;
            }
            else if (i == 3)
            {
                byte0 = 24;
                j = 19;
            }

            field_78109_a[i] = new ModelRenderer(this, byte0, j);
            field_78109_a[i].addBox(-4F, 16 + i, -4F, 8, 1, 8);
        }

        field_78108_b = new ModelRenderer(this, 0, 16);
        field_78108_b.addBox(-2F, 18F, -2F, 4, 4, 4);
    }

    public int func_78107_a()
    {
        return 5;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        EntityMagmaCube entitymagmacube = (EntityMagmaCube)par1EntityLiving;
        float f = entitymagmacube.field_70812_c + (entitymagmacube.field_70811_b - entitymagmacube.field_70812_c) * par4;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        for (int i = 0; i < field_78109_a.length; i++)
        {
            field_78109_a[i].rotationPointY = (float)(-(4 - i)) * f * 1.7F;
        }
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        field_78108_b.render(par7);
        ModelRenderer amodelrenderer[] = field_78109_a;
        int i = amodelrenderer.length;

        for (int j = 0; j < i; j++)
        {
            ModelRenderer modelrenderer = amodelrenderer[j];
            modelrenderer.render(par7);
        }
    }
}
