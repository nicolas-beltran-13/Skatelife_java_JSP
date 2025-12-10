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
    
    /**
     * Obtener todos los productos
     */
    public List<Producto> findAll() {
        try {
            System.out.println("=== ProductoDAO.findAll() ===");
            
            String jpql = "SELECT p FROM Producto p ORDER BY p.nombreproducto";
            List<Producto> lista = em.createQuery(jpql, Producto.class).getResultList();
            
            System.out.println("Productos encontrados: " + lista.size());
            return lista;
            
        } catch (Exception e) {
            System.err.println("ERROR en findAll: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Buscar producto por ID
     */
    public Producto findById(Integer id) {
        try {
            return em.find(Producto.class, id);
        } catch (Exception e) {
            System.err.println("Error en findById: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Guardar nuevo producto
     */
    public void save(Producto producto) {
        try {
            em.persist(producto);
            System.out.println("Producto guardado: " + producto.getNombreproducto());
        } catch (Exception e) {
            System.err.println("Error al guardar producto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Actualizar producto existente
     */
    public void update(Producto producto) {
        try {
            em.merge(producto);
            System.out.println("Producto actualizado: " + producto.getNombreproducto());
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Eliminar producto por ID
     */
    public void delete(Integer id) {
        try {
            Producto producto = findById(id);
            if (producto != null) {
                em.remove(producto);
                System.out.println("Producto eliminado: " + producto.getNombreproducto());
            } else {
                System.err.println("No se encontró producto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Buscar productos por categoría
     */
    public List<Producto> findByCategoria(Integer idCategoria) {
        try {
            String jpql = "SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCat ORDER BY p.nombreproducto";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("idCat", idCategoria)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en findByCategoria: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Buscar productos por nombre
     */
    public List<Producto> findByNombre(String nombre) {
        try {
            String jpql = "SELECT p FROM Producto p WHERE p.nombreproducto LIKE :nombre ORDER BY p.nombreproducto";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en findByNombre: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
}