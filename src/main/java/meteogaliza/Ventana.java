package meteogaliza;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Ventana extends JFrame {
    String str = "https://www.meteogalicia.gal/datosred/infoweb/meteo/imagenes/meteoros/ceo/220_fondo.png";
    public Ventana() throws MalformedURLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        URL url = new URL(str);

        ImageIcon imagen = new ImageIcon(url);
        JLabel etiquetaImagen = new JLabel(imagen);

        add(etiquetaImagen);
        setVisible(true);
    }
}
