package net.minecraft.src;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class RConThreadMain extends RConThreadBase
{
    private int field_72647_g;
    private int field_72651_h;
    private String field_72652_i;
    private ServerSocket field_72649_j;
    private String field_72650_k;
    private Map field_72648_l;

    public RConThreadMain(IServer par1IServer)
    {
        super(par1IServer);
        field_72649_j = null;
        field_72647_g = par1IServer.func_71327_a("rcon.port", 0);
        field_72650_k = par1IServer.func_71330_a("rcon.password", "");
        field_72652_i = par1IServer.func_71277_t();
        field_72651_h = par1IServer.func_71234_u();

        if (0 == field_72647_g)
        {
            field_72647_g = field_72651_h + 10;
            func_72609_b((new StringBuilder()).append("Setting default rcon port to ").append(field_72647_g).toString());
            par1IServer.func_71328_a("rcon.port", Integer.valueOf(field_72647_g));

            if (0 == field_72650_k.length())
            {
                par1IServer.func_71328_a("rcon.password", "");
            }

            par1IServer.func_71326_a();
        }

        if (0 == field_72652_i.length())
        {
            field_72652_i = "0.0.0.0";
        }

        func_72646_f();
        field_72649_j = null;
    }

    private void func_72646_f()
    {
        field_72648_l = new HashMap();
    }

    private void func_72645_g()
    {
        Iterator iterator = field_72648_l.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (!((RConThreadClient)entry.getValue()).func_72613_c())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void run()
    {
        func_72609_b((new StringBuilder()).append("RCON running on ").append(field_72652_i).append(":").append(field_72647_g).toString());

        try
        {
            do
            {
                if (!field_72619_a)
                {
                    break;
                }

                try
                {
                    Socket socket = field_72649_j.accept();
                    socket.setSoTimeout(500);
                    RConThreadClient rconthreadclient = new RConThreadClient(field_72617_b, socket);
                    rconthreadclient.func_72602_a();
                    field_72648_l.put(socket.getRemoteSocketAddress(), rconthreadclient);
                    func_72645_g();
                }
                catch (SocketTimeoutException sockettimeoutexception)
                {
                    func_72645_g();
                }
                catch (IOException ioexception)
                {
                    if (field_72619_a)
                    {
                        func_72609_b((new StringBuilder()).append("IO: ").append(ioexception.getMessage()).toString());
                    }
                }
            }
            while (true);
        }
        finally
        {
            func_72608_b(field_72649_j);
        }
    }

    public void func_72602_a()
    {
        if (0 == field_72650_k.length())
        {
            func_72606_c((new StringBuilder()).append("No rcon password set in '").append(field_72617_b.func_71329_c()).append("', rcon disabled!").toString());
            return;
        }

        if (0 >= field_72647_g || 65535 < field_72647_g)
        {
            func_72606_c((new StringBuilder()).append("Invalid rcon port ").append(field_72647_g).append(" found in '").append(field_72617_b.func_71329_c()).append("', rcon disabled!").toString());
            return;
        }

        if (field_72619_a)
        {
            return;
        }

        try
        {
            field_72649_j = new ServerSocket(field_72647_g, 0, InetAddress.getByName(field_72652_i));
            field_72649_j.setSoTimeout(500);
            super.func_72602_a();
        }
        catch (IOException ioexception)
        {
            func_72606_c((new StringBuilder()).append("Unable to initialise rcon on ").append(field_72652_i).append(":").append(field_72647_g).append(" : ").append(ioexception.getMessage()).toString());
        }
    }
}
