package meteogaliza.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import meteogaliza.Prediccion;

import java.io.IOException;

public class TypeAdapterPrediccion extends TypeAdapter<Prediccion> {
    @Override
    public void write(JsonWriter jsonWriter, Prediccion prediccion) throws IOException {

    }

    @Override
    public Prediccion read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
