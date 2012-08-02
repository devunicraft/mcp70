package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager field_75335_b;

    public EntityAILookAtTradePlayer(EntityVillager par1EntityVillager)
    {
        super(par1EntityVillager, net.minecraft.src.EntityPlayer.class, 8F);
        field_75335_b = par1EntityVillager;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_75335_b.func_70940_q())
        {
            closestEntity = field_75335_b.func_70931_l_();
            return true;
        }
        else
        {
            return false;
        }
    }
}
