package net.minecraft.src;

import java.util.*;

public class PlayerManager
{
    private final WorldServer theWorldServer;
    private final List field_72699_b = new ArrayList();
    private final LongHashMap field_72700_c = new LongHashMap();
    private final List field_72697_d = new ArrayList();
    private final int playerViewDistance;
    private final int field_72696_f[][] =
    {
        {
            1, 0
        }, {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }
    };

    public PlayerManager(WorldServer par1WorldServer, int par2)
    {
        if (par2 > 15)
        {
            throw new IllegalArgumentException("Too big view radius!");
        }

        if (par2 < 3)
        {
            throw new IllegalArgumentException("Too small view radius!");
        }
        else
        {
            playerViewDistance = par2;
            theWorldServer = par1WorldServer;
            return;
        }
    }

    public WorldServer func_72688_a()
    {
        return theWorldServer;
    }

    public void func_72693_b()
    {
        PlayerInstance playerinstance;

        for (Iterator iterator = field_72697_d.iterator(); iterator.hasNext(); playerinstance.func_73254_a())
        {
            playerinstance = (PlayerInstance)iterator.next();
        }

        field_72697_d.clear();

        if (field_72699_b.isEmpty())
        {
            WorldProvider worldprovider = theWorldServer.worldProvider;

            if (!worldprovider.canRespawnHere())
            {
                theWorldServer.field_73059_b.func_73240_a();
            }
        }
    }

    private PlayerInstance func_72690_a(int par1, int par2, boolean par3)
    {
        long l = (long)par1 + 0x7fffffffL | (long)par2 + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)field_72700_c.getValueByKey(l);

        if (playerinstance == null && par3)
        {
            playerinstance = new PlayerInstance(this, par1, par2);
            field_72700_c.add(l, playerinstance);
        }

        return playerinstance;
    }

    public void func_72687_a(int par1, int par2, int par3)
    {
        int i = par1 >> 4;
        int j = par3 >> 4;
        PlayerInstance playerinstance = func_72690_a(i, j, false);

        if (playerinstance != null)
        {
            playerinstance.func_73259_a(par1 & 0xf, par2, par3 & 0xf);
        }
    }

    public void func_72683_a(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        par1EntityPlayerMP.field_71131_d = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.field_71132_e = par1EntityPlayerMP.posZ;

        for (int k = i - playerViewDistance; k <= i + playerViewDistance; k++)
        {
            for (int l = j - playerViewDistance; l <= j + playerViewDistance; l++)
            {
                func_72690_a(k, l, true).func_73255_a(par1EntityPlayerMP);
            }
        }

        field_72699_b.add(par1EntityPlayerMP);
        func_72691_b(par1EntityPlayerMP);
    }

    public void func_72691_b(EntityPlayerMP par1EntityPlayerMP)
    {
        ArrayList arraylist = new ArrayList(par1EntityPlayerMP.chunksToLoad);
        int i = 0;
        int j = playerViewDistance;
        int k = (int)par1EntityPlayerMP.posX >> 4;
        int l = (int)par1EntityPlayerMP.posZ >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkcoordintpair = PlayerInstance.func_73253_a(func_72690_a(k, l, true));
        par1EntityPlayerMP.chunksToLoad.clear();

        if (arraylist.contains(chunkcoordintpair))
        {
            par1EntityPlayerMP.chunksToLoad.add(chunkcoordintpair);
        }

        for (int k1 = 1; k1 <= j * 2; k1++)
        {
            for (int i2 = 0; i2 < 2; i2++)
            {
                int ai[] = field_72696_f[i++ % 4];

                for (int j2 = 0; j2 < k1; j2++)
                {
                    i1 += ai[0];
                    j1 += ai[1];
                    ChunkCoordIntPair chunkcoordintpair1 = PlayerInstance.func_73253_a(func_72690_a(k + i1, l + j1, true));

                    if (arraylist.contains(chunkcoordintpair1))
                    {
                        par1EntityPlayerMP.chunksToLoad.add(chunkcoordintpair1);
                    }
                }
            }
        }

        i %= 4;

        for (int l1 = 0; l1 < j * 2; l1++)
        {
            i1 += field_72696_f[i][0];
            j1 += field_72696_f[i][1];
            ChunkCoordIntPair chunkcoordintpair2 = PlayerInstance.func_73253_a(func_72690_a(k + i1, l + j1, true));

            if (arraylist.contains(chunkcoordintpair2))
            {
                par1EntityPlayerMP.chunksToLoad.add(chunkcoordintpair2);
            }
        }
    }

    public void func_72695_c(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.field_71131_d >> 4;
        int j = (int)par1EntityPlayerMP.field_71132_e >> 4;

        for (int k = i - playerViewDistance; k <= i + playerViewDistance; k++)
        {
            for (int l = j - playerViewDistance; l <= j + playerViewDistance; l++)
            {
                PlayerInstance playerinstance = func_72690_a(k, l, false);

                if (playerinstance != null)
                {
                    playerinstance.func_73252_b(par1EntityPlayerMP);
                }
            }
        }

        field_72699_b.remove(par1EntityPlayerMP);
    }

    private boolean func_72684_a(int par1, int par2, int par3, int par4, int par5)
    {
        int i = par1 - par3;
        int j = par2 - par4;

        if (i < -par5 || i > par5)
        {
            return false;
        }

        return j >= -par5 && j <= par5;
    }

    public void func_72685_d(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        double d = par1EntityPlayerMP.field_71131_d - par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.field_71132_e - par1EntityPlayerMP.posZ;
        double d2 = d * d + d1 * d1;

        if (d2 < 64D)
        {
            return;
        }

        int k = (int)par1EntityPlayerMP.field_71131_d >> 4;
        int l = (int)par1EntityPlayerMP.field_71132_e >> 4;
        int i1 = playerViewDistance;
        int j1 = i - k;
        int k1 = j - l;

        if (j1 == 0 && k1 == 0)
        {
            return;
        }

        for (int l1 = i - i1; l1 <= i + i1; l1++)
        {
            for (int i2 = j - i1; i2 <= j + i1; i2++)
            {
                if (!func_72684_a(l1, i2, k, l, i1))
                {
                    func_72690_a(l1, i2, true).func_73255_a(par1EntityPlayerMP);
                }

                if (func_72684_a(l1 - j1, i2 - k1, i, j, i1))
                {
                    continue;
                }

                PlayerInstance playerinstance = func_72690_a(l1 - j1, i2 - k1, false);

                if (playerinstance != null)
                {
                    playerinstance.func_73252_b(par1EntityPlayerMP);
                }
            }
        }

        func_72691_b(par1EntityPlayerMP);
        par1EntityPlayerMP.field_71131_d = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.field_71132_e = par1EntityPlayerMP.posZ;
    }

    public boolean func_72694_a(EntityPlayerMP par1EntityPlayerMP, int par2, int par3)
    {
        PlayerInstance playerinstance = func_72690_a(par2, par3, false);
        return playerinstance != null ? PlayerInstance.func_73258_b(playerinstance).contains(par1EntityPlayerMP) : false;
    }

    public static int func_72686_a(int par0)
    {
        return par0 * 16 - 16;
    }

    static WorldServer func_72692_a(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.theWorldServer;
    }

    static LongHashMap func_72689_b(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_72700_c;
    }

    static List func_72682_c(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_72697_d;
    }
}
