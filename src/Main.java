import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InterfazMedicos extends JFrame {
    
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_medc2";
    private static final String USER = "root";
    private static final String PASS = "RafaRapter3ooo."; 

    public InterfazMedicos() {

        setTitle("Menú Proyecto Médicos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnOpcion1 = new JButton("Ejecutar Procedimiento 1");
        JButton btnOpcion2 = new JButton("Ejecutar Procedimiento 2");
        JButton btnOpcion3 = new JButton("Otra opción");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnOpcion1);
        panel.add(btnOpcion2);
        panel.add(btnOpcion3);
        panel.add(btnSalir);

        add(panel);

        // ----------------------------
        // OPCIÓN 1: procedimiento1
        // ----------------------------
        btnOpcion1.addActionListener(e -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                CallableStatement stmt = conn.prepareCall("{ CALL procedimiento1() }");
                stmt.execute();

                JOptionPane.showMessageDialog(this, "procedimiento1 ejecutado correctamente");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error ejecutando procedimiento1:\n" + ex.getMessage());
            }
        });

        // ----------------------------
        // OPCIÓN 2: procedimiento2 ✔ CORREGIDO
        // ----------------------------
        btnOpcion2.addActionListener(e -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

                CallableStatement stmt = conn.prepareCall("{ CALL procedimiento2() }");
                stmt.execute();

                JOptionPane.showMessageDialog(this, "procedimiento2 ejecutado correctamente");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error ejecutando procedimiento2:\n" + ex.getMessage());
            }
        });

        // ----------------------------
        // OPCIÓN 3
        // ----------------------------
        btnOpcion3.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opción 3 ejecutada");
        });

        // ----------------------------
        // SALIR
        // ----------------------------
        btnSalir.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazMedicos().setVisible(true));
    }
}
