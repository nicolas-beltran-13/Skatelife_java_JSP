package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("wishlistBean")
@SessionScoped
public class WishlistBean implements Serializable {
    
    private int count = 0;
    
    public void show() {
        System.out.println("Mostrando wishlist con " + count + " items");
        // Implementar lógica para mostrar wishlist
    }
    
    public void addItem() {
        count++;
    }
    
    public void removeItem() {
        if (count > 0) {
            count--;
        }
    }
    
    // Getter
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
}