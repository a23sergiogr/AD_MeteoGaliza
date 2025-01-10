package meteogaliza.Vista;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static meteogaliza.Vista.UIConstants.*;

// Renderizador personalizado para mostrar JLabels con imágenes en las celdas de la tabla
public class ImageRenderer extends JLabel implements TableCellRenderer {
    public ImageRenderer() {
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof JLabel label) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setOpaque(true); // Asegurarse de que el JLabel recibido sea opaco
            if (column == 0)
                label.setBackground(isSelected ? table.getSelectionBackground() : BACKGROUND_COLOR);
            else if (column == 1)
                label.setBackground(isSelected ? table.getSelectionBackground() : BACKGROUND_MORNING_COLOR);
            else if (column == 2)
                label.setBackground(isSelected ? table.getSelectionBackground() : BACKGROUND_DAY_COLOR);
            else if (column == 3)
                label.setBackground(isSelected ? table.getSelectionBackground() : BACKGROUND_NIGHT_COLOR);

            label.setForeground(TEXT_BRIGHT);
            label.setBorder(null);
            return label;
        }

        // Para el propio ImageRenderer
        setBackground(isSelected ? table.getSelectionBackground() : BACKGROUND_COLOR);
        setForeground(isSelected ? table.getSelectionForeground() : TEXT_BRIGHT);
        return this;
    }
}