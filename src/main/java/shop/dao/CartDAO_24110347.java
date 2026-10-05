package shop.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import shop.entity.CartItem_24110347;
import shop.entity.User_24110347;
import shop.entity.Video_24110347;
import shop.util.JPAUtil_24110347;

import java.util.Date;
import java.util.List;

public class CartDAO_24110347 {

    public List<CartItem_24110347> findByUser(String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM CartItem_24110347 c JOIN FETCH c.video v LEFT JOIN FETCH v.category "
                            + "WHERE c.user.username = :u ORDER BY c.addedDate DESC, c.cartItemId DESC",
                    CartItem_24110347.class)
                    .setParameter("u", username)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public CartItem_24110347 findByUserAndVideo(String username, Integer videoId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            List<CartItem_24110347> list = em.createQuery(
                    "SELECT c FROM CartItem_24110347 c WHERE c.user.username = :u AND c.video.videoId = :vid",
                    CartItem_24110347.class)
                    .setParameter("u", username)
                    .setParameter("vid", videoId)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    public int sumQuantity(String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            Long total = em.createQuery(
                    "SELECT SUM(c.quantity) FROM CartItem_24110347 c WHERE c.user.username = :u",
                    Long.class)
                    .setParameter("u", username)
                    .getSingleResult();
            return total == null ? 0 : total.intValue();
        } finally {
            em.close();
        }
    }

    public void insert(String username, Integer videoId, int quantity) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItem_24110347 item = new CartItem_24110347();
            item.setUser(em.getReference(User_24110347.class, username));
            item.setVideo(em.getReference(Video_24110347.class, videoId));
            item.setQuantity(quantity);
            item.setAddedDate(new Date());
            em.persist(item);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateQuantity(Integer cartItemId, int quantity) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItem_24110347 item = em.find(CartItem_24110347.class, cartItemId);
            if (item != null) {
                item.setQuantity(quantity);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public int deleteByUserAndVideo(String username, Integer videoId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int count = em.createQuery(
                    "DELETE FROM CartItem_24110347 c WHERE c.user.username = :u AND c.video.videoId = :vid")
                    .setParameter("u", username)
                    .setParameter("vid", videoId)
                    .executeUpdate();
            tx.commit();
            return count;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public int deleteByUser(String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int count = em.createQuery("DELETE FROM CartItem_24110347 c WHERE c.user.username = :u")
                    .setParameter("u", username)
                    .executeUpdate();
            tx.commit();
            return count;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
