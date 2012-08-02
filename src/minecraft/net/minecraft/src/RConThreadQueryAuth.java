package net.minecraft.src;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.Random;

class RConThreadQueryAuth
{
    private long field_72598_b;
    private int field_72599_c;
    private byte field_72596_d[];
    private byte field_72597_e[];
    private String field_72595_f;
    final RConThreadQuery field_72600_a;

    public RConThreadQueryAuth(RConThreadQuery par1RConThreadQuery, DatagramPacket par2DatagramPacket)
    {
        field_72600_a = par1RConThreadQuery;
        field_72598_b = (new Date()).getTime();
        byte abyte0[] = par2DatagramPacket.getData();
        field_72596_d = new byte[4];
        field_72596_d[0] = abyte0[3];
        field_72596_d[1] = abyte0[4];
        field_72596_d[2] = abyte0[5];
        field_72596_d[3] = abyte0[6];
        field_72595_f = new String(field_72596_d);
        field_72599_c = (new Random()).nextInt(0x1000000);
        field_72597_e = String.format("\t%s%d\0", new Object[]
                {
                    field_72595_f, Integer.valueOf(field_72599_c)
                }).getBytes();
    }

    public Boolean func_72593_a(long par1)
    {
        return Boolean.valueOf(field_72598_b < par1);
    }

    public int func_72592_a()
    {
        return field_72599_c;
    }

    public byte[] func_72594_b()
    {
        return field_72597_e;
    }

    public byte[] func_72591_c()
    {
        return field_72596_d;
    }
}
