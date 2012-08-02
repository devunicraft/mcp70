package net.minecraft.src;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanList
{
    private final LowerStringMap theBanList = new LowerStringMap();
    private final File fileName;

    /** set to true if not singlePlayer */
    private boolean listActive;

    public BanList(File par1File)
    {
        listActive = true;
        fileName = par1File;
    }

    public boolean isListActive()
    {
        return listActive;
    }

    public void setListActive(boolean par1)
    {
        listActive = par1;
    }

    /**
     * removes expired Bans before returning
     */
    public Map getBannedList()
    {
        removeExpiredBans();
        return theBanList;
    }

    public boolean isBanned(String par1Str)
    {
        if (!isListActive())
        {
            return false;
        }
        else
        {
            removeExpiredBans();
            return theBanList.containsKey(par1Str);
        }
    }

    public void put(BanEntry par1BanEntry)
    {
        theBanList.putLower(par1BanEntry.getBannedUsername(), par1BanEntry);
        saveToFileWithHeader();
    }

    public void remove(String par1Str)
    {
        theBanList.remove(par1Str);
        saveToFileWithHeader();
    }

    public void removeExpiredBans()
    {
        Iterator iterator = theBanList.values().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            BanEntry banentry = (BanEntry)iterator.next();

            if (banentry.hasBanExpired())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void func_73707_e()
    {
        if (!fileName.isFile())
        {
            return;
        }

        BufferedReader bufferedreader;

        try
        {
            bufferedreader = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            throw new Error();
        }

        try
        {
            do
            {
                String s;

                if ((s = bufferedreader.readLine()) == null)
                {
                    break;
                }

                if (!s.startsWith("#"))
                {
                    BanEntry banentry = BanEntry.func_73688_c(s);

                    if (banentry != null)
                    {
                        theBanList.putLower(banentry.getBannedUsername(), banentry);
                    }
                }
            }
            while (true);
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load ban list", ioexception);
        }
    }

    public void saveToFileWithHeader()
    {
        saveToFile(true);
    }

    /**
     * par1: include header
     */
    public void saveToFile(boolean par1)
    {
        removeExpiredBans();

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(fileName, false));

            if (par1)
            {
                printwriter.println((new StringBuilder()).append("# Updated ").append((new SimpleDateFormat()).format(new Date())).append(" by Minecraft ").append("1.3.1").toString());
                printwriter.println("# victim name | ban date | banned by | banned until | reason");
                printwriter.println();
            }

            BanEntry banentry;

            for (Iterator iterator = theBanList.values().iterator(); iterator.hasNext(); printwriter.println(banentry.func_73685_g()))
            {
                banentry = (BanEntry)iterator.next();
            }

            printwriter.close();
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save ban list", ioexception);
        }
    }
}
