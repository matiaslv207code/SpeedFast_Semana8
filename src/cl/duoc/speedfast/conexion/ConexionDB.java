package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // cambiar según su configuracion
    private static final String PASSWORD = "admin5356"; // cambia segun su configuracion

    // metodo para establecer la conexion con la base de datos mysql
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // carga el driver de conexion de mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            // realiza la conexion usando la url, el usuario y la contrasena
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // captura cualquier error de conexion o driver y lo muestra en consola
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion; // retorna el objeto de conexion listo para usar
    }
}