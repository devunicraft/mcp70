package net.minecraft.src;

import java.io.IOException;
import java.net.*;

class ThreadPollServers extends Thread
{
    final ServerData field_78318_a;

    /** Slot container for the server list */
    final GuiSlotServer serverSlotContainer;

    ThreadPollServers(GuiSlotServer par1GuiSlotServer, ServerData par2ServerData)
    {
        serverSlotContainer = par1GuiSlotServer;
        field_78318_a = par2ServerData;
    }

    public void run()
    {
        try
        {
            field_78318_a.field_78843_d = "\2478Polling..";
            long l = System.nanoTime();
            GuiMultiplayer.func_74013_a(serverSlotContainer.parentGui, field_78318_a);
            long l1 = System.nanoTime();
            field_78318_a.field_78844_e = (l1 - l) / 0xf4240L;
        }
        catch (UnknownHostException unknownhostexception)
        {
            field_78318_a.field_78844_e = -1L;
            field_78318_a.field_78843_d = "\2474Can't resolve hostname";
        }
        catch (SocketTimeoutException sockettimeoutexception)
        {
            field_78318_a.field_78844_e = -1L;
            field_78318_a.field_78843_d = "\2474Can't reach server";
        }
        catch (ConnectException connectexception)
        {
            field_78318_a.field_78844_e = -1L;
            field_78318_a.field_78843_d = "\2474Can't reach server";
        }
        catch (IOException ioexception)
        {
            field_78318_a.field_78844_e = -1L;
            field_78318_a.field_78843_d = "\2474Communication error";
        }
        catch (Exception exception)
        {
            field_78318_a.field_78844_e = -1L;
            field_78318_a.field_78843_d = (new StringBuilder()).append("ERROR: ").append(exception.getClass()).toString();
        }
        finally
        {
            synchronized (GuiMultiplayer.func_74011_h())
            {
                GuiMultiplayer.func_74018_k();
            }
        }
    }
}
