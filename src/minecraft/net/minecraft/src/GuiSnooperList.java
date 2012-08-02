package net.minecraft.src;

import java.util.List;

class GuiSnooperList extends GuiSlot
{
    final GuiSnooper field_77255_a;

    public GuiSnooperList(GuiSnooper par1GuiSnooper)
    {
        super(par1GuiSnooper.mc, par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.fontRenderer.FONT_HEIGHT + 1);
        field_77255_a = par1GuiSnooper;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiSnooper.func_74095_a(field_77255_a).size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int i, boolean flag)
    {
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return false;
    }

    protected void drawBackground()
    {
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        field_77255_a.fontRenderer.drawString((String)GuiSnooper.func_74095_a(field_77255_a).get(par1), 10, par3, 0xffffff);
        field_77255_a.fontRenderer.drawString((String)GuiSnooper.func_74094_b(field_77255_a).get(par1), 220, par3, 0xffffff);
    }

    protected int func_77225_g()
    {
        return field_77255_a.width - 10;
    }
}
