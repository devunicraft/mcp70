package net.minecraft.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BanEntry
{
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    public static Logger field_73696_b = Logger.getLogger("Minecraft");
    private final String username;
    private Date banStartDate;
    private String bannedBy;
    private Date banEndDate;
    private String reason;

    public BanEntry(String par1Str)
    {
        banStartDate = new Date();
        bannedBy = "(Unknown)";
        banEndDate = null;
        reason = "Banned by an operator.";
        username = par1Str;
    }

    public String getBannedUsername()
    {
        return username;
    }

    public Date banStart()
    {
        return banStartDate;
    }

    public void func_73681_a(Date par1Date)
    {
        banStartDate = par1Date == null ? new Date() : par1Date;
    }

    public String getBannedBy()
    {
        return bannedBy;
    }

    public void setBannedBy(String par1Str)
    {
        bannedBy = par1Str;
    }

    public Date getBanEndDate()
    {
        return banEndDate;
    }

    public void setBanEndDate(Date par1Date)
    {
        banEndDate = par1Date;
    }

    public boolean hasBanExpired()
    {
        if (banEndDate == null)
        {
            return false;
        }
        else
        {
            return banEndDate.before(new Date());
        }
    }

    public String getBanReason()
    {
        return reason;
    }

    public void setBanReason(String par1Str)
    {
        reason = par1Str;
    }

    public String func_73685_g()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(getBannedUsername());
        stringbuilder.append("|");
        stringbuilder.append(dateFormat.format(banStart()));
        stringbuilder.append("|");
        stringbuilder.append(getBannedBy());
        stringbuilder.append("|");
        stringbuilder.append(getBanEndDate() != null ? dateFormat.format(getBanEndDate()) : "Forever");
        stringbuilder.append("|");
        stringbuilder.append(getBanReason());
        return stringbuilder.toString();
    }

    public static BanEntry func_73688_c(String par0Str)
    {
        if (par0Str.trim().length() < 2)
        {
            return null;
        }

        String as[] = par0Str.trim().split(Pattern.quote("|"), 5);
        BanEntry banentry = new BanEntry(as[0].trim());
        int i = 0;

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            banentry.func_73681_a(dateFormat.parse(as[i].trim()));
        }
        catch (ParseException parseexception)
        {
            field_73696_b.log(Level.WARNING, (new StringBuilder()).append("Could not read creation date format for ban entry '").append(banentry.getBannedUsername()).append("' (was: '").append(as[i]).append("')").toString(), parseexception);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }

        banentry.setBannedBy(as[i].trim());

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            String s = as[i].trim();

            if (!s.equalsIgnoreCase("Forever") && s.length() > 0)
            {
                banentry.setBanEndDate(dateFormat.parse(s));
            }
        }
        catch (ParseException parseexception1)
        {
            field_73696_b.log(Level.WARNING, (new StringBuilder()).append("Could not read expiry date format for ban entry '").append(banentry.getBannedUsername()).append("' (was: '").append(as[i]).append("')").toString(), parseexception1);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }
        else
        {
            banentry.setBanReason(as[i].trim());
            return banentry;
        }
    }
}
