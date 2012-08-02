package net.minecraft.src;

import java.util.concurrent.atomic.AtomicInteger;

class TcpReaderThread extends Thread
{
    final TcpConnection theReaderTcpConnection;

    TcpReaderThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        theReaderTcpConnection = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_74471_a.getAndIncrement();

        try
        {
            while (TcpConnection.isRunning(theReaderTcpConnection) && !TcpConnection.isServerTerminating(theReaderTcpConnection))
            {
                while (TcpConnection.readNetworkPacket(theReaderTcpConnection)) ;

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_74471_a.getAndDecrement();
        }
    }
}
