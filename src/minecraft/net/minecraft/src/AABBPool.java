package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    /**
     * Maximum number of times the pool can be "cleaned" before the list is shrunk
     */
    private final int maxNumCleans;

    /**
     * Number of Pool entries to remove when cleanPool is called maxNumCleans times.
     */
    private final int numEntriesToRemove;

    /** List of AABB stored in this Pool */
    private final List listAABB = new ArrayList();

    /** Next index to use when adding a Pool Entry. */
    private int nextPoolIndex;

    /**
     * Largest index reached by this Pool (can be reset to 0 upon calling cleanPool)
     */
    private int maxPoolIndex;

    /** Number of times this Pool has been cleaned */
    private int numCleans;

    public AABBPool(int par1, int par2)
    {
        nextPoolIndex = 0;
        maxPoolIndex = 0;
        numCleans = 0;
        maxNumCleans = par1;
        numEntriesToRemove = par2;
    }

    /**
     * Adds a AABB to the pool, or if there is an available AABB, updates an existing AABB entry to specified
     * coordinates
     */
    public AxisAlignedBB addOrModifyAABBInPool(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        AxisAlignedBB axisalignedbb;

        if (nextPoolIndex >= listAABB.size())
        {
            axisalignedbb = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            listAABB.add(axisalignedbb);
        }
        else
        {
            axisalignedbb = (AxisAlignedBB)listAABB.get(nextPoolIndex);
            axisalignedbb.setBounds(par1, par3, par5, par7, par9, par11);
        }

        nextPoolIndex++;
        return axisalignedbb;
    }

    /**
     * Marks the pool as "empty", starting over when adding new entries. If this is called maxNumCleans times, the list
     * size is reduced
     */
    public void cleanPool()
    {
        if (nextPoolIndex > maxPoolIndex)
        {
            maxPoolIndex = nextPoolIndex;
        }

        if (numCleans++ == maxNumCleans)
        {
            for (int i = Math.max(maxPoolIndex, listAABB.size() - numEntriesToRemove); listAABB.size() > i; listAABB.remove(i)) { }

            maxPoolIndex = 0;
            numCleans = 0;
        }

        nextPoolIndex = 0;
    }

    /**
     * Clears the AABBPool
     */
    public void clearPool()
    {
        nextPoolIndex = 0;
        listAABB.clear();
    }
}
