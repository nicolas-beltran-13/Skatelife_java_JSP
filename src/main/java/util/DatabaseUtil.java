package com.skatelife.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseUtil {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/skatelife";  // Cambia según tu configuración
    private static final String USER = "root";  // Usuario de tu base de datos
    private static final String PASSWORD = "password";  // Contraseña de tu base de datos

    // Obtener la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Error al cargar el driver de MySQL", e);
        }
    }

    // Cerrar la conexión
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
