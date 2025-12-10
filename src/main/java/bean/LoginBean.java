package bean;

import com.skatelife.dao.UsuarioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Usuario;
import java.io.Serializable;

@Named("loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioDAO usuarioDAO;

    @Inject
    private SessionBean sessionBean;

    private String email;
    private String password;
    private boolean rememberMe;
    private String errorMessage;
    private String successMessage;
    private boolean showError = false;
    private boolean showSuccess = false;

    public String login() {
        try {
            System.out.println("\n========================================");
            System.out.println("🔐 INTENTANDO LOGIN");
            System.out.println("Email ingresado: " + email);
            System.out.println("========================================\n");

            // Limpiar mensajes
            showError = false;
            showSuccess = false;

            // Validación de inputs
            if (email == null || email.trim().isEmpty()) {
                showError = true;
                errorMessage = "Por favor ingresa tu correo electrónico";
                return null;
            }

            if (password == null || password.trim().isEmpty()) {
                showError = true;
                errorMessage = "Por favor ingresa tu contraseña";
                return null;
            }

            // Autenticar usuario
            Usuario usuario = usuarioDAO.authenticate(email.trim(), password);

            if (usuario == null) {
                System.out.println("❌ Usuario no encontrado / contraseña incorrecta");
                showError = true;
                errorMessage = "Correo o contraseña incorrectos";
                return null;
            }

            // Guardar usuario en la sesión
            sessionBean.login(usuario);

            System.out.println("\n========== LOGIN EXITOSO ==========");
            System.out.println("Usuario: " + usuario.getNombre());
            System.out.println("Rol original en BD: [" + usuario.getRol() + "]");

            // Normalizar rol
            String rolOriginal = usuario.getRol();
            String rol = (rolOriginal == null) ? "" : rolOriginal.trim().toLowerCase();

            System.out.println("Rol normalizado: [" + rol + "]");
            System.out.println("===================================\n");

            // VALIDACIÓN ROBUSTA DE ADMIN
            String redirectPage;
            if (rol.contains("admin")) {
                redirectPage = "admin/panel_admin.xhtml";
                System.out.println("🎯 Rol detectado: ADMIN → Redirigiendo al panel de administrador");
            } else {
                redirectPage = "/index.xhtml";
                System.out.println("🎯 Rol detectado: USUARIO → Redirigiendo al home");
            }

            return redirectPage + "?faces-redirect=true";

        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN LOGIN: " + e.getMessage());
            e.printStackTrace();
            showError = true;
            errorMessage = "Error al iniciar sesión: " + e.getMessage();
            return null;
        }
    }

    // Métodos para login social (no implementados aún)
    public String loginWithGoogle() {
        showError = true;
        errorMessage = "Inicio de sesión con Google no disponible aún";
        return null;
    }

    public String loginWithFacebook() {
        showError = true;
        errorMessage = "Inicio de sesión con Facebook no disponible aún";
        return null;
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isRememberMe() { return rememberMe; }
    public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

    public boolean isShowError() { return showError; }
    public void setShowError(boolean showError) { this.showError = showError; }

    public boolean isShowSuccess() { return showSuccess; }
    public void setShowSuccess(boolean showSuccess) { this.showSuccess = showSuccess; }
}
