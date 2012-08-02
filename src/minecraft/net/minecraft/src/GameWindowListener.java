package net.minecraft.src;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

public final class GameWindowListener extends WindowAdapter
{
    public GameWindowListener()
    {
    }

    public void windowClosing(WindowEvent par1WindowEvent)
    {
        System.err.println("Someone is closing me!");
        System.exit(1);
    }
}
