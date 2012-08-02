package net.minecraft.src;

import java.io.*;
import java.util.*;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private StringTranslate translator;

    /** set by the NetServerHandler or the ServerConfigurationManager */
    public NetServerHandler serverForThisPlayer;
    public MinecraftServer minecraftInstance;

    /** The ItemInWorldManager belonging to this player */
    public ItemInWorldManager theItemInWorldManager;
    public double field_71131_d;
    public double field_71132_e;
    public final List chunksToLoad = new LinkedList();

    /** entities added to this list will  be packet29'd to the player */
    public final List destroyedItemsNetCache = new LinkedList();

    /** set to getHealth */
    private int health;

    /** set to foodStats.GetFoodLevel */
    private int foodLevel;

    /** set to foodStats.getSaturationLevel() == 0.0F each tick */
    private boolean isHungry;

    /** Amount of experience the client was last set to */
    private int lastExperience;
    private int field_71145_cl;
    private int field_71142_cm;
    private int field_71143_cn;
    private boolean field_71140_co;
    private ItemStack field_71141_cp[] =
    {
        null, null, null, null, null
    };

    /** incremented every window open, is mod 100 */
    private int currentWindowID;
    public boolean field_71137_h;
    public int field_71138_i;
    public boolean field_71136_j;

    public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
    {
        super(par2World);
        translator = new StringTranslate("en_US");
        health = 0xfa0a1f01;
        foodLevel = 0xfa0a1f01;
        isHungry = true;
        lastExperience = 0xfa0a1f01;
        field_71145_cl = 60;
        field_71142_cm = 0;
        field_71143_cn = 0;
        field_71140_co = true;
        currentWindowID = 0;
        field_71136_j = false;
        par4ItemInWorldManager.field_73090_b = this;
        theItemInWorldManager = par4ItemInWorldManager;
        field_71142_cm = par1MinecraftServer.func_71203_ab().func_72395_o();
        ChunkCoordinates chunkcoordinates = par2World.getSpawnPoint();
        int i = chunkcoordinates.posX;
        int j = chunkcoordinates.posZ;
        int k = chunkcoordinates.posY;

        if (!par2World.worldProvider.hasNoSky && par2World.getWorldInfo().func_76077_q() != EnumGameType.ADVENTURE)
        {
            i += rand.nextInt(20) - 10;
            k = par2World.getTopSolidOrLiquidBlock(i, j);
            j += rand.nextInt(20) - 10;
        }

        setLocationAndAngles((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        minecraftInstance = par1MinecraftServer;
        stepHeight = 0.0F;
        username = par3Str;
        yOffset = 0.0F;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("playerGameType"))
        {
            theItemInWorldManager.setGameType(EnumGameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", theItemInWorldManager.getGameType().getID());
    }

    /**
     * Decrease the player level, used to pay levels for enchantments on items at enchanted table.
     */
    public void removeExperience(int par1)
    {
        super.removeExperience(par1);
        lastExperience = -1;
    }

    public void func_71116_b()
    {
        craftingInventory.func_75132_a(this);
    }

    public ItemStack[] func_70035_c()
    {
        return field_71141_cp;
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void resetHeight()
    {
        yOffset = 0.0F;
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        theItemInWorldManager.func_73075_a();
        field_71145_cl--;
        craftingInventory.updateCraftingResults();

        for (int i = 0; i < 5; i++)
        {
            ItemStack itemstack = func_71124_b(i);

            if (itemstack != field_71141_cp[i])
            {
                getServerForPlayer().getEntityTracker().func_72784_a(this, new Packet5PlayerInventory(entityId, i, itemstack));
                field_71141_cp[i] = itemstack;
            }
        }

        if (!chunksToLoad.isEmpty())
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = chunksToLoad.iterator();
            ArrayList arraylist1 = new ArrayList();

            do
            {
                if (!iterator.hasNext() || arraylist.size() >= 5)
                {
                    break;
                }

                ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
                iterator.remove();

                if (chunkcoordintpair != null && worldObj.blockExists(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4))
                {
                    arraylist.add(worldObj.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos));
                    arraylist1.addAll(((WorldServer)worldObj).getAllTileEntityInBox(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, chunkcoordintpair.chunkXPos * 16 + 16, 256, chunkcoordintpair.chunkZPos * 16 + 16));
                }
            }
            while (true);

            if (!arraylist.isEmpty())
            {
                serverForThisPlayer.sendPacketToPlayer(new Packet56MapChunks(arraylist));
                TileEntity tileentity;

                for (Iterator iterator2 = arraylist1.iterator(); iterator2.hasNext(); sendTileEntityToPlayer(tileentity))
                {
                    tileentity = (TileEntity)iterator2.next();
                }
            }
        }

        if (!destroyedItemsNetCache.isEmpty())
        {
            int j = Math.min(destroyedItemsNetCache.size(), 127);
            int ai[] = new int[j];
            Iterator iterator1 = destroyedItemsNetCache.iterator();

            for (int k = 0; iterator1.hasNext() && k < j; iterator1.remove())
            {
                ai[k++] = ((Integer)iterator1.next()).intValue();
            }

            serverForThisPlayer.sendPacketToPlayer(new Packet29DestroyEntity(ai));
        }
    }

    public void onUpdateEntity()
    {
        super.onUpdate();

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (itemstack == null || !Item.itemsList[itemstack.itemID].func_77643_m_() || serverForThisPlayer.func_72568_e() > 2)
            {
                continue;
            }

            Packet packet = ((ItemMapBase)Item.itemsList[itemstack.itemID]).func_77871_c(itemstack, worldObj, this);

            if (packet != null)
            {
                serverForThisPlayer.sendPacketToPlayer(packet);
            }
        }

        if (inPortal)
        {
            if (minecraftInstance.func_71255_r())
            {
                if (craftingInventory != inventorySlots)
                {
                    closeScreen();
                }

                if (ridingEntity != null)
                {
                    mountEntity(ridingEntity);
                }
                else
                {
                    timeInPortal += 0.0125F;

                    if (timeInPortal >= 1.0F)
                    {
                        timeInPortal = 1.0F;
                        timeUntilPortal = 10;
                        byte byte0 = 0;

                        if (dimension == -1)
                        {
                            byte0 = 0;
                        }
                        else
                        {
                            byte0 = -1;
                        }

                        minecraftInstance.func_71203_ab().transferPlayerToDimension(this, byte0);
                        lastExperience = -1;
                        health = -1;
                        foodLevel = -1;
                        triggerAchievement(AchievementList.portal);
                    }
                }

                inPortal = false;
            }
        }
        else
        {
            if (timeInPortal > 0.0F)
            {
                timeInPortal -= 0.05F;
            }

            if (timeInPortal < 0.0F)
            {
                timeInPortal = 0.0F;
            }
        }

        if (timeUntilPortal > 0)
        {
            timeUntilPortal--;
        }

        if (getHealth() != health || foodLevel != foodStats.getFoodLevel() || (foodStats.getSaturationLevel() == 0.0F) != isHungry)
        {
            serverForThisPlayer.sendPacketToPlayer(new Packet8UpdateHealth(getHealth(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
            health = getHealth();
            foodLevel = foodStats.getFoodLevel();
            isHungry = foodStats.getSaturationLevel() == 0.0F;
        }

        if (experienceTotal != lastExperience)
        {
            lastExperience = experienceTotal;
            serverForThisPlayer.sendPacketToPlayer(new Packet43Experience(experience, experienceTotal, experienceLevel));
        }
    }

    public ItemStack func_71124_b(int par1)
    {
        if (par1 == 0)
        {
            return inventory.getCurrentItem();
        }
        else
        {
            return inventory.armorInventory[par1 - 1];
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        minecraftInstance.func_71203_ab().sendPacketToAllPlayers(new Packet3Chat(par1DamageSource.func_76360_b(this)));
        inventory.dropAllItems();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (field_71145_cl > 0)
        {
            return false;
        }

        if (!minecraftInstance.isPVPEnabled() && (par1DamageSource instanceof EntityDamageSource))
        {
            Entity entity = par1DamageSource.getEntity();

            if (entity instanceof EntityPlayer)
            {
                return false;
            }

            if (entity instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entity;

                if (entityarrow.shootingEntity instanceof EntityPlayer)
                {
                    return false;
                }
            }
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * returns if pvp is enabled or not
     */
    protected boolean isPVPEnabled()
    {
        return minecraftInstance.isPVPEnabled();
    }

    public void travelToTheEnd(int par1)
    {
        if (dimension == 1 && par1 == 1)
        {
            triggerAchievement(AchievementList.theEnd2);
            worldObj.setEntityDead(this);
            field_71136_j = true;
            serverForThisPlayer.sendPacketToPlayer(new Packet70GameEvent(4, 0));
        }
        else
        {
            triggerAchievement(AchievementList.theEnd);
            ChunkCoordinates chunkcoordinates = minecraftInstance.worldServerForDimension(par1).getEntrancePortalLocation();

            if (chunkcoordinates != null)
            {
                serverForThisPlayer.setPlayerLocation(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, 0.0F, 0.0F);
            }

            minecraftInstance.func_71203_ab().transferPlayerToDimension(this, 1);
            lastExperience = -1;
            health = -1;
            foodLevel = -1;
        }
    }

    /**
     * called from onUpdate for all tileEntity in specific chunks
     */
    private void sendTileEntityToPlayer(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.func_70319_e();

            if (packet != null)
            {
                serverForThisPlayer.sendPacketToPlayer(packet);
            }
        }
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void onItemPickup(Entity par1Entity, int par2)
    {
        if (!par1Entity.isDead)
        {
            EntityTracker entitytracker = getServerForPlayer().getEntityTracker();

            if (par1Entity instanceof EntityItem)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityArrow)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }

            if (par1Entity instanceof EntityXPOrb)
            {
                entitytracker.func_72784_a(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
            }
        }

        super.onItemPickup(par1Entity, par2);
        craftingInventory.updateCraftingResults();
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        if (!isSwinging)
        {
            swingProgressInt = -1;
            isSwinging = true;
            getServerForPlayer().getEntityTracker().func_72784_a(this, new Packet18Animation(this, 1));
        }
    }

    /**
     * Attempts to have the player sleep in a bed at the specified location.
     */
    public EnumStatus sleepInBedAt(int par1, int par2, int par3)
    {
        EnumStatus enumstatus = super.sleepInBedAt(par1, par2, par3);

        if (enumstatus == EnumStatus.OK)
        {
            Packet17Sleep packet17sleep = new Packet17Sleep(this, 0, par1, par2, par3);
            getServerForPlayer().getEntityTracker().func_72784_a(this, packet17sleep);
            serverForThisPlayer.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
            serverForThisPlayer.sendPacketToPlayer(packet17sleep);
        }

        return enumstatus;
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
    {
        if (isPlayerSleeping())
        {
            getServerForPlayer().getEntityTracker().func_72789_b(this, new Packet18Animation(this, 3));
        }

        super.wakeUpPlayer(par1, par2, par3);

        if (serverForThisPlayer != null)
        {
            serverForThisPlayer.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
        }
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity)
    {
        super.mountEntity(par1Entity);
        serverForThisPlayer.sendPacketToPlayer(new Packet39AttachEntity(this, ridingEntity));
        serverForThisPlayer.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double d, boolean flag)
    {
    }

    /**
     * likeUpdateFallState, but called from updateFlyingState, rather than moveEntity
     */
    public void updateFlyingState(double par1, boolean par3)
    {
        super.updateFallState(par1, par3);
    }

    private void incrementWindowID()
    {
        currentWindowID = currentWindowID % 100 + 1;
    }

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void displayGUIWorkbench(int par1, int par2, int par3)
    {
        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 1, "Crafting", 9));
        craftingInventory = new ContainerWorkbench(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    public void displayGUIEnchantment(int par1, int par2, int par3)
    {
        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 4, "Enchanting", 9));
        craftingInventory = new ContainerEnchantment(inventory, worldObj, par1, par2, par3);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory par1IInventory)
    {
        if (craftingInventory != inventorySlots)
        {
            closeScreen();
        }

        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory()));
        craftingInventory = new ContainerChest(inventory, par1IInventory);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
    {
        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory()));
        craftingInventory = new ContainerFurnace(inventory, par1TileEntityFurnace);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
    {
        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory()));
        craftingInventory = new ContainerDispenser(inventory, par1TileEntityDispenser);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
    {
        incrementWindowID();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory()));
        craftingInventory = new ContainerBrewingStand(inventory, par1TileEntityBrewingStand);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
    }

    public void displayGUIMerchant(IMerchant par1IMerchant)
    {
        incrementWindowID();
        craftingInventory = new ContainerMerchant(inventory, par1IMerchant, worldObj);
        craftingInventory.windowId = currentWindowID;
        craftingInventory.func_75132_a(this);
        InventoryMerchant inventorymerchant = ((ContainerMerchant)craftingInventory).func_75174_d();
        serverForThisPlayer.sendPacketToPlayer(new Packet100OpenWindow(currentWindowID, 6, inventorymerchant.getInvName(), inventorymerchant.getSizeInventory()));
        MerchantRecipeList merchantrecipelist = par1IMerchant.func_70934_b(this);

        if (merchantrecipelist != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeInt(currentWindowID);
                merchantrecipelist.func_77200_a(dataoutputstream);
                serverForThisPlayer.sendPacketToPlayer(new Packet250CustomPayload("MC|TrList", bytearrayoutputstream.toByteArray()));
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }

    /**
     * inform the player of a change in a single slot
     */
    public void updateCraftingInventorySlot(Container par1Container, int par2, ItemStack par3ItemStack)
    {
        if (par1Container.getSlot(par2) instanceof SlotCrafting)
        {
            return;
        }

        if (field_71137_h)
        {
            return;
        }
        else
        {
            serverForThisPlayer.sendPacketToPlayer(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
            return;
        }
    }

    public void func_71120_a(Container par1Container)
    {
        func_71110_a(par1Container, par1Container.func_75138_a());
    }

    public void func_71110_a(Container par1Container, List par2List)
    {
        serverForThisPlayer.sendPacketToPlayer(new Packet104WindowItems(par1Container.windowId, par2List));
        serverForThisPlayer.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
    }

    /**
     * send information about the crafting inventory to the client(currently only for furnace times)
     */
    public void updateCraftingInventoryInfo(Container par1Container, int par2, int par3)
    {
        serverForThisPlayer.sendPacketToPlayer(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        serverForThisPlayer.sendPacketToPlayer(new Packet101CloseWindow(craftingInventory.windowId));
        func_71128_l();
    }

    public void func_71113_k()
    {
        if (field_71137_h)
        {
            return;
        }
        else
        {
            serverForThisPlayer.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
            return;
        }
    }

    public void func_71128_l()
    {
        craftingInventory.onCraftGuiClosed(this);
        craftingInventory = inventorySlots;
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase == null)
        {
            return;
        }

        if (!par1StatBase.isIndependent)
        {
            for (; par2 > 100; par2 -= 100)
            {
                serverForThisPlayer.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, 100));
            }

            serverForThisPlayer.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, par2));
        }
    }

    public void func_71123_m()
    {
        if (ridingEntity != null)
        {
            mountEntity(ridingEntity);
        }

        if (riddenByEntity != null)
        {
            riddenByEntity.mountEntity(this);
        }

        if (sleeping)
        {
            wakeUpPlayer(true, false, false);
        }
    }

    public void func_71118_n()
    {
        health = 0xfa0a1f01;
    }

    /**
     * Add a chat message to the player
     */
    public void addChatMessage(String par1Str)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = stringtranslate.translateKey(par1Str);
        serverForThisPlayer.sendPacketToPlayer(new Packet3Chat(s));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        serverForThisPlayer.sendPacketToPlayer(new Packet38EntityStatus(entityId, (byte)9));
        super.onItemUseFinish();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void setItemInUse(ItemStack par1ItemStack, int par2)
    {
        super.setItemInUse(par1ItemStack, par2);

        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat)
        {
            getServerForPlayer().getEntityTracker().func_72789_b(this, new Packet18Animation(this, 5));
        }
    }

    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onNewPotionEffect(par1PotionEffect);
        serverForThisPlayer.sendPacketToPlayer(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onChangedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onChangedPotionEffect(par1PotionEffect);
        serverForThisPlayer.sendPacketToPlayer(new Packet41EntityEffect(entityId, par1PotionEffect));
    }

    protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onFinishedPotionEffect(par1PotionEffect);
        serverForThisPlayer.sendPacketToPlayer(new Packet42RemoveEntityEffect(entityId, par1PotionEffect));
    }

    /**
     * Move the entity to the coordinates informed, but keep yaw/pitch values.
     */
    public void setPositionAndUpdate(double par1, double par3, double par5)
    {
        serverForThisPlayer.setPlayerLocation(par1, par3, par5, rotationYaw, rotationPitch);
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity par1Entity)
    {
        getServerForPlayer().getEntityTracker().func_72789_b(this, new Packet18Animation(par1Entity, 6));
    }

    public void onEnchantmentCritical(Entity par1Entity)
    {
        getServerForPlayer().getEntityTracker().func_72789_b(this, new Packet18Animation(par1Entity, 7));
    }

    public void func_71016_p()
    {
        if (serverForThisPlayer == null)
        {
            return;
        }
        else
        {
            serverForThisPlayer.sendPacketToPlayer(new Packet202PlayerAbilities(capabilities));
            return;
        }
    }

    public WorldServer getServerForPlayer()
    {
        return (WorldServer)worldObj;
    }

    public void sendGameTypeToPlayer(EnumGameType par1EnumGameType)
    {
        theItemInWorldManager.setGameType(par1EnumGameType);
        serverForThisPlayer.sendPacketToPlayer(new Packet70GameEvent(3, par1EnumGameType.getID()));
    }

    public void sendChatToPlayer(String par1Str)
    {
        serverForThisPlayer.sendPacketToPlayer(new Packet3Chat(par1Str));
    }

    public boolean func_70003_b(String par1Str)
    {
        if ("seed".equals(par1Str) && !minecraftInstance.isDedicatedServer())
        {
            return true;
        }
        else
        {
            return minecraftInstance.func_71203_ab().func_72353_e(username);
        }
    }

    public String func_71114_r()
    {
        String s = serverForThisPlayer.theNetworkManager.getSocketAddress().toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }

    public void func_71125_a(Packet204ClientInfo par1Packet204ClientInfo)
    {
        if (translator.getLanguageList().containsKey(par1Packet204ClientInfo.getLanguage()))
        {
            translator.setLanguage(par1Packet204ClientInfo.getLanguage());
        }

        int i = 256 >> par1Packet204ClientInfo.getRenderDistance();

        if (i > 3 && i < 15)
        {
            field_71142_cm = i;
        }

        field_71143_cn = par1Packet204ClientInfo.getChatVisibility();
        field_71140_co = par1Packet204ClientInfo.getChatColours();

        if (minecraftInstance.isSinglePlayer() && minecraftInstance.func_71214_G().equals(username))
        {
            minecraftInstance.func_71226_c(par1Packet204ClientInfo.getDifficulty());
        }
    }

    public StringTranslate getTranslator()
    {
        return translator;
    }

    public int func_71126_v()
    {
        return field_71143_cn;
    }

    public void func_71115_a(String par1Str, int par2)
    {
        String s = (new StringBuilder()).append(par1Str).append("\0").append(par2).toString();
        serverForThisPlayer.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", s.getBytes()));
    }
}
