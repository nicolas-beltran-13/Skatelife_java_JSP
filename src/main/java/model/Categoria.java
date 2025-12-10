package com.skatelife.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categoria")  // ← Nombre exacto de la tabla
public class Categoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")  // ← Nombre exacto de la columna
    private Integer idCategoria;
    
    // IMPORTANTE: El nombre de la columna en tu BD es "NombreCategoria" con mayúscula
    @Column(name = "NombreCategoria")  // ← Debe coincidir EXACTAMENTE con tu BD
    private String nombreCategoria;     // ← Este es el atributo Java que usas en JPQL
    
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
    
    // Constructor vacío (obligatorio para JPA)
    public Categoria() {
    }
    
    // Constructor con parámetros
    public Categoria(Integer idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    public Integer getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public String getNombreCategoria() {
        return nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    // ==================== MÉTODOS ÚTILES ====================
    
    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria != null && idCategoria.equals(categoria.idCategoria);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}