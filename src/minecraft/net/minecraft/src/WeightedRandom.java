package net.minecraft.src;

import java.util.*;

public class WeightedRandom
{
    /**
     * Returns the total weight of all items in a collection.
     */
    public static int getTotalWeight(Collection par0Collection)
    {
        int i = 0;

        for (Iterator iterator = par0Collection.iterator(); iterator.hasNext();)
        {
            WeightedRandomItem weightedrandomitem = (WeightedRandomItem)iterator.next();
            i += weightedrandomitem.itemWeight;
        }

        return i;
    }

    /**
     * Returns a random choice from the input items, with a total weight value.
     */
    public static WeightedRandomItem getRandomItem(Random par0Random, Collection par1Collection, int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }

        int i = par0Random.nextInt(par2);

        for (Iterator iterator = par1Collection.iterator(); iterator.hasNext();)
        {
            WeightedRandomItem weightedrandomitem = (WeightedRandomItem)iterator.next();
            i -= weightedrandomitem.itemWeight;

            if (i < 0)
            {
                return weightedrandomitem;
            }
        }

        return null;
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandomItem getRandomItem(Random par0Random, Collection par1Collection)
    {
        return getRandomItem(par0Random, par1Collection, getTotalWeight(par1Collection));
    }

    /**
     * Returns the total weight of all items in a array.
     */
    public static int getTotalWeight(WeightedRandomItem par0ArrayOfWeightedRandomItem[])
    {
        int i = 0;
        WeightedRandomItem aweightedrandomitem[] = par0ArrayOfWeightedRandomItem;
        int j = aweightedrandomitem.length;

        for (int k = 0; k < j; k++)
        {
            WeightedRandomItem weightedrandomitem = aweightedrandomitem[k];
            i += weightedrandomitem.itemWeight;
        }

        return i;
    }

    /**
     * Returns a random choice from the input array of items, with a total weight value.
     */
    public static WeightedRandomItem getRandomItem(Random par0Random, WeightedRandomItem par1ArrayOfWeightedRandomItem[], int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }

        int i = par0Random.nextInt(par2);
        WeightedRandomItem aweightedrandomitem[] = par1ArrayOfWeightedRandomItem;
        int j = aweightedrandomitem.length;

        for (int k = 0; k < j; k++)
        {
            WeightedRandomItem weightedrandomitem = aweightedrandomitem[k];
            i -= weightedrandomitem.itemWeight;

            if (i < 0)
            {
                return weightedrandomitem;
            }
        }

        return null;
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandomItem getRandomItem(Random par0Random, WeightedRandomItem par1ArrayOfWeightedRandomItem[])
    {
        return getRandomItem(par0Random, par1ArrayOfWeightedRandomItem, getTotalWeight(par1ArrayOfWeightedRandomItem));
    }
}
