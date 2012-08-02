package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
    EntityLiving entityObj;
    List field_75524_b;
    List field_75525_c;

    public EntitySenses(EntityLiving par1EntityLiving)
    {
        field_75524_b = new ArrayList();
        field_75525_c = new ArrayList();
        entityObj = par1EntityLiving;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    public void clearSensingCache()
    {
        field_75524_b.clear();
        field_75525_c.clear();
    }

    /**
     * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
     */
    public boolean canSee(Entity par1Entity)
    {
        if (field_75524_b.contains(par1Entity))
        {
            return true;
        }

        if (field_75525_c.contains(par1Entity))
        {
            return false;
        }

        entityObj.worldObj.field_72984_F.startSection("canSee");
        boolean flag = entityObj.canEntityBeSeen(par1Entity);
        entityObj.worldObj.field_72984_F.endSection();

        if (flag)
        {
            field_75524_b.add(par1Entity);
        }
        else
        {
            field_75525_c.add(par1Entity);
        }

        return flag;
    }
}
