package meteogaliza.gson;

import com.google.gson.*;
import meteogaliza.Prediccion;
import meteogaliza.PrediccionDia;

import java.lang.reflect.Type;

public class JsonDeserializerPrediccion implements JsonDeserializer<Prediccion> {
    @Override
    public Prediccion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Prediccion prediccion = new Prediccion();
        JsonObject jsonObject = jsonElement.getAsJsonObject().get("predConcello").getAsJsonObject();

        prediccion.setConcello(jsonObject.get("idConcello").getAsInt());

        JsonArray jsonArray = jsonObject.get("listaPredDiaConcello").getAsJsonArray();
        for (JsonElement element : jsonArray) {
            prediccion.addPrediccionDia(jsonDeserializationContext.deserialize(element, PrediccionDia.class));
        }

        return prediccion;
    }
}
