package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ItemDye extends Item
{
    public static final String dyeColorNames[] =
    {
        "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink",
        "lime", "yellow", "lightBlue", "magenta", "orange", "white"
    };
    public static final int dyeColors[] =
    {
        0x1e1b1b, 0xb3312c, 0x3b511a, 0x51301a, 0x253192, 0x7b2fbe, 0x287697, 0x287697, 0x434343, 0xd88198,
        0x41cd34, 0xdecf2a, 0x6689d3, 0xc354cd, 0xeb8844, 0xf0f0f0
    };

    public ItemDye(int par1)
    {
        super(par1);
        setHasSubtypes(true);
        setMaxDamage(0);
        setTabToDisplayOn(CreativeTabs.tabMaterials);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        int i = MathHelper.clamp_int(par1, 0, 15);
        return iconIndex + (i % 8) * 16 + i / 8;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return (new StringBuilder()).append(super.getItemName()).append(".").append(dyeColorNames[i]).toString();
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        if (par1ItemStack.getItemDamage() == 15)
        {
            int i = par3World.getBlockId(par4, par5, par6);

            if (i == Block.sapling.blockID)
            {
                if (!par3World.isRemote)
                {
                    ((BlockSapling)Block.sapling).growTree(par3World, par4, par5, par6, par3World.rand);
                    par1ItemStack.stackSize--;
                }

                return true;
            }

            if (i == Block.mushroomBrown.blockID || i == Block.mushroomRed.blockID)
            {
                if (!par3World.isRemote && ((BlockMushroom)Block.blocksList[i]).fertilizeMushroom(par3World, par4, par5, par6, par3World.rand))
                {
                    par1ItemStack.stackSize--;
                }

                return true;
            }

            if (i == Block.melonStem.blockID || i == Block.pumpkinStem.blockID)
            {
                if (par3World.getBlockMetadata(par4, par5, par6) == 7)
                {
                    return false;
                }

                if (!par3World.isRemote)
                {
                    ((BlockStem)Block.blocksList[i]).fertilizeStem(par3World, par4, par5, par6);
                    par1ItemStack.stackSize--;
                }

                return true;
            }

            if (i == Block.crops.blockID)
            {
                if (par3World.getBlockMetadata(par4, par5, par6) == 7)
                {
                    return false;
                }

                if (!par3World.isRemote)
                {
                    ((BlockCrops)Block.crops).fertilize(par3World, par4, par5, par6);
                    par1ItemStack.stackSize--;
                }

                return true;
            }

            if (i == Block.cocoa.blockID)
            {
                if (!par3World.isRemote)
                {
                    par3World.setBlockMetadataWithNotify(par4, par5, par6, 8 | BlockDirectional.getDirection(par3World.getBlockMetadata(par4, par5, par6)));
                    par1ItemStack.stackSize--;
                }

                return true;
            }

            if (i == Block.grass.blockID)
            {
                if (!par3World.isRemote)
                {
                    par1ItemStack.stackSize--;
                    label0:

                    for (int k = 0; k < 128; k++)
                    {
                        int i1 = par4;
                        int j1 = par5 + 1;
                        int k1 = par6;

                        for (int l1 = 0; l1 < k / 16; l1++)
                        {
                            i1 += itemRand.nextInt(3) - 1;
                            j1 += ((itemRand.nextInt(3) - 1) * itemRand.nextInt(3)) / 2;
                            k1 += itemRand.nextInt(3) - 1;

                            if (par3World.getBlockId(i1, j1 - 1, k1) != Block.grass.blockID || par3World.isBlockNormalCube(i1, j1, k1))
                            {
                                continue label0;
                            }
                        }

                        if (par3World.getBlockId(i1, j1, k1) != 0)
                        {
                            continue;
                        }

                        if (itemRand.nextInt(10) != 0)
                        {
                            if (Block.tallGrass.canBlockStay(par3World, i1, j1, k1))
                            {
                                par3World.setBlockAndMetadataWithNotify(i1, j1, k1, Block.tallGrass.blockID, 1);
                            }

                            continue;
                        }

                        if (itemRand.nextInt(3) != 0)
                        {
                            if (Block.plantYellow.canBlockStay(par3World, i1, j1, k1))
                            {
                                par3World.setBlockWithNotify(i1, j1, k1, Block.plantYellow.blockID);
                            }

                            continue;
                        }

                        if (Block.plantRed.canBlockStay(par3World, i1, j1, k1))
                        {
                            par3World.setBlockWithNotify(i1, j1, k1, Block.plantRed.blockID);
                        }
                    }
                }

                return true;
            }
        }
        else if (par1ItemStack.getItemDamage() == 3)
        {
            int j = par3World.getBlockId(par4, par5, par6);
            int l = par3World.getBlockMetadata(par4, par5, par6);

            if (j == Block.wood.blockID && BlockLog.func_72141_e(l) == 3)
            {
                if (par7 == 0)
                {
                    return false;
                }

                if (par7 == 1)
                {
                    return false;
                }

                if (par7 == 2)
                {
                    par6--;
                }

                if (par7 == 3)
                {
                    par6++;
                }

                if (par7 == 4)
                {
                    par4--;
                }

                if (par7 == 5)
                {
                    par4++;
                }

                if (par3World.isAirBlock(par4, par5, par6))
                {
                    par3World.setBlockWithNotify(par4, par5, par6, Block.cocoa.blockID);

                    if (par3World.getBlockId(par4, par5, par6) == Block.cocoa.blockID)
                    {
                        Block.blocksList[Block.cocoa.blockID].updateBlockMetadata(par3World, par4, par5, par6, par7, par8, par9, par10);
                    }

                    if (!par2EntityPlayer.capabilities.isCreativeMode)
                    {
                        par1ItemStack.stackSize--;
                    }
                }

                return true;
            }
        }

        return false;
    }

    public boolean func_77646_a(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        if (par2EntityLiving instanceof EntitySheep)
        {
            EntitySheep entitysheep = (EntitySheep)par2EntityLiving;
            int i = BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage());

            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != i)
            {
                entitysheep.setFleeceColor(i);
                par1ItemStack.stackSize--;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 16; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
