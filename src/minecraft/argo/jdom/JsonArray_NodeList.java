package argo.jdom;

import java.util.ArrayList;
import java.util.Iterator;

final class JsonArray_NodeList extends ArrayList
{
    final Iterable field_74730_a;

    JsonArray_NodeList(Iterable par1Iterable)
    {
        field_74730_a = par1Iterable;
        JsonNode jsonnode;

        for (Iterator iterator = field_74730_a.iterator(); iterator.hasNext(); add(jsonnode))
        {
            jsonnode = (JsonNode)iterator.next();
        }
    }
}
