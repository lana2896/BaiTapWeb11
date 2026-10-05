package shop.dao;

import jakarta.persistence.EntityManager;
import shop.entity.User_24110347;
import shop.util.JPAUtil_24110347;

public class UserDAO_24110347 {

    public User_24110347 findByUsername(String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.find(User_24110347.class, username);
        } finally {
            em.close();
        }
    }

    public boolean exists(String username) {
        return findByUsername(username) != null;
    }

    public void save(User_24110347 user) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(User_24110347 user) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
