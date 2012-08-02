package net.minecraft.src;

import java.util.Random;

public class EntityChicken extends EntityAnimal
{
    public boolean field_70885_d;
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i;

    /** The time until the next egg is spawned. */
    public int timeUntilNextEgg;

    public EntityChicken(World par1World)
    {
        super(par1World);
        field_70885_d = false;
        field_70886_e = 0.0F;
        destPos = 0.0F;
        field_70889_i = 1.0F;
        texture = "/mob/chicken.png";
        setSize(0.3F, 0.7F);
        timeUntilNextEgg = rand.nextInt(6000) + 6000;
        float f = 0.25F;
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        tasks.addTask(2, new EntityAIMate(this, f));
        tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.shiftedIndex, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
        tasks.addTask(5, new EntityAIWander(this, f));
        tasks.addTask(6, new EntityAIWatchClosest(this, net.minecraft.src.EntityPlayer.class, 6F));
        tasks.addTask(7, new EntityAILookIdle(this));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 4;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_70888_h = field_70886_e;
        field_70884_g = destPos;
        destPos += (double)(onGround ? -1 : 4) * 0.29999999999999999D;

        if (destPos < 0.0F)
        {
            destPos = 0.0F;
        }

        if (destPos > 1.0F)
        {
            destPos = 1.0F;
        }

        if (!onGround && field_70889_i < 1.0F)
        {
            field_70889_i = 1.0F;
        }

        field_70889_i *= 0.90000000000000002D;

        if (!onGround && motionY < 0.0D)
        {
            motionY *= 0.59999999999999998D;
        }

        field_70886_e += field_70889_i * 2.0F;

        if (!isChild() && !worldObj.isRemote && --timeUntilNextEgg <= 0)
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            dropItem(Item.egg.shiftedIndex, 1);
            timeUntilNextEgg = rand.nextInt(6000) + 6000;
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float f)
    {
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.chicken";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.chickenhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.chickenhurt";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.feather.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int i = rand.nextInt(3) + rand.nextInt(1 + par2);

        for (int j = 0; j < i; j++)
        {
            dropItem(Item.feather.shiftedIndex, 1);
        }

        if (isBurning())
        {
            dropItem(Item.chickenCooked.shiftedIndex, 1);
        }
        else
        {
            dropItem(Item.chickenRaw.shiftedIndex, 1);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        return new EntityChicken(worldObj);
    }
}
