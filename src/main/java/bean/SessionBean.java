package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import model.Usuario;
import java.io.Serializable;

@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Usuario usuarioActual;
    private boolean loggedIn = false;
    
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;
        this.loggedIn = true;
        System.out.println("✅ Usuario logueado: " + usuario.getNombre() + " - Rol: " + usuario.getRol());
    }
    
    public String logout() {
        System.out.println("🔴 Cerrando sesión de: " + (usuarioActual != null ? usuarioActual.getNombre() : "ninguno"));
        this.usuarioActual = null;
        this.loggedIn = false;
        
        // Invalidar la sesión HTTP completa
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "/index.xhtml?faces-redirect=true";
    }
    
    public boolean isLoggedIn() {
        return loggedIn && usuarioActual != null;
    }
    
    public boolean isAdmin() {
        if (!loggedIn || usuarioActual == null || usuarioActual.getRol() == null) {
            return false;
        }
        String rol = usuarioActual.getRol().trim().toLowerCase();
        System.out.println("🔍 Verificando admin - Rol: '" + rol + "'");
        return "admin".equals(rol) || "administrador".equals(rol);
    }
    
    public boolean isUsuario() {
        if (!loggedIn || usuarioActual == null || usuarioActual.getRol() == null) {
            return false;
        }
        String rol = usuarioActual.getRol().trim().toLowerCase();
        return "usuario".equals(rol) || "user".equals(rol);
    }
    
    // Getters y Setters
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}