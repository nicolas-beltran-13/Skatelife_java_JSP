package com.mycompany.skatelifejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    // Cambia estos valores según tu configuración de base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/skatelife_j"; // Reemplaza 'skatelife' con el nombre de tu base de datos
    private static final String USER = "root"; // Reemplaza con tu usuario de MySQL
    private static final String PASSWORD = ""; // Reemplaza con tu contraseña de MySQL (o deja vacío si no tiene)

    public static Connection getConnection() throws SQLException {
        try {
            // Carga el driver JDBC para MySQL 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Driver JDBC no encontrado.", ex);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}