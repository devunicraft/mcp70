package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

public class CallableModded implements Callable
{
    /** Gets if Minecraft is Modded. */
    final Minecraft minecraftModded;

    public CallableModded(Minecraft par1Minecraft)
    {
        minecraftModded = par1Minecraft;
    }

    public String func_74415_a()
    {
        String s = ClientBrandRetriever.getClientModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }

        if ((net.minecraft.client.Minecraft.class).getClassLoader().getResource("META-INF/MOJANG_C.DSA") == null)
        {
            return "Very likely";
        }
        else
        {
            return "Probably not";
        }
    }

    public Object call()
    {
        return func_74415_a();
    }
}
