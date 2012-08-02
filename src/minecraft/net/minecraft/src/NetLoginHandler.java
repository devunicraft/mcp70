package net.minecraft.src;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import net.minecraft.server.MinecraftServer;

public class NetLoginHandler extends NetHandler
{
    private byte field_72536_d[];
    public static Logger field_72540_a = Logger.getLogger("Minecraft");
    private static Random field_72537_e = new Random();
    public TcpConnection field_72538_b;
    public boolean field_72539_c;
    private MinecraftServer minecraftInstance;
    private int field_72535_g;
    private String field_72543_h;
    private volatile boolean field_72544_i;
    private String field_72541_j;
    private SecretKey field_72542_k;

    public NetLoginHandler(MinecraftServer par1MinecraftServer, Socket par2Socket, String par3Str) throws IOException
    {
        field_72539_c = false;
        field_72535_g = 0;
        field_72543_h = null;
        field_72544_i = false;
        field_72541_j = "";
        field_72542_k = null;
        minecraftInstance = par1MinecraftServer;
        field_72538_b = new TcpConnection(par2Socket, par3Str, this, par1MinecraftServer.func_71250_E().getPrivate());
        field_72538_b.field_74468_e = 0;
    }

    public void func_72532_c()
    {
        if (field_72544_i)
        {
            func_72529_d();
        }

        if (field_72535_g++ == 600)
        {
            func_72527_a("Took too long to log in");
        }
        else
        {
            field_72538_b.processReadPackets();
        }
    }

    public void func_72527_a(String par1Str)
    {
        try
        {
            field_72540_a.info((new StringBuilder()).append("Disconnecting ").append(func_72528_e()).append(": ").append(par1Str).toString());
            field_72538_b.addToSendQueue(new Packet255KickDisconnect(par1Str));
            field_72538_b.serverShutdown();
            field_72539_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol)
    {
        field_72543_h = par1Packet2ClientProtocol.getUsername();

        if (!field_72543_h.equals(StringUtils.func_76338_a(field_72543_h)))
        {
            func_72527_a("Invalid username!");
            return;
        }

        java.security.PublicKey publickey = minecraftInstance.func_71250_E().getPublic();

        if (par1Packet2ClientProtocol.getProtocolVersion() != 39)
        {
            if (par1Packet2ClientProtocol.getProtocolVersion() > 39)
            {
                func_72527_a("Outdated server!");
            }
            else
            {
                func_72527_a("Outdated client!");
            }

            return;
        }
        else
        {
            field_72541_j = minecraftInstance.isServerInOnlineMode() ? Long.toString(field_72537_e.nextLong(), 16) : "-";
            field_72536_d = new byte[4];
            field_72537_e.nextBytes(field_72536_d);
            field_72538_b.addToSendQueue(new Packet253ServerAuthData(field_72541_j, publickey, field_72536_d));
            return;
        }
    }

    public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey)
    {
        java.security.PrivateKey privatekey = minecraftInstance.func_71250_E().getPrivate();
        field_72542_k = par1Packet252SharedKey.func_73303_a(privatekey);

        if (!Arrays.equals(field_72536_d, par1Packet252SharedKey.func_73302_b(privatekey)))
        {
            func_72527_a("Invalid client reply");
        }

        field_72538_b.addToSendQueue(new Packet252SharedKey());
    }

    public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.forceRespawn == 0)
        {
            if (minecraftInstance.isServerInOnlineMode())
            {
                (new ThreadLoginVerifier(this)).start();
            }
            else
            {
                field_72544_i = true;
            }
        }
    }

    public void handleLogin(Packet1Login packet1login)
    {
    }

    public void func_72529_d()
    {
        String s = minecraftInstance.func_71203_ab().func_72399_a(field_72538_b.getSocketAddress(), field_72543_h);

        if (s != null)
        {
            func_72527_a(s);
        }
        else
        {
            EntityPlayerMP entityplayermp = minecraftInstance.func_71203_ab().func_72366_a(field_72543_h);

            if (entityplayermp != null)
            {
                minecraftInstance.func_71203_ab().func_72355_a(field_72538_b, entityplayermp);
            }
        }

        field_72539_c = true;
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        field_72540_a.info((new StringBuilder()).append(func_72528_e()).append(" lost connection").toString());
        field_72539_c = true;
    }

    /**
     * Handle a server ping packet.
     */
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
    {
        try
        {
            String s = (new StringBuilder()).append(minecraftInstance.func_71273_Y()).append("\247").append(minecraftInstance.func_71203_ab().func_72394_k()).append("\247").append(minecraftInstance.func_71203_ab().getMaxPlayers()).toString();
            java.net.InetAddress inetaddress = null;

            if (field_72538_b.func_74452_g() != null)
            {
                inetaddress = field_72538_b.func_74452_g().getInetAddress();
            }

            field_72538_b.addToSendQueue(new Packet255KickDisconnect(s));
            field_72538_b.serverShutdown();

            if (inetaddress != null && (minecraftInstance.func_71212_ac() instanceof DedicatedServerListenThread))
            {
                ((DedicatedServerListenThread)minecraftInstance.func_71212_ac()).func_71761_a(inetaddress);
            }

            field_72539_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void registerPacket(Packet par1Packet)
    {
        func_72527_a("Protocol error");
    }

    public String func_72528_e()
    {
        if (field_72543_h != null)
        {
            return (new StringBuilder()).append(field_72543_h).append(" [").append(field_72538_b.getSocketAddress().toString()).append("]").toString();
        }
        else
        {
            return field_72538_b.getSocketAddress().toString();
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return true;
    }

    static String func_72526_a(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_72541_j;
    }

    static MinecraftServer func_72530_b(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.minecraftInstance;
    }

    static SecretKey func_72525_c(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_72542_k;
    }

    static String func_72533_d(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_72543_h;
    }

    static boolean func_72531_a(NetLoginHandler par0NetLoginHandler, boolean par1)
    {
        return par0NetLoginHandler.field_72544_i = par1;
    }
}
