package meteogaliza.gson;

import com.google.gson.*;
import meteogaliza.PrediccionDia;
import meteogaliza.VariableFranxa;
import meteogaliza.enums.VariableMeteoroloxica;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class JsonDeserializerPrediccionDia implements JsonDeserializer<PrediccionDia> {
    @Override
    public PrediccionDia deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        PrediccionDia prediccionDia = new PrediccionDia();
        setIntValueIfPresent(jsonObject, "nivelAviso", prediccionDia::setNivelAviso, "Non hai nivelAviso");
        setIntValueIfPresent(jsonObject, "tMax", prediccionDia::settMax, "Non hai tMax");
        setIntValueIfPresent(jsonObject, "tMin", prediccionDia::settMin, "Non hai tMin");
        setIntValueIfPresent(jsonObject, "uvMax", prediccionDia::setUvMaz, "Non hai uvMax");

        try{
            if (jsonObject.has("dataPredicion") && !jsonObject.get("dataPredicion").isJsonNull())
                prediccionDia.setDataPredicion(LocalDateTime.parse(jsonObject.get("dataPredicion").getAsString()));
        } catch (Exception e){
            System.err.println("Non hai dataPredicion");
        }

        setVariableFranxaValueIfPresent(jsonObject, jsonDeserializationContext, "ceo", VariableMeteoroloxica.CIELO, prediccionDia::addListaVariableFranxa, "Non hai Ceo");
        setVariableFranxaValueIfPresent(jsonObject, jsonDeserializationContext, "pchoiva", VariableMeteoroloxica.LLUVIA, prediccionDia::addListaVariableFranxa, "Non hai pchoiva");
        setVariableFranxaValueIfPresent(jsonObject, jsonDeserializationContext, "tmaxFranxa", VariableMeteoroloxica.TEMPERATURA_MAXIMA, prediccionDia::addListaVariableFranxa, "Non hai tmaxFranxa");
        setVariableFranxaValueIfPresent(jsonObject, jsonDeserializationContext, "tminFranxa", VariableMeteoroloxica.TEMPERATURA_MINIMA, prediccionDia::addListaVariableFranxa, "Non hai tminFranxa");
        setVariableFranxaValueIfPresent(jsonObject, jsonDeserializationContext, "vento", VariableMeteoroloxica.VIENTO, prediccionDia::addListaVariableFranxa, "Non hai vento");

        return prediccionDia;
    }

    private void setIntValueIfPresent(JsonObject jsonObject, String key, Consumer<Integer> setter, String errorMessage) {
        try {
            if (jsonObject.has(key)) {
                if (jsonObject.get(key).isJsonNull()){
                    setter.accept(-9999);
                }
                else{
                    setter.accept(jsonObject.get(key).getAsInt());
                }
            }
        } catch (Exception e) {
            System.err.println(errorMessage);
        }
    }

    private void setVariableFranxaValueIfPresent(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, String key, VariableMeteoroloxica type, Consumer<VariableFranxa> setter, String errorMessage) {
        try {
            if (jsonObject.has(key) && !jsonObject.get(key).isJsonNull()) {
                VariableFranxa variableFranxa = jsonDeserializationContext.deserialize(jsonObject.get(key), VariableFranxa.class);
                variableFranxa.setVariableMeteoroloxica(type);
               setter.accept(variableFranxa);
            }
        } catch (Exception e) {
            System.err.println(errorMessage);
        }
    }
}
