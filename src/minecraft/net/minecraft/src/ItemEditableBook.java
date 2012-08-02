package net.minecraft.src;

import java.util.List;

public class ItemEditableBook extends Item
{
    public ItemEditableBook(int par1)
    {
        super(par1);
        setMaxStackSize(1);
    }

    public static boolean func_77828_a(NBTTagCompound par0NBTTagCompound)
    {
        if (!ItemWritableBook.func_77829_a(par0NBTTagCompound))
        {
            return false;
        }

        if (!par0NBTTagCompound.hasKey("title"))
        {
            return false;
        }

        String s = par0NBTTagCompound.getString("title");

        if (s == null || s.length() > 16)
        {
            return false;
        }

        return par0NBTTagCompound.hasKey("author");
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
            NBTTagString nbttagstring = (NBTTagString)nbttagcompound.getTag("title");

            if (nbttagstring != null)
            {
                return nbttagstring.toString();
            }
        }

        return super.getItemDisplayName(par1ItemStack);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, List par2List)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
            NBTTagString nbttagstring = (NBTTagString)nbttagcompound.getTag("author");

            if (nbttagstring != null)
            {
                par2List.add((new StringBuilder()).append("\2477").append(String.format(StatCollector.translateToLocalFormatted("book.byAuthor", new Object[]
                        {
                            nbttagstring.data
                        }), new Object[0])).toString());
            }
        }
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

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
