package meteogaliza;

import meteogaliza.controlador.Controlador;

public class Main {
    public static final String str = "https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=15078&request_locale=gl";

    public static void main(String[] args) {
        Controlador controlador = new Controlador(str);
        controlador.abrirVentana();
    }
}
