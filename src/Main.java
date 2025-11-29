package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class Main extends JFrame {

    
    public static final String DB_URL = "jdbc:mysql://localhost:3306/proyecto_medc2";
    public static final String USER = "root";
    public static final String PASSWORD = "root"; 

        // --- ESTILOS UI/UX MEJORADOS ---
    private final Color COLOR_PRIMARY = new Color(41, 128, 185);      // Azul profesional
    private final Color COLOR_PRIMARY_DARK = new Color(31, 97, 141);  // Azul hover
    private final Color TEXT_COLOR = new Color(0, 0, 0);          // Gris oscuro texto
    private final Color COLOR_SUCCESS = new Color(39, 174, 96);       // Verde éxito
    private final Color COLOR_WARNING = new Color(243, 156, 18);      // Naranja advertencia
    private final Color COLOR_DANGER = new Color(231, 76, 60);        // Rojo error
    private final Color COLOR_BG = new Color(236, 240, 241);          // Gris claro fondo
    private final Color COLOR_CARD = Color.WHITE;                     // Blanco para paneles
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_TEXT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 12);

    public Main() {
        
        setTitle("Sistema de Gestión Médica - Proyecto MEDC2");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Aplicar LookAndFeel del Sistema Operativo
         try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Personalizar colores globales de UI
            UIManager.put("TabbedPane.selected", COLOR_PRIMARY);
            UIManager.put("Table.selectionBackground", COLOR_PRIMARY);
            UIManager.put("Table.selectionForeground", Color.WHITE);
        } catch (Exception ignored) {}

        // Contenedor Principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
       // Sistema de Pestañas con estilo mejorado
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(FONT_TITLE);
        tabbedPane.setBackground(COLOR_CARD);
        tabbedPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        // --- AGREGAR PESTAÑAS ---
        tabbedPane.addTab("Vistas SQL", createPanelVistas());
        tabbedPane.addTab("Reportes (Procedimientos)", createPanelReportes());
        tabbedPane.addTab("Inventario (Triggers)", createPanelInventario());
        tabbedPane.addTab("Compras y Seguimiento", createPanelCompras());
        tabbedPane.addTab("Gestión Pacientes", createPanelPacientes()); // Nueva pestaña
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    // =================================================================================
    // PESTAÑA 1: VISTAS (Adaptadas a tus scripts)
    // =================================================================================
    private JPanel createPanelVistas() {
         JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(COLOR_BG);

        // Título de sección
        JLabel titleLabel = new JLabel("📊 Consultas y Vistas SQL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_PRIMARY_DARK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Panel de Botones con mejor distribución
        JPanel btnPanel = new JPanel(new GridLayout(5, 3, 12, 12));
        btnPanel.setBackground(COLOR_BG);
        btnPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                "Seleccione una consulta",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_SUBTITLE,
                COLOR_PRIMARY_DARK
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Botones basados en tus scripts SELECT
       JButton btnCitasDesc = createStyledButton("📅 Citas Recientes");
        btnCitasDesc.setToolTipText("Ver todas las citas ordenadas por fecha descendente");

        JButton btnPacientesAsc = createStyledButton("👥 Pacientes A-Z");
        btnPacientesAsc.setToolTipText("Ver pacientes ordenados alfabéticamente por apellido");

        JButton btnMedicosEsp = createStyledButton("👨‍⚕️ Médicos por Especialidad");
        btnMedicosEsp.setToolTipText("Ver médicos ordenados por especialidad");

        JButton btnProdCant = createStyledButton("📦 Productos por Stock");
        btnProdCant.setToolTipText("Ver productos ordenados por cantidad disponible");

        JButton btnCitasAll = createStyledButton("📋 Todas las Citas");
        btnCitasAll.setToolTipText("Ver el listado completo de citas médicas");

        JButton btnPacientesAll = createStyledButton("👤 Todos los Pacientes");
        btnPacientesAll.setToolTipText("Ver el listado completo de pacientes registrados");

        JButton btnMedicosAll = createStyledButton("🩺 Todos los Médicos");
        btnMedicosAll.setToolTipText("Ver el listado completo del personal médico");

        JButton btnProdAll = createStyledButton("💊 Todos los Productos");
        btnProdAll.setToolTipText("Ver el inventario completo de productos");

        JButton btnComprasAll = createStyledButton("🛍️ Todas las Compras");
        btnComprasAll.setToolTipText("Ver el historial completo de compras");
        
        // Botones de Agregación y JOINs
        JButton btnCitasMedico = createStyledButton("📊 Citas por Médico");
        btnCitasMedico.setToolTipText("Ver estadísticas de citas agrupadas por médico");

        JButton btnCitasMotivo = createStyledButton("📈 Citas por Motivo");
        btnCitasMotivo.setToolTipText("Ver estadísticas de citas agrupadas por motivo de consulta");

        JButton btnCitasConsultorio = createStyledButton("🏥 Citas por Consultorio");
        btnCitasConsultorio.setToolTipText("Ver estadísticas de citas agrupadas por consultorio");

        JButton btnUnion = createStyledButton("🔗 Vista Unificada");
        btnUnion.setToolTipText("Ver datos combinados de compras y productos (UNION)");

        JTable table = createStyledTable();
        
        // --- ACCIONES ---
        
        // 1. SELECT idcita, fecha, motivo, hora FROM cita ORDER BY fecha DESC;
        btnCitasDesc.addActionListener(e -> llenarTabla(table, 
            "SELECT idcita, fecha, motivo, hora FROM cita ORDER BY fecha DESC"));

        // 2. SELECT idpacientes... FROM paciente ORDER BY apellidoPaterno ASC;
        btnPacientesAsc.addActionListener(e -> llenarTabla(table, 
            "SELECT idpacientes, nombrePaciente, apellidoPaterno, apellidoMaterno FROM paciente ORDER BY apellidoPaterno ASC"));

        // 3. SELECT idmedicos... FROM medico ORDER BY especialidad ASC;
        btnMedicosEsp.addActionListener(e -> llenarTabla(table, 
            "SELECT idmedicos, nombreMedico, especialidad FROM medico ORDER BY especialidad ASC"));

        // 4. SELECT nombreProducto, cantidad FROM productos ORDER BY cantidad DESC;
        btnProdCant.addActionListener(e -> llenarTabla(table, 
            "SELECT nombreProducto, cantidad FROM productos ORDER BY cantidad DESC"));

        // 5. JOIN Medico - Cita (COUNT)
        btnCitasMedico.addActionListener(e -> llenarTabla(table, 
            "SELECT m.nombreMedico, COUNT(c.idcita) AS total_citas FROM medico m LEFT JOIN cita c ON m.idmedicos = c.id_medico GROUP BY m.idmedicos, m.nombreMedico"));

        // 6. Citas por Motivo
        btnCitasMotivo.addActionListener(e -> llenarTabla(table, 
            "SELECT motivo, COUNT(*) AS total FROM cita GROUP BY motivo"));

        // 7. Citas por Consultorio
        btnCitasConsultorio.addActionListener(e -> llenarTabla(table, 
            "SELECT co.direccion AS consultorio, COUNT(c.idcita) AS total_citas FROM consultorio co LEFT JOIN cita c ON co.idconsultorio = c.id_consultorio GROUP BY co.idconsultorio, co.direccion"));

        // 8. UNION Query
        btnUnion.addActionListener(e -> llenarTabla(table, 
            "SELECT idcompras AS ID, cantidad FROM compras UNION SELECT idproductos AS ID, cantidad FROM productos"));

            //Botones para ver todas las tablas
        btnCitasAll.addActionListener(e -> llenarTabla(table, "SELECT * FROM cita"));
        btnPacientesAll.addActionListener(e -> llenarTabla(table, "SELECT * FROM paciente"));
        btnMedicosAll.addActionListener(e -> llenarTabla(table, "SELECT * FROM medico"));
        btnProdAll.addActionListener(e -> llenarTabla(table, "SELECT * FROM productos"));
        btnComprasAll.addActionListener(e -> llenarTabla(table, "SELECT * FROM compras"));
        
        btnPanel.add(btnCitasDesc);
        btnPanel.add(btnPacientesAsc);
        btnPanel.add(btnMedicosEsp);
        btnPanel.add(btnProdCant);
        btnPanel.add(btnCitasMedico);
        btnPanel.add(btnCitasMotivo);
        btnPanel.add(btnCitasConsultorio);
        btnPanel.add(btnUnion);
        btnPanel.add(btnCitasAll);
        btnPanel.add(btnPacientesAll);
        btnPanel.add(btnMedicosAll);
        btnPanel.add(btnProdAll);
        btnPanel.add(btnComprasAll);

        // Panel contenedor para botones
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_BG);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(btnPanel, BorderLayout.CENTER);

        // Tabla con mejor estilo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                "Resultados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_SUBTITLE,
                COLOR_PRIMARY_DARK
            ),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================================
    // PESTAÑA 2: REPORTES (Stored Procedures Actualizados)
    // =================================================================================
    private JPanel createPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "Ejecutar Procedimientos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_TITLE, COLOR_PRIMARY));

        JTextField txtFecha = new JTextField("2025-11-26", 10);
        JTextField txtAnio = new JTextField("2025", 6);
        JTable tableDetalle = createStyledTable();
        JTable tablePromedio = createStyledTable();

        JButton btnComprasDiarias = createStyledButton("comprasdiarias (Fecha)");
        JButton btnReporteClientes = createStyledButton("reporteclientes (Año)");

        // SP: comprasdiarias
        btnComprasDiarias.addActionListener(e -> {
            cargarReporteConPromedio(tableDetalle, tablePromedio, txtFecha.getText());
        });

        // SP: ReporteClientes
        btnReporteClientes.addActionListener(e -> {
        ((DefaultTableModel) tablePromedio.getModel()).setRowCount(0);
        ((DefaultTableModel) tablePromedio.getModel()).setColumnCount(0);
            ejecutarProcedimiento(tableDetalle, "{CALL ReporteClientes(?)}", txtAnio.getText(), true); 
        });

        controlPanel.add(new JLabel("Fecha:"));
        controlPanel.add(txtFecha);
        controlPanel.add(btnComprasDiarias);
        controlPanel.add(Box.createHorizontalStrut(25)); 
        controlPanel.add(new JLabel("Año:"));
        controlPanel.add(txtAnio);
        controlPanel.add(btnReporteClientes);


        JScrollPane scrollPanelDetalle = new JScrollPane(tableDetalle);
        scrollPanelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de Compras"));
        
        JScrollPane scrollPanelPromedio = new JScrollPane(tablePromedio);
        scrollPanelPromedio.setBorder(BorderFactory.createTitledBorder("Promedio de Compras por Cliente"));
        scrollPanelPromedio.setPreferredSize(new Dimension(0, 200));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPanelDetalle, scrollPanelPromedio);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(300);
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }
/**
 * NUEVO MÉTODO AUXILIAR: Maneja múltiples resultados (ResultSets) de un SP.
 * Llena tableDetalle con el primer SELECT y tablePromedio con el segundo.
 */
