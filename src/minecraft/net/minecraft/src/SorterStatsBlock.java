package net.minecraft.src;

import java.util.Comparator;

class SorterStatsBlock implements Comparator
{
    final GuiStats statsGUI;
    final GuiSlotStatsBlock slotStatsBlockGUI;

    SorterStatsBlock(GuiSlotStatsBlock par1GuiSlotStatsBlock, GuiStats par2GuiStats)
    {
        slotStatsBlockGUI = par1GuiSlotStatsBlock;
        statsGUI = par2GuiStats;
    }

    public int func_78334_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting)
    {
        int i = par1StatCrafting.getItemID();
        int j = par2StatCrafting.getItemID();
        StatBase statbase = null;
        StatBase statbase1 = null;

        if (slotStatsBlockGUI.field_77264_j == 2)
        {
            statbase = StatList.mineBlockStatArray[i];
            statbase1 = StatList.mineBlockStatArray[j];
        }
        else if (slotStatsBlockGUI.field_77264_j == 0)
        {
            statbase = StatList.objectCraftStats[i];
            statbase1 = StatList.objectCraftStats[j];
        }
        else if (slotStatsBlockGUI.field_77264_j == 1)
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

            int k = GuiStats.getStatsFileWriter(slotStatsBlockGUI.field_77268_a).writeStat(statbase);
            int l = GuiStats.getStatsFileWriter(slotStatsBlockGUI.field_77268_a).writeStat(statbase1);

            if (k != l)
            {
                return (k - l) * slotStatsBlockGUI.field_77265_k;
            }
        }

        return i - j;
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return func_78334_a((StatCrafting)par1Obj, (StatCrafting)par2Obj);
    }
}
