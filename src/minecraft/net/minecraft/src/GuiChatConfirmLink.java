package net.minecraft.src;

class GuiChatConfirmLink extends GuiConfirmOpenLink
{
    final ChatClickData field_73949_a;
    final GuiChat field_73948_b;

    GuiChatConfirmLink(GuiChat par1GuiChat, GuiScreen par2GuiScreen, String par3Str, int par4, ChatClickData par5ChatClickData)
    {
        super(par2GuiScreen, par3Str, par4);
        field_73948_b = par1GuiChat;
        field_73949_a = par5ChatClickData;
    }

    public void func_73945_e()
    {
        setClipboardString(field_73949_a.func_78309_f());
    }
}
