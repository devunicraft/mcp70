package net.minecraft.src;

import java.io.*;
import java.util.*;

public abstract class Packet
{
    /** Maps packet id to packet class */
    public static IntHashMap packetIdToClassMap = new IntHashMap();

    /** Maps packet class to packet id */
    private static Map packetClassToIdMap = new HashMap();

    /** List of the client's packet IDs. */
    private static Set clientPacketIdList = new HashSet();

    /** List of the server's packet IDs. */
    private static Set serverPacketIdList = new HashSet();

    /** the system time in milliseconds when this packet was created. */
    public final long creationTimeMillis = System.currentTimeMillis();
    public static long field_73292_n;
    public static long field_73293_o;
    public static long field_73290_p;
    public static long field_73289_q;

    /**
     * Only true for Packet51MapChunk, Packet52MultiBlockChange, Packet53BlockChange and Packet59ComplexEntity. Used to
     * separate them into a different send queue.
     */
    public boolean isChunkDataPacket;

    public Packet()
    {
        isChunkDataPacket = false;
    }

    /**
     * Adds a two way mapping between the packet ID and packet class.
     */
    static void addIdClassMapping(int par0, boolean par1, boolean par2, Class par3Class)
    {
        if (packetIdToClassMap.containsItem(par0))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet id:").append(par0).toString());
        }

