package argo.jdom;

import java.util.Arrays;

public final class JsonNodeSelectors
{
    public static JsonNodeSelector func_74682_a(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_String()));
    }

    public static JsonNodeSelector func_74683_b(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_Array()));
    }

    public static JsonNodeSelector func_74681_c(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_Object()));
    }

    public static JsonNodeSelector func_74675_a(String par0Str)
    {
        return func_74680_a(JsonNodeFactories.aJsonString(par0Str));
    }

    public static JsonNodeSelector func_74680_a(JsonStringNode par0JsonStringNode)
    {
        return new JsonNodeSelector(new JsonNodeSelectors_Field(par0JsonStringNode));
    }

    public static JsonNodeSelector func_74684_b(String par0Str)
    {
        return func_74681_c(new Object[0]).with(func_74675_a(par0Str));
    }

    public static JsonNodeSelector func_74678_a(int par0)
    {
        return new JsonNodeSelector(new JsonNodeSelectors_Element(par0));
    }

    public static JsonNodeSelector func_74679_b(int par0)
    {
        return func_74683_b(new Object[0]).with(func_74678_a(par0));
    }

    private static JsonNodeSelector chainOn(Object par0ArrayOfObj[], JsonNodeSelector par1JsonNodeSelector)
    {
        JsonNodeSelector jsonnodeselector = par1JsonNodeSelector;

        for (int i = par0ArrayOfObj.length - 1; i >= 0; i--)
        {
            if (par0ArrayOfObj[i] instanceof Integer)
            {
                jsonnodeselector = chainedJsonNodeSelector(func_74679_b(((Integer)par0ArrayOfObj[i]).intValue()), jsonnodeselector);
                continue;
            }

            if (par0ArrayOfObj[i] instanceof String)
            {
                jsonnodeselector = chainedJsonNodeSelector(func_74684_b((String)par0ArrayOfObj[i]), jsonnodeselector);
            }
            else
            {
                throw new IllegalArgumentException((new StringBuilder()).append("Element [").append(par0ArrayOfObj[i]).append("] of path elements").append(" [").append(Arrays.toString(par0ArrayOfObj)).append("] was of illegal type [").append(par0ArrayOfObj[i].getClass().getCanonicalName()).append("]; only Integer and String are valid.").toString());
            }
        }

        return jsonnodeselector;
    }

    private static JsonNodeSelector chainedJsonNodeSelector(JsonNodeSelector par0JsonNodeSelector, JsonNodeSelector par1JsonNodeSelector)
    {
        return new JsonNodeSelector(new ChainedFunctor(par0JsonNodeSelector, par1JsonNodeSelector));
    }
}
