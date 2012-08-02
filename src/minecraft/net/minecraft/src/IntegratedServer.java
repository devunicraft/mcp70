package net.minecraft.src;

import java.io.*;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class IntegratedServer extends MinecraftServer
{
    private final Minecraft field_71349_l;
    private final WorldSettings field_71350_m;
    private IntegratedServerListenThread field_71347_n;
    private boolean field_71348_o;
    private boolean field_71346_p;
    private ThreadLanServerPing field_71345_q;

    public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings)
    {
        super(new File(Minecraft.getMinecraftDir(), "saves"));
        field_71348_o = false;
        setUsername(par1Minecraft.session.username);
        setFolderName(par2Str);
        setWorldName(par3Str);
        setIsDemo(par1Minecraft.getIsDemo());
        func_71194_c(par4WorldSettings.func_77167_c());
        func_71191_d(256);
        func_71210_a(new IntegratedPlayerList(this));
        field_71349_l = par1Minecraft;
        field_71350_m = par4WorldSettings;

        try
        {
            field_71347_n = new IntegratedServerListenThread(this);
        }
        catch (IOException ioexception)
        {
            throw new Error();
        }
    }

    protected void func_71247_a(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        func_71237_c(par1Str);
        dimensionServerList = new WorldServer[3];
        field_71312_k = new long[dimensionServerList.length][100];
        ISaveHandler isavehandler = func_71254_M().getSaveLoader(par1Str, true);

        for (int i = 0; i < dimensionServerList.length; i++)
        {
            byte byte0 = 0;

            if (i == 1)
            {
                byte0 = -1;
            }

            if (i == 2)
            {
                byte0 = 1;
            }

            if (i == 0)
            {
                if (getIsDemo())
                {
                    dimensionServerList[i] = new DemoWorldServer(this, isavehandler, par2Str, byte0, field_71304_b);
                }
                else
                {
                    dimensionServerList[i] = new WorldServer(this, isavehandler, par2Str, byte0, field_71350_m, field_71304_b);
                }
            }
            else
            {
                dimensionServerList[i] = new WorldServerMulti(this, isavehandler, par2Str, byte0, field_71350_m, dimensionServerList[0], field_71304_b);
            }

            dimensionServerList[i].addWorldAccess(new WorldManager(this, dimensionServerList[i]));
            func_71203_ab().func_72364_a(dimensionServerList);
        }

        func_71226_c(func_71232_g());
        func_71222_d();
    }

    protected boolean func_71197_b() throws IOException
    {
        minecraftLogger.info("Starting integrated minecraft server version 1.3.1");
        func_71229_d(false);
        func_71251_e(true);
        func_71257_f(true);
        func_71188_g(true);
        func_71245_h(true);
        minecraftLogger.info("Generating keypair");
        func_71253_a(CryptManager.func_75891_b());
        func_71247_a(func_71270_I(), func_71221_J(), field_71350_m.getSeed(), field_71350_m.getTerrainType());
        func_71205_p((new StringBuilder()).append(func_71214_G()).append(" - ").append(dimensionServerList[0].getWorldInfo().getWorldName()).toString());
        return true;
    }

    public void func_71217_p()
    {
        boolean flag = field_71348_o;
        field_71348_o = field_71347_n.func_71752_f();

        if (!flag && field_71348_o)
        {
            minecraftLogger.info("Saving and pausing game...");
            func_71203_ab().func_72389_g();
            func_71267_a(false);
        }

        if (!field_71348_o)
        {
            super.func_71217_p();
        }
    }

    public boolean func_71225_e()
    {
        return false;
    }

    public EnumGameType func_71265_f()
    {
        return field_71350_m.func_77162_e();
    }

    public int func_71232_g()
    {
        return field_71349_l.gameSettings.difficulty;
    }

    public boolean func_71199_h()
    {
        return field_71350_m.getHardcoreEnabled();
    }

    protected File func_71238_n()
    {
        return field_71349_l.mcDataDir;
    }

    public boolean isDedicatedServer()
    {
        return false;
    }

    public IntegratedServerListenThread func_71343_a()
    {
        return field_71347_n;
    }

    protected void func_71228_a(CrashReport par1CrashReport)
    {
        field_71349_l.func_71404_a(par1CrashReport);
    }

    public CrashReport func_71230_b(CrashReport par1CrashReport)
    {
        par1CrashReport.func_71500_a("Type", new CallableType3(this));
        par1CrashReport.func_71500_a("Is Modded", new CallableIsModded(this));
        return super.func_71230_b(par1CrashReport);
    }

    public boolean func_70002_Q()
    {
        return Minecraft.getMinecraft().func_70002_Q();
    }

    public String func_71206_a(EnumGameType par1EnumGameType, boolean par2)
    {
        try
        {
            String s = field_71347_n.func_71755_c();
            System.out.println((new StringBuilder()).append("Started on ").append(s).toString());
            field_71346_p = true;
            field_71345_q = new ThreadLanServerPing(func_71273_Y(), s);
            field_71345_q.start();
            func_71203_ab().func_72357_a(par1EnumGameType);
            func_71203_ab().func_72387_b(par2);
            return s;
        }
        catch (IOException ioexception)
        {
            return null;
        }
    }

    public void func_71260_j()
    {
        super.func_71260_j();

        if (field_71345_q != null)
        {
            field_71345_q.interrupt();
            field_71345_q = null;
        }
    }

    public void func_71263_m()
    {
        super.func_71263_m();

        if (field_71345_q != null)
        {
            field_71345_q.interrupt();
            field_71345_q = null;
        }
    }

    public boolean func_71344_c()
    {
        return field_71346_p;
    }

    public void func_71235_a(EnumGameType par1EnumGameType)
    {
        func_71203_ab().func_72357_a(par1EnumGameType);
    }

    public NetworkListenThread func_71212_ac()
    {
        return func_71343_a();
    }
}
