package shop.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import shop.entity.Category_24110347;
import shop.util.JPAUtil_24110347;

import java.util.List;

public class CategoryDAO_24110347 {

    public List<Category_24110347> findAll() {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            TypedQuery<Category_24110347> q = em.createQuery(
                    "SELECT c from Category_24110347 c ORDER BY c.categoryName", Category_24110347.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Category_24110347 findById(Integer id) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.find(Category_24110347.class, id);
        } finally {
            em.close();
        }
    }
}
