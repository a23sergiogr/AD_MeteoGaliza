package meteogaliza.Vista;
import java.awt.Color;

/**
 * Colores y bordes
 */
public final class UIConstants {

    // Previene la instanciación
    private UIConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Colores
    public static final Color BACKGROUND_MORNING_COLOR = new Color(244, 208, 180);
    public static final Color BACKGROUND_DAY_COLOR = new Color(194, 227, 255);
    public static final Color BACKGROUND_NIGHT_COLOR = new Color(25, 25, 28);

    public static final Color HEADER_BACKGROUND = new Color(153, 101, 21);
    public static final Color HEADER_TEXT_COLOR = Color.WHITE;

    public static final Color TEXT_BRIGHT = new Color(211, 198, 141);

    public static final Color BACKGROUND_COLOR = Color.DARK_GRAY.darker();

    // Dimensiones
    public static final int BORDER_WIDTH = 0;
}
