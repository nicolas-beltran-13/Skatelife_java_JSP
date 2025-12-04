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
public class UsuarioBean implements Serializable {

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioSeleccionado; // Para manejar la edición

    @PostConstruct
    public void init() {
        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario(1, "Juan Pérez", "juan@example.com", "cliente", true));
            usuarios.add(new Usuario(2, "Ana Gómez", "ana@example.com", "admin", false)); // Deshabilitada
            usuarios.add(new Usuario(3, "Luis Martínez", "luis@example.com", "cliente", true));
        }
    }

    // Método para cambiar el estado (activo/inactivo) de un usuario
    public String cambiarEstado(Usuario usuario) {
        usuario.setActivo(!usuario.isActivo()); // Invierte el estado
        String nuevoEstado = usuario.isActivo() ? "habilitado" : "deshabilitado";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "✅ Usuario " + nuevoEstado, "El estado del usuario '" + usuario.getNombre() + "' ha sido actualizado."));
        return null;
    }

    // Método para preparar la edición (carga el usuario seleccionado)
    public String prepararEdicion(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
        return null;
    }

    // Método para guardar los cambios del usuario editado
    public String guardarEdicion() {
        if (usuarioSeleccionado != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "✅ Usuario actualizado", "Los datos del usuario '" + usuarioSeleccionado.getNombre() + "' han sido guardados."));
        }
        return null;
    }

    public List<Usuario> getTodosLosUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    // Clase interna Usuario
    public static class Usuario {
        private int id;
        private String nombre;
        private String email;
        private String rol;
        private boolean activo; // Nuevo campo para el estado

        public Usuario(int id, String nombre, String email, String rol, boolean activo) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.rol = rol;
            this.activo = activo;
        }

        // Getters y Setters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
        public boolean isActivo() { return activo; }
        public void setActivo(boolean activo) { this.activo = activo; }
    }
}