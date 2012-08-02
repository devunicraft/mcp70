package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class CFBBlockCipher implements BlockCipher
{
    private byte field_71814_a[];
    private byte field_71812_b[];
    private byte field_71813_c[];
    private int field_71810_d;
    private BlockCipher field_71811_e;
    private boolean field_71809_f;

    public CFBBlockCipher(BlockCipher par1BlockCipher, int par2)
    {
        field_71811_e = null;
        field_71811_e = par1BlockCipher;
        field_71810_d = par2 / 8;
        field_71814_a = new byte[par1BlockCipher.func_71804_b()];
        field_71812_b = new byte[par1BlockCipher.func_71804_b()];
        field_71813_c = new byte[par1BlockCipher.func_71804_b()];
    }

    public void func_71805_a(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_71809_f = par1;

        if (par2CipherParameters instanceof ParametersWithIV)
        {
            ParametersWithIV parameterswithiv = (ParametersWithIV)par2CipherParameters;
            byte abyte0[] = parameterswithiv.func_71779_a();

            if (abyte0.length < field_71814_a.length)
            {
                System.arraycopy(abyte0, 0, field_71814_a, field_71814_a.length - abyte0.length, abyte0.length);

                for (int i = 0; i < field_71814_a.length - abyte0.length; i++)
                {
                    field_71814_a[i] = 0;
                }
            }
            else
            {
                System.arraycopy(abyte0, 0, field_71814_a, 0, field_71814_a.length);
            }

            func_71803_c();

            if (parameterswithiv.func_71780_b() != null)
            {
                field_71811_e.func_71805_a(true, parameterswithiv.func_71780_b());
            }
        }
        else
        {
            func_71803_c();
            field_71811_e.func_71805_a(true, par2CipherParameters);
        }
    }

    public String func_71802_a()
    {
        return (new StringBuilder()).append(field_71811_e.func_71802_a()).append("/CFB").append(field_71810_d * 8).toString();
    }

    public int func_71804_b()
    {
        return field_71810_d;
    }

    public int func_71806_a(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        return field_71809_f ? func_71807_b(par1ArrayOfByte, par2, par3ArrayOfByte, par4) : func_71808_c(par1ArrayOfByte, par2, par3ArrayOfByte, par4);
    }

    public int func_71807_b(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_71810_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_71810_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_71811_e.func_71806_a(field_71812_b, 0, field_71813_c, 0);

        for (int i = 0; i < field_71810_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_71813_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        System.arraycopy(field_71812_b, field_71810_d, field_71812_b, 0, field_71812_b.length - field_71810_d);
        System.arraycopy(par3ArrayOfByte, par4, field_71812_b, field_71812_b.length - field_71810_d, field_71810_d);
        return field_71810_d;
    }

    public int func_71808_c(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_71810_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_71810_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_71811_e.func_71806_a(field_71812_b, 0, field_71813_c, 0);
        System.arraycopy(field_71812_b, field_71810_d, field_71812_b, 0, field_71812_b.length - field_71810_d);
        System.arraycopy(par1ArrayOfByte, par2, field_71812_b, field_71812_b.length - field_71810_d, field_71810_d);

        for (int i = 0; i < field_71810_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_71813_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        return field_71810_d;
    }

    public void func_71803_c()
    {
        System.arraycopy(field_71814_a, 0, field_71812_b, 0, field_71814_a.length);
        field_71811_e.func_71803_c();
    }
}