        if (packetClassToIdMap.containsKey(par3Class))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet class:").append(par3Class).toString());
        }

        packetIdToClassMap.addKey(par0, par3Class);
        packetClassToIdMap.put(par3Class, Integer.valueOf(par0));

        if (par1)
        {
            clientPacketIdList.add(Integer.valueOf(par0));
        }

        if (par2)
        {
            serverPacketIdList.add(Integer.valueOf(par0));
        }
    }

    /**
     * Returns a new instance of the specified Packet class.
     */
    public static Packet getNewPacket(int par0)
    {
        try
        {
            Class class1 = (Class)packetIdToClassMap.lookup(par0);

            if (class1 == null)
            {
                return null;
            }
            else
            {
                return (Packet)class1.newInstance();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println((new StringBuilder()).append("Skipping packet with id ").append(par0).toString());
        return null;
    }

    public static void func_73274_a(DataOutputStream par0DataOutputStream, byte par1ArrayOfByte[]) throws IOException
    {
        par0DataOutputStream.writeShort(par1ArrayOfByte.length);
        par0DataOutputStream.write(par1ArrayOfByte);
    }

    public static byte[] func_73280_b(DataInputStream par0DataInputStream) throws IOException
    {
        short word0 = par0DataInputStream.readShort();

        if (word0 < 0)
        {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        else
        {
            byte abyte0[] = new byte[word0];
            par0DataInputStream.read(abyte0);
            return abyte0;
        }
    }

    /**
     * Returns the ID of this packet.
     */
    public final int getPacketId()
    {
        return ((Integer)packetClassToIdMap.get(getClass())).intValue();
    }

    /**
     * Read a packet, prefixed by its ID, from the data stream.
     */
    public static Packet readPacket(DataInputStream par0DataInputStream, boolean par1) throws IOException
    {
        int i = 0;
        Packet packet = null;

        try
        {
            i = par0DataInputStream.read();

            if (i == -1)
            {
                return null;
            }

            if (par1 && !serverPacketIdList.contains(Integer.valueOf(i)) || !par1 && !clientPacketIdList.contains(Integer.valueOf(i)))
            {
                throw new IOException((new StringBuilder()).append("Bad packet id ").append(i).toString());
            }

            packet = getNewPacket(i);

            if (packet == null)
            {
                throw new IOException((new StringBuilder()).append("Bad packet id ").append(i).toString());
            }

            packet.readPacketData(par0DataInputStream);
            field_73292_n++;
            field_73293_o += packet.getPacketSize();
        }
        catch (EOFException eofexception)
        {
            System.out.println("Reached end of stream");
            return null;
        }

        PacketCount.countPacket(i, packet.getPacketSize());
        field_73292_n++;
        field_73293_o += packet.getPacketSize();
        return packet;
    }

    /**
     * Writes a packet, prefixed by its ID, to the data stream.
     */
    public static void writePacket(Packet par0Packet, DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.write(par0Packet.getPacketId());
        par0Packet.writePacketData(par1DataOutputStream);
        field_73290_p++;
        field_73289_q += par0Packet.getPacketSize();
    }

    /**
     * Writes a String to the DataOutputStream
     */
    public static void writeString(String par0Str, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0Str.length() > 32767)
        {
            throw new IOException("String too big");
        }
        else
        {
            par1DataOutputStream.writeShort(par0Str.length());
            par1DataOutputStream.writeChars(par0Str);
            return;
        }
    }

    /**
     * Reads a string from a packet
     */
    public static String readString(DataInputStream par0DataInputStream, int par1) throws IOException
    {
        short word0 = par0DataInputStream.readShort();

        if (word0 > par1)
        {
            throw new IOException((new StringBuilder()).append("Received string length longer than maximum allowed (").append(word0).append(" > ").append(par1).append(")").toString());
        }

        if (word0 < 0)
        {
            throw new IOException("Received string length is less than zero! Weird string!");
        }

        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < word0; i++)
        {
            stringbuilder.append(par0DataInputStream.readChar());
        }

        return stringbuilder.toString();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public abstract void readPacketData(DataInputStream datainputstream) throws IOException;

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public abstract void writePacketData(DataOutputStream dataoutputstream) throws IOException;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public abstract void processPacket(NetHandler nethandler);

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public abstract int getPacketSize();

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean isRealPacket()
    {
        return false;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean containsSameEntityIDAs(Packet par1Packet)
    {
        return false;
    }

    public boolean func_73277_a_()
    {
        return false;
    }

    public String toString()
    {
        String s = getClass().getSimpleName();
        return s;
    }

    /**
     * Reads a ItemStack from the InputStream
     */
    public static ItemStack readItemStack(DataInputStream par0DataInputStream) throws IOException
    {
        ItemStack itemstack = null;
        short word0 = par0DataInputStream.readShort();

        if (word0 >= 0)
        {
            byte byte0 = par0DataInputStream.readByte();
            short word1 = par0DataInputStream.readShort();
            itemstack = new ItemStack(word0, byte0, word1);
            itemstack.stackTagCompound = readNBTTagCompound(par0DataInputStream);
        }

        return itemstack;
    }

    /**
     * Writes the ItemStack's ID (short), then size (byte), then damage. (short)
     */
    public static void writeItemStack(ItemStack par0ItemStack, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0ItemStack == null)
        {
            par1DataOutputStream.writeShort(-1);
        }
        else
        {
            par1DataOutputStream.writeShort(par0ItemStack.itemID);
            par1DataOutputStream.writeByte(par0ItemStack.stackSize);
            par1DataOutputStream.writeShort(par0ItemStack.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (par0ItemStack.getItem().isDamageable() || par0ItemStack.getItem().func_77651_p())
            {
                nbttagcompound = par0ItemStack.stackTagCompound;
            }

            writeNBTTagCompound(nbttagcompound, par1DataOutputStream);
        }
    }

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    public static NBTTagCompound readNBTTagCompound(DataInputStream par0DataInputStream) throws IOException
    {
        short word0 = par0DataInputStream.readShort();

        if (word0 < 0)
        {
            return null;
        }
        else
        {
            byte abyte0[] = new byte[word0];
            par0DataInputStream.readFully(abyte0);
            return CompressedStreamTools.decompress(abyte0);
        }
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0NBTTagCompound == null)
        {
            par1DataOutputStream.writeShort(-1);
        }
        else
        {
            byte abyte0[] = CompressedStreamTools.compress(par0NBTTagCompound);
            par1DataOutputStream.writeShort((short)abyte0.length);
            par1DataOutputStream.write(abyte0);
        }
    }

    static
    {
        addIdClassMapping(0, true, true, net.minecraft.src.Packet0KeepAlive.class);
        addIdClassMapping(1, true, true, net.minecraft.src.Packet1Login.class);
        addIdClassMapping(2, false, true, net.minecraft.src.Packet2ClientProtocol.class);
        addIdClassMapping(3, true, true, net.minecraft.src.Packet3Chat.class);
        addIdClassMapping(4, true, false, net.minecraft.src.Packet4UpdateTime.class);
        addIdClassMapping(5, true, false, net.minecraft.src.Packet5PlayerInventory.class);
        addIdClassMapping(6, true, false, net.minecraft.src.Packet6SpawnPosition.class);
        addIdClassMapping(7, false, true, net.minecraft.src.Packet7UseEntity.class);
        addIdClassMapping(8, true, false, net.minecraft.src.Packet8UpdateHealth.class);
        addIdClassMapping(9, true, true, net.minecraft.src.Packet9Respawn.class);
        addIdClassMapping(10, true, true, net.minecraft.src.Packet10Flying.class);
        addIdClassMapping(11, true, true, net.minecraft.src.Packet11PlayerPosition.class);
        addIdClassMapping(12, true, true, net.minecraft.src.Packet12PlayerLook.class);
        addIdClassMapping(13, true, true, net.minecraft.src.Packet13PlayerLookMove.class);
        addIdClassMapping(14, false, true, net.minecraft.src.Packet14BlockDig.class);
        addIdClassMapping(15, false, true, net.minecraft.src.Packet15Place.class);
        addIdClassMapping(16, false, true, net.minecraft.src.Packet16BlockItemSwitch.class);
        addIdClassMapping(17, true, false, net.minecraft.src.Packet17Sleep.class);
        addIdClassMapping(18, true, true, net.minecraft.src.Packet18Animation.class);
        addIdClassMapping(19, false, true, net.minecraft.src.Packet19EntityAction.class);
        addIdClassMapping(20, true, false, net.minecraft.src.Packet20NamedEntitySpawn.class);
        addIdClassMapping(21, true, false, net.minecraft.src.Packet21PickupSpawn.class);
        addIdClassMapping(22, true, false, net.minecraft.src.Packet22Collect.class);
        addIdClassMapping(23, true, false, net.minecraft.src.Packet23VehicleSpawn.class);
        addIdClassMapping(24, true, false, net.minecraft.src.Packet24MobSpawn.class);
        addIdClassMapping(25, true, false, net.minecraft.src.Packet25EntityPainting.class);
        addIdClassMapping(26, true, false, net.minecraft.src.Packet26EntityExpOrb.class);
        addIdClassMapping(28, true, false, net.minecraft.src.Packet28EntityVelocity.class);
        addIdClassMapping(29, true, false, net.minecraft.src.Packet29DestroyEntity.class);
        addIdClassMapping(30, true, false, net.minecraft.src.Packet30Entity.class);
        addIdClassMapping(31, true, false, net.minecraft.src.Packet31RelEntityMove.class);
        addIdClassMapping(32, true, false, net.minecraft.src.Packet32EntityLook.class);
        addIdClassMapping(33, true, false, net.minecraft.src.Packet33RelEntityMoveLook.class);
        addIdClassMapping(34, true, false, net.minecraft.src.Packet34EntityTeleport.class);
        addIdClassMapping(35, true, false, net.minecraft.src.Packet35EntityHeadRotation.class);
        addIdClassMapping(38, true, false, net.minecraft.src.Packet38EntityStatus.class);
        addIdClassMapping(39, true, false, net.minecraft.src.Packet39AttachEntity.class);
        addIdClassMapping(40, true, false, net.minecraft.src.Packet40EntityMetadata.class);
        addIdClassMapping(41, true, false, net.minecraft.src.Packet41EntityEffect.class);
        addIdClassMapping(42, true, false, net.minecraft.src.Packet42RemoveEntityEffect.class);
        addIdClassMapping(43, true, false, net.minecraft.src.Packet43Experience.class);
        addIdClassMapping(51, true, false, net.minecraft.src.Packet51MapChunk.class);
        addIdClassMapping(52, true, false, net.minecraft.src.Packet52MultiBlockChange.class);
        addIdClassMapping(53, true, false, net.minecraft.src.Packet53BlockChange.class);
        addIdClassMapping(54, true, false, net.minecraft.src.Packet54PlayNoteBlock.class);
        addIdClassMapping(55, true, false, net.minecraft.src.Packet55BlockDestroy.class);
        addIdClassMapping(56, true, false, net.minecraft.src.Packet56MapChunks.class);
        addIdClassMapping(60, true, false, net.minecraft.src.Packet60Explosion.class);
        addIdClassMapping(61, true, false, net.minecraft.src.Packet61DoorChange.class);
        addIdClassMapping(62, true, false, net.minecraft.src.Packet62LevelSound.class);
        addIdClassMapping(70, true, false, net.minecraft.src.Packet70GameEvent.class);
        addIdClassMapping(71, true, false, net.minecraft.src.Packet71Weather.class);
        addIdClassMapping(100, true, false, net.minecraft.src.Packet100OpenWindow.class);
        addIdClassMapping(101, true, true, net.minecraft.src.Packet101CloseWindow.class);
        addIdClassMapping(102, false, true, net.minecraft.src.Packet102WindowClick.class);
        addIdClassMapping(103, true, false, net.minecraft.src.Packet103SetSlot.class);
        addIdClassMapping(104, true, false, net.minecraft.src.Packet104WindowItems.class);
        addIdClassMapping(105, true, false, net.minecraft.src.Packet105UpdateProgressbar.class);
        addIdClassMapping(106, true, true, net.minecraft.src.Packet106Transaction.class);
        addIdClassMapping(107, true, true, net.minecraft.src.Packet107CreativeSetSlot.class);
        addIdClassMapping(108, false, true, net.minecraft.src.Packet108EnchantItem.class);
        addIdClassMapping(130, true, true, net.minecraft.src.Packet130UpdateSign.class);
        addIdClassMapping(131, true, false, net.minecraft.src.Packet131MapData.class);
        addIdClassMapping(132, true, false, net.minecraft.src.Packet132TileEntityData.class);
        addIdClassMapping(200, true, false, net.minecraft.src.Packet200Statistic.class);
        addIdClassMapping(201, true, false, net.minecraft.src.Packet201PlayerInfo.class);
        addIdClassMapping(202, true, true, net.minecraft.src.Packet202PlayerAbilities.class);
        addIdClassMapping(203, true, true, net.minecraft.src.Packet203AutoComplete.class);
        addIdClassMapping(204, false, true, net.minecraft.src.Packet204ClientInfo.class);
        addIdClassMapping(205, false, true, net.minecraft.src.Packet205ClientCommand.class);
        addIdClassMapping(250, true, true, net.minecraft.src.Packet250CustomPayload.class);
        addIdClassMapping(252, true, true, net.minecraft.src.Packet252SharedKey.class);
        addIdClassMapping(253, true, false, net.minecraft.src.Packet253ServerAuthData.class);
        addIdClassMapping(254, false, true, net.minecraft.src.Packet254ServerPing.class);
        addIdClassMapping(255, true, true, net.minecraft.src.Packet255KickDisconnect.class);
    }
}
