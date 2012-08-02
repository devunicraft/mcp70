package net.minecraft.src;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatClickData
{
    public static final Pattern pattern = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,3})(/\\S*)?$");
    private final FontRenderer fontR;
    private final ChatLine line;
    private final int field_78312_d;
    private final int field_78313_e;
    private final String field_78310_f;
    private final String field_78311_g;

    public ChatClickData(FontRenderer par1FontRenderer, ChatLine par2ChatLine, int par3, int par4)
    {
        fontR = par1FontRenderer;
        line = par2ChatLine;
        field_78312_d = par3;
        field_78313_e = par4;
        field_78310_f = par1FontRenderer.trimStringToWidth(par2ChatLine.func_74538_a(), par3);
        field_78311_g = func_78307_h();
    }

    public String func_78309_f()
    {
        return field_78311_g;
    }

    /**
     * computes the URI from the clicked chat data object
     */
    public URI getURI()
    {
        String s = func_78309_f();

        if (s == null)
        {
            return null;
        }

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches())
        {
            try
            {
                String s1 = matcher.group(0);

                if (matcher.group(1) == null)
                {
                    s1 = (new StringBuilder()).append("http://").append(s1).toString();
                }

                return new URI(s1);
            }
            catch (URISyntaxException urisyntaxexception)
            {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Couldn't create URI from chat", urisyntaxexception);
            }
        }

        return null;
    }

    private String func_78307_h()
    {
        int i = field_78310_f.lastIndexOf(" ", field_78310_f.length()) + 1;

        if (i < 0)
        {
            i = 0;
        }

        int j = line.func_74538_a().indexOf(" ", i);

        if (j < 0)
        {
            j = line.func_74538_a().length();
        }

        return StringUtils.func_76338_a(line.func_74538_a().substring(i, j));
    }
}
