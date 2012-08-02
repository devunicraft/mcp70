package org.bouncycastle.crypto;

public interface BlockCipher
{
    public abstract void func_71805_a(boolean flag, CipherParameters cipherparameters) throws IllegalArgumentException;

    public abstract String func_71802_a();

    public abstract int func_71804_b();

    public abstract int func_71806_a(byte abyte0[], int i, byte abyte1[], int j) throws DataLengthException, IllegalStateException;

    public abstract void func_71803_c();
}
