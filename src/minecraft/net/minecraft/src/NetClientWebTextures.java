package net.minecraft.src;

import net.minecraft.client.Minecraft;

class NetClientWebTextures extends GuiScreen
{
    final String field_74244_a;
    final NetClientHandler field_74243_b;

    NetClientWebTextures(NetClientHandler par1NetClientHandler, String par2Str)
    {
        field_74243_b = par1NetClientHandler;
        field_74244_a = par2Str;
    }

    public void confirmClicked(boolean par1, int par2)
    {
        mc = Minecraft.getMinecraft();

        if (mc.func_71362_z() != null)
        {
            mc.func_71362_z().func_78838_a(par1);
            ServerList.func_78852_b(mc.func_71362_z());
        }

        if (par1)
        {
            mc.texturePackList.func_77296_a(field_74244_a);
        }

        mc.displayGuiScreen(null);
    }
}
