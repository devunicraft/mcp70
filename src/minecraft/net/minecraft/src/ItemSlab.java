package net.minecraft.src;

public class ItemSlab extends ItemBlock
{
    private final boolean field_77891_a;
    private final BlockHalfSlab field_77889_b;
    private final BlockHalfSlab field_77890_c;

    public ItemSlab(int par1, BlockHalfSlab par2BlockHalfSlab, BlockHalfSlab par3BlockHalfSlab, boolean par4)
    {
        super(par1);
        field_77889_b = par2BlockHalfSlab;
        field_77890_c = par3BlockHalfSlab;
        field_77891_a = par4;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return Block.blocksList[shiftedIndex].getBlockTextureFromSideAndMetadata(2, par1);
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
        return field_77889_b.func_72240_d(par1ItemStack.getItemDamage());
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (field_77891_a)
        {
            return super.tryPlaceIntoWorld(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockMetadata(par4, par5, par6);
        int k = j & 7;
        boolean flag = (j & 8) != 0;

        if ((par7 == 1 && !flag || par7 == 0 && flag) && i == field_77889_b.blockID && k == par1ItemStack.getItemDamage())
        {
            if (par3World.checkIfAABBIsClear(field_77890_c.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockAndMetadataWithNotify(par4, par5, par6, field_77890_c.blockID, k))
            {
                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, field_77890_c.stepSound.getStepSound(), (field_77890_c.stepSound.getVolume() + 1.0F) / 2.0F, field_77890_c.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize--;
            }

            return true;
        }

        if (func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7))
        {
            return true;
        }
        else
        {
            return super.tryPlaceIntoWorld(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    /**
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
    public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
    {
        int i = par2;
        int j = par3;
        int k = par4;
        int l = par1World.getBlockId(par2, par3, par4);
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        int j1 = i1 & 7;
        boolean flag = (i1 & 8) != 0;

        if ((par5 == 1 && !flag || par5 == 0 && flag) && l == field_77889_b.blockID && j1 == par7ItemStack.getItemDamage())
        {
            return true;
        }

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

        l = par1World.getBlockId(par2, par3, par4);
        i1 = par1World.getBlockMetadata(par2, par3, par4);
        j1 = i1 & 7;
        flag = (i1 & 8) != 0;

        if (l == field_77889_b.blockID && j1 == par7ItemStack.getItemDamage())
        {
            return true;
        }
        else
        {
            return super.canPlaceItemBlockOnSide(par1World, i, j, k, par5, par6EntityPlayer, par7ItemStack);
        }
    }

    private boolean func_77888_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
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

        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockMetadata(par4, par5, par6);
        int k = j & 7;

        if (i == field_77889_b.blockID && k == par1ItemStack.getItemDamage())
        {
            if (par3World.checkIfAABBIsClear(field_77890_c.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockAndMetadataWithNotify(par4, par5, par6, field_77890_c.blockID, k))
            {
                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, field_77890_c.stepSound.getStepSound(), (field_77890_c.stepSound.getVolume() + 1.0F) / 2.0F, field_77890_c.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize--;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
