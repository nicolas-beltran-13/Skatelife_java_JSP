package com.mycompany.daos; // Asegúrate de usar el paquete correcto

import com.mycompany.beans.ProductoBean;
import com.mycompany.utils.ConexionBD; // Asegúrate de usar el paquete correcto
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {

    private Connection conexion;

    // Constructor que recibe la conexión
    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener todos los productos
    public List<ProductoBean.Producto> findAll() throws SQLException {
        // Asumiendo que 'categoria' en tu Bean se refiere a 'fk_idcategoria' en la BD
        // Si 'categoria' en el Bean es el nombre de la categoría, necesitarías un JOIN
        String sql = "SELECT pk_idproducto, nombreproducto, fk_idcategoria, precio, cantidad FROM tbl_producto";
        List<ProductoBean.Producto> productos = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductoBean.Producto prod = new ProductoBean.Producto();
                prod.setId(rs.getInt("pk_idproducto"));
                prod.setNombre(rs.getString("nombreproducto"));
                // Asumiendo que 'categoria' en el Bean es un String que representa el nombre de la categoría
                // Si es el ID, usarías setCategoria(rs.getInt("fk_idcategoria") + ""); o manejarías el ID como int
                // Por ahora, lo asignamos como un String (nombre de la categoría o ID como texto)
                prod.setCategoria(rs.getString("fk_idcategoria")); // Cambia esto si es el nombre real
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("cantidad"));
                productos.add(prod);
            }
        }
        return productos;
    }

    // Método para obtener un producto por ID
    public ProductoBean.Producto findById(int id) throws SQLException {
        String sql = "SELECT pk_idproducto, nombreproducto, fk_idcategoria, precio, cantidad FROM tbl_producto WHERE pk_idproducto = ?";
        ProductoBean.Producto prod = null;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    prod = new ProductoBean.Producto();
                    prod.setId(rs.getInt("pk_idproducto"));
                    prod.setNombre(rs.getString("nombreproducto"));
                    prod.setCategoria(rs.getString("fk_idcategoria")); // Cambia esto si es el nombre real
                    prod.setPrecio(rs.getDouble("precio"));
                    prod.setStock(rs.getInt("cantidad"));
                }
            }
        }
        return prod;
    }

    // Método para insertar un nuevo producto
    public void insert(ProductoBean.Producto producto) throws SQLException {
        // Asumiendo que 'categoria' en el Bean es el ID de la categoría en la BD
        String sql = "INSERT INTO tbl_producto (nombreproducto, fk_idcategoria, precio, cantidad) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria()); // Asegúrate que sea el ID si fk_idcategoria es INT
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.executeUpdate();

            // Opcional: Obtener el ID generado si es necesario
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Método para actualizar un producto existente
    public void update(ProductoBean.Producto producto) throws SQLException {
        // Asumiendo que 'categoria' en el Bean es el ID de la categoría en la BD
        String sql = "UPDATE tbl_producto SET nombreproducto=?, fk_idcategoria=?, precio=?, cantidad=? WHERE pk_idproducto=?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria()); // Asegúrate que sea el ID si fk_idcategoria es INT
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No se pudo actualizar el producto. Quizás no exista.");
            }
        }
    }

    // Método para eliminar un producto por ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tbl_producto WHERE pk_idproducto = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("No se pudo eliminar el producto. Quizás no exista.");
            }
        }
    }
}