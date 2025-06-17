package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GUI extends JFrame {

    private JButton btnJNoRoll, btnJRoll, btnReset;
    private JTextArea areaMitabla, areaMiOtraTabla;

    public GUI() {
        setTitle("Transacciones en Java");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // --- Panel de botones ---
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setBackground(new Color(230, 240, 255));

        btnJNoRoll = new JButton("Ejecutar JNoRollback");
        btnJRoll = new JButton("Ejecutar JRollback");
        btnReset = new JButton("Resetear Tablas");

        Font btnFont = new Font("SansSerif", Font.BOLD, 14);
        btnJNoRoll.setFont(btnFont);
        btnJRoll.setFont(btnFont);
        btnReset.setFont(btnFont);

        panelBotones.add(btnJNoRoll);
        panelBotones.add(btnJRoll);
        panelBotones.add(btnReset);

        // --- Panel de resultados ---
        JPanel panelDatos = new JPanel(new GridLayout(1, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaMitabla = crearArea("mitabla");
        areaMiOtraTabla = crearArea("miotratabla");

        panelDatos.add(new JScrollPane(areaMitabla));
        panelDatos.add(new JScrollPane(areaMiOtraTabla));

        // --- Listeners ---
        btnJNoRoll.addActionListener(e -> {
            JNoRollback.main(null);
            actualizarTablas();
        });

        btnJRoll.addActionListener(e -> {
            JRollback.main(null);
            actualizarTablas();
        });

        btnReset.addActionListener(e -> {
            truncarTablas();
            actualizarTablas();
        });

        // --- Composición principal ---
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBotones, BorderLayout.WEST);
        getContentPane().add(panelDatos, BorderLayout.CENTER);

        actualizarTablas();
    }

    private JTextArea crearArea(String titulo) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createTitledBorder(titulo));
        area.setBackground(new Color(250, 250, 250));
        return area;
    }

    private void actualizarTablas() {
        areaMitabla.setText(cargarTabla("mitabla"));
        areaMiOtraTabla.setText(cargarTabla("miotratabla"));
    }

    private String cargarTabla(String tabla) {
        StringBuilder sb = new StringBuilder();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabla)) {

            ResultSetMetaData meta = rs.getMetaData();
            int col = meta.getColumnCount();

            
            for (int i = 1; i <= col; i++) {
                sb.append(String.format("%-15s", meta.getColumnName(i)));
            }
            sb.append("\n");

            
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    sb.append(String.format("%-15s", rs.getString(i)));
                }
                sb.append("\n");
            }

        } catch (SQLException e) {
            sb.append("❌ Error: ").append(e.getMessage());
        }
        return sb.toString();
    }

    private void truncarTablas() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("TRUNCATE TABLE mitabla");
            stmt.executeUpdate("TRUNCATE TABLE miotratabla");
            JOptionPane.showMessageDialog(this, "Tablas reiniciadas correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al resetear tablas:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
