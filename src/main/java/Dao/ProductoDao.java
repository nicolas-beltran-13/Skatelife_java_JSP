package com.mycompany.daos; // Asegúrate de usar el paquete correcto para tus DAOs

import com.mycompany.beans.ProductoBean;
import com.mycompany.skatelifejava.ConexionBD; // Asegúrate del paquete correcto
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// ✅ CORREGIDO: Nombre del archivo coincide con el nombre de la clase
public class ProductoDao {

    private Connection conexion;

    public ProductoDao(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener todos los productos
    public List<ProductoBean.Producto> findAll() throws SQLException {
        String sql = "SELECT pk_idproducto, nombreproducto, fk_idcategoria, precio, cantidad FROM tbl_producto";
        List<ProductoBean.Producto> productos = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // ✅ CORREGIDO: Se usa el constructor con parámetros
                ProductoBean.Producto prod = new ProductoBean.Producto(
                    rs.getInt("pk_idproducto"), // id
                    rs.getString("nombreproducto"), // nombre
                    rs.getString("fk_idcategoria"), // categoria (asumiendo es un string o el ID como string)
                    rs.getDouble("precio"), // precio
                    rs.getInt("cantidad") // stock
                );
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
                    // ✅ CORREGIDO: Se usa el constructor con parámetros
                    prod = new ProductoBean.Producto(
                        rs.getInt("pk_idproducto"), // id
                        rs.getString("nombreproducto"), // nombre
                        rs.getString("fk_idcategoria"), // categoria
                        rs.getDouble("precio"), // precio
                        rs.getInt("cantidad") // stock
                    );
                }
            }
        }
        return prod;
    }

    // Método para insertar un nuevo producto
    // Este método requiere que tu clase ProductoBean.Producto tenga setters si se desea modificar después de crearlo,
    // o que pases todos los valores al constructor. Este ejemplo asume que se pasa todo al constructor.
    public void insert(ProductoBean.Producto producto) throws SQLException {
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
                    // ❌ CORREGIDO: No se puede usar setId() porque Producto no lo tiene.
                    // Debes manejar el ID generado de otra manera si es necesario.
                    // Por ejemplo, devolviendo el ID o creando un nuevo objeto Producto con el ID.
                    // Esto es más complejo si Producto no tiene setters.
                    // Una opción es que insert() devuelva el ID generado.
                    // int nuevoId = generatedKeys.getInt(1);
                    // producto.setId(nuevoId); // ESTO NO FUNCIONA CON TU CLASE ACTUAL
                }
            }
        }
    }

    // Método para actualizar un producto existente
    // Al igual que insert, actualizar es complicado sin setters. Este ejemplo ilustra el problema.
    public void update(ProductoBean.Producto producto) throws SQLException {
        // ❌ ESTE MÉTODO NO FUNCIONARÁ CON TU CLASE PRODUCTO ACTUAL QUE NO TIENE SETTERS
        // Si necesitas actualizar, debes agregar setters a tu clase ProductoBean.Producto
        // o manejar los valores de otra manera.
        String sql = "UPDATE tbl_producto SET nombreproducto=?, fk_idcategoria=?, precio=?, cantidad=? WHERE pk_idproducto=?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria()); // Asegúrate que sea el ID si fk_idcategoria es INT
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId()); // Se asume que el ID ya está disponible

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No se pudo actualizar el producto. Quizás no exista.");
            }
        }
    }

    // Método para eliminar un producto por ID
    // Este no requiere setters en Producto.
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