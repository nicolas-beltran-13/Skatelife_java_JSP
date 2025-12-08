package com.skatelife.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_administrador")
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_idadministrador")
    private Integer id;
    
    @Column(name = "pk_nombreusuario", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "apellido", length = 100)
    private String apellido;
    
    @Column(name = "correo", length = 100)
    private String correo;
    
    @Column(name = "celular", length = 15)
    private String celular;
    
    // Campo adicional para roles (puedes agregarlo a tu BD si quieres)
    @Transient // No se mapea a BD por ahora
    private String rol = "ADMIN";
    
    @Transient // No se mapea a BD por ahora
    private Boolean activo = true;
    
    // Constructor vacío
    public Usuario() {
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    // Método de conveniencia para obtener nombre completo
    public String getNombreCompleto() {
        if (nombre != null && apellido != null) {
            return nombre + " " + apellido;
        } else if (nombre != null) {
            return nombre;
        } else if (apellido != null) {
            return apellido;
        }
        return username;
    }
}