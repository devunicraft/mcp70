package net.minecraft.src;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant field_70937_a;
    private EntityPlayer field_70935_b;
    private MerchantRecipeList field_70936_c;

    public NpcMerchant(EntityPlayer par1EntityPlayer)
    {
        field_70935_b = par1EntityPlayer;
        field_70937_a = new InventoryMerchant(par1EntityPlayer, this);
    }

    public EntityPlayer func_70931_l_()
    {
        return field_70935_b;
    }

    public void func_70932_a_(EntityPlayer entityplayer)
    {
    }

    public MerchantRecipeList func_70934_b(EntityPlayer par1EntityPlayer)
    {
        return field_70936_c;
    }

    public void func_70930_a(MerchantRecipeList par1MerchantRecipeList)
    {
        field_70936_c = par1MerchantRecipeList;
    }

    public void func_70933_a(MerchantRecipe merchantrecipe)
    {
    }
}
