package net.minecraft.src;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class RConThreadQuery extends RConThreadBase
{
    private long field_72629_g;
    private int field_72636_h;
    private int field_72637_i;
    private int field_72634_j;
    private String field_72635_k;
    private String field_72632_l;
    private DatagramSocket field_72633_m;
    private byte field_72630_n[];
    private DatagramPacket field_72631_o;
    private Map field_72644_p;
    private String field_72643_q;
    private String field_72642_r;
    private Map field_72641_s;
    private long field_72640_t;
    private RConOutputStream field_72639_u;
    private long field_72638_v;

    public RConThreadQuery(IServer par1IServer)
    {
        super(par1IServer);
        field_72633_m = null;
        field_72630_n = new byte[1460];
        field_72631_o = null;
        field_72636_h = par1IServer.func_71327_a("query.port", 0);
        field_72642_r = par1IServer.func_71277_t();
        field_72637_i = par1IServer.func_71234_u();
        field_72635_k = par1IServer.func_71274_v();
        field_72634_j = par1IServer.func_71275_y();
        field_72632_l = par1IServer.func_71270_I();
        field_72638_v = 0L;
        field_72643_q = "0.0.0.0";

        if (0 == field_72642_r.length() || field_72643_q.equals(field_72642_r))
        {
            field_72642_r = "0.0.0.0";

            try
            {
                InetAddress inetaddress = InetAddress.getLocalHost();
                field_72643_q = inetaddress.getHostAddress();
            }
            catch (UnknownHostException unknownhostexception)
            {
                func_72606_c((new StringBuilder()).append("Unable to determine local host IP, please set server-ip in '").append(par1IServer.func_71329_c()).append("' : ").append(unknownhostexception.getMessage()).toString());
            }
        }
        else
        {
            field_72643_q = field_72642_r;
        }

        if (0 == field_72636_h)
        {
            field_72636_h = field_72637_i;
            func_72609_b((new StringBuilder()).append("Setting default query port to ").append(field_72636_h).toString());
            par1IServer.func_71328_a("query.port", Integer.valueOf(field_72636_h));
            par1IServer.func_71328_a("debug", Boolean.valueOf(false));
            par1IServer.func_71326_a();
        }

        field_72644_p = new HashMap();
        field_72639_u = new RConOutputStream(1460);
        field_72641_s = new HashMap();
        field_72640_t = (new Date()).getTime();
    }

    private void func_72620_a(byte par1ArrayOfByte[], DatagramPacket par2DatagramPacket) throws IOException
    {
        field_72633_m.send(new DatagramPacket(par1ArrayOfByte, par1ArrayOfByte.length, par2DatagramPacket.getSocketAddress()));
    }

    private boolean func_72621_a(DatagramPacket par1DatagramPacket) throws IOException
    {
        byte abyte0[] = par1DatagramPacket.getData();
        int i = par1DatagramPacket.getLength();
        SocketAddress socketaddress = par1DatagramPacket.getSocketAddress();
        func_72607_a((new StringBuilder()).append("Packet len ").append(i).append(" [").append(socketaddress).append("]").toString());

        if (3 > i || -2 != abyte0[0] || -3 != abyte0[1])
        {
            func_72607_a((new StringBuilder()).append("Invalid packet [").append(socketaddress).append("]").toString());
            return false;
        }

        func_72607_a((new StringBuilder()).append("Packet '").append(RConUtils.func_72663_a(abyte0[2])).append("' [").append(socketaddress).append("]").toString());

        switch (abyte0[2])
        {
            case 9:
                func_72622_d(par1DatagramPacket);
                func_72607_a((new StringBuilder()).append("Challenge [").append(socketaddress).append("]").toString());
                return true;

            case 0:
                if (!func_72627_c(par1DatagramPacket).booleanValue())
                {
                    func_72607_a((new StringBuilder()).append("Invalid challenge [").append(socketaddress).append("]").toString());
                    return false;
                }

                if (15 == i)
                {
                    func_72620_a(func_72624_b(par1DatagramPacket), par1DatagramPacket);
                    func_72607_a((new StringBuilder()).append("Rules [").append(socketaddress).append("]").toString());
                }
                else
                {
                    RConOutputStream rconoutputstream = new RConOutputStream(1460);
                    rconoutputstream.func_72667_a(0);
                    rconoutputstream.func_72670_a(func_72625_a(par1DatagramPacket.getSocketAddress()));
                    rconoutputstream.func_72671_a(field_72635_k);
                    rconoutputstream.func_72671_a("SMP");
                    rconoutputstream.func_72671_a(field_72632_l);
                    rconoutputstream.func_72671_a(Integer.toString(func_72603_d()));
                    rconoutputstream.func_72671_a(Integer.toString(field_72634_j));
                    rconoutputstream.func_72668_a((short)field_72637_i);
                    rconoutputstream.func_72671_a(field_72643_q);
                    func_72620_a(rconoutputstream.func_72672_a(), par1DatagramPacket);
                    func_72607_a((new StringBuilder()).append("Status [").append(socketaddress).append("]").toString());
                }

                break;
        }

        return true;
    }

    private byte[] func_72624_b(DatagramPacket par1DatagramPacket) throws IOException
    {
        long l = System.currentTimeMillis();

        if (l < field_72638_v + 5000L)
        {
            byte abyte0[] = field_72639_u.func_72672_a();
            byte abyte1[] = func_72625_a(par1DatagramPacket.getSocketAddress());
            abyte0[1] = abyte1[0];
            abyte0[2] = abyte1[1];
            abyte0[3] = abyte1[2];
            abyte0[4] = abyte1[3];
            return abyte0;
        }

        field_72638_v = l;
        field_72639_u.func_72669_b();
        field_72639_u.func_72667_a(0);
        field_72639_u.func_72670_a(func_72625_a(par1DatagramPacket.getSocketAddress()));
        field_72639_u.func_72671_a("splitnum");
        field_72639_u.func_72667_a(128);
        field_72639_u.func_72667_a(0);
        field_72639_u.func_72671_a("hostname");
        field_72639_u.func_72671_a(field_72635_k);
        field_72639_u.func_72671_a("gametype");
        field_72639_u.func_72671_a("SMP");
        field_72639_u.func_72671_a("game_id");
        field_72639_u.func_72671_a("MINECRAFT");
        field_72639_u.func_72671_a("version");
        field_72639_u.func_72671_a(field_72617_b.func_71249_w());
        field_72639_u.func_72671_a("plugins");
        field_72639_u.func_72671_a(field_72617_b.func_71258_A());
        field_72639_u.func_72671_a("map");
        field_72639_u.func_72671_a(field_72632_l);
        field_72639_u.func_72671_a("numplayers");
        field_72639_u.func_72671_a((new StringBuilder()).append("").append(func_72603_d()).toString());
        field_72639_u.func_72671_a("maxplayers");
        field_72639_u.func_72671_a((new StringBuilder()).append("").append(field_72634_j).toString());
        field_72639_u.func_72671_a("hostport");
        field_72639_u.func_72671_a((new StringBuilder()).append("").append(field_72637_i).toString());
        field_72639_u.func_72671_a("hostip");
        field_72639_u.func_72671_a(field_72643_q);
        field_72639_u.func_72667_a(0);
        field_72639_u.func_72667_a(1);
        field_72639_u.func_72671_a("player_");
        field_72639_u.func_72667_a(0);
        String as[] = field_72617_b.func_71213_z();
        byte byte0 = (byte)as.length;

        for (byte byte1 = (byte)(byte0 - 1); byte1 >= 0; byte1--)
        {
            field_72639_u.func_72671_a(as[byte1]);
        }

        field_72639_u.func_72667_a(0);
        return field_72639_u.func_72672_a();
    }

    private byte[] func_72625_a(SocketAddress par1SocketAddress)
    {
        return ((RConThreadQueryAuth)field_72641_s.get(par1SocketAddress)).func_72591_c();
    }

    private Boolean func_72627_c(DatagramPacket par1DatagramPacket)
    {
        SocketAddress socketaddress = par1DatagramPacket.getSocketAddress();

        if (!field_72641_s.containsKey(socketaddress))
        {
            return Boolean.valueOf(false);
        }

        byte abyte0[] = par1DatagramPacket.getData();

        if (((RConThreadQueryAuth)field_72641_s.get(socketaddress)).func_72592_a() != RConUtils.func_72664_c(abyte0, 7, par1DatagramPacket.getLength()))
        {
            return Boolean.valueOf(false);
        }
        else
        {
            return Boolean.valueOf(true);
        }
    }

    private void func_72622_d(DatagramPacket par1DatagramPacket) throws IOException
    {
        RConThreadQueryAuth rconthreadqueryauth = new RConThreadQueryAuth(this, par1DatagramPacket);
        field_72641_s.put(par1DatagramPacket.getSocketAddress(), rconthreadqueryauth);
        func_72620_a(rconthreadqueryauth.func_72594_b(), par1DatagramPacket);
    }

    private void func_72628_f()
    {
        if (!field_72619_a)
        {
            return;
        }

        long l = System.currentTimeMillis();

        if (l < field_72629_g + 30000L)
        {
            return;
        }

        field_72629_g = l;
        Iterator iterator = field_72641_s.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (((RConThreadQueryAuth)entry.getValue()).func_72593_a(l).booleanValue())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void run()
    {
        func_72609_b((new StringBuilder()).append("Query running on ").append(field_72642_r).append(":").append(field_72636_h).toString());
        field_72629_g = System.currentTimeMillis();
        field_72631_o = new DatagramPacket(field_72630_n, field_72630_n.length);

        try
        {
            while (field_72619_a)
            {
                try
                {
                    field_72633_m.receive(field_72631_o);
                    func_72628_f();
                    func_72621_a(field_72631_o);
                }
                catch (SocketTimeoutException sockettimeoutexception)
                {
                    func_72628_f();
                }
                catch (PortUnreachableException portunreachableexception) { }
                catch (IOException ioexception)
                {
                    func_72623_a(ioexception);
                }
            }
        }
        finally
        {
            func_72611_e();
        }
    }

    public void func_72602_a()
    {
        if (field_72619_a)
        {
            return;
        }

        if (0 >= field_72636_h || 65535 < field_72636_h)
        {
            func_72606_c((new StringBuilder()).append("Invalid query port ").append(field_72636_h).append(" found in '").append(field_72617_b.func_71329_c()).append("' (queries disabled)").toString());
            return;
        }

        if (func_72626_g())
        {
            super.func_72602_a();
        }
    }

    private void func_72623_a(Exception par1Exception)
    {
        if (!field_72619_a)
        {
            return;
        }

        func_72606_c((new StringBuilder()).append("Unexpected exception, buggy JRE? (").append(par1Exception.toString()).append(")").toString());

        if (!func_72626_g())
        {
            func_72610_d("Failed to recover from buggy JRE, shutting down!");
            field_72619_a = false;
        }
    }

    private boolean func_72626_g()
    {
        try
        {
            field_72633_m = new DatagramSocket(field_72636_h, InetAddress.getByName(field_72642_r));
            func_72601_a(field_72633_m);
            field_72633_m.setSoTimeout(500);
            return true;
        }
        catch (SocketException socketexception)
        {
            func_72606_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_72642_r).append(":").append(field_72636_h).append(" (Socket): ").append(socketexception.getMessage()).toString());
        }
        catch (UnknownHostException unknownhostexception)
        {
            func_72606_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_72642_r).append(":").append(field_72636_h).append(" (Unknown Host): ").append(unknownhostexception.getMessage()).toString());
        }
        catch (Exception exception)
        {
            func_72606_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_72642_r).append(":").append(field_72636_h).append(" (E): ").append(exception.getMessage()).toString());
        }

        return false;
    }
}
