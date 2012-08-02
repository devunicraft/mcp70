package net.minecraft.src;

public class WorldType
{
    public static final WorldType worldTypes[] = new WorldType[16];

    /** Default world type. */
    public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).func_77129_f();

    /** Flat world type. */
    public static final WorldType FLAT = new WorldType(1, "flat");
    public static final WorldType field_77135_d = new WorldType(2, "largeBiomes");

    /** Default (1.1) world type. */
    public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);

    /** 'default' or 'flat' */
    private final String worldType;

    /** The int version of the ChunkProvider that generated this world. */
    private final int generatorVersion;

    /**
     * Whether this world type can be generated. Normally true; set to false for out-of-date generator versions.
     */
    private boolean canBeCreated;
    private boolean field_77141_i;

    private WorldType(int par1, String par2Str)
    {
        this(par1, par2Str, 0);
    }

    private WorldType(int par1, String par2Str, int par3)
    {
        worldType = par2Str;
        generatorVersion = par3;
        canBeCreated = true;
        worldTypes[par1] = this;
    }

    public String getWorldTypeName()
    {
        return worldType;
    }

    /**
     * Gets the translation key for the name of this world type.
     */
    public String getTranslateName()
    {
        return (new StringBuilder()).append("generator.").append(worldType).toString();
    }

    /**
     * Returns generatorVersion.
     */
    public int getGeneratorVersion()
    {
        return generatorVersion;
    }

    public WorldType getWorldTypeForGeneratorVersion(int par1)
    {
        if (this == DEFAULT && par1 == 0)
        {
            return DEFAULT_1_1;
        }
        else
        {
            return this;
        }
    }

    /**
     * Sets canBeCreated to the provided value, and returns this.
     */
    private WorldType setCanBeCreated(boolean par1)
    {
        canBeCreated = par1;
        return this;
    }

    /**
     * Gets whether this WorldType can be used to generate a new world.
     */
    public boolean getCanBeCreated()
    {
        return canBeCreated;
    }

    private WorldType func_77129_f()
    {
        field_77141_i = true;
        return this;
    }

    public boolean func_77125_e()
    {
        return field_77141_i;
    }

    public static WorldType parseWorldType(String par0Str)
    {
        WorldType aworldtype[] = worldTypes;
        int i = aworldtype.length;

        for (int j = 0; j < i; j++)
        {
            WorldType worldtype = aworldtype[j];

            if (worldtype != null && worldtype.worldType.equalsIgnoreCase(par0Str))
            {
                return worldtype;
            }
        }

        return null;
    }
}
