package meteogaliza.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import meteogaliza.Vista.PrediccionVentana;
import meteogaliza.modelo.gson.JsonDeserializerPrediccion;
import meteogaliza.modelo.gson.JsonDeserializerPrediccionDia;
import meteogaliza.modelo.gson.JsonDeserializerVariableFranxa;
import meteogaliza.modelo.gson.TypeAdapterPrediccion;
import meteogaliza.modelo.objetos.Prediccion;
import meteogaliza.modelo.objetos.PrediccionDia;
import meteogaliza.modelo.objetos.VariableFranxa;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Controlador {
    PrediccionVentana ventana;
    Prediccion prediccion;
    Gson gson;

    public Controlador(){
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new JsonDeserializerPrediccion())
                .registerTypeAdapter(PrediccionDia.class, new JsonDeserializerPrediccionDia())
                .registerTypeAdapter(VariableFranxa.class, new JsonDeserializerVariableFranxa())
                .setPrettyPrinting().create();
    }

    public Controlador(String url){
        setGsonDeserializer();
        try {
            setUrlPrediccion(new URL(url));
        } catch (MalformedURLException e) {
            System.err.println("Error con URL:\n" + e);
        }
    }

    public void setGsonTypeAdapter(){
        this.gson =  new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new TypeAdapterPrediccion())
                .setPrettyPrinting().create();
    }

    public void setGsonDeserializer(){
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Prediccion.class, new JsonDeserializerPrediccion())
                .registerTypeAdapter(PrediccionDia.class, new JsonDeserializerPrediccionDia())
                .registerTypeAdapter(VariableFranxa.class, new JsonDeserializerVariableFranxa())
                .setPrettyPrinting().create();
    }

    public void setUrlPrediccion(URL urlPrediccion) {
        try (var reader = new InputStreamReader(urlPrediccion.openStream())) {
            this.prediccion = gson.fromJson(reader, Prediccion.class);
            ventana = new PrediccionVentana(prediccion);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer los datos desde la URL: " + urlPrediccion, e);
        }
    }

    public void abrirVentana(){
        ventana.abrirVentana();
    }
}
