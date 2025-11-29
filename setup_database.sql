-- Create Database
CREATE DATABASE IF NOT EXISTS proyecto_medc2;
USE proyecto_medc2;

-- ============================================
-- TABLES
-- ============================================

-- Table: medico
CREATE TABLE IF NOT EXISTS medico (
    idmedicos INT PRIMARY KEY AUTO_INCREMENT,
    nombreMedico VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL
);

-- Table: consultorio
CREATE TABLE IF NOT EXISTS consultorio (
    idconsultorio INT PRIMARY KEY AUTO_INCREMENT,
    direccion VARCHAR(200) NOT NULL
);

-- Table: paciente
CREATE TABLE IF NOT EXISTS paciente (
    idpacientes INT PRIMARY KEY AUTO_INCREMENT,
    nombrePaciente VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    direccion VARCHAR(200)
);

-- Table: cita
CREATE TABLE IF NOT EXISTS cita (
    idcita INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    hora TIME NOT NULL,
    id_medico INT,
    id_consultorio INT,
    id_paciente INT,
    FOREIGN KEY (id_medico) REFERENCES medico(idmedicos),
    FOREIGN KEY (id_consultorio) REFERENCES consultorio(idconsultorio),
    FOREIGN KEY (id_paciente) REFERENCES paciente(idpacientes)
);

-- Table: productos
CREATE TABLE IF NOT EXISTS productos (
    idproductos INT PRIMARY KEY AUTO_INCREMENT,
    nombreProducto VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    categoria VARCHAR(50)
);

-- Table: compras
CREATE TABLE IF NOT EXISTS compras (
    idcompras INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,
    productos_id INT,
    FOREIGN KEY (idPaciente) REFERENCES paciente(idpacientes),
    FOREIGN KEY (productos_id) REFERENCES productos(idproductos)
);

-- Table: seguimiento
CREATE TABLE IF NOT EXISTS seguimiento (
    idSeguimiento INT PRIMARY KEY AUTO_INCREMENT,
    idCompra INT,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    descripcion VARCHAR(255),
    FOREIGN KEY (idCompra) REFERENCES compras(idcompras)
);

-- ============================================
-- STORED PROCEDURES
-- ============================================

-- Procedure: comprasdiarias
-- Returns 2 result sets: daily purchases detail and average per customer
DROP PROCEDURE IF EXISTS comprasdiarias;
DELIMITER $$
CREATE PROCEDURE comprasdiarias(IN fecha_param DATE)
BEGIN
    -- First result set: Purchase details for the day
    SELECT 
        c.idcompras,
        c.idPaciente,
        p.nombrePaciente,
        c.cantidad,
        c.precioUnitario,
        c.cantidad * c.precioUnitario AS total,
        prod.nombreProducto
    FROM compras c
    INNER JOIN paciente p ON c.idPaciente = p.idpacientes
    LEFT JOIN productos prod ON c.productos_id = prod.idproductos
    WHERE c.fecha = fecha_param
    ORDER BY c.idcompras DESC;
    
    -- Second result set: Average purchases per customer for that day
    SELECT 
        p.nombrePaciente,
        AVG(c.cantidad * c.precioUnitario) AS promedio_compra
    FROM compras c
    INNER JOIN paciente p ON c.idPaciente = p.idpacientes
    WHERE c.fecha = fecha_param
    GROUP BY c.idPaciente, p.nombrePaciente;
END$$
DELIMITER ;

-- Procedure: ReporteClientes
-- Returns patient appointments for a specific year
DROP PROCEDURE IF EXISTS ReporteClientes;
DELIMITER $$
CREATE PROCEDURE ReporteClientes(IN anio INT)
BEGIN
    SELECT 
        p.idpacientes,
        p.nombrePaciente,
        p.apellidoPaterno,
        p.apellidoMaterno,
        COUNT(c.idcita) AS total_citas,
        GROUP_CONCAT(c.fecha ORDER BY c.fecha SEPARATOR ', ') AS fechas_citas
    FROM paciente p
    LEFT JOIN cita c ON p.idpacientes = c.id_paciente
    WHERE YEAR(c.fecha) = anio OR c.fecha IS NULL
    GROUP BY p.idpacientes, p.nombrePaciente, p.apellidoPaterno, p.apellidoMaterno
    ORDER BY total_citas DESC;
END$$
DELIMITER ;

