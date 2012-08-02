package net.minecraft.src;

import java.util.*;

class PlayerInstance
{
    private final List field_73263_b = new ArrayList();
    private final ChunkCoordIntPair field_73264_c;
    private short field_73261_d[];
    private int field_73262_e;
    private int field_73260_f;
    final PlayerManager field_73265_a;

    public PlayerInstance(PlayerManager par1PlayerManager, int par2, int par3)
    {
        field_73265_a = par1PlayerManager;
        field_73261_d = new short[64];
        field_73262_e = 0;
        field_73264_c = new ChunkCoordIntPair(par2, par3);
        par1PlayerManager.func_72688_a().field_73059_b.loadChunk(par2, par3);
    }

    public void func_73255_a(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_73263_b.contains(par1EntityPlayerMP))
        {
            throw new IllegalStateException((new StringBuilder()).append("Failed to add player. ").append(par1EntityPlayerMP).append(" already is in chunk ").append(field_73264_c.chunkXPos).append(", ").append(field_73264_c.chunkZPos).toString());
        }
        else
        {
            field_73263_b.add(par1EntityPlayerMP);
            par1EntityPlayerMP.chunksToLoad.add(field_73264_c);
            return;
        }
    }

    public void func_73252_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (!field_73263_b.contains(par1EntityPlayerMP))
        {
            return;
        }

        par1EntityPlayerMP.serverForThisPlayer.sendPacketToPlayer(new Packet51MapChunk(PlayerManager.func_72692_a(field_73265_a).getChunkFromChunkCoords(field_73264_c.chunkXPos, field_73264_c.chunkZPos), true, 0));
        field_73263_b.remove(par1EntityPlayerMP);
        par1EntityPlayerMP.chunksToLoad.remove(field_73264_c);

        if (field_73263_b.isEmpty())
        {
            long l = (long)field_73264_c.chunkXPos + 0x7fffffffL | (long)field_73264_c.chunkZPos + 0x7fffffffL << 32;
            PlayerManager.func_72689_b(field_73265_a).remove(l);

            if (field_73262_e > 0)
            {
                PlayerManager.func_72682_c(field_73265_a).remove(this);
            }

            field_73265_a.func_72688_a().field_73059_b.func_73241_b(field_73264_c.chunkXPos, field_73264_c.chunkZPos);
        }
    }

    public void func_73259_a(int par1, int par2, int par3)
    {
        if (field_73262_e == 0)
        {
            PlayerManager.func_72682_c(field_73265_a).add(this);
        }

        field_73260_f |= 1 << (par2 >> 4);

        if (field_73262_e < 64)
        {
            short word0 = (short)(par1 << 12 | par3 << 8 | par2);

            for (int i = 0; i < field_73262_e; i++)
            {
                if (field_73261_d[i] == word0)
                {
                    return;
                }
            }

            field_73261_d[field_73262_e++] = word0;
        }
    }

    public void func_73256_a(Packet par1Packet)
    {
        Iterator iterator = field_73263_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (!entityplayermp.chunksToLoad.contains(field_73264_c))
            {
                entityplayermp.serverForThisPlayer.sendPacketToPlayer(par1Packet);
            }
        }
        while (true);
    }

    public void func_73254_a()
    {
        if (field_73262_e == 0)
        {
            return;
        }

        if (field_73262_e == 1)
        {
            int i = field_73264_c.chunkXPos * 16 + (field_73261_d[0] >> 12 & 0xf);
            int l = field_73261_d[0] & 0xff;
            int k1 = field_73264_c.chunkZPos * 16 + (field_73261_d[0] >> 8 & 0xf);
            func_73256_a(new Packet53BlockChange(i, l, k1, PlayerManager.func_72692_a(field_73265_a)));

            if (PlayerManager.func_72692_a(field_73265_a).blockHasTileEntity(i, l, k1))
            {
                func_73257_a(PlayerManager.func_72692_a(field_73265_a).getBlockTileEntity(i, l, k1));
            }
        }
        else if (field_73262_e == 64)
        {
            int j = field_73264_c.chunkXPos * 16;
            int i1 = field_73264_c.chunkZPos * 16;
            func_73256_a(new Packet51MapChunk(PlayerManager.func_72692_a(field_73265_a).getChunkFromChunkCoords(field_73264_c.chunkXPos, field_73264_c.chunkZPos), false, field_73260_f));

            for (int l1 = 0; l1 < 16; l1++)
            {
                if ((field_73260_f & 1 << l1) != 0)
                {
                    int j2 = l1 << 4;
                    List list = PlayerManager.func_72692_a(field_73265_a).getAllTileEntityInBox(j, j2, i1, j + 16, j2 + 16, i1 + 16);
                    TileEntity tileentity;

                    for (Iterator iterator = list.iterator(); iterator.hasNext(); func_73257_a(tileentity))
                    {
                        tileentity = (TileEntity)iterator.next();
                    }
                }
            }
        }
        else
        {
            func_73256_a(new Packet52MultiBlockChange(field_73264_c.chunkXPos, field_73264_c.chunkZPos, field_73261_d, field_73262_e, PlayerManager.func_72692_a(field_73265_a)));

            for (int k = 0; k < field_73262_e; k++)
            {
                int j1 = field_73264_c.chunkXPos * 16 + (field_73261_d[k] >> 12 & 0xf);
                int i2 = field_73261_d[k] & 0xff;
                int k2 = field_73264_c.chunkZPos * 16 + (field_73261_d[k] >> 8 & 0xf);

                if (PlayerManager.func_72692_a(field_73265_a).blockHasTileEntity(j1, i2, k2))
                {
                    func_73257_a(PlayerManager.func_72692_a(field_73265_a).getBlockTileEntity(j1, i2, k2));
                }
            }
        }

        field_73262_e = 0;
        field_73260_f = 0;
    }

    private void func_73257_a(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.func_70319_e();

            if (packet != null)
            {
                func_73256_a(packet);
            }
        }
    }

    static ChunkCoordIntPair func_73253_a(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.field_73264_c;
    }

    static List func_73258_b(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.field_73263_b;
    }
}
