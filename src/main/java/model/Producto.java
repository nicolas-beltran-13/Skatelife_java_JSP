    package com.skatelife.model;

    import jakarta.persistence.*;
    import java.io.Serializable;

    @Entity
    @Table(name = "producto")
    public class Producto implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_producto")
        private Integer idProducto;

        @Column(name = "nombreproducto", length = 30)
        private String nombreproducto;

        @Column(name = "precio")
        private Integer precio;

        @Column(name = "Imagen", length = 45)
        private String imagen;

        @Column(name = "cantidad")
        private Integer cantidad;

        // 👉 NUEVO CAMPO AGREGADO
        @Column(name = "descripcion", length = 500)
        private String descripcion;

        @ManyToOne
        @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
        private Categoria categoria;

        // Constructor vacío
        public Producto() {
        }

        // Getters y Setters
        public Integer getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(Integer idProducto) {
            this.idProducto = idProducto;
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

        // 👉 NUEVOS GETTER Y SETTER
        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }
    }
