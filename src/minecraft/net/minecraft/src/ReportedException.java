package net.minecraft.src;

public class ReportedException extends RuntimeException
{
    private final CrashReport field_71576_a;

    public ReportedException(CrashReport par1CrashReport)
    {
        field_71576_a = par1CrashReport;
    }

    public CrashReport func_71575_a()
    {
        return field_71576_a;
    }

    public Throwable getCause()
    {
        return field_71576_a.func_71505_b();
    }

    public String getMessage()
    {
        return field_71576_a.func_71501_a();
    }
}
