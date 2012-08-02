package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerTp extends CommandBase
{
    public CommandServerTp()
    {
    }

    public String getCommandName()
    {
        return "tp";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.tp.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            MinecraftServer minecraftserver = MinecraftServer.func_71276_C();
            EntityPlayerMP entityplayermp;

            if (par2ArrayOfStr.length == 2 || par2ArrayOfStr.length == 4)
            {
                entityplayermp = minecraftserver.func_71203_ab().func_72361_f(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException();
                }
            }
            else
            {
                entityplayermp = (EntityPlayerMP)func_71521_c(par1ICommandSender);
            }

            if (par2ArrayOfStr.length == 3 || par2ArrayOfStr.length == 4)
            {
                if (entityplayermp.worldObj != null)
                {
                    int i = par2ArrayOfStr.length - 3;
                    int j = 0x1c9c380;
                    int k = func_71532_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    int l = func_71532_a(par1ICommandSender, par2ArrayOfStr[i++], 0, 256);
                    int i1 = func_71532_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    entityplayermp.setPositionAndUpdate((float)k + 0.5F, l, (float)i1 + 0.5F);
                    func_71522_a(par1ICommandSender, "commands.tp.coordinates", new Object[]
                            {
                                entityplayermp.func_70023_ak(), Integer.valueOf(k), Integer.valueOf(l), Integer.valueOf(i1)
                            });
                }
            }
            else if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
            {
                EntityPlayerMP entityplayermp1 = minecraftserver.func_71203_ab().func_72361_f(par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                if (entityplayermp1 == null)
                {
                    throw new PlayerNotFoundException();
                }

                entityplayermp.serverForThisPlayer.setPlayerLocation(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
                func_71522_a(par1ICommandSender, "commands.tp.success", new Object[]
                        {
                            entityplayermp.func_70023_ak(), entityplayermp1.func_70023_ak()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
        {
            return func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z());
        }
        else
        {
            return null;
        }
    }
}
