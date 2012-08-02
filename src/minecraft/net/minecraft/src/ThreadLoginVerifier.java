package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyPair;
import net.minecraft.server.MinecraftServer;

class ThreadLoginVerifier extends Thread
{
    final NetLoginHandler field_72590_a;

    ThreadLoginVerifier(NetLoginHandler par1NetLoginHandler)
    {
        field_72590_a = par1NetLoginHandler;
    }

    public void run()
    {
        try
        {
            String s = (new BigInteger(CryptManager.func_75895_a(NetLoginHandler.func_72526_a(field_72590_a), NetLoginHandler.func_72530_b(field_72590_a).func_71250_E().getPublic(), NetLoginHandler.func_72525_c(field_72590_a)))).toString(16);
            URL url = new URL((new StringBuilder()).append("http://session.minecraft.net/game/checkserver.jsp?user=").append(URLEncoder.encode(NetLoginHandler.func_72533_d(field_72590_a), "UTF-8")).append("&serverId=").append(URLEncoder.encode(s, "UTF-8")).toString());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s1 = bufferedreader.readLine();
            bufferedreader.close();

            if (!"YES".equals(s1))
            {
                field_72590_a.func_72527_a("Failed to verify username!");
                return;
            }

            NetLoginHandler.func_72531_a(field_72590_a, true);
        }
        catch (Exception exception)
        {
            field_72590_a.func_72527_a((new StringBuilder()).append("Failed to verify username! [internal error ").append(exception).append("]").toString());
            exception.printStackTrace();
        }
    }
}
