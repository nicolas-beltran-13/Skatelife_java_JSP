package com.skatelife.beans;

import com.skatelife.dao.UsuarioDAO;
import model.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private Usuario nuevoUsuario;
    private String busqueda = "";
    
    @PostConstruct
    public void init() {
        System.out.println("=== UsuarioBean.init() ejecutándose ===");
        busqueda = "";
        cargarUsuarios();
        nuevoUsuario = new Usuario();
    }
    
   public void cargarUsuarios() {
    try {
        System.out.println("Cargando usuarios desde DAO...");
        // Primero cargar TODOS los usuarios
        List<Usuario> todosLosUsuarios = usuarioDAO.findAll();
        System.out.println("Total usuarios en BD: " + todosLosUsuarios.size());
        
        // Si NO hay búsqueda, mostrar todos
        if (busqueda == null || busqueda.trim().isEmpty()) {
            usuarios = todosLosUsuarios;
            System.out.println("Mostrando todos los usuarios: " + usuarios.size());
        } else {
            // Si HAY búsqueda, filtrar
            usuarios = todosLosUsuarios.stream()
                .filter(u -> 
                    (u.getNumide() != null && u.getNumide().toLowerCase().contains(busqueda.toLowerCase())) ||
                    (u.getCorreo() != null && u.getCorreo().toLowerCase().contains(busqueda.toLowerCase())) ||
                    (u.getNombre() != null && u.getNombre().toLowerCase().contains(busqueda.toLowerCase())) ||
                    (u.getApellidos() != null && u.getApellidos().toLowerCase().contains(busqueda.toLowerCase()))
                )
                .collect(Collectors.toList());
            System.out.println("Usuarios filtrados: " + usuarios.size());
        }
        
    } catch (Exception e) {
        System.err.println("ERROR al cargar usuarios: " + e.getMessage());
        e.printStackTrace();
        mostrarMensaje("Error", "No se pudieron cargar los usuarios: " + e.getMessage(), 
                      FacesMessage.SEVERITY_ERROR);
    }
}
    
    public void limpiarBusqueda() {
        busqueda = "";
        cargarUsuarios();
    }
    
    public void prepararEditar(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
    }
    
    public void eliminar(Integer id) {
        try {
            usuarioDAO.delete(id);
            cargarUsuarios();
            mostrarMensaje("Éxito", "Usuario eliminado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el usuario", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void mostrarMensaje(String titulo, String mensaje, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severidad, titulo, mensaje));
    }
    
    // Getters y Setters
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }
    
    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }
    
    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }
    
    public String getBusqueda() {
        return busqueda;
    }
    
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    public void guardarNuevo() {
    try {
        usuarioDAO.create(nuevoUsuario);
        cargarUsuarios();
        nuevoUsuario = new Usuario();
        mostrarMensaje("Éxito", "Usuario creado correctamente", FacesMessage.SEVERITY_INFO);
    } catch (Exception e) {
        mostrarMensaje("Error", "No se pudo crear el usuario: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
    }
}

public void actualizar() {
    try {
        usuarioDAO.update(usuarioSeleccionado);
        cargarUsuarios();
        mostrarMensaje("Éxito", "Usuario actualizado correctamente", FacesMessage.SEVERITY_INFO);
    } catch (Exception e) {
        mostrarMensaje("Error", "No se pudo actualizar el usuario: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
    }
}
}