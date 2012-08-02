package org.bouncycastle.crypto.io;

import java.io.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;

public class CipherOutputStream extends FilterOutputStream
{
    private BufferedBlockCipher field_74849_a;
    private StreamCipher field_74847_b;
    private byte field_74848_c[];
    private byte field_74846_d[];

    public CipherOutputStream(OutputStream par1OutputStream, BufferedBlockCipher par2BufferedBlockCipher)
    {
        super(par1OutputStream);
        field_74848_c = new byte[1];
        field_74849_a = par2BufferedBlockCipher;
        field_74846_d = new byte[par2BufferedBlockCipher.func_71792_a()];
    }

    public void write(int par1) throws IOException
    {
        field_74848_c[0] = (byte)par1;

        if (field_74849_a != null)
        {
            int i = field_74849_a.func_71791_a(field_74848_c, 0, 1, field_74846_d, 0);

            if (i != 0)
            {
                out.write(field_74846_d, 0, i);
            }
        }
        else
        {
            out.write(field_74847_b.func_74851_a((byte)par1));
        }
    }

    public void write(byte par1[]) throws IOException
    {
        write(par1, 0, par1.length);
    }

    public void write(byte par1[], int par2, int par3) throws IOException
    {
        if (field_74849_a != null)
        {
            byte abyte0[] = new byte[field_74849_a.func_71789_b(par3)];
            int i = field_74849_a.func_71791_a(par1, par2, par3, abyte0, 0);

            if (i != 0)
            {
                out.write(abyte0, 0, i);
            }
        }
        else
        {
            byte abyte1[] = new byte[par3];
            field_74847_b.func_74850_a(par1, par2, par3, abyte1, 0);
            out.write(abyte1, 0, par3);
        }
    }

    public void flush() throws IOException
    {
        super.flush();
    }

    public void close() throws IOException
    {
        try
        {
            if (field_74849_a != null)
            {
                byte abyte0[] = new byte[field_74849_a.func_71789_b(0)];
                int i = field_74849_a.func_71790_a(abyte0, 0);

                if (i != 0)
                {
                    out.write(abyte0, 0, i);
                }
            }
        }
        catch (Exception exception)
        {
            throw new IOException((new StringBuilder()).append("Error closing stream: ").append(exception.toString()).toString());
        }

        flush();
        super.close();
    }
}
