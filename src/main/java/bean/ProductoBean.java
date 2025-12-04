package com.mycompany.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProductoBean implements Serializable {

    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;

    private List<Producto> productos = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (productos.isEmpty()) {
            productos.add(new Producto(1, "Tabla Cruiser", "tablas", 180000.0, 10));
            productos.add(new Producto(2, "Ruedas 56mm", "ruedas", 45000.0, 25));
        }
    }

    public String guardar() {
        if (nombre != null && !nombre.trim().isEmpty() &&
            categoria != null && precio != null && stock != null) {

            int nuevoId = productos.stream().mapToInt(p -> p.id).max().orElse(0) + 1;
            productos.add(new Producto(nuevoId, nombre, categoria, precio, stock));

            // Resetear formulario
            this.nombre = "";
            this.categoria = "";
            this.precio = null;
            this.stock = null;

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "✅ Producto agregado", "El producto se registró correctamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "❌ Error", "Todos los campos son obligatorios."));
        }
        return null;
    }

    public List<Producto> getTodosLosProductos() {
        return productos;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    // Clase interna Producto
    public static class Producto {
        private int id;          // ✅ Cambiado a private
        private String nombre;   // ✅ Cambiado a private
        private String categoria; // ✅ Cambiado a private
        private Double precio;    // ✅ Cambiado a private
        private Integer stock;    // ✅ Cambiado a private

        public Producto(int id, String nombre, String categoria, Double precio, Integer stock) {
            this.id = id;
            this.nombre = nombre;
            this.categoria = categoria;
            this.precio = precio;
            this.stock = stock;
        }

        // ✅ Todos los getters son obligatorios para JSF
        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCategoria() {
            return categoria;
        }

        public Double getPrecio() {
            return precio;
        }

        public Integer getStock() {
            return stock;
        }
    }
}