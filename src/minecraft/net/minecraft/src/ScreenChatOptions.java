package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class ScreenChatOptions extends GuiScreen
{
    private static final EnumOptions field_73891_a[];
    private final GuiScreen field_73889_b;
    private final GameSettings field_73890_c;
    private String field_73888_d;

    public ScreenChatOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        field_73889_b = par1GuiScreen;
        field_73890_c = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        int i = 0;
        field_73888_d = stringtranslate.translateKey("options.chat.title");
        EnumOptions aenumoptions[] = field_73891_a;
        int j = aenumoptions.length;

        for (int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];

            if (enumoptions.getEnumFloat())
            {
                controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, field_73890_c.getKeyBinding(enumoptions), field_73890_c.getOptionFloatValue(enumoptions)));
            }
            else
            {
                controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, field_73890_c.getKeyBinding(enumoptions)));
            }

            i++;
        }

        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
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

        if (par1GuiButton.id < 100 && (par1GuiButton instanceof GuiSmallButton))
        {
            field_73890_c.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
            par1GuiButton.displayString = field_73890_c.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
        }

        if (par1GuiButton.id == 200)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(field_73889_b);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, field_73888_d, width / 2, 20, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }

    static
    {
        field_73891_a = (new EnumOptions[]
                {
                    EnumOptions.CHAT_VISIBILITY, EnumOptions.CHAT_COLOR, EnumOptions.CHAT_LINKS, EnumOptions.CHAT_OPACITY, EnumOptions.CHAT_LINKS_PROMPT
                });
    }
}
