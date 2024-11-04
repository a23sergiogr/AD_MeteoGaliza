package meteogaliza.gson;

import com.google.gson.*;
import meteogaliza.VariableFranxa;
import meteogaliza.enums.EstadoCeo;
import meteogaliza.enums.EstadoUtils;
import meteogaliza.enums.EstadoVento;

import java.lang.reflect.Type;

public class JsonDeserializerVariableFranxa implements JsonDeserializer<VariableFranxa> {
    @Override
    public VariableFranxa deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        VariableFranxa variableFranxa = new VariableFranxa();

        int manha = jsonObject.get("manha").getAsInt();
        int tarde = jsonObject.get("tarde").getAsInt();
        int noite = jsonObject.get("noite").getAsInt();

        if (manha == -9999)
            variableFranxa.setValorManha(EstadoUtils.NON_DISPONHIBLE);
        else if (manha < 101)
            variableFranxa.setValorManha(EstadoUtils.ofValorArbitrario(manha));
        else if (manha < 299)
            variableFranxa.setValorManha(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), manha));
        else
            variableFranxa.setValorManha(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), manha));

        if (tarde == -9999)
            variableFranxa.setValorTarde(EstadoUtils.NON_DISPONHIBLE);
        else if (tarde < 101)
            variableFranxa.setValorTarde(EstadoUtils.ofValorArbitrario(tarde));
        else if (tarde < 299)
            variableFranxa.setValorTarde(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), tarde));
        else
            variableFranxa.setValorTarde(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), tarde));

        if (noite == -9999)
            variableFranxa.setValorNoche(EstadoUtils.NON_DISPONHIBLE);
        else if (noite < 101)
            variableFranxa.setValorNoche(EstadoUtils.ofValorArbitrario(noite));
        else if (noite < 299)
            variableFranxa.setValorNoche(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), noite));
        else
            variableFranxa.setValorNoche(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), noite));


        return variableFranxa;
    }
}
