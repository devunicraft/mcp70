package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public abstract class TexturePackImplementation implements TexturePackBase
{
    private final String field_77545_e;
    private final String field_77542_f;
    protected final File field_77548_a;
    protected String field_77546_b;
    protected String field_77547_c;
    protected BufferedImage field_77544_d;
    private int field_77543_g;

    protected TexturePackImplementation(String par1Str, String par2Str)
    {
        this(par1Str, null, par2Str);
    }

    protected TexturePackImplementation(String par1Str, File par2File, String par3Str)
    {
        field_77543_g = -1;
        field_77545_e = par1Str;
        field_77542_f = par3Str;
        field_77548_a = par2File;
        func_77539_g();
        func_77540_a();
    }

    private static String func_77541_b(String par0Str)
    {
        if (par0Str != null && par0Str.length() > 34)
        {
            par0Str = par0Str.substring(0, 34);
        }

        return par0Str;
    }

    private void func_77539_g()
    {
        InputStream inputstream = null;

        try
        {
            inputstream = getResourceAsStream("/pack.png");
            field_77544_d = ImageIO.read(inputstream);
        }
        catch (IOException ioexception) { }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch (IOException ioexception1) { }
        }
    }

    protected void func_77540_a()
    {
        InputStream inputstream = null;
        BufferedReader bufferedreader = null;

        try
        {
            inputstream = getResourceAsStream("/pack.txt");
            bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            field_77546_b = func_77541_b(bufferedreader.readLine());
            field_77547_c = func_77541_b(bufferedreader.readLine());
        }
        catch (IOException ioexception) { }
        finally
        {
            try
            {
                bufferedreader.close();
                inputstream.close();
            }
            catch (IOException ioexception1) { }
        }
    }

    public void func_77533_a(RenderEngine par1RenderEngine)
    {
        if (field_77544_d != null && field_77543_g != -1)
        {
            par1RenderEngine.deleteTexture(field_77543_g);
        }
    }

    public void func_77535_b(RenderEngine par1RenderEngine)
    {
        if (field_77544_d != null)
        {
            if (field_77543_g == -1)
            {
                field_77543_g = par1RenderEngine.allocateAndSetupTexture(field_77544_d);
            }

            par1RenderEngine.bindTexture(field_77543_g);
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1RenderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
    }

    public String func_77536_b()
    {
        return field_77545_e;
    }

    public String func_77538_c()
    {
        return field_77542_f;
    }

    public String func_77531_d()
    {
        return field_77546_b;
    }

    public String func_77537_e()
    {
        return field_77547_c;
    }

    public int func_77534_f()
    {
        return 16;
    }
}
