package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.AABBPool;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.CallableIsServerModded;
import net.minecraft.src.CallablePlayers;
import net.minecraft.src.CallableServerProfiler;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ConvertProgressUpdater;
import net.minecraft.src.CrashReport;
import net.minecraft.src.DemoWorldServer;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.RConConsoleSource;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ServerCommandManager;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.StringUtils;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.Vec3;
import net.minecraft.src.Vec3Pool;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldServerMulti;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

public abstract class MinecraftServer implements Runnable, IPlayerUsage, ICommandSender
{
    public static Logger minecraftLogger = Logger.getLogger("Minecraft");
    private static MinecraftServer theServer = null;
    private final ISaveFormat field_71310_m;
    private final PlayerUsageSnooper field_71307_n = new PlayerUsageSnooper("server", this);
    private final File field_71308_o;
    private final List field_71322_p = new ArrayList();
    private final ICommandManager commandManager;
    public final Profiler field_71304_b = new Profiler();
    private String field_71320_r;
    private int field_71319_s;
    public WorldServer dimensionServerList[];
    private ServerConfigurationManager field_71318_t;
    private boolean field_71317_u;
    private boolean field_71316_v;
    private int field_71315_w;
    public String field_71302_d;
    public int field_71303_e;
    private boolean field_71325_x;
    private boolean field_71324_y;
    private boolean field_71323_z;
    private boolean field_71284_A;
    private boolean field_71285_B;
    private String field_71286_C;
    private int field_71280_D;
    private long field_71281_E;
    private long field_71282_F;
    private long field_71283_G;
    private long field_71291_H;
    public final long field_71300_f[] = new long[100];
    public final long field_71301_g[] = new long[100];
    public final long field_71313_h[] = new long[100];
    public final long field_71314_i[] = new long[100];
    public final long field_71311_j[] = new long[100];
    public long field_71312_k[][];
    private KeyPair field_71292_I;
    private String field_71293_J;
    private String field_71294_K;
    private String field_71287_L;
    private boolean isDemo;
    private boolean field_71289_N;
    private boolean field_71290_O;
    private String field_71297_P;
    private boolean field_71296_Q;
    private long field_71299_R;
    private String field_71298_S;
    private boolean field_71295_T;

    public MinecraftServer(File par1File)
    {
        field_71319_s = -1;
        field_71317_u = true;
        field_71316_v = false;
        field_71315_w = 0;
        field_71297_P = "";
        field_71296_Q = false;
        theServer = this;
        field_71308_o = par1File;
        commandManager = new ServerCommandManager();
        field_71310_m = new AnvilSaveConverter(par1File);
    }

    protected abstract boolean func_71197_b() throws IOException;

    protected void func_71237_c(String par1Str)
    {
        if (func_71254_M().isOldMapFormat(par1Str))
        {
            minecraftLogger.info("Converting map!");
            func_71192_d("menu.convertingLevel");
            func_71254_M().convertMapFormat(par1Str, new ConvertProgressUpdater(this));
        }
    }

    protected synchronized void func_71192_d(String par1Str)
    {
        field_71298_S = par1Str;
    }

    public synchronized String func_71195_b_()
    {
        return field_71298_S;
    }

