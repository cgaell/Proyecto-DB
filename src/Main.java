package src;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class Main {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/proyecto_medc2";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
public static void main(String[] args) {
    System.out.println("Hello, World!");
    System.out.println("Hola Rafa!");

    //CONEXION A LA BASE DE DATOS
    try {
        Connection conectar = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("Conexion exitosa");

    } catch (SQLException e) {
        System.out.println("Error de conexion: " + e.getErrorCode());
    }

    //CONSULTA A LA BASE DE DATOS
    try {
        Connection conectar = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String consulta = "SELECT * FROM paciente";
        Statement statement = conectar.createStatement();
        ResultSet resultado = statement.executeQuery(consulta);

        while (resultado.next()) {
            System.out.println("idpacientes: " + resultado.getInt("idpacientes"));
            System.out.println("nombrePaciente: " + resultado.getString("nombrePaciente"));
            System.out.println("apellidoPaterno: " + resultado.getString("apellidoPaterno"));
            System.out.println("apellidoMaterno: " + resultado.getString("apellidoMaterno"));
            System.out.println("telefono: " + resultado.getString("telefono"));
            System.out.println("correo: " + resultado.getString("correo"));
            System.out.println("direccion: " + resultado.getString("direccion"));
            System.out.println();
        }

        resultado.close();
        statement.close();
        conectar.close();
    } catch (SQLException e) {
        System.out.println("Error en la consulta: " + e.getMessage());
    }

    try {
        Connection conectar = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String consulta2 = "SELECT idmedicos, nombreMedico, especialidad, fecha, motivo, hora FROM medico LEFT JOIN cita  ON medico.idmedicos = cita.id_medico";
        Statement statement2 = conectar.createStatement();
        ResultSet resultado2 = statement2.executeQuery(consulta2);

        while (resultado2.next()) {
            System.out.println("idmedicos: " + resultado2.getInt("idmedicos"));
            System.out.println("nombreMedico: " + resultado2.getString("nombreMedico"));
            System.out.println("especialidad: " + resultado2.getString("especialidad"));
            System.out.println("fecha: " + resultado2.getString("fecha"));
            System.out.println("motivo: " + resultado2.getString("motivo"));
            System.out.println("hora: " + resultado2.getString("hora"));
            System.out.println();
        }

        resultado2.close();
        statement2.close();
        conectar.close();
    } catch (SQLException e) {
        System.out.println("Error en la consulta: " + e.getMessage());
    }

}


}