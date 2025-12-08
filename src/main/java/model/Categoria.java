package com.skatelife.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_idcategoria")
    private Integer pkIdcategoria;
    
    @Column(name = "nombrecategoria", length = 30)
    private String nombrecategoria;
    
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
    
    // Constructor vacío
    public Categoria() {
    }
    
    // Getters y Setters
    public Integer getPkIdcategoria() {
        return pkIdcategoria;
    }
    
    public void setPkIdcategoria(Integer pkIdcategoria) {
        this.pkIdcategoria = pkIdcategoria;
    }
    
    public String getNombrecategoria() {
        return nombrecategoria;
    }
    
    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }
    
    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}