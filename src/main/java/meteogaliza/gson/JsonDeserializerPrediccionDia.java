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


        prediccionDia.setDataPredicion(LocalDateTime.parse(jsonObject.get("dataPredicion").getAsString()));

        try{
            VariableFranxa ceo = jsonDeserializationContext.deserialize(jsonObject.get("ceo"), VariableFranxa.class);
            ceo.setVariableMeteoroloxica(VariableMeteoroloxica.CIELO);
            prediccionDia.addListaVariableFranxa(ceo);
        } catch (Exception e){
            System.err.println("Non hai ceo");
        }

        try{
            VariableFranxa pchoiva = jsonDeserializationContext.deserialize(jsonObject.get("pchoiva"), VariableFranxa.class);
            pchoiva.setVariableMeteoroloxica(VariableMeteoroloxica.LLUVIA);
            prediccionDia.addListaVariableFranxa(pchoiva);
        } catch (Exception e){
            System.err.println("Non hai pchoiva");
        }

        try{
            VariableFranxa tmaxFranxa = jsonDeserializationContext.deserialize(jsonObject.get("tmaxFranxa"), VariableFranxa.class);
            tmaxFranxa.setVariableMeteoroloxica(VariableMeteoroloxica.TEMPERATURA_MAXIMA);
            prediccionDia.addListaVariableFranxa(tmaxFranxa);
        } catch (Exception e){
            System.err.println("Non hai tmaxFranxa");
        }

        try{
            VariableFranxa tminFranxa = jsonDeserializationContext.deserialize(jsonObject.get("tminFranxa"), VariableFranxa.class);
            tminFranxa.setVariableMeteoroloxica(VariableMeteoroloxica.TEMPERATURA_MINIMA);
            prediccionDia.addListaVariableFranxa(tminFranxa);
        } catch (Exception e){
            System.err.println("Non hai tminFranxa");
        }

        try{
            VariableFranxa vento = jsonDeserializationContext.deserialize(jsonObject.get("vento"), VariableFranxa.class);
            vento.setVariableMeteoroloxica(VariableMeteoroloxica.VIENTO);
            prediccionDia.addListaVariableFranxa(vento);
        } catch (Exception e){
            System.err.println("Non hai vento");
        }

        return prediccionDia;
    }

    private void setIntValueIfPresent(JsonObject jsonObject, String key, Consumer<Integer> setter, String errorMessage) {
        try {
            if (jsonObject.has(key) && !jsonObject.get(key).isJsonNull()) {
                setter.accept(jsonObject.get(key).getAsInt());
            }
        } catch (Exception e) {
            System.err.println(errorMessage);
        }
    }
}
