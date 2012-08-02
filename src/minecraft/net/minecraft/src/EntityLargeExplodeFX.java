package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class EntityLargeExplodeFX extends EntityFX
{
    private int field_70581_a;
    private int field_70584_aq;
    private RenderEngine field_70583_ar;
    private float field_70582_as;

    public EntityLargeExplodeFX(RenderEngine par1RenderEngine, World par2World, double par3, double par5, double par7, double par9, double par11, double par13)
    {
        super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
        field_70581_a = 0;
        field_70584_aq = 0;
        field_70583_ar = par1RenderEngine;
        field_70584_aq = 6 + rand.nextInt(4);
        particleRed = particleGreen = particleBlue = rand.nextFloat() * 0.6F + 0.4F;
        field_70582_as = 1.0F - (float)par9 * 0.5F;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        int i = (int)((((float)field_70581_a + par2) * 15F) / (float)field_70584_aq);

        if (i > 15)
        {
            return;
        }
        else
        {
            field_70583_ar.bindTexture(field_70583_ar.getTexture("/misc/explosion.png"));
            float f = (float)(i % 4) / 4F;
            float f1 = f + 0.24975F;
            float f2 = (float)(i / 4) / 4F;
            float f3 = f2 + 0.24975F;
            float f4 = 2.0F * field_70582_as;
            float f5 = (float)((prevPosX + (posX - prevPosX) * (double)par2) - interpPosX);
            float f6 = (float)((prevPosY + (posY - prevPosY) * (double)par2) - interpPosY);
            float f7 = (float)((prevPosZ + (posZ - prevPosZ) * (double)par2) - interpPosZ);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            RenderHelper.disableStandardItemLighting();
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, 1.0F);
            par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
            par1Tessellator.setBrightness(240);
            par1Tessellator.addVertexWithUV(f5 - par3 * f4 - par6 * f4, f6 - par4 * f4, f7 - par5 * f4 - par7 * f4, f1, f3);
            par1Tessellator.addVertexWithUV((f5 - par3 * f4) + par6 * f4, f6 + par4 * f4, (f7 - par5 * f4) + par7 * f4, f1, f2);
            par1Tessellator.addVertexWithUV(f5 + par3 * f4 + par6 * f4, f6 + par4 * f4, f7 + par5 * f4 + par7 * f4, f, f2);
            par1Tessellator.addVertexWithUV((f5 + par3 * f4) - par6 * f4, f6 - par4 * f4, (f7 + par5 * f4) - par7 * f4, f, f3);
            par1Tessellator.draw();
            GL11.glPolygonOffset(0.0F, 0.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            return;
        }
    }

    public int getBrightnessForRender(float par1)
    {
        return 61680;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        field_70581_a++;

        if (field_70581_a == field_70584_aq)
        {
            setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
