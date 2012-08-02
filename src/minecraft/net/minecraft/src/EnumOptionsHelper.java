package net.minecraft.src;

class EnumOptionsHelper
{
    static final int enumOptionsMappingHelperArray[];

    static
    {
        enumOptionsMappingHelperArray = new int[EnumOptions.values().length];

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.INVERT_MOUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.VIEW_BOBBING.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.ANAGLYPH.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.ADVANCED_OPENGL.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.AMBIENT_OCCLUSION.ordinal()] = 5;
        }
        catch (NoSuchFieldError nosuchfielderror4) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.RENDER_CLOUDS.ordinal()] = 6;
        }
        catch (NoSuchFieldError nosuchfielderror5) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.CHAT_COLOR.ordinal()] = 7;
        }
        catch (NoSuchFieldError nosuchfielderror6) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.CHAT_LINKS.ordinal()] = 8;
        }
        catch (NoSuchFieldError nosuchfielderror7) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.CHAT_LINKS_PROMPT.ordinal()] = 9;
        }
        catch (NoSuchFieldError nosuchfielderror8) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.USE_SERVER_TEXTURES.ordinal()] = 10;
        }
        catch (NoSuchFieldError nosuchfielderror9) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.SNOOPER_ENABLED.ordinal()] = 11;
        }
        catch (NoSuchFieldError nosuchfielderror10) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.USE_FULLSCREEN.ordinal()] = 12;
        }
        catch (NoSuchFieldError nosuchfielderror11) { }

        try
        {
            enumOptionsMappingHelperArray[EnumOptions.ENABLE_VSYNC.ordinal()] = 13;
        }
        catch (NoSuchFieldError nosuchfielderror12) { }
    }
}
