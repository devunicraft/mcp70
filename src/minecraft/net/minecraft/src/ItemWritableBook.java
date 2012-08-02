package net.minecraft.src;

public class ItemWritableBook extends Item
{
    public ItemWritableBook(int par1)
    {
        super(par1);
        setMaxStackSize(1);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.func_71048_c(par1ItemStack);
        return par1ItemStack;
    }

    public boolean func_77651_p()
    {
        return true;
    }

    public static boolean func_77829_a(NBTTagCompound par0NBTTagCompound)
    {
        if (par0NBTTagCompound == null)
        {
            return false;
        }

        if (!par0NBTTagCompound.hasKey("pages"))
        {
            return false;
        }

        NBTTagList nbttaglist = (NBTTagList)par0NBTTagCompound.getTag("pages");

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagString nbttagstring = (NBTTagString)nbttaglist.tagAt(i);

            if (nbttagstring.data == null)
            {
                return false;
            }

            if (nbttagstring.data.length() > 256)
            {
                return false;
            }
        }

        return true;
    }
}
