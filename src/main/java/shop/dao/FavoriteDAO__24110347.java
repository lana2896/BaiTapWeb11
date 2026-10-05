package shop.dao;

import jakarta.persistence.EntityManager;
import shop.entity.Favorite_24110347;
import shop.util.JPAUtil_24110347;

public class FavoriteDAO__24110347 {

    public long countByVideo(Integer videoId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(f) from Favorite_24110347 f WHERE f.video.videoId = :vid", Long.class)
                    .setParameter("vid", videoId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean exists(Integer videoId, String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(f) from Favorite_24110347 f WHERE f.video.videoId = :vid AND f.user.username = :u",
                    Long.class)
                    .setParameter("vid", videoId)
                    .setParameter("u", username)
                    .getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }

    public void save(Favorite_24110347 favorite) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(favorite);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
