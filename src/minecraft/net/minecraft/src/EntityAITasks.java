package net.minecraft.src;

import java.util.*;

public class EntityAITasks
{
    private List field_75782_a;
    private List field_75780_b;
    private final Profiler field_75781_c;
    private int field_75778_d;
    private int field_75779_e;

    public EntityAITasks(Profiler par1Profiler)
    {
        field_75782_a = new ArrayList();
        field_75780_b = new ArrayList();
        field_75778_d = 0;
        field_75779_e = 3;
        field_75781_c = par1Profiler;
    }

    public void addTask(int par1, EntityAIBase par2EntityAIBase)
    {
        field_75782_a.add(new EntityAITaskEntry(this, par1, par2EntityAIBase));
    }

    public void onUpdateTasks()
    {
        ArrayList arraylist = new ArrayList();

        if (field_75778_d++ % field_75779_e == 0)
        {
            Iterator iterator = field_75782_a.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EntityAITaskEntry entityaitaskentry = (EntityAITaskEntry)iterator.next();
                boolean flag = field_75780_b.contains(entityaitaskentry);

                if (flag)
                {
                    if (func_75775_b(entityaitaskentry) && func_75773_a(entityaitaskentry))
                    {
                        continue;
                    }

                    entityaitaskentry.action.resetTask();
                    field_75780_b.remove(entityaitaskentry);
                }

                if (func_75775_b(entityaitaskentry) && entityaitaskentry.action.shouldExecute())
                {
                    arraylist.add(entityaitaskentry);
                    field_75780_b.add(entityaitaskentry);
                }
            }
            while (true);
        }
        else
        {
            Iterator iterator1 = field_75780_b.iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                EntityAITaskEntry entityaitaskentry1 = (EntityAITaskEntry)iterator1.next();

                if (!entityaitaskentry1.action.continueExecuting())
                {
                    entityaitaskentry1.action.resetTask();
                    iterator1.remove();
                }
            }
            while (true);
        }

        field_75781_c.startSection("goalStart");

        for (Iterator iterator2 = arraylist.iterator(); iterator2.hasNext(); field_75781_c.endSection())
        {
            EntityAITaskEntry entityaitaskentry2 = (EntityAITaskEntry)iterator2.next();
            field_75781_c.startSection(entityaitaskentry2.action.getClass().getSimpleName());
            entityaitaskentry2.action.startExecuting();
        }

        field_75781_c.endSection();
        field_75781_c.startSection("goalTick");

        for (Iterator iterator3 = field_75780_b.iterator(); iterator3.hasNext(); field_75781_c.endSection())
        {
            EntityAITaskEntry entityaitaskentry3 = (EntityAITaskEntry)iterator3.next();
            field_75781_c.startSection(entityaitaskentry3.action.getClass().getSimpleName());
            entityaitaskentry3.action.updateTask();
        }

        field_75781_c.endSection();
    }

    private boolean func_75773_a(EntityAITaskEntry par1EntityAITaskEntry)
    {
        field_75781_c.startSection("canContinue");
        boolean flag = par1EntityAITaskEntry.action.continueExecuting();
        field_75781_c.endSection();
        return flag;
    }

    private boolean func_75775_b(EntityAITaskEntry par1EntityAITaskEntry)
    {
        label0:
        {
            field_75781_c.startSection("canUse");
            Iterator iterator = field_75782_a.iterator();
            EntityAITaskEntry entityaitaskentry;
            label1:

            do
            {
                do
                {
                    do
                    {
                        if (!iterator.hasNext())
                        {
                            break label0;
                        }

                        entityaitaskentry = (EntityAITaskEntry)iterator.next();
                    }
                    while (entityaitaskentry == par1EntityAITaskEntry);

                    if (par1EntityAITaskEntry.priority < entityaitaskentry.priority)
                    {
                        continue label1;
                    }
                }
                while (!field_75780_b.contains(entityaitaskentry) || areTasksCompatible(par1EntityAITaskEntry, entityaitaskentry));

                field_75781_c.endSection();
                return false;
            }
            while (!field_75780_b.contains(entityaitaskentry) || entityaitaskentry.action.isContinuous());

            field_75781_c.endSection();
            return false;
        }
        field_75781_c.endSection();
        return true;
    }

    /**
     * Returns whether two EntityAITaskEntries can be executed concurrently
     */
    private boolean areTasksCompatible(EntityAITaskEntry par1EntityAITaskEntry, EntityAITaskEntry par2EntityAITaskEntry)
    {
        return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0;
    }
}
