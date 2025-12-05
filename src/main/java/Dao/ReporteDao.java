package com.mycompany.Dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReporteDao {

    private Connection conexion;

    public ReporteDao(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener el conteo de usuarios
    public int contarUsuarios() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM tbl_usuario";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // Método para obtener el conteo de productos
    public int contarProductos() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM tbl_producto";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // Método para obtener el total de ventas (sumando el campo 'total' de la tabla tbl_pago)
    public double obtenerTotalVentas() throws SQLException {
        String sql = "SELECT SUM(total) AS total_ventas FROM tbl_pago";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_ventas");
            }
        }
        return 0.0;
    }

    // Método para obtener el conteo de órdenes (pedidos)
    public int contarPedidos() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM tbl_pedido";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}