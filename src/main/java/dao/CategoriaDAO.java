package com.skatelife.dao;

import com.skatelife.model.Categoria;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoriaDAO {
    
    @PersistenceContext(unitName = "SkatelifePU")
    private EntityManager em;
    
    public List<Categoria> findAll() {
        try {
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombrecategoria", Categoria.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar categorías: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public Categoria findById(Integer id) {
        return em.find(Categoria.class, id);
    }
    
    public void save(Categoria categoria) {
        em.persist(categoria);
    }
    
    public void update(Categoria categoria) {
        em.merge(categoria);
    }
    
    public void delete(Integer id) {
        Categoria categoria = findById(id);
        if (categoria != null) {
            em.remove(categoria);
        }
    }
}