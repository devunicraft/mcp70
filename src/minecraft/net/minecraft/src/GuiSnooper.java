package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen field_74100_a;
    private final GameSettings field_74097_b;
    private final List field_74098_c = new ArrayList();
    private final List field_74096_d = new ArrayList();
    private String field_74103_m;
    private String field_74101_n[];
    private GuiSnooperList field_74102_o;
    private GuiButton field_74099_p;

    public GuiSnooper(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        field_74100_a = par1GuiScreen;
        field_74097_b = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        field_74103_m = StatCollector.translateToLocal("options.snooper.title");
        String s = StatCollector.translateToLocal("options.snooper.desc");
        ArrayList arraylist = new ArrayList();
        String s1;

        for (Iterator iterator = fontRenderer.listFormattedStringToWidth(s, width - 30).iterator(); iterator.hasNext(); arraylist.add(s1))
        {
            s1 = (String)iterator.next();
        }

        field_74101_n = (String[])arraylist.toArray(new String[0]);
        field_74098_c.clear();
        field_74096_d.clear();
        controlList.add(field_74099_p = new GuiButton(1, width / 2 - 152, height - 30, 150, 20, field_74097_b.getKeyBinding(EnumOptions.SNOOPER_ENABLED)));
        controlList.add(new GuiButton(2, width / 2 + 2, height - 30, 150, 20, StatCollector.translateToLocal("gui.done")));
        java.util.Map.Entry entry;

        for (Iterator iterator1 = (new TreeMap(mc.func_71378_E().func_76465_c())).entrySet().iterator(); iterator1.hasNext(); field_74096_d.add(fontRenderer.trimStringToWidth((String)entry.getValue(), width - 210)))
        {
            entry = (java.util.Map.Entry)iterator1.next();
            field_74098_c.add(entry.getKey());
        }

        field_74102_o = new GuiSnooperList(this);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 2)
        {
            field_74097_b.saveOptions();
            field_74097_b.saveOptions();
            mc.displayGuiScreen(field_74100_a);
        }

        if (par1GuiButton.id == 1)
        {
            field_74097_b.setOptionValue(EnumOptions.SNOOPER_ENABLED, 1);
            field_74099_p.displayString = field_74097_b.getKeyBinding(EnumOptions.SNOOPER_ENABLED);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        field_74102_o.drawScreen(par1, par2, par3);
        drawCenteredString(fontRenderer, field_74103_m, width / 2, 8, 0xffffff);
        int i = 22;
        String as[] = field_74101_n;
        int j = as.length;

        for (int k = 0; k < j; k++)
        {
            String s = as[k];
            drawCenteredString(fontRenderer, s, width / 2, i, 0x808080);
            i += fontRenderer.FONT_HEIGHT;
        }

        super.drawScreen(par1, par2, par3);
    }

    static List func_74095_a(GuiSnooper par0GuiSnooper)
    {
        return par0GuiSnooper.field_74098_c;
    }

    static List func_74094_b(GuiSnooper par0GuiSnooper)
    {
        return par0GuiSnooper.field_74096_d;
    }
}
