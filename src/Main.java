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
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(COLOR_BG);

        // Título de sección
        JLabel titleLabel = new JLabel("📈 Reportes y Procedimientos Almacenados");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_PRIMARY_DARK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setBackground(COLOR_CARD);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));

        JTextField txtFecha = createStyledTextField("2025-11-26", 12);
        JTextField txtAnio = createStyledTextField("2025", 8);
        JTable tableDetalle = createStyledTable();
        JTable tablePromedio = createStyledTable();

        JButton btnComprasDiarias = createStyledButton("📅 Compras Diarias", COLOR_SUCCESS);
        btnComprasDiarias.setToolTipText("Ejecutar procedimiento almacenado de compras del día especificado");

        JButton btnReporteClientes = createStyledButton("👥 Reporte Clientes", COLOR_PRIMARY);
        btnReporteClientes.setToolTipText("Generar reporte de clientes para el año especificado");

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

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(FONT_TEXT);
        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setFont(FONT_TEXT);

        controlPanel.add(lblFecha);
        controlPanel.add(txtFecha);
        controlPanel.add(btnComprasDiarias);
        controlPanel.add(Box.createHorizontalStrut(30)); 
        controlPanel.add(lblAnio);
        controlPanel.add(txtAnio);
        controlPanel.add(btnReporteClientes);


        JScrollPane scrollPanelDetalle = new JScrollPane(tableDetalle);
        scrollPanelDetalle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 1),
            "📋 Detalle de Compras",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SUBTITLE,
            COLOR_PRIMARY_DARK
        ));
        scrollPanelDetalle.getViewport().setBackground(Color.WHITE);
        
        JScrollPane scrollPanelPromedio = new JScrollPane(tablePromedio);
        scrollPanelPromedio.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_SUCCESS, 1),
            "📊 Promedio de Compras por Cliente",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SUBTITLE,
            COLOR_PRIMARY_DARK
        ));
        scrollPanelPromedio.setPreferredSize(new Dimension(0, 220));
        scrollPanelPromedio.getViewport().setBackground(Color.WHITE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPanelDetalle, scrollPanelPromedio);
        splitPane.setResizeWeight(0.6);
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setBackground(COLOR_BG);
        topContainer.add(titleLabel, BorderLayout.NORTH);
        topContainer.add(controlPanel, BorderLayout.CENTER);

        panel.add(topContainer, BorderLayout.NORTH);
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
         JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(COLOR_BG);

        // Título de sección
        JLabel titleLabel = new JLabel("📦 Gestión de Inventario (Triggers)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_PRIMARY_DARK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JTextField txtNombre = createStyledTextField("", 20);
        txtNombre.setToolTipText("Ingrese el nombre del producto");
        JTextField txtCantidad = createStyledTextField("", 8);
        txtCantidad.setToolTipText("Ingrese la cantidad");
        JTextField txtCategoria = createStyledTextField("", 15);
        txtCategoria.setToolTipText("Ingrese la categoría");
        JTable table = createStyledTable();

         JButton btnInsertar = createStyledButton("➕ Agregar Producto", COLOR_SUCCESS);
        btnInsertar.setToolTipText("Agregar un nuevo producto al inventario (verifica duplicados)");

        JButton btnActualizar = createStyledButton("🔄 Ver Todos", COLOR_PRIMARY);
        btnActualizar.setToolTipText("Actualizar y mostrar todos los productos del inventario");

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

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        formPanel.setBackground(COLOR_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setFont(FONT_TEXT);
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(FONT_TEXT);
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(FONT_TEXT);
        formPanel.add(lblProducto);
        formPanel.add(txtNombre);
        formPanel.add(lblCantidad);
        formPanel.add(txtCantidad);
        formPanel.add(lblCategoria);
        formPanel.add(txtCategoria);
        formPanel.add(btnInsertar);
        formPanel.add(btnActualizar);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            "📊 Productos en Inventario",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SUBTITLE,
            COLOR_PRIMARY_DARK
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setBackground(COLOR_BG);
        topContainer.add(titleLabel, BorderLayout.NORTH);
        topContainer.add(formPanel, BorderLayout.CENTER);

        panel.add(topContainer, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        llenarTabla(table, "SELECT * FROM productos");
        return panel;
    }

    // =================================================================================
    // PESTAÑA 4: COMPRAS Y SEGUIMIENTO (Triggers actualizar_producto y seguimiento)
    // =================================================================================
    private JPanel createPanelCompras() {
       JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(COLOR_BG);

        // Título de sección
        JLabel titleLabel = new JLabel("🛒 Compras y Seguimiento (Triggers)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_PRIMARY_DARK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Formulario con tooltips
        JTextField txtIdPaciente = createStyledTextField("", 8);
        txtIdPaciente.setToolTipText("ID del paciente");
        JTextField txtIdProducto = createStyledTextField("", 8);
        txtIdProducto.setToolTipText("ID del producto");
        JTextField txtCantidad = createStyledTextField("", 8);
        txtCantidad.setToolTipText("Cantidad a comprar");
        JTextField txtPrecio = createStyledTextField("", 10);
        txtPrecio.setToolTipText("Precio unitario");

        JButton btnComprar = createStyledButton("💳 Registrar Compra", COLOR_SUCCESS);
        btnComprar.setToolTipText("Registrar una nueva compra y actualizar inventario automáticamente");

        JButton btnVerSeguimiento = createStyledButton("📋 Ver Seguimiento", COLOR_WARNING);
        btnVerSeguimiento.setToolTipText("Ver tabla de seguimiento de todas las transacciones");

        JButton btnVerCompras = createStyledButton("📊 Ver Compras", COLOR_PRIMARY);
        btnVerCompras.setToolTipText("Ver historial completo de compras realizadas");

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
        formPanel.setBackground(COLOR_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10), "Nueva Compra",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_SUBTITLE,
                COLOR_PRIMARY_DARK
            )
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        
        JLabel lblIdPaciente = new JLabel("ID Paciente:");
        lblIdPaciente.setFont(FONT_TEXT);
        JLabel lblIdProducto = new JLabel("ID Producto:");
        lblIdProducto.setFont(FONT_TEXT);
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(FONT_TEXT);
        JLabel lblPrecio = new JLabel("Precio Unit.:");
        lblPrecio.setFont(FONT_TEXT);

        gbc.gridx=0; gbc.gridy=0; formPanel.add(lblIdPaciente, gbc);
        gbc.gridx=1; gbc.gridy=0; formPanel.add(txtIdPaciente, gbc);
        gbc.gridx=2; gbc.gridy=0; formPanel.add(lblIdProducto, gbc);
        gbc.gridx=3; gbc.gridy=0; formPanel.add(txtIdProducto, gbc);
        
        gbc.gridx=0; gbc.gridy=1; formPanel.add(lblCantidad, gbc);
        gbc.gridx=1; gbc.gridy=1; formPanel.add(txtCantidad, gbc);
        gbc.gridx=2; gbc.gridy=1; formPanel.add(lblPrecio, gbc);
        gbc.gridx=3; gbc.gridy=1; formPanel.add(txtPrecio, gbc);
        
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=4; 
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnContainer.setBackground(COLOR_CARD);
        btnContainer.add(btnComprar);
        btnContainer.add(btnVerCompras);
        btnContainer.add(btnVerSeguimiento);
        formPanel.add(btnContainer, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            "📊 Historial",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SUBTITLE,
            COLOR_PRIMARY_DARK
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setBackground(COLOR_BG);
        topContainer.add(titleLabel, BorderLayout.NORTH);
        topContainer.add(formPanel, BorderLayout.CENTER);

        panel.add(topContainer, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================================
    // PESTAÑA 5: GESTIÓN PACIENTES (SP RegistrarPacienteUnico)
    // =================================================================================
    private JPanel createPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(COLOR_BG);

        // Título de sección
        JLabel titleLabel = new JLabel("👤 Gestión de Pacientes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_PRIMARY_DARK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JTextField txtNom = createStyledTextField("", 15);
        txtNom.setToolTipText("Nombre del paciente");
        JTextField txtApP = createStyledTextField("", 15);
        txtApP.setToolTipText("Apellido paterno");
        JTextField txtApM = createStyledTextField("", 15);
        txtApM.setToolTipText("Apellido materno");
        JTextField txtTel = createStyledTextField("", 15);
        txtTel.setToolTipText("Teléfono de contacto");
        JTextField txtMail = createStyledTextField("", 20);
        txtMail.setToolTipText("Correo electrónico");
        JTextField txtDir = createStyledTextField("", 20);
        txtDir.setToolTipText("Dirección");

        JButton btnRegistrar = createStyledButton("✅ Registrar Paciente", COLOR_SUCCESS);
        btnRegistrar.setToolTipText("Registrar un nuevo paciente verificando que no exista duplicado");
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

        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBackground(COLOR_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                "Nuevo Paciente (Verifica Unicidad)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_SUBTITLE,
                COLOR_PRIMARY_DARK
            )
        ));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(FONT_TEXT);
        JLabel lblApP = new JLabel("Ap. Paterno:");
        lblApP.setFont(FONT_TEXT);
        JLabel lblApM = new JLabel("Ap. Materno:");
        lblApM.setFont(FONT_TEXT);
        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setFont(FONT_TEXT);
        JLabel lblMail = new JLabel("Correo:");
        lblMail.setFont(FONT_TEXT);
        JLabel lblDir = new JLabel("Dirección:");
        lblDir.setFont(FONT_TEXT);
        
        formPanel.add(lblNombre); formPanel.add(txtNom);
        formPanel.add(lblApP); formPanel.add(txtApP);
        formPanel.add(lblApM); formPanel.add(txtApM);
        formPanel.add(lblTel); formPanel.add(txtTel);
        formPanel.add(lblMail); formPanel.add(txtMail);
        formPanel.add(lblDir); formPanel.add(txtDir);
        formPanel.add(new JLabel(""));
        formPanel.add(btnRegistrar);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            "📋 Pacientes Registrados",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SUBTITLE,
            COLOR_PRIMARY_DARK
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setBackground(COLOR_BG);
        topContainer.add(titleLabel, BorderLayout.NORTH);
        topContainer.add(formPanel, BorderLayout.CENTER);

        panel.add(topContainer, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
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
        return createStyledButton(text, COLOR_PRIMARY);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bgColor);
        btn.setForeground(TEXT_COLOR);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, 35));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        //Hover 
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }
    private JTextField createStyledTextField(String defaultText, int columns) {
        JTextField field = new JTextField(defaultText, columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo redondeado
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Borde
                if (hasFocus()) {
                    g2.setColor(COLOR_PRIMARY);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(new Color(189, 195, 199));
                    g2.setStroke(new BasicStroke(1));
                }
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        field.setFont(FONT_TEXT);
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(44, 62, 80));
        field.setCaretColor(COLOR_PRIMARY);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 38));

        // Focus effects con repaint para actualizar el borde
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.repaint();
            }
        });

        return field;
    }

    private JTable createStyledTable() {
       JTable table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setRowHeight(28);
         table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(COLOR_PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Estilo del header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(COLOR_PRIMARY_DARK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY_DARK));

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(44, 62, 80)); // Dark text color for readability
                } else {
                    c.setForeground(Color.WHITE); // White text when selected
                }
                setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return c;
            }
        });
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}