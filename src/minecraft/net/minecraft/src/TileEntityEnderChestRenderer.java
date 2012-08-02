package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private ModelChest field_76900_a;

    public TileEntityEnderChestRenderer()
    {
        field_76900_a = new ModelChest();
    }

    public void func_76899_a(TileEntityEnderChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
        int i = 0;

        if (par1TileEntityEnderChest.func_70309_m())
        {
            i = par1TileEntityEnderChest.getBlockMetadata();
        }

        bindTextureByName("/item/enderchest.png");
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        int j = 0;

        if (i == 2)
        {
            j = 180;
        }

        if (i == 3)
        {
            j = 0;
        }

        if (i == 4)
        {
            j = 90;
        }

        if (i == 5)
        {
            j = -90;
        }

        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f = par1TileEntityEnderChest.field_70368_b + (par1TileEntityEnderChest.field_70370_a - par1TileEntityEnderChest.field_70368_b) * par8;
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        field_76900_a.chestLid.rotateAngleX = -((f * (float)Math.PI) / 2.0F);
        field_76900_a.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        func_76899_a((TileEntityEnderChest)par1TileEntity, par2, par4, par6, par8);
    }
}
