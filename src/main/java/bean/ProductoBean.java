package com.skatelife.bean;

import com.skatelife.dao.ProductoDAO;
import com.skatelife.dao.CategoriaDAO;
import com.skatelife.model.Producto;
import com.skatelife.model.Categoria;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named("productoBean")
@SessionScoped
public class ProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "C:/skatelife/imagenes/";

    private Producto producto;
    private List<Producto> productos;
    private List<Categoria> categorias;
    private Part file;
    private Integer idCategoriaSeleccionada;

    @Inject
    private ProductoDAO productoDAO;

    @Inject
    private CategoriaDAO categoriaDAO;

    public ProductoBean() {
        this.producto = new Producto();
        this.productos = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        crearDirectorioImagenes();
        cargarDatos();
    }

    private void crearDirectorioImagenes() {
        File directorio = new File(UPLOAD_DIRECTORY);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    public void cargarDatos() {
        try {
            productos = productoDAO.findAll();
        } catch (Exception e) {
            productos = new ArrayList<>();
        }

        try {
            categorias = categoriaDAO.findAll();
        } catch (Exception e) {
            categorias = new ArrayList<>();
        }
    }

    public String guardar() {
        try {
            if (idCategoriaSeleccionada != null) {
                Categoria categoria = categoriaDAO.findById(idCategoriaSeleccionada);
                producto.setCategoria(categoria);
            }

            if (file != null && file.getSize() > 0) {
                String nombre = guardarImagen();
                producto.setImagen(nombre);
            }

            if (producto.getIdProducto() == null) {
                productoDAO.save(producto);
            } else {
                productoDAO.update(producto);
            }

            producto = new Producto();
            idCategoriaSeleccionada = null;
            file = null;
            cargarDatos();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String guardarImagen() {
        try {
            String nombreOriginal = file.getSubmittedFileName();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf('.')).toLowerCase();

            String nombreUnico = UUID.randomUUID().toString() + extension;

            File destino = new File(UPLOAD_DIRECTORY + nombreUnico);

            try (InputStream input = file.getInputStream()) {
                Files.copy(input, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            return nombreUnico;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String editar(Producto p) {
        this.producto = p;
        this.idCategoriaSeleccionada = p.getCategoria() != null ? p.getCategoria().getIdCategoria() : null;
        this.file = null;
        return null;
    }

    public String eliminar(Producto p) {
        try {
            if (p.getImagen() != null) {
                File img = new File(UPLOAD_DIRECTORY + p.getImagen());
                if (img.exists()) img.delete();
            }

            productoDAO.delete(p.getIdProducto());
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String cancelar() {
        producto = new Producto();
        idCategoriaSeleccionada = null;
        file = null;
        return null;
    }

    // ======================================================
    //  GETTERS Y SETTERS
    // ======================================================

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    // 🔥 GETTER ESPECIAL PARA QUE FUNCIONE TU XHTML
    public List<Producto> getListaProductos() {
        return productos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Integer getIdCategoriaSeleccionada() {
        return idCategoriaSeleccionada;
    }

    public void setIdCategoriaSeleccionada(Integer idCategoriaSeleccionada) {
        this.idCategoriaSeleccionada = idCategoriaSeleccionada;
    }
}
