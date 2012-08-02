package net.minecraft.src;

import java.util.List;

public abstract class GuiConfirmOpenLink extends GuiYesNo
{
    private String field_73947_a;
    private String field_73946_b;

    public GuiConfirmOpenLink(GuiScreen par1GuiScreen, String par2Str, int par3)
    {
        super(par1GuiScreen, StringTranslate.getInstance().translateKey("chat.link.confirm"), par2Str, par3);
        StringTranslate stringtranslate = StringTranslate.getInstance();
        buttonText1 = stringtranslate.translateKey("gui.yes");
        buttonText2 = stringtranslate.translateKey("gui.no");
        field_73946_b = stringtranslate.translateKey("chat.copy");
        field_73947_a = stringtranslate.translateKey("chat.link.warning");
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.add(new GuiButton(0, (width / 3 - 83) + 0, height / 6 + 96, 100, 20, buttonText1));
        controlList.add(new GuiButton(2, (width / 3 - 83) + 105, height / 6 + 96, 100, 20, field_73946_b));
        controlList.add(new GuiButton(1, (width / 3 - 83) + 210, height / 6 + 96, 100, 20, buttonText2));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 2)
        {
            func_73945_e();
            super.actionPerformed((GuiButton)controlList.get(1));
        }
        else
        {
            super.actionPerformed(par1GuiButton);
        }
    }

    public abstract void func_73945_e();

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        drawCenteredString(fontRenderer, field_73947_a, width / 2, 110, 0xffcccc);
    }
}
