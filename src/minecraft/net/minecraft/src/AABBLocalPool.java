package net.minecraft.src;

final class AABBLocalPool extends ThreadLocal
{
    AABBLocalPool()
    {
    }

    protected AABBPool func_72341_a()
    {
        return new AABBPool(300, 2000);
    }

    protected Object initialValue()
    {
        return func_72341_a();
    }
}
