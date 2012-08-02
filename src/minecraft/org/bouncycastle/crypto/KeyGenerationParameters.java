package org.bouncycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters
{
    private SecureRandom field_71845_a;
    private int field_71844_b;

    public KeyGenerationParameters(SecureRandom par1SecureRandom, int par2)
    {
        field_71845_a = par1SecureRandom;
        field_71844_b = par2;
    }

    public SecureRandom func_71843_a()
    {
        return field_71845_a;
    }

    public int func_71842_b()
    {
        return field_71844_b;
    }
}
