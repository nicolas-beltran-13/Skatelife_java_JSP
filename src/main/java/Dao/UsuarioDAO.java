package com.skatelife.dao;

import com.skatelife.model.Usuario;
import jakarta.persistence.*;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless

public class UsuarioDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    // Obtener todos los usuarios
    public List<Usuario> findAll() {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u ORDER BY u.fechaRegistro DESC", 
                Usuario.class
            );
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Buscar usuario por ID
    public Usuario findById(Integer id) {
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Buscar por username
    public Usuario findByUsername(String username) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.username = :username", 
                Usuario.class
            );
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Buscar por email/correo
    public Usuario findByEmail(String email) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.correo = :email", 
                Usuario.class
            );
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Buscar por rol
    public List<Usuario> findByRol(String rol) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.rol = :rol ORDER BY u.username", 
                Usuario.class
            );
            query.setParameter("rol", rol);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Crear nuevo usuario
    public void create(Usuario usuario) {
        try {
            em.persist(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear usuario: " + e.getMessage());
        }
    }
    
    // Actualizar usuario
    public void update(Usuario usuario) {
        try {
            em.merge(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage());
        }
    }
    
    // Eliminar usuario
    public void delete(Integer id) {
        try {
            Usuario usuario = findById(id);
            if (usuario != null) {
                em.remove(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
        }
    }
    
    // Contar usuarios por rol
    public Long countByRol(String rol) {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol", 
                Long.class
            );
            query.setParameter("rol", rol);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
    
    // Verificar si username existe
    public boolean existeUsername(String username) {
        return findByUsername(username) != null;
    }
    
    // Verificar si email existe
    public boolean existeEmail(String email) {
        return findByEmail(email) != null;
    }
}