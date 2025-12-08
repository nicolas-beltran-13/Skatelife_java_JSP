package com.skatelife.dao;

import com.skatelife.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductoDAO {
    
    @PersistenceContext(unitName = "SkatelifePU")
    private EntityManager em;
    
    public List<Producto> findAll() {
        try {
            return em.createQuery("SELECT p FROM Producto p ORDER BY p.pkIdproducto", Producto.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public Producto findById(Integer id) {
        return em.find(Producto.class, id);
    }
    
    public void save(Producto producto) {
        em.persist(producto);
    }
    
    public void update(Producto producto) {
        em.merge(producto);
    }
    
    public void delete(Integer id) {
        Producto producto = findById(id);
        if (producto != null) {
            em.remove(producto);
        }
    }
}