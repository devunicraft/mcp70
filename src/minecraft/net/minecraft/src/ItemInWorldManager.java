package net.minecraft.src;

public class ItemInWorldManager
{
    public World field_73092_a;
    public EntityPlayerMP field_73090_b;
    private EnumGameType gameType;
    private boolean field_73088_d;
    private int field_73089_e;
    private int field_73086_f;
    private int field_73087_g;
    private int field_73099_h;
    private int field_73100_i;
    private boolean field_73097_j;
    private int field_73098_k;
    private int field_73095_l;
    private int field_73096_m;
    private int field_73093_n;
    private int field_73094_o;

    public ItemInWorldManager(World par1World)
    {
        gameType = EnumGameType.NOT_SET;
        field_73094_o = -1;
        field_73092_a = par1World;
    }

    public void setGameType(EnumGameType par1EnumGameType)
    {
        gameType = par1EnumGameType;
        par1EnumGameType.configurePlayerCapabilities(field_73090_b.capabilities);
        field_73090_b.func_71016_p();
    }

    public EnumGameType getGameType()
    {
        return gameType;
    }

    public boolean isCreative()
    {
        return gameType.isCreative();
    }

    public void func_73077_b(EnumGameType par1EnumGameType)
    {
        if (gameType == EnumGameType.NOT_SET)
        {
            gameType = par1EnumGameType;
        }

        setGameType(gameType);
    }

    public void func_73075_a()
    {
        field_73100_i++;

        if (field_73097_j)
        {
            int i = field_73100_i - field_73093_n;
            int k = field_73092_a.getBlockId(field_73098_k, field_73095_l, field_73096_m);

            if (k == 0)
            {
                field_73097_j = false;
            }
            else
            {
                Block block1 = Block.blocksList[k];
                float f = block1.func_71908_a(field_73090_b, field_73090_b.worldObj, field_73098_k, field_73095_l, field_73096_m) * (float)(i + 1);
                int i1 = (int)(f * 10F);

                if (i1 != field_73094_o)
                {
                    field_73092_a.func_72888_f(field_73090_b.entityId, field_73098_k, field_73095_l, field_73096_m, i1);
                    field_73094_o = i1;
                }

                if (f >= 1.0F)
                {
                    field_73097_j = false;
                    tryHarvestBlock(field_73098_k, field_73095_l, field_73096_m);
                }
            }
        }
        else if (field_73088_d)
        {
            int j = field_73092_a.getBlockId(field_73086_f, field_73087_g, field_73099_h);
            Block block = Block.blocksList[j];

            if (block == null)
            {
                field_73092_a.func_72888_f(field_73090_b.entityId, field_73086_f, field_73087_g, field_73099_h, -1);
                field_73094_o = -1;
                field_73088_d = false;
            }
            else
            {
                int l = field_73100_i - field_73089_e;
                float f1 = block.func_71908_a(field_73090_b, field_73090_b.worldObj, field_73086_f, field_73087_g, field_73099_h) * (float)(l + 1);
                int j1 = (int)(f1 * 10F);

                if (j1 != field_73094_o)
                {
                    field_73092_a.func_72888_f(field_73090_b.entityId, field_73086_f, field_73087_g, field_73099_h, j1);
                    field_73094_o = j1;
                }
            }
        }
    }

    public void func_73074_a(int par1, int par2, int par3, int par4)
    {
        if (gameType.isAdventure())
        {
            return;
        }

        if (isCreative())
        {
            if (!field_73092_a.func_72886_a(null, par1, par2, par3, par4))
            {
                tryHarvestBlock(par1, par2, par3);
            }

            return;
        }

        field_73092_a.func_72886_a(field_73090_b, par1, par2, par3, par4);
        field_73089_e = field_73100_i;
        float f = 1.0F;
        int i = field_73092_a.getBlockId(par1, par2, par3);

        if (i > 0)
        {
            Block.blocksList[i].onBlockClicked(field_73092_a, par1, par2, par3, field_73090_b);
            f = Block.blocksList[i].func_71908_a(field_73090_b, field_73090_b.worldObj, par1, par2, par3);
        }

        if (i > 0 && f >= 1.0F)
        {
            tryHarvestBlock(par1, par2, par3);
        }
        else
        {
            field_73088_d = true;
            field_73086_f = par1;
            field_73087_g = par2;
            field_73099_h = par3;
            int j = (int)(f * 10F);
            field_73092_a.func_72888_f(field_73090_b.entityId, par1, par2, par3, j);
            field_73094_o = j;
        }
    }

