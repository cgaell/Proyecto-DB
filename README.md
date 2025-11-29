# Sistema de Gestión Médica

A Java Swing application for managing a medical clinic with patient records, appointments, inventory, and purchase tracking.

## 🎯 Features

- **SQL Views**: Query appointments, patients, doctors, and products with various filters
- **Stored Procedures**: 
  - `comprasdiarias`: Daily purchase reports with customer averages
  - `ReporteClientes`: Patient appointment reports by year
  - `RegistrarPacienteUnico`: Register patients with duplicate checking
- **Triggers**:
  - `no_duplicados`: Prevents duplicate products in inventory
  - `actualizar_producto`: Auto-updates inventory on purchase
  - `trg_seguimiento_compra`: Creates tracking records for purchases
- **5 Management Tabs**:
  1. SQL Views (queries and aggregations)
  2. Reports (stored procedures)
  3. Inventory Management (with triggers)
  4. Purchases and Tracking
  5. Patient Management

## 📋 Prerequisites

All prerequisites are now installed:
- ✅ Java 25.0.1 (OpenJDK)
- ✅ MySQL 9.5.0
- ✅ MySQL JDBC Connector 9.1.0

## 🚀 Quick Start

### 1. Start MySQL Service (if not running)
```bash
brew services start mysql
```

### 2. Compile the Application
```bash
./compile.sh
```

### 3. Run the Application
```bash
./run.sh
```

## 🗄️ Database Configuration

The application connects to:
- **Host**: localhost:3306
- **Database**: proyecto_medc2
- **Username**: root
- **Password**: root

To reset or recreate the database:
```bash
mysql -u root -proot < setup_database.sql
```

## 📊 Sample Data

The database includes sample data:
- 3 Doctors (Cardiología, Pediatría, Traumatología)
- 3 Consultation rooms
- 2 Patients
- 3 Products (Paracetamol, Vendas, Termómetro)
- Sample appointments and purchases for testing

## 🛠️ Manual Compilation/Execution

If you prefer not to use the scripts:

**Compile:**
```bash
javac -cp ".:lib/mysql-connector-j-9.1.0.jar" src/Main.java
```

**Run:**
```bash
java -cp ".:lib/mysql-connector-j-9.1.0.jar" src.Main
```

## 📁 Project Structure

```
Proyecto-DB/
├── src/
│   └── Main.java          # Main application
├── lib/
│   └── mysql-connector-j-9.1.0.jar  # JDBC driver
├── setup_database.sql     # Database schema & data
├── compile.sh             # Compilation script
├── run.sh                 # Execution script
└── README.md              # This file
```

## 🔧 Troubleshooting

**MySQL connection refused:**
```bash
brew services restart mysql
```

**Compilation errors:**
- Ensure Java 25 is installed: `java -version`
- Check JDBC driver exists: `ls lib/`

**Database errors:**
- Verify database exists: `mysql -u root -proot -e "SHOW DATABASES;"`
- Recreate if needed: `mysql -u root -proot < setup_database.sql`

## 📝 Database Schema

**Tables:**
- `medico` - Doctors
- `consultorio` - Consultation rooms
- `paciente` - Patients
- `cita` - Appointments
- `productos` - Products/inventory
- `compras` - Purchases
- `seguimiento` - Purchase tracking

## 🎨 UI Features

- Modern Swing interface with tabbed navigation
- Dynamic table loading from database queries
- Form validation and error handling
- Trigger-based alerts (e.g., duplicate products)
- Multi-result set handling for complex procedures

## 📞 Support

For issues or questions about the database structure, check `setup_database.sql` for detailed table definitions, stored procedures, and trigger implementations.
