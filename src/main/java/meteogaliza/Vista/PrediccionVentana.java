package meteogaliza.Vista;

import meteogaliza.modelo.enums.EstadoCeo;
import meteogaliza.modelo.enums.EstadoUtils;
import meteogaliza.modelo.enums.EstadoVento;
import meteogaliza.modelo.objetos.Prediccion;
import meteogaliza.modelo.objetos.PrediccionDia;
import meteogaliza.modelo.objetos.VariableFranxa;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import static meteogaliza.Vista.UIConstants.*;

public class PrediccionVentana extends JFrame {

    private final Prediccion prediccion;
    private DefaultTableModel tableImgModel, tableTempMode;

    public PrediccionVentana(Prediccion prediccion) {
        this.prediccion = prediccion;

        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                 IllegalAccessException e) {
            System.err.println("Problema cargando Look and Feel");
        }
    }

    public void abrirVentana(){
        SwingUtilities.invokeLater(() -> {
            this.crearVista();
            this.setVisible(true);
        });
    }

    private void crearVista(){
        // Configuración de la interfaz
        setTitle("Predicción Meteorológica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel jPanelMain = new JPanel(new BorderLayout());
        setContentPane(jPanelMain);

        JTabbedPane jTabbedPane = new JTabbedPane();

        for (PrediccionDia dia : prediccion.getListaPredDiaConcello()) {

            // Crear el panel central para cada día de predicción
            JPanel jPanelCenter = new JPanel(new BorderLayout());

            // Configuración de la tabla de imágenes de predicción
            tableImgModel = new DefaultTableModel(new Object[]{"Variable", "Mañana", "Tarde", "Noche"}, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return JLabel.class;
                }
            };
            JTable tablePrediction = new JTable(tableImgModel);
            tablePrediction.setRowHeight(50);
            tablePrediction.setDefaultRenderer(JLabel.class, new ImageRenderer());
            tablePrediction.setGridColor(BACKGROUND_COLOR);
            tablePrediction.setDefaultRenderer(Object.class, new CellBorderRenderer());

            tablePrediction.setBackground(BACKGROUND_COLOR);
            tablePrediction.setOpaque(true);

            // Configuración de la tabla de temperatura
            tableTempMode = new DefaultTableModel(new Object[]{"Variable", "Mañana", "Tarde", "Noche"}, 0);
            JTable tableTemp = new JTable(tableTempMode);
            tableTemp.setGridColor(BACKGROUND_COLOR);

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            // Alinear las columnas según el índice
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            tableTemp.getColumnModel().getColumn(0).setCellRenderer(renderer);
            tableTemp.getColumnModel().getColumn(1).setCellRenderer(renderer);
            tableTemp.getColumnModel().getColumn(2).setCellRenderer(renderer);
            tableTemp.getColumnModel().getColumn(3).setCellRenderer(renderer);

            tableTemp.setForeground(TEXT_BRIGHT);
            tableTemp.setBackground(BACKGROUND_COLOR);  // Color de fondo de la tabla
            tableTemp.setOpaque(true);

            // Personalizar colores de las tablas
            configurarColoresHeader(tablePrediction);
            configurarColoresHeader(tableTemp);

            // Actualizar las tablas con los datos del día actual
            actualizarTablaImgPrediccion(dia);
            actualizarTablaPrediccion(dia);

            // Crear panel para cada tabla y una para ambas
            JPanel jPanelCenterTable = new JPanel(new GridLayout(1, 2, 10, 10));
            JPanel centerLeftPanel = new JPanel(new BorderLayout());
            JPanel centerRightPanel = new JPanel(new BorderLayout());

            jPanelCenterTable.setBackground(BACKGROUND_COLOR);
            jPanelCenterTable.setOpaque(true);

            // Agregar encabezados de cada tabla explícitamente
            centerLeftPanel.add(tablePrediction.getTableHeader(), BorderLayout.NORTH);
            centerRightPanel.add(tableTemp.getTableHeader(), BorderLayout.NORTH);

            // Agregar las tablas al centro del BorderLayout
            centerLeftPanel.add(tablePrediction, BorderLayout.CENTER);
            centerRightPanel.add(tableTemp, BorderLayout.CENTER);

            // Agregar los paneles con las tablas al panel central
            jPanelCenterTable.add(centerLeftPanel);
            jPanelCenterTable.add(centerRightPanel);

            // Otros datos
            JPanel JPanelDatos = getDatosjPanel(dia);

            JPanelDatos.setBackground(BACKGROUND_COLOR);
            JPanelDatos.setOpaque(true);

            // Agregar las tablas y otros datos al panel central
            jPanelCenter.add(jPanelCenterTable, BorderLayout.CENTER);
            jPanelCenter.add(JPanelDatos, BorderLayout.SOUTH);

            jPanelCenter.setBackground(BACKGROUND_COLOR);
            jPanelCenter.setOpaque(true);

            // Agregar el panel de predicción como una pestaña en el JTabbedPane
            jTabbedPane.addTab(formatLocalDateTime(dia.getDataPredicion()), jPanelCenter);
        }

        jPanelMain.add(jTabbedPane, BorderLayout.CENTER);

        // Panel de detalles del Concello
        JPanel concelloPanel = new JPanel(new BorderLayout());
        JLabel labelConcello = new JLabel("Concello: " + prediccion.getConcello().getNome() +
                " (ID: " + prediccion.getConcello().getIdConcello() + ")");
        concelloPanel.add(labelConcello, BorderLayout.WEST);
        jPanelMain.add(concelloPanel, BorderLayout.NORTH);
    }

    private static void configurarColoresHeader(JTable table) {
        // Configurar encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new CellBorderRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                headerLabel.setBorder(null); // Elimina el borde en el encabezado

                headerLabel.setBackground(HEADER_BACKGROUND);
                headerLabel.setForeground(HEADER_TEXT_COLOR);
                headerLabel.setFont(header.getFont().deriveFont(Font.BOLD, 12f));
                return headerLabel;
            }
        });
    }

    private JPanel getDatosjPanel(PrediccionDia dia) {
        JPanel JPanelDatos = new JPanel(new GridLayout(4, 1));
        JLabel labelFecha = new JLabel("Fecha: " + dia.getDataPredicion());
        JLabel labelTMax = new JLabel("T. Máxima: " + dia.gettMax());
        JLabel labelTMin = new JLabel("T. Mínima: " + dia.gettMin());
        JLabel labelUvMax = new JLabel("UV Max: " + dia.getUvMaz());
        labelFecha.setForeground(TEXT_BRIGHT);
        labelTMax.setForeground(TEXT_BRIGHT);
        labelTMin.setForeground(TEXT_BRIGHT);
        labelUvMax.setForeground(TEXT_BRIGHT);
        JPanelDatos.add(labelFecha);
        JPanelDatos.add(labelTMax);
        JPanelDatos.add(labelTMin);
        JPanelDatos.add(labelUvMax);
        return JPanelDatos;
    }

    private String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

    private void actualizarTablaImgPrediccion(PrediccionDia dia) {
        // Limpiar la tabla antes de agregar filas
        tableImgModel.setRowCount(0);

        // Verificar si el día de predicción tiene variables
        if (dia.getListaVariableFranxa().isEmpty()) return;

        // Agregar filas de VariableFranxa para cada variable en el día de predicción
        for (VariableFranxa variableFranxa : dia.getListaVariableFranxa()) {
            if (variableFranxa.getValorManha() instanceof EstadoVento) {
                String imgTemplateWind = "https://www.meteogalicia.gal/datosred/infoweb/meteo/imagenes/meteoros/vento/<Valor_Numerico>.png";

                Object[] row = {
                        new JLabel(variableFranxa.getVariableMeteoroloxica().getNome()),
                        addImg(imgTemplateWind, variableFranxa.getValorManha().getCodigo()),
                        addImg(imgTemplateWind, variableFranxa.getValorTarde().getCodigo()),
                        addImg(imgTemplateWind, variableFranxa.getValorNoche().getCodigo())
                };
                tableImgModel.addRow(row);
            }
            if (variableFranxa.getValorManha() instanceof EstadoCeo) {
                String imgTemplateDay = "https://www.meteogalicia.gal/datosred/infoweb/meteo/imagenes/meteoros/ceo/<Valor_Numerico>.png";
                String imgTemplateNight = "https://www.meteogalicia.gal/datosred/infoweb/meteo/imagenes/meteoros/ceo/<Valor_Numerico>_fondo.png";

                Object[] row = {
                        new JLabel(variableFranxa.getVariableMeteoroloxica().getNome()),
                        addImg(imgTemplateDay, variableFranxa.getValorManha().getCodigo()),
                        addImg(imgTemplateDay, variableFranxa.getValorTarde().getCodigo()),
                        addImg(imgTemplateNight, variableFranxa.getValorNoche().getCodigo())
                };
                tableImgModel.addRow(row);
            }
        }
    }

    private void actualizarTablaPrediccion(PrediccionDia dia) {
        // Limpiar la tabla antes de agregar filas
        tableTempMode.setRowCount(0);

        // Verificar si el día de predicción tiene variables
        if (dia.getListaVariableFranxa().isEmpty()) return;

        // Agregar filas de VariableFranxa para cada variable en el día de predicción
        for (VariableFranxa variableFranxa : dia.getListaVariableFranxa()) {
            // Solo procesamos si la variable no es de tipo EstadoVento ni EstadoCeo
            if (!(variableFranxa.getValorManha() instanceof EstadoVento || variableFranxa.getValorManha() instanceof EstadoCeo)) {
                Object[] row;
                if (variableFranxa.getVariableMeteoroloxica().getNome().equals("pchoiva")) {
                    row = new Object[]{
                            variableFranxa.getVariableMeteoroloxica().getNome(),
                            formatEstadoChuvia(variableFranxa.getValorManha()),
                            formatEstadoChuvia(variableFranxa.getValorTarde()),
                            formatEstadoChuvia(variableFranxa.getValorNoche())
                    };
                } else {
                    row = new Object[]{
                            variableFranxa.getVariableMeteoroloxica().getNome(),
                            formatEstadoTemp(variableFranxa.getValorManha()),
                            formatEstadoTemp(variableFranxa.getValorTarde()),
                            formatEstadoTemp(variableFranxa.getValorNoche())
                    };
                }
                tableTempMode.addRow(row);
            }
        }
    }

    private String formatEstadoChuvia(EstadoUtils estado) {
        if (estado == null || estado.getNome().equals("-9999")) return "N/A";
        return estado.getNome() + "%";
    }
    private String formatEstadoTemp(EstadoUtils estado) {
        if (estado == null || estado.getNome().equals("-9999")) return "N/A";
        return estado.getNome() + "º";
    }

    public JLabel addImg(String imgTemplate, int codigo) {
        String imgURL = imgTemplate.replaceAll("<Valor_Numerico>", String.valueOf(codigo));
        URL url = null;
        try {
            url = new URL(imgURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ImageIcon imagen = new ImageIcon(url);
        return new JLabel(imagen);
    }
}
