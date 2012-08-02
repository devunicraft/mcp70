package org.bouncycastle.asn1;

public abstract class ASN1Object implements ASN1Encodable
{
    public ASN1Object()
    {
    }

    public int hashCode()
    {
        return func_71606_a().hashCode();
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }

        if (!(par1Obj instanceof ASN1Encodable))
        {
            return false;
        }
        else
        {
            ASN1Encodable asn1encodable = (ASN1Encodable)par1Obj;
            return func_71606_a().equals(asn1encodable.func_71606_a());
        }
    }

    public abstract ASN1Primitive func_71606_a();
}
