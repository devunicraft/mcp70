package org.bouncycastle.jce.provider;

import java.security.PrivilegedAction;

class BouncyCastleProviderAction implements PrivilegedAction
{
    final BouncyCastleProvider field_74801_a;

    BouncyCastleProviderAction(BouncyCastleProvider par1BouncyCastleProvider)
    {
        field_74801_a = par1BouncyCastleProvider;
    }

    public Object run()
    {
        BouncyCastleProvider.func_74821_a(field_74801_a);
        return null;
    }
}
