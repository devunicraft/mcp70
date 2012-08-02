package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import net.minecraft.server.MinecraftServer;

public class IntegratedServerListenThread extends NetworkListenThread
{
    private final MemoryConnection field_71760_c = new MemoryConnection(null);
    private MemoryConnection field_71758_d;
    private String field_71759_e;
    private boolean field_71756_f;
    private ServerListenThread field_71757_g;

    public IntegratedServerListenThread(IntegratedServer par1IntegratedServer) throws IOException
    {
        super(par1IntegratedServer);
        field_71756_f = false;
    }

    public void func_71754_a(MemoryConnection par1MemoryConnection, String par2Str)
    {
        field_71758_d = par1MemoryConnection;
        field_71759_e = par2Str;
    }

    public String func_71755_c() throws IOException
    {
        if (field_71757_g == null)
        {
            int i = -1;

            try
            {
                i = HttpUtil.func_76181_a();
            }
            catch (IOException ioexception) { }

            if (i <= 0)
            {
                i = 25564;
            }

            try
            {
                field_71757_g = new ServerListenThread(this, InetAddress.getLocalHost(), i);
                field_71757_g.start();
            }
            catch (IOException ioexception1)
            {
                throw ioexception1;
            }
        }

        return (new StringBuilder()).append(field_71757_g.func_71767_c().getHostAddress()).append(":").append(field_71757_g.func_71765_d()).toString();
    }

    public void func_71744_a()
    {
        super.func_71744_a();

        if (field_71757_g != null)
        {
            System.out.println("Stopping server connection");
            field_71757_g.func_71768_b();
            field_71757_g.interrupt();
            field_71757_g = null;
        }
    }

    public void func_71747_b()
    {
        if (field_71758_d != null)
        {
            EntityPlayerMP entityplayermp = func_71753_e().func_71203_ab().func_72366_a(field_71759_e);

            if (entityplayermp != null)
            {
                field_71760_c.func_74434_a(field_71758_d);
                field_71756_f = true;
                func_71753_e().func_71203_ab().func_72355_a(field_71760_c, entityplayermp);
            }

            field_71758_d = null;
            field_71759_e = null;
        }

        if (field_71757_g != null)
        {
            field_71757_g.func_71766_a();
        }

        super.func_71747_b();
    }

    public IntegratedServer func_71753_e()
    {
        return (IntegratedServer)super.func_71746_d();
    }

    public boolean func_71752_f()
    {
        return field_71756_f && field_71760_c.func_74432_i().func_74435_g() && field_71760_c.func_74432_i().func_74433_h();
    }

    public MinecraftServer func_71746_d()
    {
        return func_71753_e();
    }
}
