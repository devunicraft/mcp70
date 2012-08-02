package net.minecraft.src;

import java.util.Comparator;

class SorterStatsItem implements Comparator
{
    final GuiStats statsGUI;
    final GuiSlotStatsItem slotStatsItemGUI;

    SorterStatsItem(GuiSlotStatsItem par1GuiSlotStatsItem, GuiStats par2GuiStats)
    {
        slotStatsItemGUI = par1GuiSlotStatsItem;
        statsGUI = par2GuiStats;
    }

    public int func_78337_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting)
    {
        int i = par1StatCrafting.getItemID();
        int j = par2StatCrafting.getItemID();
        StatBase statbase = null;
        StatBase statbase1 = null;

        if (slotStatsItemGUI.field_77264_j == 0)
        {
            statbase = StatList.objectBreakStats[i];
            statbase1 = StatList.objectBreakStats[j];
        }
        else if (slotStatsItemGUI.field_77264_j == 1)
        {
            statbase = StatList.objectCraftStats[i];
            statbase1 = StatList.objectCraftStats[j];
        }
        else if (slotStatsItemGUI.field_77264_j == 2)
        {
            statbase = StatList.objectUseStats[i];
            statbase1 = StatList.objectUseStats[j];
        }

        if (statbase != null || statbase1 != null)
        {
            if (statbase == null)
            {
                return 1;
            }

            if (statbase1 == null)
            {
                return -1;
            }

            int k = GuiStats.getStatsFileWriter(slotStatsItemGUI.field_77269_a).writeStat(statbase);
            int l = GuiStats.getStatsFileWriter(slotStatsItemGUI.field_77269_a).writeStat(statbase1);

            if (k != l)
            {
                return (k - l) * slotStatsItemGUI.field_77265_k;
            }
        }

        return i - j;
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return func_78337_a((StatCrafting)par1Obj, (StatCrafting)par2Obj);
    }
}
