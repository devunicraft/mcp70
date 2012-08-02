package argo.jdom;

import java.util.LinkedList;
import java.util.List;

public final class JsonObjectNodeBuilder implements JsonNodeBuilder
{
    private final List fieldBuilders = new LinkedList();

    JsonObjectNodeBuilder()
    {
    }

    public JsonObjectNodeBuilder withFieldBuilder(JsonFieldBuilder par1JsonFieldBuilder)
    {
        fieldBuilders.add(par1JsonFieldBuilder);
        return this;
    }

    public JsonRootNode func_74606_a()
    {
        return JsonNodeFactories.aJsonObject(new JsonObjectNodeBuilder_List(this));
    }

    public JsonNode buildNode()
    {
        return func_74606_a();
    }

    static List func_74607_a(JsonObjectNodeBuilder par0JsonObjectNodeBuilder)
    {
        return par0JsonObjectNodeBuilder.fieldBuilders;
    }
}
