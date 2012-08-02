package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScreenBook extends GuiScreen
{
    private final EntityPlayer field_74169_a;
    private final ItemStack field_74167_b;
    private final boolean field_74168_c;
    private boolean field_74166_d;
    private boolean field_74172_m;
    private int field_74170_n;
    private int field_74171_o;
    private int field_74180_p;
    private int field_74179_q;
    private int field_74178_r;
    private NBTTagList field_74177_s;
    private String field_74176_t;
    private GuiButtonNextPage field_74175_u;
    private GuiButtonNextPage field_74174_v;
    private GuiButton field_74173_w;
    private GuiButton field_74183_x;
    private GuiButton field_74182_y;
    private GuiButton field_74181_z;

    public GuiScreenBook(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, boolean par3)
    {
        field_74171_o = 192;
        field_74180_p = 192;
        field_74179_q = 1;
        field_74176_t = "";
        field_74169_a = par1EntityPlayer;
        field_74167_b = par2ItemStack;
        field_74168_c = par3;

        if (par2ItemStack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = par2ItemStack.getTagCompound();
            field_74177_s = nbttagcompound.getTagList("pages");

            if (field_74177_s != null)
            {
                field_74177_s = (NBTTagList)field_74177_s.copy();
                field_74179_q = field_74177_s.tagCount();

                if (field_74179_q < 1)
                {
                    field_74179_q = 1;
                }
            }
        }

        if (field_74177_s == null && par3)
        {
            field_74177_s = new NBTTagList("pages");
            field_74177_s.appendTag(new NBTTagString("1", ""));
            field_74179_q = 1;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        field_74170_n++;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        Keyboard.enableRepeatEvents(true);

        if (field_74168_c)
        {
            controlList.add(field_74183_x = new GuiButton(3, width / 2 - 100, 4 + field_74180_p, 98, 20, StatCollector.translateToLocal("book.signButton")));
            controlList.add(field_74173_w = new GuiButton(0, width / 2 + 2, 4 + field_74180_p, 98, 20, StatCollector.translateToLocal("gui.done")));
            controlList.add(field_74182_y = new GuiButton(5, width / 2 - 100, 4 + field_74180_p, 98, 20, StatCollector.translateToLocal("book.finalizeButton")));
            controlList.add(field_74181_z = new GuiButton(4, width / 2 + 2, 4 + field_74180_p, 98, 20, StatCollector.translateToLocal("gui.cancel")));
        }
        else
        {
            controlList.add(field_74173_w = new GuiButton(0, width / 2 - 100, 4 + field_74180_p, 200, 20, StatCollector.translateToLocal("gui.done")));
        }

        int i = (width - field_74171_o) / 2;
        byte byte0 = 2;
        controlList.add(field_74175_u = new GuiButtonNextPage(1, i + 120, byte0 + 154, true));
        controlList.add(field_74174_v = new GuiButtonNextPage(2, i + 38, byte0 + 154, false));
        func_74161_g();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    private void func_74161_g()
    {
        field_74175_u.drawButton = !field_74172_m && (field_74178_r < field_74179_q - 1 || field_74168_c);
        field_74174_v.drawButton = !field_74172_m && field_74178_r > 0;
        field_74173_w.drawButton = !field_74168_c || !field_74172_m;

        if (field_74168_c)
        {
            field_74183_x.drawButton = !field_74172_m;
            field_74181_z.drawButton = field_74172_m;
            field_74182_y.drawButton = field_74172_m;
            field_74182_y.enabled = field_74176_t.trim().length() > 0;
        }
    }

    private void func_74163_a(boolean par1)
    {
        if (!field_74168_c || !field_74166_d)
        {
            return;
        }

        if (field_74177_s != null)
        {
            do
            {
                if (field_74177_s.tagCount() <= 1)
                {
                    break;
                }

                NBTTagString nbttagstring = (NBTTagString)field_74177_s.tagAt(field_74177_s.tagCount() - 1);

                if (nbttagstring.data != null && nbttagstring.data.length() != 0)
                {
                    break;
                }

                field_74177_s.func_74744_a(field_74177_s.tagCount() - 1);
            }
            while (true);

            if (field_74167_b.hasTagCompound())
            {
                NBTTagCompound nbttagcompound = field_74167_b.getTagCompound();
                nbttagcompound.setTag("pages", field_74177_s);
            }
            else
            {
                field_74167_b.func_77983_a("pages", field_74177_s);
            }

            String s = "MC|BEdit";

            if (par1)
            {
                s = "MC|BSign";
                field_74167_b.func_77983_a("author", new NBTTagString("author", field_74169_a.username));
                field_74167_b.func_77983_a("title", new NBTTagString("title", field_74176_t.trim()));
                field_74167_b.itemID = Item.writtenBook.shiftedIndex;
            }

            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);

            try
            {
                Packet.writeItemStack(field_74167_b, dataoutputstream);
                mc.getSendQueue().addToSendQueue(new Packet250CustomPayload(s, bytearrayoutputstream.toByteArray()));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
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

        if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(null);
            func_74163_a(false);
        }
        else if (par1GuiButton.id == 3 && field_74168_c)
        {
            field_74172_m = true;
        }
        else if (par1GuiButton.id == 1)
        {
            if (field_74178_r < field_74179_q - 1)
            {
                field_74178_r++;
            }
            else if (field_74168_c)
            {
                func_74165_h();

                if (field_74178_r < field_74179_q - 1)
                {
                    field_74178_r++;
                }
            }
        }
        else if (par1GuiButton.id == 2)
        {
            if (field_74178_r > 0)
            {
                field_74178_r--;
            }
        }
        else if (par1GuiButton.id == 5 && field_74172_m)
        {
            func_74163_a(true);
            mc.displayGuiScreen(null);
        }
        else if (par1GuiButton.id == 4 && field_74172_m)
        {
            field_74172_m = false;
        }

        func_74161_g();
    }

    private void func_74165_h()
    {
        if (field_74177_s == null || field_74177_s.tagCount() >= 50)
        {
            return;
        }
        else
        {
            field_74177_s.appendTag(new NBTTagString((new StringBuilder()).append("").append(field_74179_q + 1).toString(), ""));
            field_74179_q++;
            field_74166_d = true;
            return;
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);

        if (!field_74168_c)
        {
            return;
        }

        if (field_74172_m)
        {
            func_74162_c(par1, par2);
        }
        else
        {
            func_74164_b(par1, par2);
        }
    }

    private void func_74164_b(char par1, int par2)
    {
        switch (par1)
        {
            case 22:
                func_74160_b(GuiScreen.getClipboardString());
                return;
        }

        switch (par2)
        {
            case 14:
                String s = func_74158_i();

                if (s.length() > 0)
                {
                    func_74159_a(s.substring(0, s.length() - 1));
                }

                return;

            case 28:
                func_74160_b("\n");
                return;
        }

        if (ChatAllowedCharacters.isAllowedCharacter(par1))
        {
            func_74160_b(Character.toString(par1));
            return;
        }
        else
        {
            return;
        }
    }

    private void func_74162_c(char par1, int par2)
    {
        switch (par2)
        {
            case 14:
                if (field_74176_t.length() > 0)
                {
                    field_74176_t = field_74176_t.substring(0, field_74176_t.length() - 1);
                    func_74161_g();
                }

                return;

            case 28:
                if (field_74176_t.length() > 0)
                {
                    func_74163_a(true);
                    mc.displayGuiScreen(null);
                }

                return;
        }

        if (field_74176_t.length() >= 16 || !ChatAllowedCharacters.isAllowedCharacter(par1))
        {
            return;
        }

        field_74176_t += Character.toString(par1);
        func_74161_g();
        field_74166_d = true;
        return;
    }

    private String func_74158_i()
    {
        if (field_74177_s != null && field_74178_r >= 0 && field_74178_r < field_74177_s.tagCount())
        {
            NBTTagString nbttagstring = (NBTTagString)field_74177_s.tagAt(field_74178_r);
            return nbttagstring.toString();
        }
        else
        {
            return "";
        }
    }

    private void func_74159_a(String par1Str)
    {
        if (field_74177_s != null && field_74178_r >= 0 && field_74178_r < field_74177_s.tagCount())
        {
            NBTTagString nbttagstring = (NBTTagString)field_74177_s.tagAt(field_74178_r);
            nbttagstring.data = par1Str;
            field_74166_d = true;
        }
    }

    private void func_74160_b(String par1Str)
    {
        String s = func_74158_i();
        String s1 = (new StringBuilder()).append(s).append(par1Str).toString();
        int i = fontRenderer.splitStringWidth((new StringBuilder()).append(s1).append("\2470_").toString(), 118);

        if (i <= 118 && s1.length() < 256)
        {
            func_74159_a(s1);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        int i = mc.renderEngine.getTexture("/gui/book.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - field_74171_o) / 2;
        byte byte0 = 2;
        drawTexturedModalRect(j, byte0, 0, 0, field_74171_o, field_74180_p);

        if (field_74172_m)
        {
            String s = field_74176_t;

            if (field_74168_c)
            {
                if ((field_74170_n / 6) % 2 == 0)
                {
                    s = (new StringBuilder()).append(s).append("\2470_").toString();
                }
                else
                {
                    s = (new StringBuilder()).append(s).append("\2477_").toString();
                }
            }

            String s2 = StatCollector.translateToLocal("book.editTitle");
            int k = fontRenderer.getStringWidth(s2);
            fontRenderer.drawString(s2, j + 36 + (116 - k) / 2, byte0 + 16 + 16, 0);
            int i1 = fontRenderer.getStringWidth(s);
            fontRenderer.drawString(s, j + 36 + (116 - i1) / 2, byte0 + 48, 0);
            String s4 = String.format(StatCollector.translateToLocal("book.byAuthor"), new Object[]
                    {
                        field_74169_a.username
                    });
            int j1 = fontRenderer.getStringWidth(s4);
            fontRenderer.drawString((new StringBuilder()).append("\2478").append(s4).toString(), j + 36 + (116 - j1) / 2, byte0 + 48 + 10, 0);
            String s5 = StatCollector.translateToLocal("book.finalizeWarning");
            fontRenderer.drawSplitString(s5, j + 36, byte0 + 80, 116, 0);
        }
        else
        {
            String s1 = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[]
                    {
                        Integer.valueOf(field_74178_r + 1), Integer.valueOf(field_74179_q)
                    });
            String s3 = "";

            if (field_74177_s != null && field_74178_r >= 0 && field_74178_r < field_74177_s.tagCount())
            {
                NBTTagString nbttagstring = (NBTTagString)field_74177_s.tagAt(field_74178_r);
                s3 = nbttagstring.toString();
            }

            if (field_74168_c)
            {
                if (fontRenderer.getBidiFlag())
                {
                    s3 = (new StringBuilder()).append(s3).append("_").toString();
                }
                else if ((field_74170_n / 6) % 2 == 0)
                {
                    s3 = (new StringBuilder()).append(s3).append("\2470_").toString();
                }
                else
                {
                    s3 = (new StringBuilder()).append(s3).append("\2477_").toString();
                }
            }

            int l = fontRenderer.getStringWidth(s1);
            fontRenderer.drawString(s1, ((j - l) + field_74171_o) - 44, byte0 + 16, 0);
            fontRenderer.drawSplitString(s3, j + 36, byte0 + 16 + 16, 116, 0);
        }

        super.drawScreen(par1, par2, par3);
    }
}
