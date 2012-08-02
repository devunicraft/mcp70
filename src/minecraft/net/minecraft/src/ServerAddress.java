package net.minecraft.src;

import java.util.Hashtable;
import javax.naming.directory.*;

public class ServerAddress
{
    private final String ipAddress;
    private final int serverPort;

    private ServerAddress(String par1Str, int par2)
    {
        ipAddress = par1Str;
        serverPort = par2;
    }

    public String getIP()
    {
        return ipAddress;
    }

    public int getPort()
    {
        return serverPort;
    }

    public static ServerAddress func_78860_a(String par0Str)
    {
        if (par0Str == null)
        {
            return null;
        }

        String as[] = par0Str.split(":");

        if (par0Str.startsWith("["))
        {
            int i = par0Str.indexOf("]");

            if (i > 0)
            {
                String s1 = par0Str.substring(1, i);
                String s2 = par0Str.substring(i + 1).trim();

                if (s2.startsWith(":") && s2.length() > 0)
                {
                    s2 = s2.substring(1);
                    as = new String[2];
                    as[0] = s1;
                    as[1] = s2;
                }
                else
                {
                    as = new String[1];
                    as[0] = s1;
                }
            }
        }

        if (as.length > 2)
        {
            as = new String[1];
            as[0] = par0Str;
        }

        String s = as[0];
        int j = as.length <= 1 ? 25565 : parseIntWithDefault(as[1], 25565);

        if (j == 25565)
        {
            String as1[] = func_78863_b(s);
            s = as1[0];
            j = parseIntWithDefault(as1[1], 25565);
        }

        return new ServerAddress(s, j);
    }

    private static String[] func_78863_b(String par0Str)
    {
        try
        {
            Hashtable hashtable = new Hashtable();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            hashtable.put("java.naming.provider.url", "dns:");
            InitialDirContext initialdircontext = new InitialDirContext(hashtable);
            Attributes attributes = initialdircontext.getAttributes((new StringBuilder()).append("_minecraft._tcp.").append(par0Str).toString(), new String[]
                    {
                        "SRV"
                    });
            String as[] = attributes.get("srv").get().toString().split(" ", 4);
            return (new String[]
                    {
                        as[3], as[2]
                    });
        }
        catch (Throwable throwable)
        {
            return (new String[]
                    {
                        par0Str, Integer.toString(25565)
                    });
        }
    }

    private static int parseIntWithDefault(String par0Str, int par1)
    {
        try
        {
            return Integer.parseInt(par0Str.trim());
        }
        catch (Exception exception)
        {
            return par1;
        }
    }
}
