package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class RenderEngine
{
    private HashMap textureMap;

    /** Texture contents map (key: texture name, value: int[] contents) */
    private HashMap textureContentsMap;

    /** A mapping from GL texture names (integers) to BufferedImage instances */
    private IntHashMap textureNameToImageMap;

    /** An IntBuffer storing 1 int used as scratch space in RenderEngine */
    private IntBuffer singleIntBuffer;

    /** Stores the image data for the texture. */
    private ByteBuffer imageData;
    private java.util.List textureList;

    /** A mapping from image URLs to ThreadDownloadImageData instances */
    private Map urlToImageDataMap;

    /** Reference to the GameSettings object */
    private GameSettings options;

    /** Flag set when a texture should not be repeated */
    public boolean clampTexture;

    /** Flag set when a texture should use blurry resizing */
    public boolean blurTexture;

    /** Texture pack */
    private TexturePackList texturePack;

    /** Missing texture image */
    private BufferedImage missingTextureImage;

    public RenderEngine(TexturePackList par1TexturePackList, GameSettings par2GameSettings)
    {
        textureMap = new HashMap();
        textureContentsMap = new HashMap();
        textureNameToImageMap = new IntHashMap();
        singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
        imageData = GLAllocation.createDirectByteBuffer(0x1000000);
        textureList = new ArrayList();
        urlToImageDataMap = new HashMap();
        clampTexture = false;
        blurTexture = false;
        missingTextureImage = new BufferedImage(64, 64, 2);
        texturePack = par1TexturePackList;
        options = par2GameSettings;
        Graphics g = missingTextureImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.BLACK);
        g.drawString("missingtex", 1, 10);
        g.dispose();
    }

    public int[] getTextureContents(String par1Str)
    {
        TexturePackBase texturepackbase = texturePack.func_77292_e();
        int ai[] = (int[])textureContentsMap.get(par1Str);

        if (ai != null)
        {
            return ai;
        }

        try
        {
            int ai1[] = null;

            if (par1Str.startsWith("##"))
            {
                ai1 = getImageContentsAndAllocate(unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(2)))));
            }
            else if (par1Str.startsWith("%clamp%"))
            {
                clampTexture = true;
                ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(7))));
                clampTexture = false;
            }
            else if (par1Str.startsWith("%blur%"))
            {
                blurTexture = true;
                clampTexture = true;
                ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(6))));
                clampTexture = false;
                blurTexture = false;
            }
            else
            {
                InputStream inputstream = texturepackbase.getResourceAsStream(par1Str);

                if (inputstream == null)
                {
                    ai1 = getImageContentsAndAllocate(missingTextureImage);
                }
                else
                {
                    ai1 = getImageContentsAndAllocate(readTextureImage(inputstream));
                }
            }

            textureContentsMap.put(par1Str, ai1);
            return ai1;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        int ai2[] = getImageContentsAndAllocate(missingTextureImage);
        textureContentsMap.put(par1Str, ai2);
        return ai2;
    }

    private int[] getImageContentsAndAllocate(BufferedImage par1BufferedImage)
    {
        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        int ai[] = new int[i * j];
        par1BufferedImage.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    private int[] getImageContents(BufferedImage par1BufferedImage, int par2ArrayOfInteger[])
    {
        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        par1BufferedImage.getRGB(0, 0, i, j, par2ArrayOfInteger, 0, i);
        return par2ArrayOfInteger;
    }

    public int getTexture(String par1Str)
    {
        Object obj = (Integer)textureMap.get(par1Str);

        if (obj != null)
        {
            return ((Integer)(obj)).intValue();
        }

        obj = texturePack.func_77292_e();

        try
        {
            singleIntBuffer.clear();
            GLAllocation.generateTextureNames(singleIntBuffer);
            int i = singleIntBuffer.get(0);

            if (par1Str.startsWith("##"))
            {
                setupTexture(unwrapImageByColumns(readTextureImage(((TexturePackBase)(obj)).getResourceAsStream(par1Str.substring(2)))), i);
            }
            else if (par1Str.startsWith("%clamp%"))
            {
                clampTexture = true;
                setupTexture(readTextureImage(((TexturePackBase)(obj)).getResourceAsStream(par1Str.substring(7))), i);
                clampTexture = false;
            }
            else if (par1Str.startsWith("%blur%"))
            {
                blurTexture = true;
                setupTexture(readTextureImage(((TexturePackBase)(obj)).getResourceAsStream(par1Str.substring(6))), i);
                blurTexture = false;
            }
            else if (par1Str.startsWith("%blurclamp%"))
            {
                blurTexture = true;
                clampTexture = true;
                setupTexture(readTextureImage(((TexturePackBase)(obj)).getResourceAsStream(par1Str.substring(11))), i);
                blurTexture = false;
                clampTexture = false;
            }
            else
            {
                InputStream inputstream = ((TexturePackBase)(obj)).getResourceAsStream(par1Str);

                if (inputstream == null)
                {
                    setupTexture(missingTextureImage, i);
                }
                else
                {
                    setupTexture(readTextureImage(inputstream), i);
                }
            }

            textureMap.put(par1Str, Integer.valueOf(i));
            return i;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        GLAllocation.generateTextureNames(singleIntBuffer);
        int j = singleIntBuffer.get(0);
        setupTexture(missingTextureImage, j);
        textureMap.put(par1Str, Integer.valueOf(j));
        return j;
    }

    /**
     * Takes an image with multiple 16-pixel-wide columns and creates a new 16-pixel-wide image where the columns are
     * stacked vertically
     */
    private BufferedImage unwrapImageByColumns(BufferedImage par1BufferedImage)
    {
        int i = par1BufferedImage.getWidth() / 16;
        BufferedImage bufferedimage = new BufferedImage(16, par1BufferedImage.getHeight() * i, 2);
        Graphics g = bufferedimage.getGraphics();

        for (int j = 0; j < i; j++)
        {
            g.drawImage(par1BufferedImage, -j * 16, j * par1BufferedImage.getHeight(), null);
        }

        g.dispose();
        return bufferedimage;
    }

    /**
     * Copy the supplied image onto a newly-allocated OpenGL texture, returning the allocated texture name
     */
    public int allocateAndSetupTexture(BufferedImage par1BufferedImage)
    {
        singleIntBuffer.clear();
        GLAllocation.generateTextureNames(singleIntBuffer);
        int i = singleIntBuffer.get(0);
        setupTexture(par1BufferedImage, i);
        textureNameToImageMap.addKey(i, par1BufferedImage);
        return i;
    }

    /**
     * Copy the supplied image onto the specified OpenGL texture
     */
    public void setupTexture(BufferedImage par1BufferedImage, int par2)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par2);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        if (blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        int ai[] = new int[i * j];
        byte abyte0[] = new byte[i * j * 4];
        par1BufferedImage.getRGB(0, 0, i, j, ai, 0, i);

        for (int k = 0; k < ai.length; k++)
        {
            int l = ai[k] >> 24 & 0xff;
            int i1 = ai[k] >> 16 & 0xff;
            int j1 = ai[k] >> 8 & 0xff;
            int k1 = ai[k] & 0xff;

            if (options != null && options.anaglyph)
            {
                int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
                int i2 = (i1 * 30 + j1 * 70) / 100;
                int j2 = (i1 * 30 + k1 * 70) / 100;
                i1 = l1;
                j1 = i2;
                k1 = j2;
            }

            abyte0[k * 4 + 0] = (byte)i1;
            abyte0[k * 4 + 1] = (byte)j1;
            abyte0[k * 4 + 2] = (byte)k1;
            abyte0[k * 4 + 3] = (byte)l;
        }

        imageData.clear();
        imageData.put(abyte0);
        imageData.position(0).limit(abyte0.length);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i, j, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
    }

    public void createTextureFromBytes(int par1ArrayOfInteger[], int par2, int par3, int par4)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par4);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        if (blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        byte abyte0[] = new byte[par2 * par3 * 4];

        for (int i = 0; i < par1ArrayOfInteger.length; i++)
        {
            int j = par1ArrayOfInteger[i] >> 24 & 0xff;
            int k = par1ArrayOfInteger[i] >> 16 & 0xff;
            int l = par1ArrayOfInteger[i] >> 8 & 0xff;
            int i1 = par1ArrayOfInteger[i] & 0xff;

            if (options != null && options.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            abyte0[i * 4 + 0] = (byte)k;
            abyte0[i * 4 + 1] = (byte)l;
            abyte0[i * 4 + 2] = (byte)i1;
            abyte0[i * 4 + 3] = (byte)j;
        }

        imageData.clear();
        imageData.put(abyte0);
        imageData.position(0).limit(abyte0.length);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, par2, par3, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
    }

    /**
     * Deletes a single GL texture
     */
    public void deleteTexture(int par1)
    {
        textureNameToImageMap.removeObject(par1);
        singleIntBuffer.clear();
        singleIntBuffer.put(par1);
        singleIntBuffer.flip();
        GL11.glDeleteTextures(singleIntBuffer);
    }

    /**
     * Takes a URL of a downloadable image and the name of the local image to be used as a fallback.  If the image has
     * been downloaded, returns the GL texture of the downloaded image, otherwise returns the GL texture of the fallback
     * image.
     */
    public int getTextureForDownloadableImage(String par1Str, String par2Str)
    {
        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata != null && threaddownloadimagedata.image != null && !threaddownloadimagedata.textureSetupComplete)
        {
            if (threaddownloadimagedata.textureName < 0)
            {
                threaddownloadimagedata.textureName = allocateAndSetupTexture(threaddownloadimagedata.image);
            }
            else
            {
                setupTexture(threaddownloadimagedata.image, threaddownloadimagedata.textureName);
            }

            threaddownloadimagedata.textureSetupComplete = true;
        }

        if (threaddownloadimagedata == null || threaddownloadimagedata.textureName < 0)
        {
            if (par2Str == null)
            {
                return -1;
            }
            else
            {
                return getTexture(par2Str);
            }
        }
        else
        {
            return threaddownloadimagedata.textureName;
        }
    }

    /**
     * Return a ThreadDownloadImageData instance for the given URL.  If it does not already exist, it is created and
     * uses the passed ImageBuffer.  If it does, its reference count is incremented.
     */
    public ThreadDownloadImageData obtainImageData(String par1Str, ImageBuffer par2ImageBuffer)
    {
        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata == null)
        {
            urlToImageDataMap.put(par1Str, new ThreadDownloadImageData(par1Str, par2ImageBuffer));
        }
        else
        {
            threaddownloadimagedata.referenceCount++;
        }

        return threaddownloadimagedata;
    }

    /**
     * Decrements the reference count for a given URL, deleting the image data if the reference count hits 0
     */
    public void releaseImageData(String par1Str)
    {
        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata != null)
        {
            threaddownloadimagedata.referenceCount--;

            if (threaddownloadimagedata.referenceCount == 0)
            {
                if (threaddownloadimagedata.textureName >= 0)
                {
                    deleteTexture(threaddownloadimagedata.textureName);
                }

                urlToImageDataMap.remove(par1Str);
            }
        }
    }

    public void registerTextureFX(TextureFX par1TextureFX)
    {
        textureList.add(par1TextureFX);
        par1TextureFX.onTick();
    }

    public void updateDynamicTextures()
    {
        int i = -1;

        for (Iterator iterator = textureList.iterator(); iterator.hasNext();)
        {
            TextureFX texturefx = (TextureFX)iterator.next();
            texturefx.anaglyphEnabled = options.anaglyph;
            texturefx.onTick();
            imageData.clear();
            imageData.put(texturefx.imageData);
            imageData.position(0).limit(texturefx.imageData.length);

            if (texturefx.iconIndex != i)
            {
                texturefx.bindImage(this);
                i = texturefx.iconIndex;
            }

            int j = 0;

            while (j < texturefx.tileSize)
            {
                for (int k = 0; k < texturefx.tileSize; k++)
                {
                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, (texturefx.iconIndex % 16) * 16 + j * 16, (texturefx.iconIndex / 16) * 16 + k * 16, 16, 16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
                }

                j++;
            }
        }
    }

    /**
     * Call setupTexture on all currently-loaded textures again to account for changes in rendering options
     */
    public void refreshTextures()
    {
        TexturePackBase texturepackbase = texturePack.func_77292_e();
        int i;
        BufferedImage bufferedimage;

        for (Iterator iterator = textureNameToImageMap.getKeySet().iterator(); iterator.hasNext(); setupTexture(bufferedimage, i))
        {
            i = ((Integer)iterator.next()).intValue();
            bufferedimage = (BufferedImage)textureNameToImageMap.lookup(i);
        }

        for (Iterator iterator1 = urlToImageDataMap.values().iterator(); iterator1.hasNext();)
        {
            ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)iterator1.next();
            threaddownloadimagedata.textureSetupComplete = false;
        }

        for (Iterator iterator2 = textureMap.keySet().iterator(); iterator2.hasNext();)
        {
            String s = (String)iterator2.next();

            try
            {
                BufferedImage bufferedimage1;

                if (s.startsWith("##"))
                {
                    bufferedimage1 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s.substring(2))));
                }
                else if (s.startsWith("%clamp%"))
                {
                    clampTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(7)));
                }
                else if (s.startsWith("%blur%"))
                {
                    blurTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(6)));
                }
                else if (s.startsWith("%blurclamp%"))
                {
                    blurTexture = true;
                    clampTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(11)));
                }
                else
                {
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s));
                }

                int j = ((Integer)textureMap.get(s)).intValue();
                setupTexture(bufferedimage1, j);
                blurTexture = false;
                clampTexture = false;
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }

        for (Iterator iterator3 = textureContentsMap.keySet().iterator(); iterator3.hasNext();)
        {
            String s1 = (String)iterator3.next();

            try
            {
                BufferedImage bufferedimage2;

                if (s1.startsWith("##"))
                {
                    bufferedimage2 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s1.substring(2))));
                }
                else if (s1.startsWith("%clamp%"))
                {
                    clampTexture = true;
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(7)));
                }
                else if (s1.startsWith("%blur%"))
                {
                    blurTexture = true;
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(6)));
                }
                else
                {
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1));
                }

                getImageContents(bufferedimage2, (int[])textureContentsMap.get(s1));
                blurTexture = false;
                clampTexture = false;
            }
            catch (IOException ioexception1)
            {
                ioexception1.printStackTrace();
            }
        }
    }

    /**
     * Returns a BufferedImage read off the provided input stream.  Args: inputStream
     */
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return bufferedimage;
    }

    public void bindTexture(int par1)
    {
        if (par1 < 0)
        {
            return;
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1);
            return;
        }
    }
}
