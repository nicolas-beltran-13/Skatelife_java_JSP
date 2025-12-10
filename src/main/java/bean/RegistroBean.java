package bean;

import com.skatelife.dao.UsuarioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import model.Usuario;
import java.io.Serializable;
import java.util.Date;

@Named("registroBean")
@RequestScoped
public class RegistroBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;
    private String confirmPassword;
    private boolean acceptTerms;
    private String errorMessage;
    private String successMessage;
    private boolean showError = false;
    private boolean showSuccess = false;
    
    public String register() {
        try {
            System.out.println("=== Registrando nuevo usuario ===");
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellido: " + apellido);
            System.out.println("Email: " + email);
            System.out.println("Teléfono: " + telefono);
            
            // Limpiar mensajes anteriores
            showError = false;
            showSuccess = false;
            
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                showError = true;
                errorMessage = "Las contraseñas no coinciden";
                return null;
            }
            
            // Validar términos
            if (!acceptTerms) {
                showError = true;
                errorMessage = "Debes aceptar los términos y condiciones";
                return null;
            }
            
            // Verificar si el email ya existe
            if (usuarioDAO.existsEmail(email.trim())) {
                showError = true;
                errorMessage = "Este correo electrónico ya está registrado";
                return null;
            }
            
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            
            // No establecer el ID si es auto-increment
            // Si NO es auto-increment, descomenta la siguiente línea:
            // nuevoUsuario.setIdUsuario(generarNuevoId());
            
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setApellidos(apellido.trim());
            nuevoUsuario.setCorreo(email.trim());
            nuevoUsuario.setContrasena(password); // TODO: Hashear en producción
            nuevoUsuario.setTelefono(telefono.trim());
            nuevoUsuario.setRol("usuario"); // Por defecto es usuario
            nuevoUsuario.setNumide(""); // Puedes dejarlo vacío o generar uno
            nuevoUsuario.setDireccion(""); // Vacío por ahora
            nuevoUsuario.setFechaNacimiento(new Date()); // Fecha actual por defecto
            
            System.out.println("Intentando guardar usuario...");
            
            // Guardar en la base de datos
            usuarioDAO.create(nuevoUsuario);
            
            System.out.println("Usuario creado exitosamente en BD");
            
            // Mostrar mensaje de éxito
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Éxito", "¡Cuenta creada exitosamente!"));
            
            // Limpiar campos
            limpiarCampos();
            
            // Redirigir al login
            return "/login.xhtml?faces-redirect=true";
            
        } catch (Exception e) {
            System.err.println("ERROR al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            showError = true;
            errorMessage = "Error al crear la cuenta: " + e.getMessage();
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "No se pudo crear la cuenta: " + e.getMessage()));
            
            return null;
        }
    }
    
    private void limpiarCampos() {
        nombre = null;
        apellido = null;
        email = null;
        telefono = null;
        password = null;
        confirmPassword = null;
        acceptTerms = false;
    }
    
    public String registerWithGoogle() {
        showError = true;
        errorMessage = "Registro con Google no disponible aún";
        return null;
    }
    
    public String registerWithFacebook() {
        showError = true;
        errorMessage = "Registro con Facebook no disponible aún";
        return null;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    
    public boolean isAcceptTerms() { return acceptTerms; }
    public void setAcceptTerms(boolean acceptTerms) { this.acceptTerms = acceptTerms; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }
    
    public boolean isShowError() { return showError; }
    public void setShowError(boolean showError) { this.showError = showError; }
    
    public boolean isShowSuccess() { return showSuccess; }
    public void setShowSuccess(boolean showSuccess) { this.showSuccess = showSuccess; }
}