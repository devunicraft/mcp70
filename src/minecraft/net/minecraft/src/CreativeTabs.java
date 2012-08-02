package net.minecraft.src;

import java.util.List;

public class CreativeTabs
{
    public static final CreativeTabs creativeTabArray[] = new CreativeTabs[12];
    public static final CreativeTabs tabBlock = new CreativeTabBlock(0, "buildingBlocks");
    public static final CreativeTabs tabDeco = new CreativeTabDeco(1, "decorations");
    public static final CreativeTabs tabRedstone = new CreativeTabRedstone(2, "redstone");
    public static final CreativeTabs tabTransport = new CreativeTabTransport(3, "transportation");
    public static final CreativeTabs tabMisc = new CreativeTabMisc(4, "misc");
    public static final CreativeTabs tabAllSearch = (new CreativeTabSearch(5, "search")).func_78025_a("search.png");
    public static final CreativeTabs tabFood = new CreativeTabFood(6, "food");
    public static final CreativeTabs tabTools = new CreativeTabTools(7, "tools");
    public static final CreativeTabs tabCombat = new CreativeTabCombat(8, "combat");
    public static final CreativeTabs tabBrewing = new CreativeTabBrewing(9, "brewing");
    public static final CreativeTabs tabMaterials = new CreativeTabMaterial(10, "materials");
    public static final CreativeTabs tabInventory = (new CreativeTabInventory(11, "inventory")).func_78025_a("survival_inv.png").func_78022_j().disableForegroundDraw();
    private final int tabIndex;
    private final String tabLabel;
    private String field_78043_p;
    private boolean field_78042_q;
    private boolean drawInForground;

    public CreativeTabs(int par1, String par2Str)
    {
        field_78043_p = "list_items.png";
        field_78042_q = true;
        drawInForground = true;
        tabIndex = par1;
        tabLabel = par2Str;
        creativeTabArray[par1] = this;
    }

    public int getTabIndex()
    {
        return tabIndex;
    }

    public String getTabLabel()
    {
        return tabLabel;
    }

    public String func_78024_c()
    {
        return StringTranslate.getInstance().translateKey((new StringBuilder()).append("itemGroup.").append(getTabLabel()).toString());
    }

    public Item getTabIconItem()
    {
        return Item.itemsList[getTabIconItemIndex()];
    }

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return 1;
    }

    public String func_78015_f()
    {
        return field_78043_p;
    }

    public CreativeTabs func_78025_a(String par1Str)
    {
        field_78043_p = par1Str;
        return this;
    }

    public boolean drawInForegroundOfTab()
    {
        return drawInForground;
    }

    public CreativeTabs disableForegroundDraw()
    {
        drawInForground = false;
        return this;
    }

    public boolean func_78017_i()
    {
        return field_78042_q;
    }

    public CreativeTabs func_78022_j()
    {
        field_78042_q = false;
        return this;
    }

    public int func_78020_k()
    {
        return tabIndex % 6;
    }

    public boolean func_78023_l()
    {
        return tabIndex < 6;
    }

    public void func_78018_a(List par1List)
    {
        Item aitem[] = Item.itemsList;
        int i = aitem.length;

        for (int j = 0; j < i; j++)
        {
            Item item = aitem[j];

            if (item != null && item.getTabToDisplayOn() == this)
            {
                item.getSubItems(item.shiftedIndex, this, par1List);
            }
        }
    }
}
