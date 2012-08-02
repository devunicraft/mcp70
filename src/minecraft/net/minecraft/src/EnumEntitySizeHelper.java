package net.minecraft.src;

class EnumEntitySizeHelper
{
    static final int field_75628_a[];

    static
    {
        field_75628_a = new int[EnumEntitySize.values().length];

        try
        {
            field_75628_a[EnumEntitySize.SIZE_1.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) { }

        try
        {
            field_75628_a[EnumEntitySize.SIZE_2.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) { }

        try
        {
            field_75628_a[EnumEntitySize.SIZE_3.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) { }

        try
        {
            field_75628_a[EnumEntitySize.SIZE_4.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) { }

        try
        {
            field_75628_a[EnumEntitySize.SIZE_5.ordinal()] = 5;
        }
        catch (NoSuchFieldError nosuchfielderror4) { }

        try
        {
            field_75628_a[EnumEntitySize.SIZE_6.ordinal()] = 6;
        }
        catch (NoSuchFieldError nosuchfielderror5) { }
    }
}
