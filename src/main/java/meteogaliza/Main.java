package meteogaliza;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import meteogaliza.enums.EstadoCeo;
import meteogaliza.enums.EstadoUtils;
import meteogaliza.enums.EstadoVento;
import meteogaliza.enums.VariableMeteoroloxica;
import meteogaliza.gson.JsonDeserializerPrediccion;
import meteogaliza.gson.JsonDeserializerPrediccionDia;
import meteogaliza.gson.JsonDeserializerVariableFranxa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new JsonDeserializerPrediccion())
                .registerTypeAdapter(PrediccionDia.class, new JsonDeserializerPrediccionDia())
                .registerTypeAdapter(VariableFranxa.class, new JsonDeserializerVariableFranxa())
                .setPrettyPrinting().create();

        Gson gsonTypeAdapter = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new TypeAdapter<Prediccion>() {
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
                                    }
                                }
                                jsonReader.endObject();
                            }
                        }
                        jsonReader.endObject();

                        return prediccion;
                    }
                })
                .setPrettyPrinting().create();

        Prediccion prediccion = new Prediccion();
        prediccion.setConcello(15071);
        PrediccionDia prediccionDia = new PrediccionDia()
                .setNivelAviso(0)
                .settMax(10)
                .settMin(1)
                .setUvMaz(12)
                .setDataPredicion(LocalDateTime.of(2024, 10, 29, 9, 46, 50))
                .addListaVariableFranxa(new VariableFranxa()
                        .setVariableMeteoroloxica(VariableMeteoroloxica.CIELO)
                        .setValorManha(EstadoCeo.ANUBRADO_75)
                        .setValorTarde(EstadoCeo.BRE_TEMA)
                        .setValorNoche(EstadoCeo.NOITE_ANUBRADO_75))
                .addListaVariableFranxa(new VariableFranxa()
                        .setVariableMeteoroloxica(VariableMeteoroloxica.TEMPERATURA_MINIMA)
                        .setValorManha(EstadoUtils.ofValorArbitrario(1))
                        .setValorTarde(EstadoUtils.ofValorArbitrario(5))
                        .setValorNoche(EstadoUtils.ofValorArbitrario(10)))
                .addListaVariableFranxa(new VariableFranxa()
                        .setVariableMeteoroloxica(VariableMeteoroloxica.VIENTO)
                        .setValorManha(EstadoVento.VENTO_FORTE_LESTE)
                        .setValorTarde(EstadoVento.VENTO_FORTE_NOROESTE)
                        .setValorNoche(EstadoVento.VENTO_FROUXO_NOROESTE));

        prediccion.addPrediccionDia(prediccionDia);

        System.out.println(prediccion);

        Path path1 = Paths.get("src\\main\\resources\\meteogaliza.json");
        Path path2 = Paths.get("src\\main\\resources\\meteogalizaLista.json");
        Prediccion prediccion1;
        try(var br = Files.newBufferedReader(path2)){
            prediccion1 = gson.fromJson(br, Prediccion.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prediccion1);
    }
}
