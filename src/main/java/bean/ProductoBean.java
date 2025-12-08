package com.skatelife.bean;

import com.skatelife.dao.CategoriaDAO;
import com.skatelife.dao.ProductoDAO;
import com.skatelife.model.Categoria;
import com.skatelife.model.Producto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named("productoBean")
@SessionScoped
public class ProductoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private ProductoDAO productoDAO;
    
    @EJB
    private CategoriaDAO categoriaDAO;
    
    private List<Producto> productos;
    private List<Categoria> categorias;
    private Producto nuevoProducto;
    private Producto productoSeleccionado;
    
    @PostConstruct
    public void init() {
        System.out.println("=== ProductoBean.init() ejecutándose ===");
        cargarProductos();
        cargarCategorias();
        nuevoProducto = new Producto();
    }
    
    public void cargarProductos() {
        try {
            System.out.println("Cargando productos...");
            productos = productoDAO.findAll();
            System.out.println("Productos cargados: " + (productos != null ? productos.size() : "null"));
        } catch (Exception e) {
            System.err.println("ERROR al cargar productos: " + e.getMessage());
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudieron cargar los productos: " + e.getMessage(), 
                          FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void cargarCategorias() {
        try {
            System.out.println("Cargando categorías...");
            categorias = categoriaDAO.findAll();
            System.out.println("Categorías cargadas: " + (categorias != null ? categorias.size() : "null"));
        } catch (Exception e) {
            System.err.println("ERROR al cargar categorías: " + e.getMessage());
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudieron cargar las categorías: " + e.getMessage(), 
                          FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void guardarNuevo() {
        try {
            System.out.println("Guardando nuevo producto: " + nuevoProducto.getNombreproducto());
            productoDAO.save(nuevoProducto);
            cargarProductos();
            nuevoProducto = new Producto();
            mostrarMensaje("Éxito", "Producto creado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            System.err.println("ERROR al guardar producto: " + e.getMessage());
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudo crear el producto: " + e.getMessage(), 
                          FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void prepararEditar(Producto producto) {
        this.productoSeleccionado = producto;
    }
    
    public void actualizar() {
        try {
            productoDAO.update(productoSeleccionado);
            cargarProductos();
            mostrarMensaje("Éxito", "Producto actualizado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo actualizar el producto", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void eliminar(Integer id) {
        try {
            productoDAO.delete(id);
            cargarProductos();
            mostrarMensaje("Éxito", "Producto eliminado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el producto", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void mostrarMensaje(String titulo, String mensaje, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severidad, titulo, mensaje));
    }
    
    // Getters y Setters
    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    public Producto getNuevoProducto() {
        return nuevoProducto;
    }
    
    public void setNuevoProducto(Producto nuevoProducto) {
        this.nuevoProducto = nuevoProducto;
    }
    
    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }
    
    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
}