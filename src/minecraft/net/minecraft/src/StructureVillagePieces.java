package net.minecraft.src;

import java.util.*;

public class StructureVillagePieces
{
    public static ArrayList getStructureVillageWeightedPieceList(Random par0Random, int par1)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageHouse4_Garden.class, 4, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageChurch.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 1 + par1)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageHouse1.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageWoodHut.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 5 + par1 * 3)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageHall.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageField.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 4 + par1)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageField2.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageHouse2.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0, 1 + par1)));
        arraylist.add(new StructureVillagePieceWeight(net.minecraft.src.ComponentVillageHouse3.class, 8, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 3 + par1 * 2)));
        Iterator iterator = arraylist.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            if (((StructureVillagePieceWeight)iterator.next()).villagePiecesLimit == 0)
            {
                iterator.remove();
            }
        }
        while (true);

        return arraylist;
    }

    private static int func_75079_a(List par0List)
    {
        boolean flag = false;
        int i = 0;

        for (Iterator iterator = par0List.iterator(); iterator.hasNext();)
        {
            StructureVillagePieceWeight structurevillagepieceweight = (StructureVillagePieceWeight)iterator.next();

            if (structurevillagepieceweight.villagePiecesLimit > 0 && structurevillagepieceweight.villagePiecesSpawned < structurevillagepieceweight.villagePiecesLimit)
            {
                flag = true;
            }

            i += structurevillagepieceweight.villagePieceWeight;
        }

        return flag ? i : -1;
    }

    private static ComponentVillage func_75083_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, StructureVillagePieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8)
    {
        Class class1 = par1StructureVillagePieceWeight.villagePieceClass;
        Object obj = null;

        if (class1 == (net.minecraft.src.ComponentVillageHouse4_Garden.class))
        {
            obj = ComponentVillageHouse4_Garden.func_74912_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageChurch.class))
        {
            obj = ComponentVillageChurch.func_74919_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageHouse1.class))
        {
            obj = ComponentVillageHouse1.func_74898_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageWoodHut.class))
        {
            obj = ComponentVillageWoodHut.func_74908_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageHall.class))
        {
            obj = ComponentVillageHall.func_74906_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageField.class))
        {
            obj = ComponentVillageField.func_74900_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageField2.class))
        {
            obj = ComponentVillageField2.func_74902_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageHouse2.class))
        {
            obj = ComponentVillageHouse2.func_74915_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (class1 == (net.minecraft.src.ComponentVillageHouse3.class))
        {
            obj = ComponentVillageHouse3.func_74921_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }

        return ((ComponentVillage)(obj));
    }

    /**
     * attempts to find a next Village Component to be spawned
     */
    private static ComponentVillage getNextVillageComponent(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        int var8 = func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);

        if (var8 <= 0)
        {
            return null;
        }
        else
        {
            int var9 = 0;

            while (var9 < 5)
            {
                ++var9;
                int var10 = par2Random.nextInt(var8);
                Iterator var11 = par0ComponentVillageStartPiece.structureVillageWeightedPieceList.iterator();

                while (var11.hasNext())
                {
                    StructureVillagePieceWeight var12 = (StructureVillagePieceWeight)var11.next();
                    var10 -= var12.villagePieceWeight;

                    if (var10 < 0)
                    {
                        if (!var12.canSpawnMoreVillagePiecesOfType(par7) || var12 == par0ComponentVillageStartPiece.structVillagePieceWeight && par0ComponentVillageStartPiece.structureVillageWeightedPieceList.size() > 1)
                        {
                            break;
                        }

                        ComponentVillage var13 = func_75083_a(par0ComponentVillageStartPiece, var12, par1List, par2Random, par3, par4, par5, par6, par7);

                        if (var13 != null)
                        {
                            ++var12.villagePiecesSpawned;
                            par0ComponentVillageStartPiece.structVillagePieceWeight = var12;

                            if (!var12.canSpawnMoreVillagePieces())
                            {
                                par0ComponentVillageStartPiece.structureVillageWeightedPieceList.remove(var12);
                            }

                            return var13;
                        }
                    }
                }
            }

            StructureBoundingBox var14 = ComponentVillageTorch.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (var14 != null)
            {
                return new ComponentVillageTorch(par0ComponentVillageStartPiece, par7, par2Random, var14, par6);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned, private Village function
     */
    private static StructureComponent getNextVillageStructureComponent(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 50)
        {
            return null;
        }

        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) > 112 || Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) > 112)
        {
            return null;
        }

        ComponentVillage componentvillage = getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);

        if (componentvillage != null)
        {
            int i = (((StructureComponent)(componentvillage)).boundingBox.minX + ((StructureComponent)(componentvillage)).boundingBox.maxX) / 2;
            int j = (((StructureComponent)(componentvillage)).boundingBox.minZ + ((StructureComponent)(componentvillage)).boundingBox.maxZ) / 2;
            int k = ((StructureComponent)(componentvillage)).boundingBox.maxX - ((StructureComponent)(componentvillage)).boundingBox.minX;
            int l = ((StructureComponent)(componentvillage)).boundingBox.maxZ - ((StructureComponent)(componentvillage)).boundingBox.minZ;
            int i1 = k <= l ? l : k;

            if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes))
            {
                par1List.add(componentvillage);
                par0ComponentVillageStartPiece.field_74932_i.add(componentvillage);
                return componentvillage;
            }
        }

        return null;
    }

    private static StructureComponent getNextComponentVillagePath(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 3 + par0ComponentVillageStartPiece.terrainType)
        {
            return null;
        }

        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) > 112 || Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) > 112)
        {
            return null;
        }

        StructureBoundingBox structureboundingbox = ComponentVillagePathGen.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

        if (structureboundingbox != null && structureboundingbox.minY > 10)
        {
            ComponentVillagePathGen componentvillagepathgen = new ComponentVillagePathGen(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
            int i = (((StructureComponent)(componentvillagepathgen)).boundingBox.minX + ((StructureComponent)(componentvillagepathgen)).boundingBox.maxX) / 2;
            int j = (((StructureComponent)(componentvillagepathgen)).boundingBox.minZ + ((StructureComponent)(componentvillagepathgen)).boundingBox.maxZ) / 2;
            int k = ((StructureComponent)(componentvillagepathgen)).boundingBox.maxX - ((StructureComponent)(componentvillagepathgen)).boundingBox.minX;
            int l = ((StructureComponent)(componentvillagepathgen)).boundingBox.maxZ - ((StructureComponent)(componentvillagepathgen)).boundingBox.minZ;
            int i1 = k <= l ? l : k;

            if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes))
            {
                par1List.add(componentvillagepathgen);
                par0ComponentVillageStartPiece.field_74930_j.add(componentvillagepathgen);
                return componentvillagepathgen;
            }
        }

        return null;
    }

    /**
     * attempts to find a next Structure Component to be spawned
     */
    static StructureComponent getNextStructureComponent(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return getNextVillageStructureComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructureComponent getNextStructureComponentVillagePath(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return getNextComponentVillagePath(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }
}
