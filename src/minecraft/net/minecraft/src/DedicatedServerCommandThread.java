package net.minecraft.src;

import java.io.*;

class DedicatedServerCommandThread extends Thread
{
    final DedicatedServer field_72428_a;

    DedicatedServerCommandThread(DedicatedServer par1DedicatedServer)
    {
        field_72428_a = par1DedicatedServer;
    }

    public void run()
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String s;

            for (; !field_72428_a.func_71241_aa() && field_72428_a.func_71278_l() && (s = bufferedreader.readLine()) != null; field_72428_a.func_71331_a(s, field_72428_a)) { }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }
}
