package net.minecraft.src;

import java.util.Random;

public class WeightedRandomChestContent extends WeightedRandomItem
{
    private int field_76297_b;
    private int field_76298_c;
    private int field_76295_d;
    private int field_76296_e;

    public WeightedRandomChestContent(int par1, int par2, int par3, int par4, int par5)
    {
        super(par5);
        field_76297_b = par1;
        field_76298_c = par2;
        field_76295_d = par3;
        field_76296_e = par4;
    }

    public static void func_76293_a(Random par0Random, WeightedRandomChestContent par1ArrayOfWeightedRandomChestContent[], TileEntityChest par2TileEntityChest, int par3)
    {
        for (int i = 0; i < par3; i++)
        {
            WeightedRandomChestContent weightedrandomchestcontent = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            int j = weightedrandomchestcontent.field_76295_d + par0Random.nextInt((weightedrandomchestcontent.field_76296_e - weightedrandomchestcontent.field_76295_d) + 1);

            if (Item.itemsList[weightedrandomchestcontent.field_76297_b].getItemStackLimit() >= j)
            {
                par2TileEntityChest.setInventorySlotContents(par0Random.nextInt(par2TileEntityChest.getSizeInventory()), new ItemStack(weightedrandomchestcontent.field_76297_b, j, weightedrandomchestcontent.field_76298_c));
                continue;
            }

            for (int k = 0; k < j; k++)
            {
                par2TileEntityChest.setInventorySlotContents(par0Random.nextInt(par2TileEntityChest.getSizeInventory()), new ItemStack(weightedrandomchestcontent.field_76297_b, 1, weightedrandomchestcontent.field_76298_c));
            }
        }
    }

    public static void func_76294_a(Random par0Random, WeightedRandomChestContent par1ArrayOfWeightedRandomChestContent[], TileEntityDispenser par2TileEntityDispenser, int par3)
    {
        for (int i = 0; i < par3; i++)
        {
            WeightedRandomChestContent weightedrandomchestcontent = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            int j = weightedrandomchestcontent.field_76295_d + par0Random.nextInt((weightedrandomchestcontent.field_76296_e - weightedrandomchestcontent.field_76295_d) + 1);

            if (Item.itemsList[weightedrandomchestcontent.field_76297_b].getItemStackLimit() >= j)
            {
                par2TileEntityDispenser.setInventorySlotContents(par0Random.nextInt(par2TileEntityDispenser.getSizeInventory()), new ItemStack(weightedrandomchestcontent.field_76297_b, j, weightedrandomchestcontent.field_76298_c));
                continue;
            }

            for (int k = 0; k < j; k++)
            {
                par2TileEntityDispenser.setInventorySlotContents(par0Random.nextInt(par2TileEntityDispenser.getSizeInventory()), new ItemStack(weightedrandomchestcontent.field_76297_b, 1, weightedrandomchestcontent.field_76298_c));
            }
        }
    }
}
