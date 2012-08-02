package org.bouncycastle.asn1;

public class DERObjectIdentifier extends ASN1Primitive
{
    String field_71611_a;
    private static ASN1ObjectIdentifier field_71610_b[][] = new ASN1ObjectIdentifier[255][];

    public DERObjectIdentifier(String par1Str)
    {
        if (!func_71608_a(par1Str))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("string ").append(par1Str).append(" not an OID").toString());
        }
        else
        {
            field_71611_a = par1Str;
            return;
        }
    }

    public String func_71609_b()
    {
        return field_71611_a;
    }

    public int hashCode()
    {
        return field_71611_a.hashCode();
    }

    boolean func_71607_a(ASN1Primitive par1ASN1Primitive)
    {
        if (!(par1ASN1Primitive instanceof DERObjectIdentifier))
        {
            return false;
        }
        else
        {
            return field_71611_a.equals(((DERObjectIdentifier)par1ASN1Primitive).field_71611_a);
        }
    }

    public String toString()
    {
        return func_71609_b();
    }

    private static boolean func_71608_a(String par0Str)
    {
        if (par0Str.length() < 3 || par0Str.charAt(1) != '.')
        {
            return false;
        }

        char c = par0Str.charAt(0);

        if (c < '0' || c > '2')
        {
            return false;
        }

        boolean flag = false;

        for (int i = par0Str.length() - 1; i >= 2; i--)
        {
            char c1 = par0Str.charAt(i);

            if ('0' <= c1 && c1 <= '9')
            {
                flag = true;
                continue;
            }

            if (c1 == '.')
            {
                if (!flag)
                {
                    return false;
                }

                flag = false;
            }
            else
            {
                return false;
            }
        }

        return flag;
    }
}
