package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class TcpWriterThread extends Thread
{
    final TcpConnection theWriterTcpConnection;

    TcpWriterThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        theWriterTcpConnection = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_74469_b.getAndIncrement();

        try
        {
            while (TcpConnection.isRunning(theWriterTcpConnection))
            {
                boolean flag;

                for (flag = false; TcpConnection.sendNetworkPacket(theWriterTcpConnection); flag = true) { }

                try
                {
                    if (flag && TcpConnection.getOutputStream(theWriterTcpConnection) != null)
                    {
                        TcpConnection.getOutputStream(theWriterTcpConnection).flush();
                    }
                }
                catch (IOException ioexception)
                {
                    if (!TcpConnection.isTerminating(theWriterTcpConnection))
                    {
                        TcpConnection.sendError(theWriterTcpConnection, ioexception);
                    }

                    ioexception.printStackTrace();
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_74469_b.getAndDecrement();
        }
    }
}
