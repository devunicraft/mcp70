package net.minecraft.src;

class TcpMonitorThread extends Thread
{
    final TcpConnection theMonitorTcpConnection;

    TcpMonitorThread(TcpConnection par1TcpConnection)
    {
        theMonitorTcpConnection = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(2000L);

            if (TcpConnection.isRunning(theMonitorTcpConnection))
            {
                TcpConnection.getWriteThread(theMonitorTcpConnection).interrupt();
                theMonitorTcpConnection.networkShutdown("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
