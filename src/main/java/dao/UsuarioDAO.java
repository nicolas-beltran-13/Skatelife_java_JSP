package com.skatelife.dao;

import model.Usuario; 
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    
    // ⭐ NUEVO: Buscar usuario por correo
    public Usuario findByEmail(String correo) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class)
                    .setParameter("correo", correo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    // ⭐ NUEVO: Autenticar usuario
    public Usuario authenticate(String correo, String contrasena) {
        try {
            Usuario usuario = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasena = :contrasena", 
                Usuario.class)
                .setParameter("correo", correo)
                .setParameter("contrasena", contrasena)
                .getSingleResult();
            System.out.println("Usuario autenticado: " + usuario.getNombre() + " - Rol: " + usuario.getRol());
            return usuario;
        } catch (NoResultException e) {
            System.out.println("No se encontró usuario con correo: " + correo);
            return null;
        }
    }
    
    // ⭐ NUEVO: Verificar si existe email
    public boolean existsEmail(String correo) {
        Long count = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo", Long.class)
                .setParameter("correo", correo)
                .getSingleResult();
        return count > 0;
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