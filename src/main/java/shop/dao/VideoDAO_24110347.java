package shop.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import shop.entity.Video_24110347;
import shop.util.JPAUtil_24110347;

import java.util.List;

public class VideoDAO_24110347 {

    public Video_24110347 findById(Integer id) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.find(Video_24110347.class, id);
        } finally {
            em.close();
        }
    }

    public long countAll() {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) FROM Video_24110347 v", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Video_24110347> findPage(int page, int pageSize) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            TypedQuery<Video_24110347> q = em.createQuery(
                    "SELECT v FROM Video_24110347 v ORDER BY v.videoId DESC", Video_24110347.class);
            q.setFirstResult((page - 1) * pageSize);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public long countByCategory(Integer categoryId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(v) FROM Video_24110347 v WHERE v.category.categoryId = :cid AND v.active = true",
                    Long.class)
                    .setParameter("cid", categoryId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Video_24110347> findPageByCategory(Integer categoryId, int page, int pageSize) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            TypedQuery<Video_24110347> q = em.createQuery(
                    "SELECT v FROM Video_24110347 v WHERE v.category.categoryId = :cid AND v.active = true "
                            + "ORDER BY v.videoId DESC",
                    Video_24110347.class);
            q.setParameter("cid", categoryId);
            q.setFirstResult((page - 1) * pageSize);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Video_24110347 video) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(Video_24110347 video) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(video);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            Video_24110347 v = em.find(Video_24110347.class, id);
            if (v != null) {
                em.remove(v);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void incrementViews(Integer id) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            em.getTransaction().begin();
            Video_24110347 v = em.find(Video_24110347.class, id);
            if (v != null) {
                v.setViews(v.getViews() + 1);
                em.merge(v);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
