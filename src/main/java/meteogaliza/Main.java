package meteogaliza;

import com.google.gson.*;
import javafx.application.Application;
import meteogaliza.enums.EstadoCeo;
import meteogaliza.enums.EstadoUtils;
import meteogaliza.enums.EstadoVento;
import meteogaliza.enums.VariableMeteoroloxica;
import meteogaliza.gson.JsonDeserializerPrediccion;
import meteogaliza.gson.JsonDeserializerPrediccionDia;
import meteogaliza.gson.JsonDeserializerVariableFranxa;
import meteogaliza.gson.TypeAdapterPrediccion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


public class Main {
    public static final String uri = "https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=15078&request_locale=gl";
    public static final Path path1 = Paths.get("src\\main\\resources\\meteogaliza.json");
    public static final Path path2 = Paths.get("src\\main\\resources\\meteogalizaLista.json");


    public static void main(String[] args) throws MalformedURLException {
        Gson gsonDeserializer = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new JsonDeserializerPrediccion())
                .registerTypeAdapter(PrediccionDia.class, new JsonDeserializerPrediccionDia())
                .registerTypeAdapter(VariableFranxa.class, new JsonDeserializerVariableFranxa())
                .setPrettyPrinting().create();

        Gson gsonTypeAdapter = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new TypeAdapterPrediccion())
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

//        prediccion.addPrediccionDia(prediccionDia);
//
//        System.out.println(prediccion);

        Prediccion prediccionBR;
        try(var br = Files.newBufferedReader(path2)){
            prediccionBR = gsonTypeAdapter.fromJson(br, Prediccion.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(prediccionBR);


//        Prediccion prediccionURI;
//        try (var is = new BufferedReader(new InputStreamReader(new URI(uri).toURL().openConnection().getInputStream()))) {
//            prediccionURI = gsonTypeAdapter.fromJson(is, Prediccion.class);
//        } catch (IOException | URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(prediccionURI);

        Ventana ventana = new Ventana();
    }
}
