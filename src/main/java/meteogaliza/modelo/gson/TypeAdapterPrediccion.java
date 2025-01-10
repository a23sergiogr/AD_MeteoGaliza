package meteogaliza.modelo.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import meteogaliza.modelo.objetos.Prediccion;
import meteogaliza.modelo.objetos.PrediccionDia;
import meteogaliza.modelo.objetos.VariableFranxa;
import meteogaliza.modelo.enums.EstadoCeo;
import meteogaliza.modelo.enums.EstadoUtils;
import meteogaliza.modelo.enums.EstadoVento;
import meteogaliza.modelo.enums.VariableMeteoroloxica;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class TypeAdapterPrediccion extends TypeAdapter<Prediccion> {
    @Override
    public void write(JsonWriter jsonWriter, Prediccion prediccion) throws IOException {

    }

    @Override
    public Prediccion read(JsonReader jsonReader) throws IOException {
        Prediccion prediccion = new Prediccion();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("predConcello")){
                jsonReader.beginObject();
                while (jsonReader.hasNext()){
                    switch (jsonReader.nextName()){
                        case "idConcello" -> prediccion.setConcello(jsonReader.nextInt());
                        case "listaPredDiaConcello" -> {
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                PrediccionDia prediccionDia = new PrediccionDia();
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()){
                                    switch (jsonReader.nextName()){
                                        case "dataPredicion" -> prediccionDia.setDataPredicion(LocalDateTime.parse(jsonReader.nextString()));
                                        case "nivelAviso" -> setIntValueIfNotNull(jsonReader, prediccionDia::setNivelAviso, "Non hai Aviso");
                                        case "tMax" -> setIntValueIfNotNull(jsonReader, prediccionDia::settMax, "Non hai Aviso");
                                        case "tMin" -> setIntValueIfNotNull(jsonReader, prediccionDia::settMin, "Non hai Aviso");
                                        case "uvMax" -> setIntValueIfNotNull(jsonReader, prediccionDia::setUvMaz, "Non hai Aviso");
                                        case "ceo" -> {
                                            jsonReader.beginObject();
                                            VariableFranxa variableFranxa = new VariableFranxa()
                                                    .setVariableMeteoroloxica(VariableMeteoroloxica.CIELO);
                                            while(jsonReader.hasNext()){
                                                switch (jsonReader.nextName()){
                                                    case "manha" -> variableFranxa.setValorManha(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), jsonReader.nextInt()));
                                                    case "noite" -> variableFranxa.setValorNoche(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), jsonReader.nextInt()));
                                                    case "tarde" -> variableFranxa.setValorTarde(EstadoUtils.getEstadoPorCodigo(EstadoCeo.values(), jsonReader.nextInt()));
                                                    default -> jsonReader.skipValue();
                                                }
                                            }
                                            prediccionDia.addListaVariableFranxa(variableFranxa);
                                            jsonReader.endObject();
                                        }
                                        case "pchoiva" -> {
                                            jsonReader.beginObject();
                                            VariableFranxa variableFranxa = new VariableFranxa()
                                                    .setVariableMeteoroloxica(VariableMeteoroloxica.LLUVIA);
                                            while(jsonReader.hasNext()){
                                                switch (jsonReader.nextName()){
                                                    case "manha" -> variableFranxa.setValorManha(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "noite" -> variableFranxa.setValorNoche(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "tarde" -> variableFranxa.setValorTarde(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    default -> jsonReader.skipValue();
                                                }
                                            }
                                            prediccionDia.addListaVariableFranxa(variableFranxa);
                                            jsonReader.endObject();
                                        }
                                        case "tmaxFranxa" -> {
                                            jsonReader.beginObject();
                                            VariableFranxa variableFranxa = new VariableFranxa()
                                                    .setVariableMeteoroloxica(VariableMeteoroloxica.TEMPERATURA_MAXIMA);
                                            while(jsonReader.hasNext()){
                                                switch (jsonReader.nextName()){
                                                    case "manha" -> variableFranxa.setValorManha(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "noite" -> variableFranxa.setValorNoche(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "tarde" -> variableFranxa.setValorTarde(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    default -> jsonReader.skipValue();
                                                }
                                            }
                                            prediccionDia.addListaVariableFranxa(variableFranxa);
                                            jsonReader.endObject();
                                        }
                                        case "tminFranxa" -> {
                                            jsonReader.beginObject();
                                            VariableFranxa variableFranxa = new VariableFranxa()
                                                    .setVariableMeteoroloxica(VariableMeteoroloxica.TEMPERATURA_MINIMA);
                                            while(jsonReader.hasNext()){
                                                switch (jsonReader.nextName()){
                                                    case "manha" -> variableFranxa.setValorManha(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "noite" -> variableFranxa.setValorNoche(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    case "tarde" -> variableFranxa.setValorTarde(EstadoUtils.ofValorArbitrario(jsonReader.nextInt()));
                                                    default -> jsonReader.skipValue();
                                                }
                                            }
                                            prediccionDia.addListaVariableFranxa(variableFranxa);
                                            jsonReader.endObject();
                                        }
                                        case "vento" -> {
                                            jsonReader.beginObject();
                                            VariableFranxa variableFranxa = new VariableFranxa()
                                                    .setVariableMeteoroloxica(VariableMeteoroloxica.VIENTO);
                                            while(jsonReader.hasNext()){
                                                switch (jsonReader.nextName()){
                                                    case "manha" -> variableFranxa.setValorManha(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), jsonReader.nextInt()));
                                                    case "noite" -> variableFranxa.setValorNoche(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), jsonReader.nextInt()));
                                                    case "tarde" -> variableFranxa.setValorTarde(EstadoUtils.getEstadoPorCodigo(EstadoVento.values(), jsonReader.nextInt()));
                                                    default -> jsonReader.skipValue();
                                                }
                                            }
                                            prediccionDia.addListaVariableFranxa(variableFranxa);
                                            jsonReader.endObject();
                                        }
                                        default -> jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();
                                prediccion.addPrediccionDia(prediccionDia);
                            }
                            jsonReader.endArray();
                        }
                        default -> jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
            }
        }
        jsonReader.endObject();

        return prediccion;
    }
    private void setIntValueIfNotNull(JsonReader jsonReader, Consumer<Integer> setter, String errorMessage){
        try {
            if (jsonReader.peek() != JsonToken.NULL)
                setter.accept(jsonReader.nextInt());
            else{
                setter.accept(-9999);
                jsonReader.skipValue();
            }
        }
        catch (Exception e){
            System.err.println(errorMessage);
        }
    }
}
