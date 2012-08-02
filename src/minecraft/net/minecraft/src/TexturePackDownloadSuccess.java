package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
    final TexturePackList field_76171_a;

    TexturePackDownloadSuccess(TexturePackList par1TexturePackList)
    {
        field_76171_a = par1TexturePackList;
    }

    public void func_76170_a(File par1File)
    {
        if (!TexturePackList.func_77301_a(field_76171_a))
        {
            return;
        }
        else
        {
            TexturePackList.func_77303_a(field_76171_a, new TexturePackCustom(TexturePackList.func_77291_a(field_76171_a, par1File), par1File));
            TexturePackList.func_77306_b(field_76171_a).func_71395_y();
            return;
        }
    }
}
