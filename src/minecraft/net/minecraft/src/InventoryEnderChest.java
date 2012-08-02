package net.minecraft.src;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest field_70488_a;

    public InventoryEnderChest()
    {
        super("container.enderchest", 27);
    }

    public void func_70485_a(TileEntityEnderChest par1TileEntityEnderChest)
    {
        field_70488_a = par1TileEntityEnderChest;
    }

    public void func_70486_a(NBTTagList par1NBTTagList)
    {
        for (int i = 0; i < getSizeInventory(); i++)
        {
            setInventorySlotContents(i, null);
        }

        for (int j = 0; j < par1NBTTagList.tagCount(); j++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.tagAt(j);
            int k = nbttagcompound.getByte("Slot") & 0xff;

            if (k >= 0 && k < getSizeInventory())
            {
                setInventorySlotContents(k, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
    }

    public NBTTagList func_70487_g()
    {
        NBTTagList nbttaglist = new NBTTagList("EnderItems");

        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack itemstack = getStackInSlot(i);

            if (itemstack != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        return nbttaglist;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        if (field_70488_a != null && !field_70488_a.func_70365_a(par1EntityPlayer))
        {
            return false;
        }
        else
        {
            return super.isUseableByPlayer(par1EntityPlayer);
        }
    }

    public void openChest()
    {
        if (field_70488_a != null)
        {
            field_70488_a.func_70364_a();
        }

        super.openChest();
    }

    public void closeChest()
    {
        if (field_70488_a != null)
        {
            field_70488_a.func_70366_b();
        }

        super.closeChest();
        field_70488_a = null;
    }
}