private void cargarReporteConPromedio(JTable tableDetalle, JTable tablePromedio, String fecha) {
    DefaultTableModel modelDetalle = (DefaultTableModel) tableDetalle.getModel();
    DefaultTableModel modelPromedio = (DefaultTableModel) tablePromedio.getModel();
    
    // Limpiar ambas tablas antes de cargar
    modelDetalle.setRowCount(0); modelDetalle.setColumnCount(0);
    modelPromedio.setRowCount(0); modelPromedio.setColumnCount(0);

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         CallableStatement cstmt = conn.prepareCall("{CALL comprasdiarias(?)}")) {

        cstmt.setString(1, fecha);
        
        // Ejecutar y verificar si hay un primer resultado
        boolean hasResults = cstmt.execute(); 
        int contadorTablas = 0;

        // BUCLE: Recorremos todos los resultados que envíe el SP
        while (hasResults || cstmt.getUpdateCount() != -1) {
            if (hasResults) {
                try (ResultSet rs = cstmt.getResultSet()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    
                    // LÓGICA: Si es el 1er resultado -> Detalle. Si es el 2do -> Promedio
                    DefaultTableModel targetModel = (contadorTablas == 0) ? modelDetalle : modelPromedio;

                    
                    for (int i = 1; i <= colCount; i++) {
                        targetModel.addColumn(meta.getColumnLabel(i));
                    }

                    // Llenar filas
                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        for (int i = 1; i <= colCount; i++) {
                            row.add(rs.getObject(i));
                        }
                        targetModel.addRow(row);
                    }
                }
                contadorTablas++;
            }
            
            hasResults = cstmt.getMoreResults();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error SQL: " + e.getMessage());
    }
}
    // =================================================================================
    // PESTAÑA 3: INVENTARIO (Trigger no_duplicados)
    // =================================================================================
    private JPanel createPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JTextField txtNombre = new JTextField(15);
        JTextField txtCantidad = new JTextField(5);
        JTextField txtCategoria = new JTextField(10);
        JTable table = createStyledTable();

        JButton btnInsertar = createStyledButton("Agregar Producto");
        JButton btnActualizar = createStyledButton("Ver Todos");

        // Acción: Insertar Producto (Probando Trigger no_duplicados)
        btnInsertar.addActionListener(e -> {
            String sql = "INSERT INTO productos (nombreProducto, cantidad, categoria) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, txtNombre.getText());
                pstmt.setInt(2, Integer.parseInt(txtCantidad.getText()));
                pstmt.setString(3, txtCategoria.getText());
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Producto agregado.");
                llenarTabla(table, "SELECT * FROM productos ORDER BY idproductos DESC");
                
            } catch (SQLException ex) {
                // Captura SIGNAL SQLSTATE '45000' del trigger
                if ("45000".equals(ex.getSQLState())) {
                    JOptionPane.showMessageDialog(this, "¡ALERTA TRIGGER!\n" + ex.getMessage(), "Producto Duplicado", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número.");
            }
        });

        btnActualizar.addActionListener(e -> llenarTabla(table, "SELECT * FROM productos"));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(new JLabel("Producto:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(txtCantidad);
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(txtCategoria);
        formPanel.add(btnInsertar);
        formPanel.add(btnActualizar);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        llenarTabla(table, "SELECT * FROM productos");
        return panel;
    }

    // =================================================================================
    // PESTAÑA 4: COMPRAS Y SEGUIMIENTO (Triggers actualizar_producto y seguimiento)
    // =================================================================================
    private JPanel createPanelCompras() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        // Formulario
        JTextField txtIdPaciente = new JTextField("", 5);
        JTextField txtIdProducto = new JTextField(5);
        JTextField txtCantidad = new JTextField(5);
        JTextField txtPrecio = new JTextField(8);

        JButton btnComprar = createStyledButton("Registrar Compra");
        JButton btnVerSeguimiento = createStyledButton("Ver Tabla Seguimiento");
        JButton btnVerCompras = createStyledButton("Ver Compras");

        JTable table = createStyledTable();

        // Acción: Insertar Compra
        // Esto dispara: 1. trigger 'actualizar_producto' (resta inventario)
        //               2. trigger 'trg_seguimiento_compra' (llena tabla seguimiento)
        btnComprar.addActionListener(e -> {
            String sql = "INSERT INTO compras (idPaciente, fecha, cantidad, precioUnitario, productos_id) VALUES (?, CURDATE(), ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, Integer.parseInt(txtIdPaciente.getText()));
                pstmt.setInt(2, Integer.parseInt(txtCantidad.getText()));
                pstmt.setDouble(3, Double.parseDouble(txtPrecio.getText()));
                pstmt.setInt(4, Integer.parseInt(txtIdProducto.getText())); // Necesario para el trigger actualizar_producto
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Compra registrada.\n- Inventario actualizado.\n- Seguimiento creado.");
                llenarTabla(table, "SELECT * FROM compras ORDER BY idcompras DESC");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al comprar: " + ex.getMessage());
            }
        });

        btnVerSeguimiento.addActionListener(e -> llenarTabla(table, "SELECT * FROM seguimiento ORDER BY idSeguimiento DESC"));
        btnVerCompras.addActionListener(e -> llenarTabla(table, "SELECT * FROM compras ORDER BY idcompras DESC"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Nueva Compra"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridx=0; gbc.gridy=0; formPanel.add(new JLabel("ID Paciente:"), gbc);
        gbc.gridx=1; gbc.gridy=0; formPanel.add(txtIdPaciente, gbc);
        gbc.gridx=2; gbc.gridy=0; formPanel.add(new JLabel("ID Producto:"), gbc);
        gbc.gridx=3; gbc.gridy=0; formPanel.add(txtIdProducto, gbc);
        
        gbc.gridx=0; gbc.gridy=1; formPanel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx=1; gbc.gridy=1; formPanel.add(txtCantidad, gbc);
        gbc.gridx=2; gbc.gridy=1; formPanel.add(new JLabel("Precio Unit:"), gbc);
        gbc.gridx=3; gbc.gridy=1; formPanel.add(txtPrecio, gbc);
        
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=4; 
        JPanel btnContainer = new JPanel();
        btnContainer.setBackground(Color.WHITE);
        btnContainer.add(btnComprar);
        btnContainer.add(btnVerCompras);
        btnContainer.add(btnVerSeguimiento);
        formPanel.add(btnContainer, gbc);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // =================================================================================
    // PESTAÑA 5: GESTIÓN PACIENTES (SP RegistrarPacienteUnico)
    // =================================================================================
    private JPanel createPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JTextField txtNom = new JTextField(10);
        JTextField txtApP = new JTextField(10);
        JTextField txtApM = new JTextField(10);
        JTextField txtTel = new JTextField(10);
        JTextField txtMail = new JTextField(15);
        JTextField txtDir = new JTextField(15);

        JButton btnRegistrar = createStyledButton("Registrar Paciente");
        JTable table = createStyledTable();

        // Llamada al SP RegistrarPacienteUnico
        btnRegistrar.addActionListener(e -> {
            String call = "{CALL RegistrarPacienteUnico(?, ?, ?, ?, ?, ?)}";
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 CallableStatement cstmt = conn.prepareCall(call)) {
                
                cstmt.setString(1, txtNom.getText());
                cstmt.setString(2, txtApP.getText());
                cstmt.setString(3, txtApM.getText());
                cstmt.setString(4, txtTel.getText());
                cstmt.setString(5, txtMail.getText());
                cstmt.setString(6, txtDir.getText());

                boolean hasResult = cstmt.execute();
                
                // El SP devuelve un SELECT con un mensaje ('mensaje' si error, 'registro' si éxito)
                if (hasResult) {
                    try (ResultSet rs = cstmt.getResultSet()) {
                        if (rs.next()) {
                            
                            String respuesta = rs.getString(1);
                            JOptionPane.showMessageDialog(this, respuesta);
                        }
                    }
                }
                llenarTabla(table, "SELECT * FROM paciente ORDER BY idpacientes DESC");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        JPanel formPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Nuevo Paciente (Verifica Unicidad)"));
        
        formPanel.add(new JLabel("Nombre:")); formPanel.add(txtNom);
        formPanel.add(new JLabel("Ap. Paterno:")); formPanel.add(txtApP);
        formPanel.add(new JLabel("Ap. Materno:")); formPanel.add(txtApM);
        formPanel.add(new JLabel("Teléfono:")); formPanel.add(txtTel);
        formPanel.add(new JLabel("Correo:")); formPanel.add(txtMail);
        formPanel.add(new JLabel("Dirección:")); formPanel.add(txtDir);
        formPanel.add(new JLabel("")); formPanel.add(btnRegistrar);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        llenarTabla(table, "SELECT * FROM paciente");
        return panel;
    }

    // =================================================================================
    // MÉTODOS AUXILIARES
    // =================================================================================

    private void llenarTabla(JTable table, String query) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); model.setColumnCount(0);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            for (int i = 1; i <= colCount; i++) model.addColumn(meta.getColumnLabel(i));
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= colCount; i++) row.add(rs.getObject(i));
                model.addRow(row);
            }
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void ejecutarProcedimiento(JTable table, String call, String param, boolean isInt) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); model.setColumnCount(0);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             CallableStatement cstmt = conn.prepareCall(call)) {
            
            if (isInt) cstmt.setInt(1, Integer.parseInt(param));
            else cstmt.setString(1, param);

            if (cstmt.execute()) {
                try (ResultSet rs = cstmt.getResultSet()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    for (int i = 1; i <= colCount; i++) model.addColumn(meta.getColumnLabel(i));
                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        for (int i = 1; i <= colCount; i++) row.add(rs.getObject(i));
                        model.addRow(row);
                    }
                }
            } else { JOptionPane.showMessageDialog(this, "Ejecutado sin resultados."); }
        } catch (SQLException | NumberFormatException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_TEXT);
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.BLACK); 
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTable createStyledTable() {
        JTable table = new JTable(new DefaultTableModel());
        table.setRowHeight(25);
        table.setFont(FONT_TEXT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}