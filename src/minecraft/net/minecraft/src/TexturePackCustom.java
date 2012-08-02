package net.minecraft.src;

import java.io.*;
import java.util.zip.ZipFile;

public class TexturePackCustom extends TexturePackImplementation
{
    private ZipFile field_77550_e;

    public TexturePackCustom(String par1Str, File par2File)
    {
        super(par1Str, par2File, par2File.getName());
    }

    public void func_77533_a(RenderEngine par1RenderEngine)
    {
        super.func_77533_a(par1RenderEngine);

        try
        {
            field_77550_e.close();
        }
        catch (IOException ioexception) { }

        field_77550_e = null;
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        func_77549_g();

        try
        {
            java.util.zip.ZipEntry zipentry = field_77550_e.getEntry(par1Str.substring(1));

            if (zipentry != null)
            {
                return field_77550_e.getInputStream(zipentry);
            }
        }
        catch (Exception exception) { }

        return super.getResourceAsStream(par1Str);
    }

    private void func_77549_g()
    {
        if (field_77550_e != null)
        {
            return;
        }

        try
        {
            field_77550_e = new ZipFile(field_77548_a);
        }
        catch (IOException ioexception) { }
    }
}
