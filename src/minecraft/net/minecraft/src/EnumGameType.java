package net.minecraft.src;

public enum EnumGameType
{
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");

    int id;
    String name;

    private EnumGameType(int par3, String par4Str)
    {
        id = par3;
        name = par4Str;
    }

    /**
     * Returns the ID of this game type
     */
    public int getID()
    {
        return id;
    }

    /**
     * Returns the name of this game type
     */
    public String getName()
    {
        return name;
    }

    /**
     * Configures the player capabilities based on the game type
     */
    public void configurePlayerCapabilities(PlayerCapabilities par1PlayerCapabilities)
    {
        if (this == CREATIVE)
        {
            par1PlayerCapabilities.allowFlying = true;
            par1PlayerCapabilities.isCreativeMode = true;
            par1PlayerCapabilities.disableDamage = true;
        }
        else
        {
            par1PlayerCapabilities.allowFlying = false;
            par1PlayerCapabilities.isCreativeMode = false;
            par1PlayerCapabilities.disableDamage = false;
            par1PlayerCapabilities.isFlying = false;
        }

        par1PlayerCapabilities.allowEdit = !isAdventure();
    }

    /**
     * Returns true if this is the ADVENTURE game type
     */
    public boolean isAdventure()
    {
        return this == ADVENTURE;
    }

    /**
     * Returns true if this is the CREATIVE game type
     */
    public boolean isCreative()
    {
        return this == CREATIVE;
    }

    public boolean func_77144_e()
    {
        return this == SURVIVAL || this == ADVENTURE;
    }

    /**
     * Returns the game type with the specified ID, or SURVIVAL if none found. Args: id
     */
    public static EnumGameType getByID(int par0)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.id == par0)
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }

    /**
     * Returns the game type with the specified name, or SURVIVAL if none found. This is case sensitive. Args: name
     */
    public static EnumGameType getByName(String par0Str)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.name.equals(par0Str))
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }
}
