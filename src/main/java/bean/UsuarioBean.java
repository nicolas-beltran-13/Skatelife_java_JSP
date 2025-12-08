package com.skatelife.bean;

import com.skatelife.dao.UsuarioDAO;
import com.skatelife.model.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    @EJB
    private UsuarioDAO usuarioDAO;

    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private Usuario nuevoUsuario;
    private String busqueda = "";

    @PostConstruct
    public void init() {
        cargarUsuarios();
        nuevoUsuario = new Usuario();
    }

    // Cargar todos los usuarios
    public void cargarUsuarios() {
        try {
            usuarios = usuarioDAO.findAll();

            // Si hay búsqueda, filtrar resultados
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                usuarios = usuarios.stream()
                    .filter(u ->
                        (u.getUsername() != null && u.getUsername().toLowerCase().contains(busqueda.toLowerCase())) ||
                        (u.getCorreo() != null && u.getCorreo().toLowerCase().contains(busqueda.toLowerCase())) ||
                        (u.getNombre() != null && u.getNombre().toLowerCase().contains(busqueda.toLowerCase())) ||
                        (u.getApellido() != null && u.getApellido().toLowerCase().contains(busqueda.toLowerCase()))
                    )
                    .collect(Collectors.toList());
            }

        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron cargar los usuarios: " + e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    // Preparar para editar
    public void prepararEdicion(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
    }

    // Actualizar solo el rol
    public void actualizarRol() {
        try {
            usuarioDAO.update(usuarioSeleccionado);
            cargarUsuarios();
            mostrarMensaje("Éxito", "Rol actualizado correctamente",
                    FacesMessage.SEVERITY_INFO);
            usuarioSeleccionado = null;
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo actualizar el rol: " + e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    // Actualizar usuario completo
    public void actualizarUsuario() {
        try {
            if (usuarioSeleccionado.getUsername() == null ||
                usuarioSeleccionado.getUsername().trim().isEmpty()) {
                mostrarMensaje("Error", "El nombre de usuario es obligatorio",
                        FacesMessage.SEVERITY_WARN);
                return;
            }

            usuarioDAO.update(usuarioSeleccionado);
            cargarUsuarios();
            mostrarMensaje("Éxito", "Usuario actualizado correctamente",
                    FacesMessage.SEVERITY_INFO);
            usuarioSeleccionado = null;

        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo actualizar el usuario: " + e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    // Eliminar usuario
    public void eliminarUsuario(Usuario usuario) {
        try {
            usuarioDAO.delete(usuario.getId());
            cargarUsuarios();
            mostrarMensaje("Éxito", "Usuario eliminado correctamente",
                    FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el usuario: " + e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    // Crear un usuario nuevo
    public void crearUsuario() {
        try {

            if (usuarioDAO.existeUsername(nuevoUsuario.getUsername())) {
                mostrarMensaje("Error", "El nombre de usuario ya existe",
                        FacesMessage.SEVERITY_WARN);
                return;
            }

            if (usuarioDAO.existeEmail(nuevoUsuario.getCorreo())) {
                mostrarMensaje("Error", "El email ya está registrado",
                        FacesMessage.SEVERITY_WARN);
                return;
            }

            usuarioDAO.create(nuevoUsuario);
            cargarUsuarios();
            nuevoUsuario = new Usuario();

            mostrarMensaje("Éxito", "Usuario creado correctamente",
                    FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo crear el usuario: " + e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    // Cancelar edición
    public void cancelarEdicion() {
        usuarioSeleccionado = null;
    }

    // Aplicar filtro de búsqueda
    public void aplicarFiltros() {
        cargarUsuarios();
    }

    // Limpiar búsqueda
    public void limpiarFiltros() {
        busqueda = "";
        cargarUsuarios();
    }

    // Mostrar mensajes
    private void mostrarMensaje(String titulo, String mensaje, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidad, titulo, mensaje));
    }

    // Getters y setters
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

    public String getBusqueda() {      // ← ¡ESTE FALTABA!
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
}
