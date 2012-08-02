package net.minecraft.src;

public class ServerData
{
    public String field_78847_a;
    public String field_78845_b;
    public String field_78846_c;
    public String field_78843_d;
    public long field_78844_e;
    public boolean field_78841_f;
    private boolean field_78842_g;
    private boolean field_78848_h;

    public ServerData(String par1Str, String par2Str)
    {
        field_78841_f = false;
        field_78842_g = true;
        field_78848_h = false;
        field_78847_a = par1Str;
        field_78845_b = par2Str;
    }

    public NBTTagCompound func_78836_a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("name", field_78847_a);
        nbttagcompound.setString("ip", field_78845_b);

        if (!field_78842_g)
        {
            nbttagcompound.setBoolean("acceptTextures", field_78848_h);
        }

        return nbttagcompound;
    }

    public boolean func_78839_b()
    {
        return field_78848_h;
    }

    public boolean func_78840_c()
    {
        return field_78842_g;
    }

    public void func_78838_a(boolean par1)
    {
        field_78848_h = par1;
        field_78842_g = false;
    }

    public static ServerData func_78837_a(NBTTagCompound par0NBTTagCompound)
    {
        ServerData serverdata = new ServerData(par0NBTTagCompound.getString("name"), par0NBTTagCompound.getString("ip"));

        if (par0NBTTagCompound.hasKey("acceptTextures"))
        {
            serverdata.func_78838_a(par0NBTTagCompound.getBoolean("acceptTextures"));
        }

        return serverdata;
    }
}
