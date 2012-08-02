package net.minecraft.src;

public class ModelIronGolem extends ModelBase
{
    /** The head model for the iron golem. */
    public ModelRenderer ironGolemHead;

    /** The body model for the iron golem. */
    public ModelRenderer ironGolemBody;

    /** The right arm model for the iron golem. */
    public ModelRenderer ironGolemRightArm;

    /** The left arm model for the iron golem. */
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer field_78175_e;
    public ModelRenderer field_78173_f;

    public ModelIronGolem()
    {
        this(0.0F);
    }

    public ModelIronGolem(float par1)
    {
        this(par1, -7F);
    }

    public ModelIronGolem(float par1, float par2)
    {
        char c = '\200';
        char c1 = '\200';
        ironGolemHead = (new ModelRenderer(this)).setTextureSize(c, c1);
        ironGolemHead.setRotationPoint(0.0F, 0.0F + par2, -2F);
        ironGolemHead.setTextureOffset(0, 0).addBox(-4F, -12F, -5.5F, 8, 10, 8, par1);
        ironGolemHead.setTextureOffset(24, 0).addBox(-1F, -5F, -7.5F, 2, 4, 2, par1);
        ironGolemBody = (new ModelRenderer(this)).setTextureSize(c, c1);
        ironGolemBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
        ironGolemBody.setTextureOffset(0, 40).addBox(-9F, -2F, -6F, 18, 12, 11, par1);
        ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10F, -3F, 9, 5, 6, par1 + 0.5F);
        ironGolemRightArm = (new ModelRenderer(this)).setTextureSize(c, c1);
        ironGolemRightArm.setRotationPoint(0.0F, -7F, 0.0F);
        ironGolemRightArm.setTextureOffset(60, 21).addBox(-13F, -2.5F, -3F, 4, 30, 6, par1);
        ironGolemLeftArm = (new ModelRenderer(this)).setTextureSize(c, c1);
        ironGolemLeftArm.setRotationPoint(0.0F, -7F, 0.0F);
        ironGolemLeftArm.setTextureOffset(60, 58).addBox(9F, -2.5F, -3F, 4, 30, 6, par1);
        field_78175_e = (new ModelRenderer(this, 0, 22)).setTextureSize(c, c1);
        field_78175_e.setRotationPoint(-4F, 18F + par2, 0.0F);
        field_78175_e.setTextureOffset(37, 0).addBox(-3.5F, -3F, -3F, 6, 16, 5, par1);
        field_78173_f = (new ModelRenderer(this, 0, 22)).setTextureSize(c, c1);
        field_78173_f.mirror = true;
        field_78173_f.setTextureOffset(60, 0).setRotationPoint(5F, 18F + par2, 0.0F);
        field_78173_f.addBox(-3.5F, -3F, -3F, 6, 16, 5, par1);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        ironGolemHead.render(par7);
        ironGolemBody.render(par7);
        field_78175_e.render(par7);
        field_78173_f.render(par7);
        ironGolemRightArm.render(par7);
        ironGolemLeftArm.render(par7);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        ironGolemHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        ironGolemHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        field_78175_e.rotateAngleX = -1.5F * func_78172_a(par1, 13F) * par2;
        field_78173_f.rotateAngleX = 1.5F * func_78172_a(par1, 13F) * par2;
        field_78175_e.rotateAngleY = 0.0F;
        field_78173_f.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        EntityIronGolem entityirongolem = (EntityIronGolem)par1EntityLiving;
        int i = entityirongolem.func_70854_o();

        if (i > 0)
        {
            ironGolemRightArm.rotateAngleX = -2F + 1.5F * func_78172_a((float)i - par4, 10F);
            ironGolemLeftArm.rotateAngleX = -2F + 1.5F * func_78172_a((float)i - par4, 10F);
        }
        else
        {
            int j = entityirongolem.func_70853_p();

            if (j > 0)
            {
                ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * func_78172_a(j, 70F);
                ironGolemLeftArm.rotateAngleX = 0.0F;
            }
            else
            {
                ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * func_78172_a(par2, 13F)) * par3;
                ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * func_78172_a(par2, 13F)) * par3;
            }
        }
    }

    private float func_78172_a(float par1, float par2)
    {
        return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
    }
}
