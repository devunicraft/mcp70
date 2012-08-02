package net.minecraft.src;

import java.util.*;

public class MapGenScatteredFeature extends MapGenStructure
{
    private static List field_75061_e;

    public MapGenScatteredFeature()
    {
    }

    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        byte byte0 = 32;
        byte byte1 = 8;
        int i = par1;
        int j = par2;

        if (par1 < 0)
        {
            par1 -= byte0 - 1;
        }

        if (par2 < 0)
        {
            par2 -= byte0 - 1;
        }

        int k = par1 / byte0;
        int l = par2 / byte0;
        Random random = worldObj.setRandomSeed(k, l, 0xdb1471);
        k *= byte0;
        l *= byte0;
        k += random.nextInt(byte0 - byte1);
        l += random.nextInt(byte0 - byte1);
        par1 = i;
        par2 = j;

        if (par1 == k && par2 == l)
        {
            boolean flag = worldObj.getWorldChunkManager().areBiomesViable(par1 * 16 + 8, par2 * 16 + 8, 0, field_75061_e);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureScatteredFeatureStart(worldObj, rand, par1, par2);
    }

    static
    {
        field_75061_e = Arrays.asList(new BiomeGenBase[]
                {
                    BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle
                });
    }
}