    protected void func_71247_a(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        func_71237_c(par1Str);
        func_71192_d("menu.loadingLevel");
        dimensionServerList = new WorldServer[3];
        field_71312_k = new long[dimensionServerList.length][100];
        ISaveHandler isavehandler = field_71310_m.getSaveLoader(par1Str, true);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        WorldSettings worldsettings;

        if (worldinfo == null)
        {
            worldsettings = new WorldSettings(par3, func_71265_f(), func_71225_e(), func_71199_h(), par5WorldType);
        }
        else
        {
            worldsettings = new WorldSettings(worldinfo);
        }

        if (field_71289_N)
        {
            worldsettings.func_77159_a();
        }

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
                    dimensionServerList[i] = new WorldServer(this, isavehandler, par2Str, byte0, worldsettings, field_71304_b);
                }
            }
            else
            {
                dimensionServerList[i] = new WorldServerMulti(this, isavehandler, par2Str, byte0, worldsettings, dimensionServerList[0], field_71304_b);
            }

            dimensionServerList[i].addWorldAccess(new WorldManager(this, dimensionServerList[i]));

            if (!isSinglePlayer())
            {
                dimensionServerList[i].getWorldInfo().func_76060_a(func_71265_f());
            }

            field_71318_t.func_72364_a(dimensionServerList);
        }

        func_71226_c(func_71232_g());
        func_71222_d();
    }

    protected void func_71222_d()
    {
        char c = '\304';
        long l = System.currentTimeMillis();
        func_71192_d("menu.generatingTerrain");

        for (int i = 0; i < 1; i++)
        {
            minecraftLogger.info((new StringBuilder()).append("Preparing start region for level ").append(i).toString());
            WorldServer worldserver = dimensionServerList[i];
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();

            for (int j = -c; j <= c && func_71278_l(); j += 16)
            {
                for (int k = -c; k <= c && func_71278_l(); k += 16)
                {
                    long l1 = System.currentTimeMillis();

                    if (l1 < l)
                    {
                        l = l1;
                    }

                    if (l1 > l + 1000L)
                    {
                        int i1 = (c * 2 + 1) * (c * 2 + 1);
                        int j1 = (j + c) * (c * 2 + 1) + (k + 1);
                        func_71216_a_("Preparing spawn area", (j1 * 100) / i1);
                        l = l1;
                    }

                    worldserver.field_73059_b.loadChunk(chunkcoordinates.posX + j >> 4, chunkcoordinates.posZ + k >> 4);

                    while (worldserver.updatingLighting() && func_71278_l()) ;
                }
            }
        }

        func_71243_i();
    }

    public abstract boolean func_71225_e();

    public abstract EnumGameType func_71265_f();

    public abstract int func_71232_g();

    public abstract boolean func_71199_h();

    protected void func_71216_a_(String par1Str, int par2)
    {
        field_71302_d = par1Str;
        field_71303_e = par2;
        minecraftLogger.info((new StringBuilder()).append(par1Str).append(": ").append(par2).append("%").toString());
    }

    protected void func_71243_i()
    {
        field_71302_d = null;
        field_71303_e = 0;
    }

    protected void func_71267_a(boolean par1)
    {
        if (field_71290_O)
        {
            return;
        }

        WorldServer aworldserver[] = dimensionServerList;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];

            if (worldserver == null)
            {
                continue;
            }

            if (!par1)
            {
                minecraftLogger.info((new StringBuilder()).append("Saving chunks for level '").append(worldserver.getWorldInfo().getWorldName()).append("'/").append(worldserver.worldProvider).toString());
            }

            try
            {
                worldserver.func_73044_a(true, null);
            }
            catch (MinecraftException minecraftexception)
            {
                minecraftLogger.warning(minecraftexception.getMessage());
            }
        }
    }

    public void func_71260_j()
    {
        if (field_71290_O)
        {
            return;
        }

        minecraftLogger.info("Stopping server");

        if (func_71212_ac() != null)
        {
            func_71212_ac().func_71744_a();
        }

        if (field_71318_t != null)
        {
            minecraftLogger.info("Saving players");
            field_71318_t.func_72389_g();
            field_71318_t.func_72392_r();
        }

        minecraftLogger.info("Saving worlds");
        func_71267_a(false);
        WorldServer aworldserver[] = dimensionServerList;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];
            worldserver.func_73041_k();
        }

        if (field_71307_n != null && field_71307_n.func_76468_d())
        {
            field_71307_n.func_76470_e();
        }
    }

    public String func_71211_k()
    {
        return field_71320_r;
    }

    public void func_71189_e(String par1Str)
    {
        field_71320_r = par1Str;
    }

    public boolean func_71278_l()
    {
        return field_71317_u;
    }

    public void func_71263_m()
    {
        field_71317_u = false;
    }

    public void run()
    {
        try
        {
            if (func_71197_b())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;

                while (field_71317_u)
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;

                    if (l3 > 2000L && l - field_71299_R >= 15000L)
                    {
                        minecraftLogger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                        field_71299_R = l;
                    }

                    if (l3 < 0L)
                    {
                        minecraftLogger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }

                    l1 += l3;
                    l = l2;

                    if (dimensionServerList[0].func_73056_e())
                    {
                        func_71217_p();
                        l1 = 0L;
                    }
                    else
                    {
                        while (l1 > 50L)
                        {
                            l1 -= 50L;
                            func_71217_p();
                        }
                    }

                    Thread.sleep(1L);
                    field_71296_Q = true;
                }
            }
            else
            {
                func_71228_a(null);
            }
        }
        catch (Throwable throwable1)
        {
            throwable1.printStackTrace();
            minecraftLogger.log(Level.SEVERE, (new StringBuilder()).append("Encountered an unexpected exception ").append(throwable1.getClass().getSimpleName()).toString(), throwable1);
            CrashReport crashreport = null;

            if (throwable1 instanceof ReportedException)
            {
                crashreport = func_71230_b(((ReportedException)throwable1).func_71575_a());
            }
            else
            {
                crashreport = func_71230_b(new CrashReport("Exception in server tick loop", throwable1));
            }

            File file = new File(new File(func_71238_n(), "crash-reports"), (new StringBuilder()).append("crash-").append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-server.txt").toString());

            if (crashreport.func_71508_a(file))
            {
                minecraftLogger.severe((new StringBuilder()).append("This crash report has been saved to: ").append(file.getAbsolutePath()).toString());
            }
            else
            {
                minecraftLogger.severe("We were unable to save this crash report to disk.");
            }

            func_71228_a(crashreport);
        }
        finally
        {
            try
            {
                func_71260_j();
                field_71316_v = true;
            }
            catch (Throwable throwable2)
            {
                throwable2.printStackTrace();
            }
            finally
            {
                func_71240_o();
            }
        }
    }

    protected File func_71238_n()
    {
        return new File(".");
    }

    protected void func_71228_a(CrashReport crashreport)
    {
    }

    protected void func_71240_o()
    {
    }

    public void func_71217_p()
    {
        long l = System.nanoTime();
        AxisAlignedBB.getAABBPool().cleanPool();
        Vec3.func_72437_a().func_72343_a();
        field_71315_w++;

        if (field_71295_T)
        {
            field_71295_T = false;
            field_71304_b.profilingEnabled = true;
            field_71304_b.clearProfiling();
        }

        field_71304_b.startSection("root");
        func_71190_q();

        if (field_71315_w % 900 == 0)
        {
            field_71304_b.startSection("save");
            field_71318_t.func_72389_g();
            func_71267_a(true);
            field_71304_b.endSection();
        }

        field_71304_b.startSection("tallying");
        field_71311_j[field_71315_w % 100] = System.nanoTime() - l;
        field_71300_f[field_71315_w % 100] = Packet.field_73290_p - field_71281_E;
        field_71281_E = Packet.field_73290_p;
        field_71301_g[field_71315_w % 100] = Packet.field_73289_q - field_71282_F;
        field_71282_F = Packet.field_73289_q;
        field_71313_h[field_71315_w % 100] = Packet.field_73292_n - field_71283_G;
        field_71283_G = Packet.field_73292_n;
        field_71314_i[field_71315_w % 100] = Packet.field_73293_o - field_71291_H;
        field_71291_H = Packet.field_73293_o;
        field_71304_b.endSection();
        field_71304_b.startSection("snooper");

        if (!field_71307_n.func_76468_d() && field_71315_w > 100)
        {
            field_71307_n.func_76463_a();
        }

        if (field_71315_w % 6000 == 0)
        {
            field_71307_n.func_76471_b();
        }

        field_71304_b.endSection();
        field_71304_b.endSection();
    }

    public void func_71190_q()
    {
        field_71304_b.startSection("levels");

        for (int i = 0; i < dimensionServerList.length; i++)
        {
            long l = System.nanoTime();

            if (i == 0 || func_71255_r())
            {
                WorldServer worldserver = dimensionServerList[i];
                field_71304_b.startSection(worldserver.getWorldInfo().getWorldName());

                if (field_71315_w % 20 == 0)
                {
                    field_71304_b.startSection("timeSync");
                    field_71318_t.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(worldserver.getWorldTime()), worldserver.worldProvider.worldType);
                    field_71304_b.endSection();
                }

                field_71304_b.startSection("tick");
                worldserver.tick();
                field_71304_b.endStartSection("lights");

                while (worldserver.updatingLighting()) ;

                field_71304_b.endSection();

                if (!worldserver.playerEntities.isEmpty())
                {
                    worldserver.updateEntities();
                }

                field_71304_b.startSection("tracker");
                worldserver.getEntityTracker().func_72788_a();
                field_71304_b.endSection();
                field_71304_b.endSection();
            }

            field_71312_k[i][field_71315_w % 100] = System.nanoTime() - l;
        }

        field_71304_b.endStartSection("connection");
        func_71212_ac().func_71747_b();
        field_71304_b.endStartSection("players");
        field_71318_t.func_72374_b();
        field_71304_b.endStartSection("tickables");
        IUpdatePlayerListBox iupdateplayerlistbox;

        for (Iterator iterator = field_71322_p.iterator(); iterator.hasNext(); iupdateplayerlistbox.func_73660_a())
        {
            iupdateplayerlistbox = (IUpdatePlayerListBox)iterator.next();
        }

        field_71304_b.endSection();
    }

    public boolean func_71255_r()
    {
        return true;
    }

    public void func_71256_s()
    {
        (new ThreadServerApplication(this, "Server thread")).start();
    }

    public File func_71209_f(String par1Str)
    {
        return new File(func_71238_n(), par1Str);
    }

    public void func_71244_g(String par1Str)
    {
        minecraftLogger.info(par1Str);
    }

    public void func_71236_h(String par1Str)
    {
        minecraftLogger.warning(par1Str);
    }

    public WorldServer worldServerForDimension(int par1)
    {
        if (par1 == -1)
        {
            return dimensionServerList[1];
        }

        if (par1 == 1)
        {
            return dimensionServerList[2];
        }
        else
        {
            return dimensionServerList[0];
        }
    }

    public String func_71277_t()
    {
        return field_71320_r;
    }

    public int func_71234_u()
    {
        return field_71319_s;
    }

    public String func_71274_v()
    {
        return field_71286_C;
    }

    public String func_71249_w()
    {
        return "1.3.1";
    }

    public int func_71233_x()
    {
        return field_71318_t.func_72394_k();
    }

    public int func_71275_y()
    {
        return field_71318_t.getMaxPlayers();
    }

    public String[] func_71213_z()
    {
        return field_71318_t.getAllUsernames();
    }

    public String func_71258_A()
    {
        return "";
    }

    public String func_71252_i(String par1Str)
    {
        RConConsoleSource.field_70010_a.func_70007_b();
        commandManager.executeCommand(RConConsoleSource.field_70010_a, par1Str);
        return RConConsoleSource.field_70010_a.func_70008_c();
    }

    public boolean func_71239_B()
    {
        return false;
    }

    public void func_71201_j(String par1Str)
    {
        minecraftLogger.log(Level.SEVERE, par1Str);
    }

    public void func_71198_k(String par1Str)
    {
        if (func_71239_B())
        {
            minecraftLogger.log(Level.INFO, par1Str);
        }
    }

    public String getServerModName()
    {
        return "vanilla";
    }

    public CrashReport func_71230_b(CrashReport par1CrashReport)
    {
        par1CrashReport.func_71500_a("Is Modded", new CallableIsServerModded(this));
        par1CrashReport.func_71500_a("Profiler Position", new CallableServerProfiler(this));

        if (field_71318_t != null)
        {
            par1CrashReport.func_71500_a("Player Count", new CallablePlayers(this));
        }

        if (dimensionServerList != null)
        {
            WorldServer aworldserver[] = dimensionServerList;
            int i = aworldserver.length;

            for (int j = 0; j < i; j++)
            {
                WorldServer worldserver = aworldserver[j];

                if (worldserver != null)
                {
                    worldserver.func_72914_a(par1CrashReport);
                }
            }
        }

        return par1CrashReport;
    }

    public List func_71248_a(ICommandSender par1ICommandSender, String par2Str)
    {
        ArrayList arraylist = new ArrayList();

        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
            boolean flag = !par2Str.contains(" ");
            List list = commandManager.func_71558_b(par1ICommandSender, par2Str);

            if (list != null)
            {
                for (Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    String s1 = (String)iterator.next();

                    if (flag)
                    {
                        arraylist.add((new StringBuilder()).append("/").append(s1).toString());
                    }
                    else
                    {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        }

        String as[] = par2Str.split(" ", -1);
        String s = as[as.length - 1];
        String as1[] = field_71318_t.getAllUsernames();
        int i = as1.length;

        for (int j = 0; j < i; j++)
        {
            String s2 = as1[j];

            if (CommandBase.func_71523_a(s, s2))
            {
                arraylist.add(s2);
            }
        }

        return arraylist;
    }

    public static MinecraftServer func_71276_C()
    {
        return theServer;
    }

    public String func_70005_c_()
    {
        return "Server";
    }

    public void sendChatToPlayer(String par1Str)
    {
        minecraftLogger.info(StringUtils.func_76338_a(par1Str));
    }

    public boolean func_70003_b(String par1Str)
    {
        return true;
    }

    public String func_70004_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }

    public ICommandManager func_71187_D()
    {
        return commandManager;
    }

    public KeyPair func_71250_E()
    {
        return field_71292_I;
    }

    public int func_71215_F()
    {
        return field_71319_s;
    }

    public void func_71208_b(int par1)
    {
        field_71319_s = par1;
    }

    public String func_71214_G()
    {
        return field_71293_J;
    }

    public void setUsername(String par1Str)
    {
        field_71293_J = par1Str;
    }

    public boolean isSinglePlayer()
    {
        return field_71293_J != null;
    }

    public String func_71270_I()
    {
        return field_71294_K;
    }

    public void setFolderName(String par1Str)
    {
        field_71294_K = par1Str;
    }

    public void setWorldName(String par1Str)
    {
        field_71287_L = par1Str;
    }

    public String func_71221_J()
    {
        return field_71287_L;
    }

    public void func_71253_a(KeyPair par1KeyPair)
    {
        field_71292_I = par1KeyPair;
    }

    public void func_71226_c(int par1)
    {
        for (int i = 0; i < dimensionServerList.length; i++)
        {
            WorldServer worldserver = dimensionServerList[i];

            if (worldserver == null)
            {
                continue;
            }

            if (worldserver.getWorldInfo().isHardcoreModeEnabled())
            {
                worldserver.difficultySetting = 3;
                worldserver.setAllowedSpawnTypes(true, true);
                continue;
            }

            if (isSinglePlayer())
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(((World)(worldserver)).difficultySetting > 0, true);
            }
            else
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(func_71193_K(), field_71324_y);
            }
        }
    }

    protected boolean func_71193_K()
    {
        return true;
    }

    public boolean getIsDemo()
    {
        return isDemo;
    }

    public void setIsDemo(boolean par1)
    {
        isDemo = par1;
    }

    public void func_71194_c(boolean par1)
    {
        field_71289_N = par1;
    }

    public ISaveFormat func_71254_M()
    {
        return field_71310_m;
    }

    public void func_71272_O()
    {
        field_71290_O = true;
        func_71254_M().flushCache();

        for (int i = 0; i < dimensionServerList.length; i++)
        {
            WorldServer worldserver = dimensionServerList[i];

            if (worldserver != null)
            {
                worldserver.func_73041_k();
            }
        }

        func_71254_M().deleteWorldDirectory(dimensionServerList[0].getSaveHandler().getSaveDirectoryName());
        func_71263_m();
    }

    public String func_71202_P()
    {
        return field_71297_P;
    }

    public void func_71269_o(String par1Str)
    {
        field_71297_P = par1Str;
    }

    public void func_70000_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.addData("whitelist_enabled", Boolean.valueOf(false));
        par1PlayerUsageSnooper.addData("whitelist_count", Integer.valueOf(0));
        par1PlayerUsageSnooper.addData("players_current", Integer.valueOf(func_71233_x()));
        par1PlayerUsageSnooper.addData("players_max", Integer.valueOf(func_71275_y()));
        par1PlayerUsageSnooper.addData("players_seen", Integer.valueOf(field_71318_t.func_72373_m().length));
        par1PlayerUsageSnooper.addData("uses_auth", Boolean.valueOf(field_71325_x));
        par1PlayerUsageSnooper.addData("gui_state", func_71279_ae() ? "enabled" : "disabled");
        par1PlayerUsageSnooper.addData("avg_tick_ms", Integer.valueOf((int)(MathHelper.func_76127_a(field_71311_j) * 9.9999999999999995E-007D)));
        par1PlayerUsageSnooper.addData("avg_sent_packet_count", Integer.valueOf((int)MathHelper.func_76127_a(field_71300_f)));
        par1PlayerUsageSnooper.addData("avg_sent_packet_size", Integer.valueOf((int)MathHelper.func_76127_a(field_71301_g)));
        par1PlayerUsageSnooper.addData("avg_rec_packet_count", Integer.valueOf((int)MathHelper.func_76127_a(field_71313_h)));
        par1PlayerUsageSnooper.addData("avg_rec_packet_size", Integer.valueOf((int)MathHelper.func_76127_a(field_71314_i)));
        int i = 0;

        for (int j = 0; j < dimensionServerList.length; j++)
        {
            if (dimensionServerList[j] != null)
            {
                WorldServer worldserver = dimensionServerList[j];
                WorldInfo worldinfo = worldserver.getWorldInfo();
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][dimension]").toString(), Integer.valueOf(worldserver.worldProvider.worldType));
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][mode]").toString(), worldinfo.func_76077_q());
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][difficulty]").toString(), Integer.valueOf(worldserver.difficultySetting));
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][hardcore]").toString(), Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][generator_name]").toString(), worldinfo.getTerrainType().getWorldTypeName());
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][generator_version]").toString(), Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][height]").toString(), Integer.valueOf(field_71280_D));
                par1PlayerUsageSnooper.addData((new StringBuilder()).append("world[").append(i).append("][chunks_loaded]").toString(), Integer.valueOf(worldserver.getChunkProvider().func_73152_e()));
                i++;
            }
        }

        par1PlayerUsageSnooper.addData("worlds", Integer.valueOf(i));
    }

    public void func_70001_b(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.addData("singleplayer", Boolean.valueOf(isSinglePlayer()));
        par1PlayerUsageSnooper.addData("server_brand", getServerModName());
        par1PlayerUsageSnooper.addData("gui_supported", java.awt.GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        par1PlayerUsageSnooper.addData("dedicated", Boolean.valueOf(isDedicatedServer()));
    }

    public boolean func_70002_Q()
    {
        return true;
    }

    public int func_71227_R()
    {
        return 16;
    }

    public abstract boolean isDedicatedServer();

    public boolean isServerInOnlineMode()
    {
        return field_71325_x;
    }

    public void func_71229_d(boolean par1)
    {
        field_71325_x = par1;
    }

    public boolean func_71268_U()
    {
        return field_71324_y;
    }

    public void func_71251_e(boolean par1)
    {
        field_71324_y = par1;
    }

    public boolean func_71220_V()
    {
        return field_71323_z;
    }

    public void func_71257_f(boolean par1)
    {
        field_71323_z = par1;
    }

    public boolean isPVPEnabled()
    {
        return field_71284_A;
    }

    public void func_71188_g(boolean par1)
    {
        field_71284_A = par1;
    }

    public boolean func_71231_X()
    {
        return field_71285_B;
    }

    public void func_71245_h(boolean par1)
    {
        field_71285_B = par1;
    }

    public String func_71273_Y()
    {
        return field_71286_C;
    }

    public void func_71205_p(String par1Str)
    {
        field_71286_C = par1Str;
    }

    public int func_71207_Z()
    {
        return field_71280_D;
    }

    public void func_71191_d(int par1)
    {
        field_71280_D = par1;
    }

    public boolean func_71241_aa()
    {
        return field_71316_v;
    }

    public ServerConfigurationManager func_71203_ab()
    {
        return field_71318_t;
    }

    public void func_71210_a(ServerConfigurationManager par1ServerConfigurationManager)
    {
        field_71318_t = par1ServerConfigurationManager;
    }

    public void func_71235_a(EnumGameType par1EnumGameType)
    {
        for (int i = 0; i < dimensionServerList.length; i++)
        {
            func_71276_C().dimensionServerList[i].getWorldInfo().func_76060_a(par1EnumGameType);
        }
    }

    public abstract NetworkListenThread func_71212_ac();

    public boolean func_71200_ad()
    {
        return field_71296_Q;
    }

    public boolean func_71279_ae()
    {
        return false;
    }

    public abstract String func_71206_a(EnumGameType enumgametype, boolean flag);

    public int func_71259_af()
    {
        return field_71315_w;
    }

    public void func_71223_ag()
    {
        field_71295_T = true;
    }

    public static ServerConfigurationManager func_71196_a(MinecraftServer par0MinecraftServer)
    {
        return par0MinecraftServer.field_71318_t;
    }
}