    public void func_73082_a(int par1, int par2, int par3)
    {
        if (par1 == field_73086_f && par2 == field_73087_g && par3 == field_73099_h)
        {
            int i = field_73100_i - field_73089_e;
            int j = field_73092_a.getBlockId(par1, par2, par3);

            if (j != 0)
            {
                Block block = Block.blocksList[j];
                float f = block.func_71908_a(field_73090_b, field_73090_b.worldObj, par1, par2, par3) * (float)(i + 1);

                if (f >= 0.7F)
                {
                    field_73088_d = false;
                    field_73092_a.func_72888_f(field_73090_b.entityId, par1, par2, par3, -1);
                    tryHarvestBlock(par1, par2, par3);
                }
                else if (!field_73097_j)
                {
                    field_73088_d = false;
                    field_73097_j = true;
                    field_73098_k = par1;
                    field_73095_l = par2;
                    field_73096_m = par3;
                    field_73093_n = field_73089_e;
                }
            }
        }
    }

    public void func_73073_c(int par1, int par2, int par3)
    {
        field_73088_d = false;
        field_73092_a.func_72888_f(field_73090_b.entityId, field_73086_f, field_73087_g, field_73099_h, -1);
    }

    private boolean func_73079_d(int par1, int par2, int par3)
    {
        Block block = Block.blocksList[field_73092_a.getBlockId(par1, par2, par3)];
        int i = field_73092_a.getBlockMetadata(par1, par2, par3);

        if (block != null)
        {
            block.onBlockHarvested(field_73092_a, par1, par2, par3, i, field_73090_b);
        }

        boolean flag = field_73092_a.setBlockWithNotify(par1, par2, par3, 0);

        if (block != null && flag)
        {
            block.onBlockDestroyedByPlayer(field_73092_a, par1, par2, par3, i);
        }

        return flag;
    }

    /**
     * Attempts to harvest a block at the given coordinate
     */
    public boolean tryHarvestBlock(int par1, int par2, int par3)
    {
        if (gameType.isAdventure())
        {
            return false;
        }

        int i = field_73092_a.getBlockId(par1, par2, par3);
        int j = field_73092_a.getBlockMetadata(par1, par2, par3);
        field_73092_a.playAuxSFXAtEntity(field_73090_b, 2001, par1, par2, par3, i + (field_73092_a.getBlockMetadata(par1, par2, par3) << 12));
        boolean flag = func_73079_d(par1, par2, par3);

        if (isCreative())
        {
            field_73090_b.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(par1, par2, par3, field_73092_a));
        }
        else
        {
            ItemStack itemstack = field_73090_b.getCurrentEquippedItem();
            boolean flag1 = field_73090_b.canHarvestBlock(Block.blocksList[i]);

            if (itemstack != null)
            {
                itemstack.func_77941_a(field_73092_a, i, par1, par2, par3, field_73090_b);

                if (itemstack.stackSize == 0)
                {
                    field_73090_b.destroyCurrentEquippedItem();
                }
            }

            if (flag && flag1)
            {
                Block.blocksList[i].harvestBlock(field_73092_a, field_73090_b, par1, par2, par3, j);
            }
        }

        return flag;
    }

    /**
     * Attempts to right-click use an item by the given EntityPlayer in the given World
     */
    public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        int i = par3ItemStack.stackSize;
        int j = par3ItemStack.getItemDamage();
        ItemStack itemstack = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);

        if (itemstack != par3ItemStack || itemstack != null && itemstack.stackSize != i || itemstack != null && itemstack.getMaxItemUseDuration() > 0)
        {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = itemstack;

            if (isCreative())
            {
                itemstack.stackSize = i;
                itemstack.setItemDamage(j);
            }

            if (itemstack.stackSize == 0)
            {
                par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Tries to place an item from the given ItemStack into the world at the given location.
     */
    public boolean tryPlaceItemStackIntoWorld(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i = par2World.getBlockId(par4, par5, par6);

        if (i > 0 && Block.blocksList[i].onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10))
        {
            return true;
        }

        if (par3ItemStack == null)
        {
            return false;
        }

        if (isCreative())
        {
            int j = par3ItemStack.getItemDamage();
            int k = par3ItemStack.stackSize;
            boolean flag = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setItemDamage(j);
            par3ItemStack.stackSize = k;
            return flag;
        }
        else
        {
            return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    /**
     * Sets the world instance.
     */
    public void setWorld(WorldServer par1WorldServer)
    {
        field_73092_a = par1WorldServer;
    }
}
