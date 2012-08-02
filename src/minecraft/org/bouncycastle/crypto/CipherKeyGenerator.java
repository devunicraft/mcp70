package org.bouncycastle.crypto;

import java.security.SecureRandom;

public class CipherKeyGenerator
{
    protected SecureRandom field_71788_a;
    protected int field_71787_b;

    public CipherKeyGenerator()
    {
    }

    public void init(KeyGenerationParameters par1)
    {
        field_71788_a = par1.func_71843_a();
        field_71787_b = (par1.func_71842_b() + 7) / 8;
    }

    public byte[] generateKey()
    {
        byte abyte0[] = new byte[field_71787_b];
        field_71788_a.nextBytes(abyte0);
        return abyte0;
    }
}
