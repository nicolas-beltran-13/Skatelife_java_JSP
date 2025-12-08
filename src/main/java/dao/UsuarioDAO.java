package com.skatelife.dao;

import model.Usuario; 
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UsuarioDAO {
    
    @PersistenceContext(unitName = "SkatelifePU")
    private EntityManager em;
    
    public List<Usuario> findAll() {
        System.out.println("=== UsuarioDAO.findAll() ejecutándose ===");
        try {
            List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class)
                    .getResultList();
            System.out.println("Usuarios encontrados en BD: " + usuarios.size());
            return usuarios;
        } catch (Exception e) {
            System.err.println("ERROR en findAll(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public void create(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void update(Usuario usuario) {
        em.merge(usuario);
    }
    
    public void delete(int id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }
    
    public Usuario findById(int id) {
        return em.find(Usuario.class, id);
    }
}