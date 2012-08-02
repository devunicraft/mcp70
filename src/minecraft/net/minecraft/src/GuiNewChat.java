package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiNewChat extends Gui
{
    private final Minecraft field_73772_a;
    private final List field_73770_b = new ArrayList();
    private final List field_73771_c = new ArrayList();
    private int field_73768_d;
    private boolean field_73769_e;

    public GuiNewChat(Minecraft par1Minecraft)
    {
        field_73768_d = 0;
        field_73769_e = false;
        field_73772_a = par1Minecraft;
    }

    public void func_73762_a(int par1)
    {
        if (field_73772_a.gameSettings.chatVisibility == 2)
        {
            return;
        }

        byte byte0 = 10;
        boolean flag = false;
        int i = 0;
        int j = field_73771_c.size();
        float f = field_73772_a.gameSettings.chatOpacity * 0.9F + 0.1F;

        if (j <= 0)
        {
            return;
        }

        if (func_73760_d())
        {
            byte0 = 20;
            flag = true;
        }

        for (int k = 0; k + field_73768_d < field_73771_c.size() && k < byte0; k++)
        {
            ChatLine chatline = (ChatLine)field_73771_c.get(k + field_73768_d);

            if (chatline == null)
            {
                continue;
            }

            int j1 = par1 - chatline.func_74540_b();

            if (j1 >= 200 && !flag)
            {
                continue;
            }

            double d = (double)j1 / 200D;
            d = 1.0D - d;
            d *= 10D;

            if (d < 0.0D)
            {
                d = 0.0D;
            }

            if (d > 1.0D)
            {
                d = 1.0D;
            }

            d *= d;
            int j2 = (int)(255D * d);

            if (flag)
            {
                j2 = 255;
            }

            j2 = (int)((float)j2 * f);
            i++;

            if (j2 <= 3)
            {
                continue;
            }

            byte byte1 = 3;
            int l2 = -k * 9;
            drawRect(byte1, l2 - 1, byte1 + 320 + 4, l2 + 8, j2 / 2 << 24);
            GL11.glEnable(GL11.GL_BLEND);
            String s = chatline.func_74538_a();

            if (!field_73772_a.gameSettings.chatColours)
            {
                s = StringUtils.func_76338_a(s);
            }

            field_73772_a.fontRenderer.drawStringWithShadow(s, byte1, l2, 0xffffff + (j2 << 24));
        }

        if (flag)
        {
            int l = field_73772_a.fontRenderer.FONT_HEIGHT;
            GL11.glTranslatef(0.0F, l, 0.0F);
            int i1 = j * l + j;
            int k1 = i * l + i;
            int l1 = (field_73768_d * k1) / j;
            int i2 = (k1 * k1) / i1;

            if (i1 != k1)
            {
                char c = l1 <= 0 ? '`' : '\252';
                int k2 = field_73769_e ? 0xcc3333 : 0x3333aa;
                drawRect(0, -l1, 2, -l1 - i2, k2 + (c << 24));
                drawRect(2, -l1, 1, -l1 - i2, 0xcccccc + (c << 24));
            }
        }
    }

    public void func_73761_a()
    {
        field_73771_c.clear();
        field_73770_b.clear();
    }

    public void func_73765_a(String par1Str)
    {
        func_73763_a(par1Str, 0);
    }

    public void func_73763_a(String par1Str, int par2)
    {
        boolean flag = func_73760_d();
        boolean flag1 = true;

        if (par2 != 0)
        {
            func_73759_c(par2);
        }

        String s;

        for (Iterator iterator = field_73772_a.fontRenderer.listFormattedStringToWidth(par1Str, 320).iterator(); iterator.hasNext(); field_73771_c.add(0, new ChatLine(field_73772_a.ingameGUI.func_73834_c(), s, par2)))
        {
            s = (String)iterator.next();

            if (flag && field_73768_d > 0)
            {
                field_73769_e = true;
                func_73758_b(1);
            }

            if (!flag1)
            {
                s = (new StringBuilder()).append(" ").append(s).toString();
            }

            flag1 = false;
        }

        for (; field_73771_c.size() > 100; field_73771_c.remove(field_73771_c.size() - 1)) { }
    }

    public List func_73756_b()
    {
        return field_73770_b;
    }

    public void func_73767_b(String par1Str)
    {
        if (field_73770_b.isEmpty() || !((String)field_73770_b.get(field_73770_b.size() - 1)).equals(par1Str))
        {
            field_73770_b.add(par1Str);
        }
    }

    public void func_73764_c()
    {
        field_73768_d = 0;
        field_73769_e = false;
    }

    public void func_73758_b(int par1)
    {
        field_73768_d += par1;
        int i = field_73771_c.size();

        if (field_73768_d > i - 20)
        {
            field_73768_d = i - 20;
        }

        if (field_73768_d <= 0)
        {
            field_73768_d = 0;
            field_73769_e = false;
        }
    }

    public ChatClickData func_73766_a(int par1, int par2)
    {
        if (!func_73760_d())
        {
            return null;
        }

        ScaledResolution scaledresolution = new ScaledResolution(field_73772_a.gameSettings, field_73772_a.displayWidth, field_73772_a.displayHeight);
        int i = scaledresolution.func_78325_e();
        int j = par1 / i - 3;
        int k = par2 / i - 40;

        if (j < 0 || k < 0)
        {
            return null;
        }

        int l = Math.min(20, field_73771_c.size());

        if (j <= 320 && k < field_73772_a.fontRenderer.FONT_HEIGHT * l + l)
        {
            int i1 = k / (field_73772_a.fontRenderer.FONT_HEIGHT + 1) + field_73768_d;
            return new ChatClickData(field_73772_a.fontRenderer, (ChatLine)field_73771_c.get(i1), j, (k - (i1 - field_73768_d) * field_73772_a.fontRenderer.FONT_HEIGHT) + i1);
        }
        else
        {
            return null;
        }
    }

    public void func_73757_a(String par1Str, Object par2ArrayOfObj[])
    {
        func_73765_a(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
    }

    public boolean func_73760_d()
    {
        return field_73772_a.currentScreen instanceof GuiChat;
    }

    public void func_73759_c(int par1)
    {
        for (Iterator iterator = field_73771_c.iterator(); iterator.hasNext();)
        {
            ChatLine chatline = (ChatLine)iterator.next();

            if (chatline.func_74539_c() == par1)
            {
                iterator.remove();
                return;
            }
        }
    }
}
