package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
    /**
     * The damage value of the thrown potion that this EntityPotion represents.
     */
    private int potionDamage;

    public EntityPotion(World par1World)
    {
        super(par1World);
    }

    public EntityPotion(World par1World, EntityLiving par2EntityLiving, int par3)
    {
        super(par1World, par2EntityLiving);
        potionDamage = par3;
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World, par2, par4, par6);
        potionDamage = par8;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20F;
    }

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
    public int getPotionDamage()
    {
        return potionDamage;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!worldObj.isRemote)
        {
            List list = Item.potion.getEffects(potionDamage);

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = boundingBox.expand(4D, 2D, 4D);
                List list1 = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    for (Iterator iterator = list1.iterator(); iterator.hasNext();)
                    {
                        EntityLiving entityliving = (EntityLiving)iterator.next();
                        double d = getDistanceSqToEntity(entityliving);

                        if (d < 16D)
                        {
                            double d1 = 1.0D - Math.sqrt(d) / 4D;

                            if (entityliving == par1MovingObjectPosition.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                    Potion.potionTypes[i].affectEntity(thrower, entityliving, potioneffect.getAmplifier(), d1);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entityliving.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            worldObj.playAuxSFX(2002, (int)Math.round(posX), (int)Math.round(posY), (int)Math.round(posZ), potionDamage);
            setDead();
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        potionDamage = par1NBTTagCompound.getInteger("potionValue");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("potionValue", potionDamage);
    }
}
