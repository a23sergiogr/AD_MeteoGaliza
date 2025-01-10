package meteogaliza.Vista;

import meteogaliza.modelo.objetos.Concello;

import javax.swing.*;
import java.awt.*;

public class SeleccionConcello extends JFrame {
    Concello concello;
    public SeleccionConcello(int idConcello){
        this.concello = new Concello(idConcello);
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                 IllegalAccessException e) {
            System.err.println("Problema cargando Look and Feel");
        }
    }

    public void crearVista(){
        // Configuración de la interfaz
        setTitle("Selecciona Concello");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea jTextArea = new JTextArea(1,20);
    }
}
