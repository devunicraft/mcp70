package net.minecraft.src;

import java.io.*;

public class TexturePackFolder extends TexturePackImplementation
{
    public TexturePackFolder(String par1Str, File par2File)
    {
        super(par1Str, par2File, par2File.getName());
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        try
        {
            File file = new File(field_77548_a, par1Str.substring(1));

            if (file.exists())
            {
                return new BufferedInputStream(new FileInputStream(file));
            }
        }
        catch (IOException ioexception) { }

        return super.getResourceAsStream(par1Str);
    }
}
