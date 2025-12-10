package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("searchBean")
@SessionScoped
public class SearchBean implements Serializable {
    
    private String searchTerm;
    
    public void search() {
        System.out.println("Buscando: " + searchTerm);
        // Implementar lógica de búsqueda
    }
    
    // Getters y Setters
    public String getSearchTerm() {
        return searchTerm;
    }
    
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}