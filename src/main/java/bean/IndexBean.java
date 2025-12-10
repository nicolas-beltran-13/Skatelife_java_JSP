package com.skatelife.bean;

import com.skatelife.dao.ProductoDAO;
import com.skatelife.model.Producto;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("indexBean")
@RequestScoped
public class IndexBean {
    
    @Inject
    private ProductoDAO productoDAO;
    
    private List<Producto> productos;
    private List<Producto> productosDestacados;
    
    @PostConstruct
    public void init() {
        cargarProductos();
    }
    
    private void cargarProductos() {
        try {
            // Cargar todos los productos
            productos = productoDAO.findAll();
            
            // Obtener productos destacados (primeros 8)
            if (productos != null && !productos.isEmpty()) {
                int limite = Math.min(8, productos.size());
                productosDestacados = productos.subList(0, limite);
            } else {
                productosDestacados = new ArrayList<>();
            }
            
            System.out.println("Productos cargados en index: " + productos.size());
            System.out.println("Productos destacados: " + productosDestacados.size());
            
        } catch (Exception e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            e.printStackTrace();
            productos = new ArrayList<>();
            productosDestacados = new ArrayList<>();
        }
    }
    
    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public List<Producto> getProductosDestacados() {
        return productosDestacados;
    }
    
    public void setProductosDestacados(List<Producto> productosDestacados) {
        this.productosDestacados = productosDestacados;
    }
}