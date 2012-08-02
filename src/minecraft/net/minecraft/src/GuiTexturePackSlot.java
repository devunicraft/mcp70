package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiTexturePackSlot extends GuiSlot
{
    final GuiTexturePacks parentTexturePackGui;

    public GuiTexturePackSlot(GuiTexturePacks par1GuiTexturePacks)
    {
        super(GuiTexturePacks.func_73950_a(par1GuiTexturePacks), par1GuiTexturePacks.width, par1GuiTexturePacks.height, 32, (par1GuiTexturePacks.height - 55) + 4, 36);
        parentTexturePackGui = par1GuiTexturePacks;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiTexturePacks.func_73955_b(parentTexturePackGui).texturePackList.availableTexturePacks().size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        List list = GuiTexturePacks.func_73958_c(parentTexturePackGui).texturePackList.availableTexturePacks();

        try
        {
            GuiTexturePacks.func_73951_d(parentTexturePackGui).texturePackList.setTexturePack((TexturePackBase)list.get(par1));
            GuiTexturePacks.func_73952_e(parentTexturePackGui).renderEngine.refreshTextures();
        }
        catch (Exception exception)
        {
            GuiTexturePacks.func_73962_f(parentTexturePackGui).texturePackList.setTexturePack((TexturePackBase)list.get(0));
            GuiTexturePacks.func_73959_g(parentTexturePackGui).renderEngine.refreshTextures();
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        List list = GuiTexturePacks.func_73957_h(parentTexturePackGui).texturePackList.availableTexturePacks();
        return GuiTexturePacks.func_73956_i(parentTexturePackGui).texturePackList.func_77292_e() == list.get(par1);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return getSize() * 36;
    }

    protected void drawBackground()
    {
        parentTexturePackGui.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        TexturePackBase texturepackbase = (TexturePackBase)GuiTexturePacks.func_73953_j(parentTexturePackGui).texturePackList.availableTexturePacks().get(par1);
        texturepackbase.func_77535_b(GuiTexturePacks.func_73961_k(parentTexturePackGui).renderEngine);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        par5Tessellator.startDrawingQuads();
        par5Tessellator.setColorOpaque_I(0xffffff);
        par5Tessellator.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
        par5Tessellator.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
        par5Tessellator.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
        par5Tessellator.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
        par5Tessellator.draw();
        parentTexturePackGui.drawString(GuiTexturePacks.func_73960_l(parentTexturePackGui), texturepackbase.func_77538_c(), par2 + 32 + 2, par3 + 1, 0xffffff);
        parentTexturePackGui.drawString(GuiTexturePacks.func_73963_m(parentTexturePackGui), texturepackbase.func_77531_d(), par2 + 32 + 2, par3 + 12, 0x808080);
        parentTexturePackGui.drawString(GuiTexturePacks.func_73954_n(parentTexturePackGui), texturepackbase.func_77537_e(), par2 + 32 + 2, par3 + 12 + 10, 0x808080);
    }
}
