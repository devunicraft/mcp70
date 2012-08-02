package argo.jdom;

public final class JsonStringNodeBuilder implements JsonNodeBuilder
{
    private final String field_74601_a;

    JsonStringNodeBuilder(String par1Str)
    {
        field_74601_a = par1Str;
    }

    public JsonStringNode func_74600_a()
    {
        return JsonNodeFactories.aJsonString(field_74601_a);
    }

    public JsonNode buildNode()
    {
        return func_74600_a();
    }
}
