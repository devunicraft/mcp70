package net.minecraft.src;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long seed;
    private final EnumGameType field_77172_b;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean mapFeaturesEnabled;

    /** True if hardcore mode is enabled */
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    private boolean field_77168_f;
    private boolean field_77169_g;

    public WorldSettings(long par1, EnumGameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
    {
        seed = par1;
        field_77172_b = par3EnumGameType;
        mapFeaturesEnabled = par4;
        hardcoreEnabled = par5;
        terrainType = par6WorldType;
    }

    public WorldSettings(WorldInfo par1WorldInfo)
    {
        this(par1WorldInfo.getSeed(), par1WorldInfo.func_76077_q(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
    }

    public WorldSettings func_77159_a()
    {
        field_77169_g = true;
        return this;
    }

    public WorldSettings func_77166_b()
    {
        field_77168_f = true;
        return this;
    }

    public boolean func_77167_c()
    {
        return field_77169_g;
    }

    /**
     * Returns the seed for the world.
     */
    public long getSeed()
    {
        return seed;
    }

    public EnumGameType func_77162_e()
    {
        return field_77172_b;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean getHardcoreEnabled()
    {
        return hardcoreEnabled;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }

    public WorldType getTerrainType()
    {
        return terrainType;
    }

    public boolean func_77163_i()
    {
        return field_77168_f;
    }

    public static EnumGameType func_77161_a(int par0)
    {
        return EnumGameType.getByID(par0);
    }
}
