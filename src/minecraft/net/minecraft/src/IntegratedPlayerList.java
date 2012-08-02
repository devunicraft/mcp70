package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound field_72416_e;

    public IntegratedPlayerList(IntegratedServer par1IntegratedServer)
    {
        super(par1IntegratedServer);
        field_72416_e = null;
        viewDistance = 10;
    }

    protected void func_72391_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP.func_70005_c_().equals(func_72415_s().func_71214_G()))
        {
            field_72416_e = new NBTTagCompound();
            par1EntityPlayerMP.writeToNBT(field_72416_e);
        }

        super.func_72391_b(par1EntityPlayerMP);
    }

    public IntegratedServer func_72415_s()
    {
        return (IntegratedServer)super.func_72365_p();
    }

    public NBTTagCompound func_72378_q()
    {
        return field_72416_e;
    }

    public MinecraftServer func_72365_p()
    {
        return func_72415_s();
    }
}
