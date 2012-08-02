package net.minecraft.src;

import java.util.List;

public class ItemBlock extends Item
{
    /** The block ID of the Block associated with this ItemBlock */
    private int blockID;

    public ItemBlock(int par1)
    {
        super(par1);
        blockID = par1 + 256;
        setIconIndex(Block.blocksList[par1 + 256].getBlockTextureFromSide(2));
    }

    /**
     * Returns the blockID for this Item
     */
    public int getBlockID()
    {
        return blockID;
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i = par3World.getBlockId(par4, par5, par6);

        if (i == Block.snow.blockID)
        {
            par7 = 1;
        }
        else if (i != Block.vine.blockID && i != Block.tallGrass.blockID && i != Block.deadBush.blockID)
        {
            if (par7 == 0)
            {
                par5--;
            }

            if (par7 == 1)
            {
                par5++;
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
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        if (par5 == 255 && Block.blocksList[blockID].blockMaterial.isSolid())
        {
            return false;
        }

        if (par3World.canPlaceEntityOnSide(blockID, par4, par5, par6, false, par7, par2EntityPlayer))
        {
            Block block = Block.blocksList[blockID];

            if (par3World.setBlockAndMetadataWithNotify(par4, par5, par6, blockID, getMetadata(par1ItemStack.getItemDamage())))
            {
                if (par3World.getBlockId(par4, par5, par6) == blockID)
                {
                    Block.blocksList[blockID].updateBlockMetadata(par3World, par4, par5, par6, par7, par8, par9, par10);
                    Block.blocksList[blockID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer);
                }

                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
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
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
    public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
    {
        int i = par1World.getBlockId(par2, par3, par4);

        if (i == Block.snow.blockID)
        {
            par5 = 1;
        }
        else if (i != Block.vine.blockID && i != Block.tallGrass.blockID && i != Block.deadBush.blockID)
        {
            if (par5 == 0)
            {
                par3--;
            }

            if (par5 == 1)
            {
                par3++;
            }

            if (par5 == 2)
            {
                par4--;
            }

            if (par5 == 3)
            {
                par4++;
            }

            if (par5 == 4)
            {
                par2--;
            }

            if (par5 == 5)
            {
                par2++;
            }
        }

        return par1World.canPlaceEntityOnSide(getBlockID(), par2, par3, par4, false, par5, null);
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return Block.blocksList[blockID].getBlockName();
    }

    public String getItemName()
    {
        return Block.blocksList[blockID].getBlockName();
    }

    public CreativeTabs getTabToDisplayOn()
    {
        return Block.blocksList[blockID].getCreativeTabToDisplayOn();
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        Block.blocksList[blockID].getSubBlocks(par1, par2CreativeTabs, par3List);
    }
}
