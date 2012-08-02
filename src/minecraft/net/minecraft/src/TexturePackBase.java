package net.minecraft.src;

import java.io.InputStream;

public interface TexturePackBase
{
    public abstract void func_77533_a(RenderEngine renderengine);

    public abstract void func_77535_b(RenderEngine renderengine);

    /**
     * Gives a texture resource as InputStream.
     */
    public abstract InputStream getResourceAsStream(String s);

    public abstract String func_77536_b();

    public abstract String func_77538_c();

    public abstract String func_77531_d();

    public abstract String func_77537_e();

    public abstract int func_77534_f();
}
