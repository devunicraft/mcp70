package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    public ServerCommandManager()
    {
        func_71560_a(new CommandTime());
        func_71560_a(new CommandGameMode());
        func_71560_a(new CommandDefaultGameMode());
        func_71560_a(new CommandKill());
        func_71560_a(new CommandToggleDownfall());
        func_71560_a(new CommandXP());
        func_71560_a(new CommandServerTp());
        func_71560_a(new CommandGive());
        func_71560_a(new CommandServerEmote());
        func_71560_a(new CommandShowSeed());
        func_71560_a(new CommandHelp());
        func_71560_a(new CommandDebug());

        if (MinecraftServer.func_71276_C().isDedicatedServer())
        {
            func_71560_a(new CommandServerOp());
            func_71560_a(new CommandServerDeop());
            func_71560_a(new CommandServerStop());
            func_71560_a(new CommandServerSaveAll());
            func_71560_a(new CommandServerSaveOff());
            func_71560_a(new CommandServerSaveOn());
            func_71560_a(new CommandServerBanIp());
            func_71560_a(new CommandServerPardonIp());
            func_71560_a(new CommandServerBan());
            func_71560_a(new CommandServerBanlist());
            func_71560_a(new CommandServerPardon());
            func_71560_a(new CommandServerKick());
            func_71560_a(new CommandServerList());
            func_71560_a(new CommandServerSay());
            func_71560_a(new CommandServerWhitelist());
        }
        else
        {
            func_71560_a(new CommandServerPublishLocal());
        }

        CommandBase.func_71529_a(this);
    }

    public void func_71563_a(ICommandSender par1ICommandSender, int par2, String par3Str, Object par4ArrayOfObj[])
    {
        Iterator iterator = MinecraftServer.func_71276_C().func_71203_ab().playerList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1ICommandSender && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(((EntityPlayer)(entityplayermp)).username))
            {
                entityplayermp.sendChatToPlayer((new StringBuilder()).append("\2477\247o[").append(par1ICommandSender.func_70005_c_()).append(": ").append(entityplayermp.func_70004_a(par3Str, par4ArrayOfObj)).append("]").toString());
            }
        }
        while (true);

        if (par1ICommandSender != MinecraftServer.func_71276_C())
        {
            MinecraftServer.minecraftLogger.info((new StringBuilder()).append("[").append(par1ICommandSender.func_70005_c_()).append(": ").append(MinecraftServer.func_71276_C().func_70004_a(par3Str, par4ArrayOfObj)).append("]").toString());
        }

        if ((par2 & 1) != 1)
        {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.func_70004_a(par3Str, par4ArrayOfObj));
        }
    }
}
