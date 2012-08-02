package net.minecraft.src;

class DedicatedServerSleepThread extends Thread
{
    final DedicatedServer field_72451_a;

    DedicatedServerSleepThread(DedicatedServer par1DedicatedServer)
    {
        field_72451_a = par1DedicatedServer;
        setDaemon(true);
        start();
    }

    public void run()
    {
        do
        {
            try
            {
                Thread.sleep(0x7fffffffL);
            }
            catch (InterruptedException interruptedexception) { }
        }
        while (true);
    }
}
