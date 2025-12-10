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
    
    /**
     * Obtener todas las categorías
     */
    public List<Categoria> findAll() {
        try {
            System.out.println("=== CategoriaDAO.findAll() ===");
            
            String jpql = "SELECT c FROM Categoria c ORDER BY c.nombreCategoria";
            List<Categoria> lista = em.createQuery(jpql, Categoria.class).getResultList();
            
            System.out.println("Categorías encontradas: " + lista.size());
            
            if (!lista.isEmpty()) {
                for (Categoria c : lista) {
                    System.out.println("  - ID: " + c.getIdCategoria() + " | Nombre: " + c.getNombreCategoria());
                }
            } else {
                System.err.println("⚠️ No hay categorías en la BD");
            }
            
            return lista;
            
        } catch (Exception e) {
            System.err.println("ERROR en findAll: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Buscar categoría por ID
     */
    public Categoria findById(Integer id) {
        try {
            return em.find(Categoria.class, id);
        } catch (Exception e) {
            System.err.println("Error en findById: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Guardar nueva categoría
     */
    public void save(Categoria categoria) {
        try {
            em.persist(categoria);
            System.out.println("Categoría guardada: " + categoria.getNombreCategoria());
        } catch (Exception e) {
            System.err.println("Error al guardar categoría: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Actualizar categoría existente
     */
    public void update(Categoria categoria) {
        try {
            em.merge(categoria);
            System.out.println("Categoría actualizada: " + categoria.getNombreCategoria());
        } catch (Exception e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Eliminar categoría por ID
     */
    public void delete(Integer id) {
        try {
            Categoria categoria = findById(id);
            if (categoria != null) {
                em.remove(categoria);
                System.out.println("Categoría eliminada: " + categoria.getNombreCategoria());
            } else {
                System.err.println("No se encontró categoría con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            throw e;
        }
    }
}