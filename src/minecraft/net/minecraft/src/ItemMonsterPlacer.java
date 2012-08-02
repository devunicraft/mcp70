package net.minecraft.src;

import java.util.*;

public class ItemMonsterPlacer extends Item
{
    public ItemMonsterPlacer(int par1)
    {
        super(par1);
        setHasSubtypes(true);
        setTabToDisplayOn(CreativeTabs.tabMisc);
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        String s = (new StringBuilder()).append("").append(StatCollector.translateToLocal((new StringBuilder()).append(getItemName()).append(".name").toString())).toString().trim();
        String s1 = EntityList.getStringFromID(par1ItemStack.getItemDamage());

        if (s1 != null)
        {
            s = (new StringBuilder()).append(s).append(" ").append(StatCollector.translateToLocal((new StringBuilder()).append("entity.").append(s1).append(".name").toString())).toString();
        }

        return s;
    }

    public int getColorFromDamage(int par1, int par2)
    {
        EntityEggInfo entityegginfo = (EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(par1));

        if (entityegginfo != null)
        {
            if (par2 == 0)
            {
                return entityegginfo.primaryColor;
            }
            else
            {
                return entityegginfo.secondaryColor;
            }
        }
        else
        {
            return 0xffffff;
        }
    }

    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public int getIconFromDamageForRenderPass(int par1, int par2)
    {
        if (par2 > 0)
        {
            return super.getIconFromDamageForRenderPass(par1, par2) + 16;
        }
        else
        {
            return super.getIconFromDamageForRenderPass(par1, par2);
        }
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }

        int i = par3World.getBlockId(par4, par5, par6);
        par4 += Facing.offsetsXForSide[par7];
        par5 += Facing.offsetsYForSide[par7];
        par6 += Facing.offsetsZForSide[par7];
        double d = 0.0D;

        if (par7 == 1 && i == Block.fence.blockID || i == Block.netherFence.blockID)
        {
            d = 0.5D;
        }

        if (spawnCreature(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + d, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
        {
            par1ItemStack.stackSize--;
        }

        return true;
    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public static boolean spawnCreature(World par0World, int par1, double par2, double par4, double par6)
    {
        if (!EntityList.entityEggs.containsKey(Integer.valueOf(par1)))
        {
            return false;
        }

        Entity entity = EntityList.createEntityByID(par1, par0World);

        if (entity != null)
        {
            entity.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360F, 0.0F);

            if (entity instanceof EntityVillager)
            {
                EntityVillager entityvillager = (EntityVillager)entity;
                entityvillager.setProfession(entityvillager.getRNG().nextInt(5));
                par0World.spawnEntityInWorld(entityvillager);
                return true;
            }

            par0World.spawnEntityInWorld(entity);
            ((EntityLiving)entity).playLivingSound();
        }

        return entity != null;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        EntityEggInfo entityegginfo;

        for (Iterator iterator = EntityList.entityEggs.values().iterator(); iterator.hasNext(); par3List.add(new ItemStack(par1, 1, entityegginfo.spawnedID)))
        {
            entityegginfo = (EntityEggInfo)iterator.next();
        }
    }
}
