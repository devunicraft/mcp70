package net.minecraft.src;

import java.util.Random;

public class TileEntityEnderChest extends TileEntity
{
    public float field_70370_a;
    public float field_70368_b;
    public int field_70369_c;
    private int field_70367_d;

    public TileEntityEnderChest()
    {
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if ((++field_70367_d % 20) * 4 == 0)
        {
            worldObj.sendClientEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, field_70369_c);
        }

        field_70368_b = field_70370_a;
        float f = 0.1F;

        if (field_70369_c > 0 && field_70370_a == 0.0F)
        {
            double d = (double)xCoord + 0.5D;
            double d1 = (double)zCoord + 0.5D;
            worldObj.playSoundEffect(d, (double)yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (field_70369_c == 0 && field_70370_a > 0.0F || field_70369_c > 0 && field_70370_a < 1.0F)
        {
            float f1 = field_70370_a;

            if (field_70369_c > 0)
            {
                field_70370_a += f;
            }
            else
            {
                field_70370_a -= f;
            }

            if (field_70370_a > 1.0F)
            {
                field_70370_a = 1.0F;
            }

            float f2 = 0.5F;

            if (field_70370_a < f2 && f1 >= f2)
            {
                double d2 = (double)xCoord + 0.5D;
                double d3 = (double)zCoord + 0.5D;
                worldObj.playSoundEffect(d2, (double)yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (field_70370_a < 0.0F)
            {
                field_70370_a = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public void receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            field_70369_c = par2;
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        updateContainingBlockInfo();
        super.invalidate();
    }

    public void func_70364_a()
    {
        field_70369_c++;
        worldObj.sendClientEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, field_70369_c);
    }

    public void func_70366_b()
    {
        field_70369_c--;
        worldObj.sendClientEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, field_70369_c);
    }

    public boolean func_70365_a(EntityPlayer par1EntityPlayer)
    {
        if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }
}
