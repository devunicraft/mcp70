package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

abstract class GuiSlotStats extends GuiSlot
{
    protected int field_77262_g;
    protected List field_77266_h;
    protected Comparator field_77267_i;
    protected int field_77264_j;
    protected int field_77265_k;
    final GuiStats field_77263_l;

    protected GuiSlotStats(GuiStats par1GuiStats)
    {
        super(GuiStats.getMinecraft1(par1GuiStats), par1GuiStats.width, par1GuiStats.height, 32, par1GuiStats.height - 64, 20);
        field_77263_l = par1GuiStats;
        field_77262_g = -1;
        field_77264_j = -1;
        field_77265_k = 0;
        func_77216_a(false);
        func_77223_a(true, 20);
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
        field_77263_l.drawDefaultBackground();
    }

    protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator)
    {
        if (!Mouse.isButtonDown(0))
        {
            field_77262_g = -1;
        }

        if (field_77262_g == 0)
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 115) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 115) - 18, par2 + 1, 0, 18);
        }

        if (field_77262_g == 1)
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 165) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 165) - 18, par2 + 1, 0, 18);
        }

        if (field_77262_g == 2)
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 215) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_77263_l, (par1 + 215) - 18, par2 + 1, 0, 18);
        }

        if (field_77264_j != -1)
        {
            char c = 'O';
            byte byte0 = 18;

            if (field_77264_j == 1)
            {
                c = '\201';
            }
            else if (field_77264_j == 2)
            {
                c = '\263';
            }

            if (field_77265_k == 1)
            {
                byte0 = 36;
            }

            GuiStats.drawSprite(field_77263_l, par1 + c, par2 + 1, byte0, 0);
        }
    }

    protected void func_77224_a(int par1, int par2)
    {
        field_77262_g = -1;

        if (par1 >= 79 && par1 < 115)
        {
            field_77262_g = 0;
        }
        else if (par1 >= 129 && par1 < 165)
        {
            field_77262_g = 1;
        }
        else if (par1 >= 179 && par1 < 215)
        {
            field_77262_g = 2;
        }

        if (field_77262_g >= 0)
        {
            func_77261_e(field_77262_g);
            GuiStats.getMinecraft2(field_77263_l).sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        }
    }

    /**
     * Gets the size of the current slot list.
     */
    protected final int getSize()
    {
        return field_77266_h.size();
    }

    protected final StatCrafting func_77257_d(int par1)
    {
        return (StatCrafting)field_77266_h.get(par1);
    }

    protected abstract String func_77258_c(int i);

    protected void func_77260_a(StatCrafting par1StatCrafting, int par2, int par3, boolean par4)
    {
        if (par1StatCrafting != null)
        {
            String s = par1StatCrafting.func_75968_a(GuiStats.getStatsFileWriter(field_77263_l).writeStat(par1StatCrafting));
            field_77263_l.drawString(GuiStats.getFontRenderer4(field_77263_l), s, par2 - GuiStats.getFontRenderer5(field_77263_l).getStringWidth(s), par3 + 5, par4 ? 0xffffff : 0x909090);
        }
        else
        {
            String s1 = "-";
            field_77263_l.drawString(GuiStats.getFontRenderer6(field_77263_l), s1, par2 - GuiStats.getFontRenderer7(field_77263_l).getStringWidth(s1), par3 + 5, par4 ? 0xffffff : 0x909090);
        }
    }

    protected void func_77215_b(int par1, int par2)
    {
        if (par2 < top || par2 > bottom)
        {
            return;
        }

        int i = func_77210_c(par1, par2);
        int j = field_77263_l.width / 2 - 92 - 16;

        if (i >= 0)
        {
            if (par1 < j + 40 || par1 > j + 40 + 20)
            {
                return;
            }

            StatCrafting statcrafting = func_77257_d(i);
            func_77259_a(statcrafting, par1, par2);
        }
        else
        {
            String s = "";

            if (par1 >= (j + 115) - 18 && par1 <= j + 115)
            {
                s = func_77258_c(0);
            }
            else if (par1 >= (j + 165) - 18 && par1 <= j + 165)
            {
                s = func_77258_c(1);
            }
            else if (par1 >= (j + 215) - 18 && par1 <= j + 215)
            {
                s = func_77258_c(2);
            }
            else
            {
                return;
            }

            s = (new StringBuilder()).append("").append(StringTranslate.getInstance().translateKey(s)).toString().trim();

            if (s.length() > 0)
            {
                int k = par1 + 12;
                int l = par2 - 12;
                int i1 = GuiStats.getFontRenderer8(field_77263_l).getStringWidth(s);
                GuiStats.drawGradientRect(field_77263_l, k - 3, l - 3, k + i1 + 3, l + 8 + 3, 0xc0000000, 0xc0000000);
                GuiStats.getFontRenderer9(field_77263_l).drawStringWithShadow(s, k, l, -1);
            }
        }
    }

    protected void func_77259_a(StatCrafting par1StatCrafting, int par2, int par3)
    {
        if (par1StatCrafting == null)
        {
            return;
        }

        Item item = Item.itemsList[par1StatCrafting.getItemID()];
        String s = (new StringBuilder()).append("").append(StringTranslate.getInstance().translateNamedKey(item.getItemName())).toString().trim();

        if (s.length() > 0)
        {
            int i = par2 + 12;
            int j = par3 - 12;
            int k = GuiStats.getFontRenderer10(field_77263_l).getStringWidth(s);
            GuiStats.drawGradientRect1(field_77263_l, i - 3, j - 3, i + k + 3, j + 8 + 3, 0xc0000000, 0xc0000000);
            GuiStats.getFontRenderer11(field_77263_l).drawStringWithShadow(s, i, j, -1);
        }
    }

    protected void func_77261_e(int par1)
    {
        if (par1 != field_77264_j)
        {
            field_77264_j = par1;
            field_77265_k = -1;
        }
        else if (field_77265_k == -1)
        {
            field_77265_k = 1;
        }
        else
        {
            field_77264_j = -1;
            field_77265_k = 0;
        }

        Collections.sort(field_77266_h, field_77267_i);
    }
}
