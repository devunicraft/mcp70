package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ScreenShotHelper
{
    private static final DateFormat field_74295_a = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static IntBuffer field_74293_b;
    private static int field_74294_c[];

    /**
     * Takes a screenshot and saves it to the screenshots directory. Returns the filename of the screenshot.
     */
    public static String saveScreenshot(File par0File, int par1, int par2)
    {
        return func_74292_a(par0File, null, par1, par2);
    }

    public static String func_74292_a(File par0File, String par1Str, int par2, int par3)
    {
        try
        {
            File file = new File(par0File, "screenshots");
            file.mkdir();
            int i = par2 * par3;

            if (field_74293_b == null || field_74293_b.capacity() < i)
            {
                field_74293_b = BufferUtils.createIntBuffer(i);
                field_74294_c = new int[i];
            }

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            field_74293_b.clear();
            GL11.glReadPixels(0, 0, par2, par3, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, field_74293_b);
            field_74293_b.get(field_74294_c);
            func_74289_a(field_74294_c, par2, par3);
            BufferedImage bufferedimage = new BufferedImage(par2, par3, 1);
            bufferedimage.setRGB(0, 0, par2, par3, field_74294_c, 0, par2);
            File file1;

            if (par1Str == null)
            {
                file1 = func_74290_a(file);
            }
            else
            {
                file1 = new File(file, par1Str);
            }

            ImageIO.write(bufferedimage, "png", file1);
            return (new StringBuilder()).append("Saved screenshot as ").append(file1.getName()).toString();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return (new StringBuilder()).append("Failed to save: ").append(exception).toString();
        }
    }

    private static File func_74290_a(File par0File)
    {
        String s = field_74295_a.format(new Date()).toString();
        int i = 1;

        do
        {
            File file = new File(par0File, (new StringBuilder()).append(s).append(i != 1 ? (new StringBuilder()).append("_").append(i).toString() : "").append(".png").toString());

            if (!file.exists())
            {
                return file;
            }

            i++;
        }
        while (true);
    }

    private static void func_74289_a(int par0ArrayOfInteger[], int par1, int par2)
    {
        int ai[] = new int[par1];
        int i = par2 / 2;

        for (int j = 0; j < i; j++)
        {
            System.arraycopy(par0ArrayOfInteger, j * par1, ai, 0, par1);
            System.arraycopy(par0ArrayOfInteger, (par2 - 1 - j) * par1, par0ArrayOfInteger, j * par1, par1);
            System.arraycopy(ai, 0, par0ArrayOfInteger, (par2 - 1 - j) * par1, par1);
        }
    }
}
