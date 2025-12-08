package com.skatelife.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_idproducto")
    private Integer pkIdproducto;
    
    @Column(name = "nombreproducto", length = 30)
    private String nombreproducto;
    
    @Column(name = "precio")
    private Integer precio;
    
    @Column(name = "Imagen", length = 45)
    private String imagen;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "informacion", length = 45)
    private String informacion;
    
    @ManyToOne
    @JoinColumn(name = "fk_idcategoria", referencedColumnName = "pk_idcategoria")
    private Categoria categoria;
    
    // Constructor vacío
    public Producto() {
    }
    
    // Getters y Setters
    public Integer getPkIdproducto() {
        return pkIdproducto;
    }
    
    public void setPkIdproducto(Integer pkIdproducto) {
        this.pkIdproducto = pkIdproducto;
    }
    
    public String getNombreproducto() {
        return nombreproducto;
    }
    
    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }
    
    public Integer getPrecio() {
        return precio;
    }
    
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getInformacion() {
        return informacion;
    }
    
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}