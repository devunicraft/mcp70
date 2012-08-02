package net.minecraft.src;

import java.util.*;

public class LowerStringMap implements Map
{
    private final Map internalMap = new LinkedHashMap();

    public LowerStringMap()
    {
    }

    public int size()
    {
        return internalMap.size();
    }

    public boolean isEmpty()
    {
        return internalMap.isEmpty();
    }

    public boolean containsKey(Object par1Obj)
    {
        return internalMap.containsKey(par1Obj.toString().toLowerCase());
    }

    public boolean containsValue(Object par1Obj)
    {
        return internalMap.containsKey(par1Obj);
    }

    public Object get(Object par1Obj)
    {
        return internalMap.get(par1Obj.toString().toLowerCase());
    }

    /**
     * a map already defines a general put
     */
    public Object putLower(String par1Str, Object par2Obj)
    {
        return internalMap.put(par1Str.toLowerCase(), par2Obj);
    }

    public Object remove(Object par1Obj)
    {
        return internalMap.remove(par1Obj.toString().toLowerCase());
    }

    public void putAll(Map par1Map)
    {
        java.util.Map.Entry entry;

        for (Iterator iterator = par1Map.entrySet().iterator(); iterator.hasNext(); putLower((String)entry.getKey(), entry.getValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
        }
    }

    public void clear()
    {
        internalMap.clear();
    }

    public Set keySet()
    {
        return internalMap.keySet();
    }

    public Collection values()
    {
        return internalMap.values();
    }

    public Set entrySet()
    {
        return internalMap.entrySet();
    }

    public Object put(Object par1Obj, Object par2Obj)
    {
        return putLower((String)par1Obj, par2Obj);
    }
}
