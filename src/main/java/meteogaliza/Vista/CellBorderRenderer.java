package meteogaliza.Vista;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static meteogaliza.Vista.UIConstants.*;

// Renderizador de celdas personalizado
class CellBorderRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Llamar al método de la superclase para obtener el componente predeterminado (JLabel)
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Configurar borde de cada celda (usando MatteBorder)
        label.setBorder(new MatteBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BACKGROUND_COLOR));

        // Alinear el texto al centro
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        return label; // Devuelve el JLabel con el borde y el alineamiento
    }
}