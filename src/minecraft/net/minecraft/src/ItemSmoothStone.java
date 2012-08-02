package net.minecraft.src;

public class ItemSmoothStone extends ItemBlock
{
    private Block field_77887_a;

    public ItemSmoothStone(int par1, Block par2Block)
    {
        super(par1);
        field_77887_a = par2Block;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return field_77887_a.getBlockTextureFromSideAndMetadata(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= BlockStoneBrick.field_72188_a.length)
        {
            i = 0;
        }

        return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockStoneBrick.field_72188_a[i]).toString();
    }
}
