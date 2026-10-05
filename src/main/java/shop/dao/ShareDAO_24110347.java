package shop.dao;

import jakarta.persistence.EntityManager;
import shop.entity.Share_24110347;
import shop.util.JPAUtil_24110347;

public class ShareDAO_24110347 {

    public long countByVideo(Integer videoId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(s) from Share_24110347 s WHERE s.video.videoId = :vid", Long.class)
                    .setParameter("vid", videoId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public void save(Share_24110347 share) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(share);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
