package net.minecraft.src;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScreenDemo extends GuiScreen
{
    public GuiScreenDemo()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        byte byte0 = -16;
        controlList.add(new GuiButton(1, width / 2 - 116, height / 4 + 132 + byte0, 114, 20, StatCollector.translateToLocal("demo.help.buy")));
        controlList.add(new GuiButton(2, width / 2 + 2, height / 4 + 132 + byte0, 114, 20, StatCollector.translateToLocal("demo.help.later")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            default:
                break;

            case 2:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;

            case 1:
                par1GuiButton.enabled = false;

                try
                {
                    Class class1 = Class.forName("java.awt.Desktop");
                    Object obj = class1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    class1.getMethod("browse", new Class[]
                            {
                                java.net.URI.class
                            }).invoke(obj, new Object[]
                                    {
                                        new URI("http://www.minecraft.net/")
                                    });
                }
                catch (Throwable throwable)
                {
                    throwable.printStackTrace();
                }

                break;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground()
    {
        super.drawDefaultBackground();
        int i = mc.renderEngine.getTexture("/gui/demo_bg.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - 248) / 2;
        int k = (height - 166) / 2;
        drawTexturedModalRect(j, k, 0, 0, 248, 166);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        int i = (width - 248) / 2 + 10;
        fontRenderer.drawString(StatCollector.translateToLocal("demo.help.title"), i, 44, 0x1f1f1f);
        GameSettings gamesettings = mc.gameSettings;
        byte byte0 = 60;
        String s = StatCollector.translateToLocal("demo.help.movementShort");
        s = String.format(s, new Object[]
                {
                    Keyboard.getKeyName(gamesettings.keyBindForward.keyCode), Keyboard.getKeyName(gamesettings.keyBindLeft.keyCode), Keyboard.getKeyName(gamesettings.keyBindBack.keyCode), Keyboard.getKeyName(gamesettings.keyBindRight.keyCode)
                });
        fontRenderer.drawString(s, i, byte0, 0x4f4f4f);
        s = StatCollector.translateToLocal("demo.help.movementMouse");
        fontRenderer.drawString(s, i, byte0 + 12, 0x4f4f4f);
        s = StatCollector.translateToLocal("demo.help.jump");
        s = String.format(s, new Object[]
                {
                    Keyboard.getKeyName(gamesettings.keyBindJump.keyCode)
                });
        fontRenderer.drawString(s, i, byte0 + 24, 0x4f4f4f);
        s = StatCollector.translateToLocal("demo.help.inventory");
        s = String.format(s, new Object[]
                {
                    Keyboard.getKeyName(gamesettings.keyBindInventory.keyCode)
                });
        fontRenderer.drawString(s, i, byte0 + 36, 0x4f4f4f);
        fontRenderer.drawSplitString(StatCollector.translateToLocal("demo.help.fullWrapped"), i, byte0 + 68, 218, 0x1f1f1f);
        super.drawScreen(par1, par2, par3);
    }
}
