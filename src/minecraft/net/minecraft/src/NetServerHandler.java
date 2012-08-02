package net.minecraft.src;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class NetServerHandler extends NetHandler
{
    public static Logger theLogger = Logger.getLogger("Minecraft");
    public NetworkManager theNetworkManager;
    public boolean serverShuttingDown;
    private MinecraftServer theMinecraftServer;
    private EntityPlayerMP MPPlayer;

    /** incremented each tick */
    private int currentTicks;

    /**
     * player is kicked if they float for over 80 ticks without flying enabled
     */
    private int ticksForFloatKick;
    private boolean field_72584_h;
    private int field_72585_i;
    private long field_72582_j;
    private static Random field_72583_k = new Random();
    private long ticksOfLastKeepAlive;
    private int chatSpamThresholdCount;
    private int creativeItemCreationSpamThresholdTally;
    private double posX;
    private double posY;
    private double posZ;
    private boolean field_72587_r;
    private IntHashMap field_72586_s;

    public NetServerHandler(MinecraftServer par1MinecraftServer, NetworkManager par2NetworkManager, EntityPlayerMP par3EntityPlayerMP)
    {
        serverShuttingDown = false;
        chatSpamThresholdCount = 0;
        creativeItemCreationSpamThresholdTally = 0;
        field_72587_r = true;
        field_72586_s = new IntHashMap();
        theMinecraftServer = par1MinecraftServer;
        theNetworkManager = par2NetworkManager;
        par2NetworkManager.setNetHandler(this);
        MPPlayer = par3EntityPlayerMP;
        par3EntityPlayerMP.serverForThisPlayer = this;
    }

    public void func_72570_d()
    {
        field_72584_h = false;
        currentTicks++;
        theMinecraftServer.field_71304_b.startSection("packetflow");
        theNetworkManager.processReadPackets();
        theMinecraftServer.field_71304_b.endStartSection("keepAlive");

        if ((long)currentTicks - ticksOfLastKeepAlive > 20L)
        {
            ticksOfLastKeepAlive = currentTicks;
            field_72582_j = System.nanoTime() / 0xf4240L;
            field_72585_i = field_72583_k.nextInt();
            sendPacketToPlayer(new Packet0KeepAlive(field_72585_i));
        }

        if (chatSpamThresholdCount > 0)
        {
            chatSpamThresholdCount--;
        }

        if (creativeItemCreationSpamThresholdTally > 0)
        {
            creativeItemCreationSpamThresholdTally--;
        }

        theMinecraftServer.field_71304_b.endStartSection("playerTick");

        if (!field_72584_h && !MPPlayer.field_71136_j)
        {
            MPPlayer.onUpdateEntity();
        }

        theMinecraftServer.field_71304_b.endSection();
    }

    public void kickPlayerFromServer(String par1Str)
    {
        if (serverShuttingDown)
        {
            return;
        }
        else
        {
            MPPlayer.func_71123_m();
            sendPacketToPlayer(new Packet255KickDisconnect(par1Str));
            theNetworkManager.serverShutdown();
            theMinecraftServer.func_71203_ab().sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(MPPlayer.username).append(" left the game.").toString()));
            theMinecraftServer.func_71203_ab().func_72367_e(MPPlayer);
            serverShuttingDown = true;
            return;
        }
    }

    public void handleFlying(Packet10Flying par1Packet10Flying)
    {
        WorldServer worldserver = theMinecraftServer.worldServerForDimension(MPPlayer.dimension);
        field_72584_h = true;

        if (MPPlayer.field_71136_j)
        {
            return;
        }

        if (!field_72587_r)
        {
            double d = par1Packet10Flying.yPosition - posY;

            if (par1Packet10Flying.xPosition == posX && d * d < 0.01D && par1Packet10Flying.zPosition == posZ)
            {
                field_72587_r = true;
            }
        }

        if (field_72587_r)
        {
            if (MPPlayer.ridingEntity != null)
            {
                float f = MPPlayer.rotationYaw;
                float f1 = MPPlayer.rotationPitch;
                MPPlayer.ridingEntity.updateRiderPosition();
                double d2 = MPPlayer.posX;
                double d4 = MPPlayer.posY;
                double d6 = MPPlayer.posZ;
                double d8 = 0.0D;
                double d9 = 0.0D;

                if (par1Packet10Flying.rotating)
                {
                    f = par1Packet10Flying.yaw;
                    f1 = par1Packet10Flying.pitch;
                }

                if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999D && par1Packet10Flying.stance == -999D)
                {
                    if (par1Packet10Flying.xPosition > 1.0D || par1Packet10Flying.zPosition > 1.0D)
                    {
                        System.err.println((new StringBuilder()).append(MPPlayer.username).append(" was caught trying to crash the server with an invalid position.").toString());
                        kickPlayerFromServer("Nope!");
                        return;
                    }

                    d8 = par1Packet10Flying.xPosition;
                    d9 = par1Packet10Flying.zPosition;
                }

                MPPlayer.onGround = par1Packet10Flying.onGround;
                MPPlayer.onUpdateEntity();
                MPPlayer.moveEntity(d8, 0.0D, d9);
                MPPlayer.setPositionAndRotation(d2, d4, d6, f, f1);
                MPPlayer.motionX = d8;
                MPPlayer.motionZ = d9;

                if (MPPlayer.ridingEntity != null)
                {
                    worldserver.func_73050_b(MPPlayer.ridingEntity, true);
                }

                if (MPPlayer.ridingEntity != null)
                {
                    MPPlayer.ridingEntity.updateRiderPosition();
                }

                theMinecraftServer.func_71203_ab().func_72358_d(MPPlayer);
                posX = MPPlayer.posX;
                posY = MPPlayer.posY;
                posZ = MPPlayer.posZ;
                worldserver.updateEntity(MPPlayer);
                return;
            }

            if (MPPlayer.isPlayerSleeping())
            {
                MPPlayer.onUpdateEntity();
                MPPlayer.setPositionAndRotation(posX, posY, posZ, MPPlayer.rotationYaw, MPPlayer.rotationPitch);
                worldserver.updateEntity(MPPlayer);
                return;
            }

            double d1 = MPPlayer.posY;
            posX = MPPlayer.posX;
            posY = MPPlayer.posY;
            posZ = MPPlayer.posZ;
            double d3 = MPPlayer.posX;
            double d5 = MPPlayer.posY;
            double d7 = MPPlayer.posZ;
            float f2 = MPPlayer.rotationYaw;
            float f3 = MPPlayer.rotationPitch;

            if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999D && par1Packet10Flying.stance == -999D)
            {
                par1Packet10Flying.moving = false;
            }

            if (par1Packet10Flying.moving)
            {
                d3 = par1Packet10Flying.xPosition;
                d5 = par1Packet10Flying.yPosition;
                d7 = par1Packet10Flying.zPosition;
                double d10 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;

                if (!MPPlayer.isPlayerSleeping() && (d10 > 1.6499999999999999D || d10 < 0.10000000000000001D))
                {
                    kickPlayerFromServer("Illegal stance");
                    theLogger.warning((new StringBuilder()).append(MPPlayer.username).append(" had an illegal stance: ").append(d10).toString());
                    return;
                }

                if (Math.abs(par1Packet10Flying.xPosition) > 32000000D || Math.abs(par1Packet10Flying.zPosition) > 32000000D)
                {
                    kickPlayerFromServer("Illegal position");
                    return;
                }
            }

            if (par1Packet10Flying.rotating)
            {
                f2 = par1Packet10Flying.yaw;
                f3 = par1Packet10Flying.pitch;
            }

            MPPlayer.onUpdateEntity();
            MPPlayer.ySize = 0.0F;
            MPPlayer.setPositionAndRotation(posX, posY, posZ, f2, f3);

            if (!field_72587_r)
            {
                return;
            }

            double d11 = d3 - MPPlayer.posX;
            double d12 = d5 - MPPlayer.posY;
            double d13 = d7 - MPPlayer.posZ;
            double d14 = Math.min(Math.abs(d11), Math.abs(MPPlayer.motionX));
            double d15 = Math.min(Math.abs(d12), Math.abs(MPPlayer.motionY));
            double d16 = Math.min(Math.abs(d13), Math.abs(MPPlayer.motionZ));
            double d17 = d14 * d14 + d15 * d15 + d16 * d16;

            if (d17 > 100D && (!theMinecraftServer.isSinglePlayer() || !theMinecraftServer.func_71214_G().equals(MPPlayer.username)))
            {
                theLogger.warning((new StringBuilder()).append(MPPlayer.username).append(" moved too quickly! ").append(d11).append(",").append(d12).append(",").append(d13).append(" (").append(d14).append(", ").append(d15).append(", ").append(d16).append(")").toString());
                setPlayerLocation(posX, posY, posZ, MPPlayer.rotationYaw, MPPlayer.rotationPitch);
                return;
            }

            float f4 = 0.0625F;
            boolean flag = worldserver.getCollidingBoundingBoxes(MPPlayer, MPPlayer.boundingBox.copy().contract(f4, f4, f4)).isEmpty();

            if (MPPlayer.onGround && !par1Packet10Flying.onGround && d12 > 0.0D)
            {
                MPPlayer.addExhaustion(0.2F);
            }

            MPPlayer.moveEntity(d11, d12, d13);
            MPPlayer.onGround = par1Packet10Flying.onGround;
            MPPlayer.addMovementStat(d11, d12, d13);
            double d18 = d12;
            d11 = d3 - MPPlayer.posX;
            d12 = d5 - MPPlayer.posY;

            if (d12 > -0.5D || d12 < 0.5D)
            {
                d12 = 0.0D;
            }

            d13 = d7 - MPPlayer.posZ;
            d17 = d11 * d11 + d12 * d12 + d13 * d13;
            boolean flag1 = false;

            if (d17 > 0.0625D && !MPPlayer.isPlayerSleeping() && !MPPlayer.theItemInWorldManager.isCreative())
            {
                flag1 = true;
                theLogger.warning((new StringBuilder()).append(MPPlayer.username).append(" moved wrongly!").toString());
            }

            MPPlayer.setPositionAndRotation(d3, d5, d7, f2, f3);
            boolean flag2 = worldserver.getCollidingBoundingBoxes(MPPlayer, MPPlayer.boundingBox.copy().contract(f4, f4, f4)).isEmpty();

            if (flag && (flag1 || !flag2) && !MPPlayer.isPlayerSleeping())
            {
                setPlayerLocation(posX, posY, posZ, f2, f3);
                return;
            }

            AxisAlignedBB axisalignedbb = MPPlayer.boundingBox.copy().expand(f4, f4, f4).addCoord(0.0D, -0.55000000000000004D, 0.0D);

            if (!theMinecraftServer.func_71231_X() && !MPPlayer.theItemInWorldManager.isCreative() && !worldserver.isAABBNonEmpty(axisalignedbb))
            {
                if (d18 >= -0.03125D)
                {
                    ticksForFloatKick++;

                    if (ticksForFloatKick > 80)
                    {
                        theLogger.warning((new StringBuilder()).append(MPPlayer.username).append(" was kicked for floating too long!").toString());
                        kickPlayerFromServer("Flying is not enabled on this server");
                        return;
                    }
                }
            }
            else
            {
                ticksForFloatKick = 0;
            }

            MPPlayer.onGround = par1Packet10Flying.onGround;
            theMinecraftServer.func_71203_ab().func_72358_d(MPPlayer);
            MPPlayer.updateFlyingState(MPPlayer.posY - d1, par1Packet10Flying.onGround);
        }
    }

    public void setPlayerLocation(double par1, double par3, double par5, float par7, float par8)
    {
        field_72587_r = false;
        posX = par1;
        posY = par3;
        posZ = par5;
        MPPlayer.setPositionAndRotation(par1, par3, par5, par7, par8);
        MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet13PlayerLookMove(par1, par3 + 1.6200000047683716D, par3, par5, par7, par8, false));
    }

    public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig)
    {
        WorldServer worldserver = theMinecraftServer.worldServerForDimension(MPPlayer.dimension);

        if (par1Packet14BlockDig.status == 4)
        {
            MPPlayer.dropOneItem();
            return;
        }

        if (par1Packet14BlockDig.status == 5)
        {
            MPPlayer.stopUsingItem();
            return;
        }

        boolean flag = worldserver.field_73060_c = worldserver.worldProvider.worldType != 0 || theMinecraftServer.func_71203_ab().func_72353_e(MPPlayer.username) || theMinecraftServer.isSinglePlayer();
        boolean flag1 = false;

        if (par1Packet14BlockDig.status == 0)
        {
            flag1 = true;
        }

        if (par1Packet14BlockDig.status == 2)
        {
            flag1 = true;
        }

        int i = par1Packet14BlockDig.xPosition;
        int j = par1Packet14BlockDig.yPosition;
        int k = par1Packet14BlockDig.zPosition;

        if (flag1)
        {
            double d = MPPlayer.posX - ((double)i + 0.5D);
            double d1 = (MPPlayer.posY - ((double)j + 0.5D)) + 1.5D;
            double d3 = MPPlayer.posZ - ((double)k + 0.5D);
            double d5 = d * d + d1 * d1 + d3 * d3;

            if (d5 > 36D)
            {
                return;
            }

            if (j >= theMinecraftServer.func_71207_Z())
            {
                return;
            }
        }

        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        int l = MathHelper.func_76130_a(i - chunkcoordinates.posX);
        int i1 = MathHelper.func_76130_a(k - chunkcoordinates.posZ);

        if (l > i1)
        {
            i1 = l;
        }

        if (par1Packet14BlockDig.status == 0)
        {
            if (i1 > 16 || flag)
            {
                MPPlayer.theItemInWorldManager.func_73074_a(i, j, k, par1Packet14BlockDig.face);
            }
            else
            {
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 2)
        {
            MPPlayer.theItemInWorldManager.func_73082_a(i, j, k);

            if (worldserver.getBlockId(i, j, k) != 0)
            {
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 1)
        {
            MPPlayer.theItemInWorldManager.func_73073_c(i, j, k);

            if (worldserver.getBlockId(i, j, k) != 0)
            {
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 3)
        {
            double d2 = MPPlayer.posX - ((double)i + 0.5D);
            double d4 = MPPlayer.posY - ((double)j + 0.5D);
            double d6 = MPPlayer.posZ - ((double)k + 0.5D);
            double d7 = d2 * d2 + d4 * d4 + d6 * d6;

            if (d7 < 256D)
            {
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));
            }
        }

        worldserver.field_73060_c = false;
    }

    public void handlePlace(Packet15Place par1Packet15Place)
    {
        WorldServer worldserver = theMinecraftServer.worldServerForDimension(MPPlayer.dimension);
        ItemStack itemstack = MPPlayer.inventory.getCurrentItem();
        boolean flag = false;
        int i = par1Packet15Place.getXPosition();
        int j = par1Packet15Place.getYPosition();
        int k = par1Packet15Place.getZPosition();
        int l = par1Packet15Place.getDirection();
        boolean flag1 = worldserver.field_73060_c = worldserver.worldProvider.worldType != 0 || theMinecraftServer.func_71203_ab().func_72353_e(MPPlayer.username) || theMinecraftServer.isSinglePlayer();

        if (par1Packet15Place.getDirection() == 255)
        {
            if (itemstack == null)
            {
                return;
            }

            MPPlayer.theItemInWorldManager.tryUseItem(MPPlayer, worldserver, itemstack);
        }
        else if (par1Packet15Place.getYPosition() < theMinecraftServer.func_71207_Z() - 1 || par1Packet15Place.getDirection() != 1 && par1Packet15Place.getYPosition() < theMinecraftServer.func_71207_Z())
        {
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
            int i1 = MathHelper.func_76130_a(i - chunkcoordinates.posX);
            int j1 = MathHelper.func_76130_a(k - chunkcoordinates.posZ);

            if (i1 > j1)
            {
                j1 = i1;
            }

            if (field_72587_r && MPPlayer.getDistanceSq((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D) < 64D && (j1 > 16 || flag1))
            {
                MPPlayer.theItemInWorldManager.tryPlaceItemStackIntoWorld(MPPlayer, worldserver, itemstack, i, j, k, l, par1Packet15Place.getXOffset(), par1Packet15Place.getYOffset(), par1Packet15Place.getZOffset());
            }

            flag = true;
        }
        else
        {
            MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet3Chat((new StringBuilder()).append("\2477Height limit for building is ").append(theMinecraftServer.func_71207_Z()).toString()));
            flag = true;
        }

        if (flag)
        {
            MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));

            if (l == 0)
            {
                j--;
            }

            if (l == 1)
            {
                j++;
            }

            if (l == 2)
            {
                k--;
            }

            if (l == 3)
            {
                k++;
            }

            if (l == 4)
            {
                i--;
            }

            if (l == 5)
            {
                i++;
            }

            MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet53BlockChange(i, j, k, worldserver));
        }

        itemstack = MPPlayer.inventory.getCurrentItem();

        if (itemstack != null && itemstack.stackSize == 0)
        {
            MPPlayer.inventory.mainInventory[MPPlayer.inventory.currentItem] = null;
            itemstack = null;
        }

        if (itemstack == null || itemstack.getMaxItemUseDuration() == 0)
        {
            MPPlayer.field_71137_h = true;
            MPPlayer.inventory.mainInventory[MPPlayer.inventory.currentItem] = ItemStack.copyItemStack(MPPlayer.inventory.mainInventory[MPPlayer.inventory.currentItem]);
            Slot slot = MPPlayer.craftingInventory.func_75147_a(MPPlayer.inventory, MPPlayer.inventory.currentItem);
            MPPlayer.craftingInventory.updateCraftingResults();
            MPPlayer.field_71137_h = false;

            if (!ItemStack.areItemStacksEqual(MPPlayer.inventory.getCurrentItem(), par1Packet15Place.getItemStack()))
            {
                sendPacketToPlayer(new Packet103SetSlot(MPPlayer.craftingInventory.windowId, slot.slotNumber, MPPlayer.inventory.getCurrentItem()));
            }
        }

        worldserver.field_73060_c = false;
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        theLogger.info((new StringBuilder()).append(MPPlayer.username).append(" lost connection: ").append(par1Str).toString());
        theMinecraftServer.func_71203_ab().sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(MPPlayer.username).append(" left the game.").toString()));
        theMinecraftServer.func_71203_ab().func_72367_e(MPPlayer);
        serverShuttingDown = true;

        if (theMinecraftServer.isSinglePlayer() && MPPlayer.username.equals(theMinecraftServer.func_71214_G()))
        {
            theLogger.info("Stopping singleplayer server as player logged out");
            theMinecraftServer.func_71263_m();
        }
    }

    public void registerPacket(Packet par1Packet)
    {
        theLogger.warning((new StringBuilder()).append(getClass()).append(" wasn't prepared to deal with a ").append(par1Packet.getClass()).toString());
        kickPlayerFromServer("Protocol error, unexpected packet");
    }

    /**
     * addToSendQueue. if it is a chat packet, check before sending it
     */
    public void sendPacketToPlayer(Packet par1Packet)
    {
        if (par1Packet instanceof Packet3Chat)
        {
            Packet3Chat packet3chat = (Packet3Chat)par1Packet;
            int i = MPPlayer.func_71126_v();

            if (i == 2)
            {
                return;
            }

            if (i == 1 && !packet3chat.func_73475_d())
            {
                return;
            }
        }

        theNetworkManager.addToSendQueue(par1Packet);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
    {
        if (par1Packet16BlockItemSwitch.id < 0 || par1Packet16BlockItemSwitch.id >= InventoryPlayer.func_70451_h())
        {
            theLogger.warning((new StringBuilder()).append(MPPlayer.username).append(" tried to set an invalid carried item").toString());
            return;
        }
        else
        {
            MPPlayer.inventory.currentItem = par1Packet16BlockItemSwitch.id;
            return;
        }
    }

    public void handleChat(Packet3Chat par1Packet3Chat)
    {
        if (MPPlayer.func_71126_v() == 2)
        {
            sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
            return;
        }

        String s = par1Packet3Chat.message;

        if (s.length() > 100)
        {
            kickPlayerFromServer("Chat message too long");
            return;
        }

        s = s.trim();

        for (int i = 0; i < s.length(); i++)
        {
            if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i)))
            {
                kickPlayerFromServer("Illegal characters in chat");
                return;
            }
        }

        if (s.startsWith("/"))
        {
            func_72566_d(s);
        }
        else
        {
            if (MPPlayer.func_71126_v() == 1)
            {
                sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
                return;
            }

            s = (new StringBuilder()).append("<").append(MPPlayer.username).append("> ").append(s).toString();
            theLogger.info(s);
            theMinecraftServer.func_71203_ab().sendPacketToAllPlayers(new Packet3Chat(s, false));
        }

        chatSpamThresholdCount += 20;

        if (chatSpamThresholdCount > 200 && !theMinecraftServer.func_71203_ab().func_72353_e(MPPlayer.username))
        {
            kickPlayerFromServer("disconnect.spam");
        }
    }

    private void func_72566_d(String par1Str)
    {
        if (theMinecraftServer.func_71203_ab().func_72353_e(MPPlayer.username) || "/seed".equals(par1Str))
        {
            theLogger.info((new StringBuilder()).append(MPPlayer.username).append(" issued server command: ").append(par1Str).toString());
            theMinecraftServer.func_71187_D().executeCommand(MPPlayer, par1Str);
        }
    }

    public void handleAnimation(Packet18Animation par1Packet18Animation)
    {
        if (par1Packet18Animation.animate == 1)
        {
            MPPlayer.swingItem();
        }
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction)
    {
        if (par1Packet19EntityAction.state == 1)
        {
            MPPlayer.setSneaking(true);
        }
        else if (par1Packet19EntityAction.state == 2)
        {
            MPPlayer.setSneaking(false);
        }
        else if (par1Packet19EntityAction.state == 4)
        {
            MPPlayer.setSprinting(true);
        }
        else if (par1Packet19EntityAction.state == 5)
        {
            MPPlayer.setSprinting(false);
        }
        else if (par1Packet19EntityAction.state == 3)
        {
            MPPlayer.wakeUpPlayer(false, true, true);
            field_72587_r = false;
        }
    }

    public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        theNetworkManager.networkShutdown("disconnect.quitting", new Object[0]);
    }

    public int func_72568_e()
    {
        return theNetworkManager.func_74426_e();
    }

    public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity)
    {
        WorldServer worldserver = theMinecraftServer.worldServerForDimension(MPPlayer.dimension);
        Entity entity = worldserver.func_73045_a(par1Packet7UseEntity.targetEntity);

        if (entity != null)
        {
            boolean flag = MPPlayer.canEntityBeSeen(entity);
            double d = 36D;

            if (!flag)
            {
                d = 9D;
            }

            if (MPPlayer.getDistanceSqToEntity(entity) < d)
            {
                if (par1Packet7UseEntity.isLeftClick == 0)
                {
                    MPPlayer.func_70998_m(entity);
                }
                else if (par1Packet7UseEntity.isLeftClick == 1)
                {
                    MPPlayer.attackTargetEntityWithCurrentItem(entity);
                }
            }
        }
    }

    public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.forceRespawn == 1)
        {
            if (MPPlayer.field_71136_j)
            {
                MPPlayer = theMinecraftServer.func_71203_ab().func_72368_a(MPPlayer, 0, true);
            }
            else if (MPPlayer.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
            {
                if (theMinecraftServer.isSinglePlayer() && MPPlayer.username.equals(theMinecraftServer.func_71214_G()))
                {
                    MPPlayer.serverForThisPlayer.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    theMinecraftServer.func_71272_O();
                }
                else
                {
                    BanEntry banentry = new BanEntry(MPPlayer.username);
                    banentry.setBanReason("Death in Hardcore");
                    theMinecraftServer.func_71203_ab().getBannedPlayers().put(banentry);
                    MPPlayer.serverForThisPlayer.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                }
            }
            else
            {
                if (MPPlayer.getHealth() > 0)
                {
                    return;
                }

                MPPlayer = theMinecraftServer.func_71203_ab().func_72368_a(MPPlayer, 0, false);
            }
        }
    }

    public boolean func_72469_b()
    {
        return true;
    }

    /**
     * respawns the player
     */
    public void handleRespawn(Packet9Respawn packet9respawn)
    {
    }

    public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
    {
        MPPlayer.func_71128_l();
    }

    public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick)
    {
        if (MPPlayer.craftingInventory.windowId == par1Packet102WindowClick.window_Id && MPPlayer.craftingInventory.func_75129_b(MPPlayer))
        {
            ItemStack itemstack = MPPlayer.craftingInventory.slotClick(par1Packet102WindowClick.inventorySlot, par1Packet102WindowClick.mouseClick, par1Packet102WindowClick.holdingShift, MPPlayer);

            if (ItemStack.areItemStacksEqual(par1Packet102WindowClick.itemStack, itemstack))
            {
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, true));
                MPPlayer.field_71137_h = true;
                MPPlayer.craftingInventory.updateCraftingResults();
                MPPlayer.func_71113_k();
                MPPlayer.field_71137_h = false;
            }
            else
            {
                field_72586_s.addKey(MPPlayer.craftingInventory.windowId, Short.valueOf(par1Packet102WindowClick.action));
                MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, false));
                MPPlayer.craftingInventory.func_75128_a(MPPlayer, false);
                ArrayList arraylist = new ArrayList();

                for (int i = 0; i < MPPlayer.craftingInventory.inventorySlots.size(); i++)
                {
                    arraylist.add(((Slot)MPPlayer.craftingInventory.inventorySlots.get(i)).getStack());
                }

                MPPlayer.func_71110_a(MPPlayer.craftingInventory, arraylist);
            }
        }
    }

    public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem)
    {
        if (MPPlayer.craftingInventory.windowId == par1Packet108EnchantItem.windowId && MPPlayer.craftingInventory.func_75129_b(MPPlayer))
        {
            MPPlayer.craftingInventory.enchantItem(MPPlayer, par1Packet108EnchantItem.enchantment);
            MPPlayer.craftingInventory.updateCraftingResults();
        }
    }

    /**
     * Handle a creative slot packet.
     */
    public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot)
    {
        if (MPPlayer.theItemInWorldManager.isCreative())
        {
            boolean flag = par1Packet107CreativeSetSlot.slot < 0;
            ItemStack itemstack = par1Packet107CreativeSetSlot.itemStack;
            boolean flag1 = par1Packet107CreativeSetSlot.slot >= 1 && par1Packet107CreativeSetSlot.slot < 36 + InventoryPlayer.func_70451_h();
            boolean flag2 = itemstack == null || itemstack.itemID < Item.itemsList.length && itemstack.itemID >= 0 && Item.itemsList[itemstack.itemID] != null;
            boolean flag3 = itemstack == null || itemstack.getItemDamage() >= 0 && itemstack.getItemDamage() >= 0 && itemstack.stackSize <= 64 && itemstack.stackSize > 0;

            if (flag1 && flag2 && flag3)
            {
                if (itemstack == null)
                {
                    MPPlayer.inventorySlots.putStackInSlot(par1Packet107CreativeSetSlot.slot, null);
                }
                else
                {
                    MPPlayer.inventorySlots.putStackInSlot(par1Packet107CreativeSetSlot.slot, itemstack);
                }

                MPPlayer.inventorySlots.func_75128_a(MPPlayer, true);
            }
            else if (flag && flag2 && flag3 && creativeItemCreationSpamThresholdTally < 200)
            {
                creativeItemCreationSpamThresholdTally += 20;
                EntityItem entityitem = MPPlayer.dropPlayerItem(itemstack);

                if (entityitem != null)
                {
                    entityitem.func_70288_d();
                }
            }
        }
    }

    public void handleTransaction(Packet106Transaction par1Packet106Transaction)
    {
        Short short1 = (Short)field_72586_s.lookup(MPPlayer.craftingInventory.windowId);

        if (short1 != null && par1Packet106Transaction.shortWindowId == short1.shortValue() && MPPlayer.craftingInventory.windowId == par1Packet106Transaction.windowId && !MPPlayer.craftingInventory.func_75129_b(MPPlayer))
        {
            MPPlayer.craftingInventory.func_75128_a(MPPlayer, true);
        }
    }

    /**
     * Updates Client side signs
     */
    public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
    {
        WorldServer worldserver = theMinecraftServer.worldServerForDimension(MPPlayer.dimension);

        if (worldserver.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition))
        {
            TileEntity tileentity = worldserver.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);

            if (tileentity instanceof TileEntitySign)
            {
                TileEntitySign tileentitysign = (TileEntitySign)tileentity;

                if (!tileentitysign.isEditable())
                {
                    theMinecraftServer.func_71236_h((new StringBuilder()).append("Player ").append(MPPlayer.username).append(" just tried to change non-editable sign").toString());
                    return;
                }
            }

            for (int i = 0; i < 4; i++)
            {
                boolean flag = true;

                if (par1Packet130UpdateSign.signLines[i].length() > 15)
                {
                    flag = false;
                }
                else
                {
                    for (int l = 0; l < par1Packet130UpdateSign.signLines[i].length(); l++)
                    {
                        if (ChatAllowedCharacters.allowedCharacters.indexOf(par1Packet130UpdateSign.signLines[i].charAt(l)) < 0)
                        {
                            flag = false;
                        }
                    }
                }

                if (!flag)
                {
                    par1Packet130UpdateSign.signLines[i] = "!?";
                }
            }

            if (tileentity instanceof TileEntitySign)
            {
                int j = par1Packet130UpdateSign.xPosition;
                int k = par1Packet130UpdateSign.yPosition;
                int i1 = par1Packet130UpdateSign.zPosition;
                TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;
                System.arraycopy(par1Packet130UpdateSign.signLines, 0, tileentitysign1.signText, 0, 4);
                tileentitysign1.onInventoryChanged();
                worldserver.markBlockNeedsUpdate(j, k, i1);
            }
        }
    }

    /**
     * Handle a keep alive packet.
     */
    public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
    {
        if (par1Packet0KeepAlive.randomId == field_72585_i)
        {
            int i = (int)(System.nanoTime() / 0xf4240L - field_72582_j);
            MPPlayer.field_71138_i = (MPPlayer.field_71138_i * 3 + i) / 4;
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return true;
    }

    /**
     * Handle a player abilities packet.
     */
    public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities)
    {
        MPPlayer.capabilities.isFlying = par1Packet202PlayerAbilities.func_73350_f() && MPPlayer.capabilities.allowFlying;
    }

    public void func_72461_a(Packet203AutoComplete par1Packet203AutoComplete)
    {
        StringBuilder stringbuilder = new StringBuilder();
        String s;

        for (Iterator iterator = theMinecraftServer.func_71248_a(MPPlayer, par1Packet203AutoComplete.func_73473_d()).iterator(); iterator.hasNext(); stringbuilder.append(s))
        {
            s = (String)iterator.next();

            if (stringbuilder.length() > 0)
            {
                stringbuilder.append("\0");
            }
        }

        MPPlayer.serverForThisPlayer.sendPacketToPlayer(new Packet203AutoComplete(stringbuilder.toString()));
    }

    public void handleClientInfo(Packet204ClientInfo par1Packet204ClientInfo)
    {
        MPPlayer.func_71125_a(par1Packet204ClientInfo);
    }

    public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload)
    {
        if ("MC|BEdit".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                ItemStack itemstack = Packet.readItemStack(datainputstream);

                if (!ItemWritableBook.func_77829_a(itemstack.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack2 = MPPlayer.inventory.getCurrentItem();

                if (itemstack != null && itemstack.itemID == Item.writableBook.shiftedIndex && itemstack.itemID == itemstack2.itemID)
                {
                    itemstack2.setTagCompound(itemstack.getTagCompound());
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else if ("MC|BSign".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream1 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                ItemStack itemstack1 = Packet.readItemStack(datainputstream1);

                if (!ItemEditableBook.func_77828_a(itemstack1.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack3 = MPPlayer.inventory.getCurrentItem();

                if (itemstack1 != null && itemstack1.itemID == Item.writtenBook.shiftedIndex && itemstack3.itemID == Item.writableBook.shiftedIndex)
                {
                    itemstack3.setTagCompound(itemstack1.getTagCompound());
                    itemstack3.itemID = Item.writtenBook.shiftedIndex;
                }
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }
        else if ("MC|TrSel".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                int i = datainputstream2.readInt();
                Container container = MPPlayer.craftingInventory;

                if (container instanceof ContainerMerchant)
                {
                    ((ContainerMerchant)container).func_75175_c(i);
                }
            }
            catch (Exception exception2)
            {
                exception2.printStackTrace();
            }
        }
    }
}
