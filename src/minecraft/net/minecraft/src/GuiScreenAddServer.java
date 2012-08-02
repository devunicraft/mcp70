package net.minecraft.src;

import java.util.List;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen
{
    /** This GUI's parent GUI. */
    private GuiScreen parentGui;
    private GuiTextField serverAddress;
    private GuiTextField serverName;
    private ServerData field_73996_d;

    public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerData par2ServerData)
    {
        parentGui = par1GuiScreen;
        field_73996_d = par2ServerData;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        serverName.updateCursorCounter();
        serverAddress.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("addServer.add")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        serverName = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
        serverName.setFocused(true);
        serverName.setText(field_73996_d.field_78847_a);
        serverAddress = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
        serverAddress.setMaxStringLength(128);
        serverAddress.setText(field_73996_d.field_78845_b);
        ((GuiButton)controlList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
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

        if (par1GuiButton.id == 1)
        {
            parentGui.confirmClicked(false, 0);
        }
        else if (par1GuiButton.id == 0)
        {
            field_73996_d.field_78847_a = serverName.getText();
            field_73996_d.field_78845_b = serverAddress.getText();
            parentGui.confirmClicked(true, 0);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        serverName.textboxKeyTyped(par1, par2);
        serverAddress.textboxKeyTyped(par1, par2);

        if (par1 == '\t')
        {
            if (serverName.isFocused())
            {
                serverName.setFocused(false);
                serverAddress.setFocused(true);
            }
            else
            {
                serverName.setFocused(true);
                serverAddress.setFocused(false);
            }
        }

        if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;

        if (((GuiButton)controlList.get(0)).enabled)
        {
            String s = serverAddress.getText().trim();
            String as[] = s.split(":");

            if (as.length > 2)
            {
                ((GuiButton)controlList.get(0)).enabled = false;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        serverAddress.mouseClicked(par1, par2, par3);
        serverName.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("addServer.title"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("addServer.enterName"), width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, stringtranslate.translateKey("addServer.enterIp"), width / 2 - 100, 104, 0xa0a0a0);
        serverName.drawTextBox();
        serverAddress.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
