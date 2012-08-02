package org.bouncycastle.crypto;

public class BufferedBlockCipher
{
    protected byte field_71801_a[];
    protected int field_71799_b;
    protected boolean field_71800_c;
    protected BlockCipher field_71797_d;
    protected boolean field_71798_e;
    protected boolean field_71796_f;

    protected BufferedBlockCipher()
    {
    }

    public BufferedBlockCipher(BlockCipher par1BlockCipher)
    {
        field_71797_d = par1BlockCipher;
        field_71801_a = new byte[par1BlockCipher.func_71804_b()];
        field_71799_b = 0;
        String s = par1BlockCipher.func_71802_a();
        int i = s.indexOf('/') + 1;
        field_71796_f = i > 0 && s.startsWith("PGP", i);

        if (field_71796_f)
        {
            field_71798_e = true;
        }
        else
        {
            field_71798_e = i > 0 && (s.startsWith("CFB", i) || s.startsWith("OFB", i) || s.startsWith("OpenPGP", i) || s.startsWith("SIC", i) || s.startsWith("GCTR", i));
        }
    }

    public void init(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_71800_c = par1;
        func_71794_b();
        field_71797_d.func_71805_a(par1, par2CipherParameters);
    }

    public int func_71792_a()
    {
        return field_71797_d.func_71804_b();
    }

    public int func_71793_a(int par1)
    {
        int i = par1 + field_71799_b;
        int j;

        if (field_71796_f)
        {
            j = i % field_71801_a.length - (field_71797_d.func_71804_b() + 2);
        }
        else
        {
            j = i % field_71801_a.length;
        }

        return i - j;
    }

    public int func_71789_b(int par1)
    {
        return par1 + field_71799_b;
    }

    public int func_71791_a(byte par1ArrayOfByte[], int par2, int par3, byte par4ArrayOfByte[], int par5) throws DataLengthException, IllegalStateException
    {
        if (par3 < 0)
        {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }

        int i = func_71792_a();
        int j = func_71793_a(par3);

        if (j > 0 && par5 + j > par4ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        int k = 0;
        int l = field_71801_a.length - field_71799_b;

        if (par3 > l)
        {
            System.arraycopy(par1ArrayOfByte, par2, field_71801_a, field_71799_b, l);
            k += field_71797_d.func_71806_a(field_71801_a, 0, par4ArrayOfByte, par5);
            field_71799_b = 0;
            par3 -= l;

            for (par2 += l; par3 > field_71801_a.length; par2 += i)
            {
                k += field_71797_d.func_71806_a(par1ArrayOfByte, par2, par4ArrayOfByte, par5 + k);
                par3 -= i;
            }
        }

        System.arraycopy(par1ArrayOfByte, par2, field_71801_a, field_71799_b, par3);
        field_71799_b += par3;

        if (field_71799_b == field_71801_a.length)
        {
            k += field_71797_d.func_71806_a(field_71801_a, 0, par4ArrayOfByte, par5 + k);
            field_71799_b = 0;
        }

        return k;
    }

    public int func_71790_a(byte par1ArrayOfByte[], int par2) throws DataLengthException, IllegalStateException
    {
        try
        {
            int i = 0;

            if (par2 + field_71799_b > par1ArrayOfByte.length)
            {
                throw new DataLengthException("output buffer too short for doFinal()");
            }

            if (field_71799_b != 0)
            {
                if (!field_71798_e)
                {
                    throw new DataLengthException("data not block size aligned");
                }

                field_71797_d.func_71806_a(field_71801_a, 0, field_71801_a, 0);
                i = field_71799_b;
                field_71799_b = 0;
                System.arraycopy(field_71801_a, 0, par1ArrayOfByte, par2, i);
            }

            int j = i;
            return j;
        }
        finally
        {
            func_71794_b();
        }
    }

    public void func_71794_b()
    {
        for (int i = 0; i < field_71801_a.length; i++)
        {
            field_71801_a[i] = 0;
        }

        field_71799_b = 0;
        field_71797_d.func_71803_c();
    }
}
