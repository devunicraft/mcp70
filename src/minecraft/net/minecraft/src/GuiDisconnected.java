package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiDisconnected extends GuiScreen
{
    /** The error message. */
    private String errorMessage;

    /** The details about the error. */
    private String errorDetail;
    private Object field_74247_c[];
    private List field_74245_d;

    public GuiDisconnected(String par1Str, String par2Str, Object par3ArrayOfObj[])
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        errorMessage = stringtranslate.translateKey(par1Str);
        errorDetail = par2Str;
        field_74247_c = par3ArrayOfObj;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.toMenu")));

        if (field_74247_c != null)
        {
            field_74245_d = fontRenderer.listFormattedStringToWidth(stringtranslate.translateKeyFormat(errorDetail, field_74247_c), width - 50);
        }
        else
        {
            field_74245_d = fontRenderer.listFormattedStringToWidth(stringtranslate.translateKey(errorDetail), width - 50);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, errorMessage, width / 2, height / 2 - 50, 0xaaaaaa);
        int i = height / 2 - 30;

        if (field_74245_d == null)
        {
            initGui();
        }

        for (Iterator iterator = field_74245_d.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            drawCenteredString(fontRenderer, s, width / 2, i, 0xffffff);
            i += 5;
        }

        super.drawScreen(par1, par2, par3);
    }
}
