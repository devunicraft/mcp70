package net.minecraft.src;

public class EntityLookHelper
{
    private EntityLiving entity;

    /**
     * The amount of change that is made each update for an entity facing a direction.
     */
    private float deltaLookYaw;

    /**
     * The amount of change that is made each update for an entity facing a direction.
     */
    private float deltaLookPitch;

    /** Whether or not the entity is trying to look at something. */
    private boolean isLooking;
    private double posX;
    private double posY;
    private double posZ;

    public EntityLookHelper(EntityLiving par1EntityLiving)
    {
        isLooking = false;
        entity = par1EntityLiving;
    }

    /**
     * Sets position to look at using entity
     */
    public void setLookPositionWithEntity(Entity par1Entity, float par2, float par3)
    {
        posX = par1Entity.posX;

        if (par1Entity instanceof EntityLiving)
        {
            posY = par1Entity.posY + (double)par1Entity.getEyeHeight();
        }
        else
        {
            posY = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2D;
        }

        posZ = par1Entity.posZ;
        deltaLookYaw = par2;
        deltaLookPitch = par3;
        isLooking = true;
    }

    /**
     * Sets position to look at
     */
    public void setLookPosition(double par1, double par3, double par5, float par7, float par8)
    {
        posX = par1;
        posY = par3;
        posZ = par5;
        deltaLookYaw = par7;
        deltaLookPitch = par8;
        isLooking = true;
    }

    /**
     * Updates look
     */
    public void onUpdateLook()
    {
        entity.rotationPitch = 0.0F;

        if (isLooking)
        {
            isLooking = false;
            double d = posX - entity.posX;
            double d1 = posY - (entity.posY + (double)entity.getEyeHeight());
            double d2 = posZ - entity.posZ;
            double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
            float f1 = (float)((Math.atan2(d2, d) * 180D) / Math.PI) - 90F;
            float f2 = (float)(-((Math.atan2(d1, d3) * 180D) / Math.PI));
            entity.rotationPitch = updateRotation(entity.rotationPitch, f2, deltaLookPitch);
            entity.rotationYawHead = updateRotation(entity.rotationYawHead, f1, deltaLookYaw);
        }
        else
        {
            entity.rotationYawHead = updateRotation(entity.rotationYawHead, entity.renderYawOffset, 10F);
        }

        float f = MathHelper.func_76142_g(entity.rotationYawHead - entity.renderYawOffset);

        if (!entity.getNavigator().noPath())
        {
            if (f < -75F)
            {
                entity.rotationYawHead = entity.renderYawOffset - 75F;
            }

            if (f > 75F)
            {
                entity.rotationYawHead = entity.renderYawOffset + 75F;
            }
        }
    }

    private float updateRotation(float par1, float par2, float par3)
    {
        float f = MathHelper.func_76142_g(par2 - par1);

        if (f > par3)
        {
            f = par3;
        }

        if (f < -par3)
        {
            f = -par3;
        }

        return par1 + f;
    }
}
