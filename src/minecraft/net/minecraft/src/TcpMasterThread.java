package net.minecraft.src;

class TcpMasterThread extends Thread
{
    final TcpConnection theMasterTcpConnection;

    TcpMasterThread(TcpConnection par1TcpConnection)
    {
        theMasterTcpConnection = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (TcpConnection.getReadThread(theMasterTcpConnection).isAlive())
            {
                try
                {
                    TcpConnection.getReadThread(theMasterTcpConnection).stop();
                }
                catch (Throwable throwable) { }
            }

            if (TcpConnection.getWriteThread(theMasterTcpConnection).isAlive())
            {
                try
                {
                    TcpConnection.getWriteThread(theMasterTcpConnection).stop();
                }
                catch (Throwable throwable1) { }
            }
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }
}
