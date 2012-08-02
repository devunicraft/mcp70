package net.minecraft.src;

public class SaveFormatComparator implements Comparable
{
    /** the file name of this save */
    private final String fileName;

    /** the displayed name of this save file */
    private final String displayName;
    private final long lastTimePlayed;
    private final long sizeOnDisk;
    private final boolean requiresConversion;
    private final EnumGameType field_75791_f;
    private final boolean hardcore;
    private final boolean field_75798_h;

    public SaveFormatComparator(String par1Str, String par2Str, long par3, long par5, EnumGameType par7EnumGameType, boolean par8, boolean par9, boolean par10)
    {
        fileName = par1Str;
        displayName = par2Str;
        lastTimePlayed = par3;
        sizeOnDisk = par5;
        field_75791_f = par7EnumGameType;
        requiresConversion = par8;
        hardcore = par9;
        field_75798_h = par10;
    }

    /**
     * return the file name
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * return the display name of the save
     */
    public String getDisplayName()
    {
        return displayName;
    }

    public boolean requiresConversion()
    {
        return requiresConversion;
    }

    public long getLastTimePlayed()
    {
        return lastTimePlayed;
    }

    public int compareTo(SaveFormatComparator par1SaveFormatComparator)
    {
        if (lastTimePlayed < par1SaveFormatComparator.lastTimePlayed)
        {
            return 1;
        }

        if (lastTimePlayed > par1SaveFormatComparator.lastTimePlayed)
        {
            return -1;
        }
        else
        {
            return fileName.compareTo(par1SaveFormatComparator.fileName);
        }
    }

    public EnumGameType func_75790_f()
    {
        return field_75791_f;
    }

    public boolean isHardcoreModeEnabled()
    {
        return hardcore;
    }

    public boolean func_75783_h()
    {
        return field_75798_h;
    }

    public int compareTo(Object par1Obj)
    {
        return compareTo((SaveFormatComparator)par1Obj);
    }
}
