package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;

class GuiSlotLanguage extends GuiSlot
{
    private ArrayList field_77251_g;
    private TreeMap field_77253_h;
    final GuiLanguage field_77252_a;

    public GuiSlotLanguage(GuiLanguage par1GuiLanguage)
    {
        super(par1GuiLanguage.mc, par1GuiLanguage.width, par1GuiLanguage.height, 32, (par1GuiLanguage.height - 65) + 4, 18);
        field_77252_a = par1GuiLanguage;
        field_77253_h = StringTranslate.getInstance().getLanguageList();
        field_77251_g = new ArrayList();
        String s;

        for (Iterator iterator = field_77253_h.keySet().iterator(); iterator.hasNext(); field_77251_g.add(s))
        {
            s = (String)iterator.next();
        }
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return field_77251_g.size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        StringTranslate.getInstance().setLanguage((String)field_77251_g.get(par1));
        field_77252_a.mc.fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
        GuiLanguage.Returns(field_77252_a).language = (String)field_77251_g.get(par1);
        field_77252_a.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.Returns(field_77252_a).language));
        GuiLanguage.getDoneButton(field_77252_a).displayString = StringTranslate.getInstance().translateKey("gui.done");
        GuiLanguage.Returns(field_77252_a).saveOptions();
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return ((String)field_77251_g.get(par1)).equals(StringTranslate.getInstance().getCurrentLanguage());
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return getSize() * 18;
    }

    protected void drawBackground()
    {
        field_77252_a.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        field_77252_a.fontRenderer.setBidiFlag(true);
        field_77252_a.drawCenteredString(field_77252_a.fontRenderer, (String)field_77253_h.get(field_77251_g.get(par1)), field_77252_a.width / 2, par3 + 1, 0xffffff);
        field_77252_a.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.Returns(field_77252_a).language));
    }
}