-- Procedure: RegistrarPacienteUnico
-- Registers a patient only if they don't already exist (by full name)
DROP PROCEDURE IF EXISTS RegistrarPacienteUnico;
DELIMITER $$
CREATE PROCEDURE RegistrarPacienteUnico(
    IN p_nombre VARCHAR(100),
    IN p_apPaterno VARCHAR(100),
    IN p_apMaterno VARCHAR(100),
    IN p_telefono VARCHAR(20),
    IN p_correo VARCHAR(100),
    IN p_direccion VARCHAR(200)
)
BEGIN
    DECLARE existe INT DEFAULT 0;
    
    -- Check if patient already exists
    SELECT COUNT(*) INTO existe
    FROM paciente
    WHERE nombrePaciente = p_nombre
      AND apellidoPaterno = p_apPaterno
      AND apellidoMaterno = p_apMaterno;
    
    IF existe > 0 THEN
        -- Return error message
        SELECT 'El paciente ya existe en el sistema.' AS mensaje;
    ELSE
        -- Insert new patient
        INSERT INTO paciente (nombrePaciente, apellidoPaterno, apellidoMaterno, telefono, correo, direccion)
        VALUES (p_nombre, p_apPaterno, p_apMaterno, p_telefono, p_correo, p_direccion);
        
        -- Return success message
        SELECT 'Paciente registrado exitosamente.' AS registro;
    END IF;
END$$
DELIMITER ;

-- ============================================
-- TRIGGERS
-- ============================================

-- Trigger: no_duplicados
-- Prevents duplicate products by name
DROP TRIGGER IF EXISTS no_duplicados;
DELIMITER $$
CREATE TRIGGER no_duplicados
BEFORE INSERT ON productos
FOR EACH ROW
BEGIN
    DECLARE existe INT DEFAULT 0;
    
    SELECT COUNT(*) INTO existe
    FROM productos
    WHERE nombreProducto = NEW.nombreProducto;
    
    IF existe > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Este producto ya existe en el inventario.';
    END IF;
END$$
DELIMITER ;

-- Trigger: actualizar_producto
-- Updates product inventory when a purchase is made
DROP TRIGGER IF EXISTS actualizar_producto;
DELIMITER $$
CREATE TRIGGER actualizar_producto
AFTER INSERT ON compras
FOR EACH ROW
BEGIN
    UPDATE productos
    SET cantidad = cantidad - NEW.cantidad
    WHERE idproductos = NEW.productos_id;
END$$
DELIMITER ;

-- Trigger: trg_seguimiento_compra
-- Creates a tracking record for each purchase
DROP TRIGGER IF EXISTS trg_seguimiento_compra;
DELIMITER $$
CREATE TRIGGER trg_seguimiento_compra
AFTER INSERT ON compras
FOR EACH ROW
BEGIN
    INSERT INTO seguimiento (idCompra, descripcion)
    VALUES (NEW.idcompras, CONCAT('Compra registrada: ', NEW.cantidad, ' unidades'));
END$$
DELIMITER ;

-- ============================================
-- SAMPLE DATA (Optional)
-- ============================================

-- Insert sample doctors
INSERT INTO medico (nombreMedico, especialidad) VALUES
    ('Dr. Juan Pérez', 'Cardiología'),
    ('Dra. María González', 'Pediatría'),
    ('Dr. Carlos Rodríguez', 'Traumatología')
ON DUPLICATE KEY UPDATE nombreMedico = nombreMedico;

-- Insert sample consultation rooms
INSERT INTO consultorio (direccion) VALUES
    ('Av. Principal 123, Consultorio 1'),
    ('Av. Principal 123, Consultorio 2'),
    ('Calle Secundaria 45, Consultorio A')
ON DUPLICATE KEY UPDATE direccion = direccion;

-- Insert sample patients
INSERT INTO paciente (nombrePaciente, apellidoPaterno, apellidoMaterno, telefono, correo, direccion) VALUES
    ('Ana', 'López', 'Martínez', '5551234567', 'ana.lopez@email.com', 'Calle Flores 10'),
    ('Pedro', 'Sánchez', 'García', '5559876543', 'pedro.sanchez@email.com', 'Av. Juárez 200')
ON DUPLICATE KEY UPDATE nombrePaciente = nombrePaciente;

-- Insert sample products
INSERT INTO productos (nombreProducto, cantidad, categoria) VALUES
    ('Paracetamol 500mg', 100, 'Medicamentos'),
    ('Vendas Elásticas', 50, 'Material Médico'),
    ('Termómetro Digital', 25, 'Equipos')
ON DUPLICATE KEY UPDATE nombreProducto = nombreProducto;

-- Insert sample appointments
INSERT INTO cita (fecha, motivo, hora, id_medico, id_consultorio, id_paciente) VALUES
    ('2025-11-26', 'Consulta General', '10:00:00', 1, 1, 1),
    ('2025-11-26', 'Revisión', '11:30:00', 2, 2, 2),
    ('2025-11-27', 'Seguimiento', '09:00:00', 1, 1, 1)
ON DUPLICATE KEY UPDATE fecha = fecha;

-- Insert sample purchases for testing procedures
INSERT INTO compras (idPaciente, fecha, cantidad, precioUnitario, productos_id) VALUES
    (1, '2025-11-26', 2, 15.50, 1),
    (2, '2025-11-26', 1, 45.00, 2)
ON DUPLICATE KEY UPDATE idPaciente = idPaciente;

SELECT 'Database setup completed successfully!' AS status;
