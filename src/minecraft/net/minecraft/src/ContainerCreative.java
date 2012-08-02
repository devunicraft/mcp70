package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class ContainerCreative extends Container
{
    /** the list of items in this container */
    public List itemList;

    public ContainerCreative(EntityPlayer par1EntityPlayer)
    {
        itemList = new ArrayList();
        InventoryPlayer inventoryplayer = par1EntityPlayer.inventory;

        for (int i = 0; i < 5; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                func_75146_a(new Slot(GuiContainerCreative.getInventory(), i * 9 + k, 9 + k * 18, 18 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            func_75146_a(new Slot(inventoryplayer, j, 9 + j * 18, 112));
        }

        scrollTo(0.0F);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    /**
     * Updates the gui slots ItemStack's based on scroll position.
     */
    public void scrollTo(float par1)
    {
        int i = (itemList.size() / 9 - 5) + 1;
        int j = (int)((double)(par1 * (float)i) + 0.5D);

        if (j < 0)
        {
            j = 0;
        }

        for (int k = 0; k < 5; k++)
        {
            for (int l = 0; l < 9; l++)
            {
                int i1 = l + (k + j) * 9;

                if (i1 >= 0 && i1 < itemList.size())
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 9, (ItemStack)itemList.get(i1));
                }
                else
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 9, null);
                }
            }
        }
    }

    /**
     * theCreativeContainer seems to be hard coded to 9x5 items
     */
    public boolean hasMoreThan1PageOfItemsInList()
    {
        return itemList.size() > 45;
    }

    protected void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer)
    {
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        if (par1 >= inventorySlots.size() - 9 && par1 < inventorySlots.size())
        {
            Slot slot = (Slot)inventorySlots.get(par1);

            if (slot != null && slot.getHasStack())
            {
                slot.putStack(null);
            }
        }

        return null;
    }
}
