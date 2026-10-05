package shop.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import shop.entity.CartItem_24110347;
import shop.entity.OrderDetail_24110347;
import shop.entity.OrderStatus_24110347;
import shop.entity.Order_24110347;
import shop.entity.User_24110347;
import shop.entity.Video_24110347;
import shop.util.BusinessException_24110347;
import shop.util.JPAUtil_24110347;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO_24110347 {

    public Integer placeCodOrder(String username, String receiverName, String phone, String address, String note) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<CartItem_24110347> items = em.createQuery(
                    "SELECT c FROM CartItem_24110347 c WHERE c.user.username = :u ORDER BY c.cartItemId",
                    CartItem_24110347.class)
                    .setParameter("u", username)
                    .getResultList();
            if (items.isEmpty()) {
                throw new BusinessException_24110347("Giỏ hàng của bạn đang trống.");
            }

            List<String> problems = new ArrayList<>();
            for (CartItem_24110347 item : items) {
                Video_24110347 video = item.getVideo();
                em.refresh(video, LockModeType.PESSIMISTIC_WRITE);
                if (!video.isActive()) {
                    problems.add("\"" + video.getTitle() + "\" đã ngừng bán");
                } else if (video.getStock() <= 0) {
                    problems.add("\"" + video.getTitle() + "\" đã hết hàng");
                } else if (item.getQuantity() > video.getStock()) {
                    problems.add("\"" + video.getTitle() + "\" chỉ còn " + video.getStock() + " sản phẩm");
                } else if (item.getQuantity() > CartItem_24110347.MAX_QUANTITY_PER_ITEM) {
                    problems.add("\"" + video.getTitle() + "\" chỉ được mua tối đa "
                            + CartItem_24110347.MAX_QUANTITY_PER_ITEM + " sản phẩm/đơn");
                }
            }
            if (!problems.isEmpty()) {
                throw new BusinessException_24110347("Không thể đặt hàng: " + String.join("; ", problems)
                        + ". Vui lòng cập nhật lại giỏ hàng.");
            }

            Date now = new Date();
            Order_24110347 order = new Order_24110347();
            order.setUser(em.getReference(User_24110347.class, username));
            order.setReceiverName(receiverName);
            order.setPhone(phone);
            order.setAddress(address);
            order.setNote(note);
            order.setPaymentMethod(Order_24110347.PAYMENT_COD);
            order.setPaid(false);
            order.setStatus(OrderStatus_24110347.PENDING);
            order.setCreatedDate(now);

            BigDecimal total = BigDecimal.ZERO;
            for (CartItem_24110347 item : items) {
                Video_24110347 video = item.getVideo();
                OrderDetail_24110347 detail = new OrderDetail_24110347();
                detail.setVideo(video);
                detail.setVideoTitle(video.getTitle() == null ? ("Video #" + video.getVideoId()) : video.getTitle());
                detail.setPrice(video.getPrice());
                detail.setQuantity(item.getQuantity());
                order.addDetail(detail);
                total = total.add(detail.getSubtotal());
                video.setStock(video.getStock() - item.getQuantity());
            }
            order.setTotalAmount(total);

            em.persist(order);
            for (CartItem_24110347 item : items) {
                em.remove(item);
            }
            tx.commit();
            return order.getOrderId();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void changeStatus(Integer orderId, OrderStatus_24110347 target, String ownerUsername) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order_24110347 order = em.find(Order_24110347.class, orderId, LockModeType.PESSIMISTIC_WRITE);
            if (order == null
                    || (ownerUsername != null && !ownerUsername.equals(order.getUser().getUsername()))) {
                throw new BusinessException_24110347("Không tìm thấy đơn hàng.");
            }
            OrderStatus_24110347 current = order.getStatus();
            if (ownerUsername != null && !(current == OrderStatus_24110347.PENDING
                    && target == OrderStatus_24110347.CANCELLED)) {
                throw new BusinessException_24110347("Chỉ có thể hủy đơn hàng đang ở trạng thái chờ xác nhận.");
            }
            if (current == null || !current.canChangeTo(target)) {
                throw new BusinessException_24110347("Không thể chuyển đơn hàng từ \""
                        + (current == null ? "?" : current.getLabel()) + "\" sang \""
                        + (target == null ? "?" : target.getLabel()) + "\".");
            }

            Date now = new Date();
            if (target == OrderStatus_24110347.CANCELLED) {
                for (OrderDetail_24110347 detail : order.getDetails()) {
                    Video_24110347 video = detail.getVideo();
                    if (video != null) {
                        em.refresh(video, LockModeType.PESSIMISTIC_WRITE);
                        video.setStock(video.getStock() + detail.getQuantity());
                    }
                }
            }
            if (target == OrderStatus_24110347.COMPLETED) {
                order.setPaid(true);
                order.setPaidDate(now);
            }
            order.setStatus(target);
            order.setUpdatedDate(now);
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

    public Order_24110347 findByIdWithDetails(Integer orderId) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            List<Order_24110347> list = em.createQuery(
                    "SELECT DISTINCT o FROM Order_24110347 o LEFT JOIN FETCH o.details WHERE o.orderId = :id",
                    Order_24110347.class)
                    .setParameter("id", orderId)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    public List<Order_24110347> findByUser(String username) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT o FROM Order_24110347 o WHERE o.user.username = :u ORDER BY o.createdDate DESC, o.orderId DESC",
                    Order_24110347.class)
                    .setParameter("u", username)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long count(OrderStatus_24110347 status) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            if (status == null) {
                return em.createQuery("SELECT COUNT(o) FROM Order_24110347 o", Long.class).getSingleResult();
            }
            return em.createQuery("SELECT COUNT(o) FROM Order_24110347 o WHERE o.status = :s", Long.class)
                    .setParameter("s", status)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Order_24110347> findPage(OrderStatus_24110347 status, int page, int pageSize) {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            TypedQuery<Order_24110347> q;
            if (status == null) {
                q = em.createQuery("SELECT o FROM Order_24110347 o ORDER BY o.createdDate DESC, o.orderId DESC",
                        Order_24110347.class);
            } else {
                q = em.createQuery("SELECT o FROM Order_24110347 o WHERE o.status = :s "
                        + "ORDER BY o.createdDate DESC, o.orderId DESC", Order_24110347.class);
                q.setParameter("s", status);
            }
            q.setFirstResult((page - 1) * pageSize);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BigDecimal sumRevenue() {
        EntityManager em = JPAUtil_24110347.getEntityManager();
        try {
            BigDecimal total = em.createQuery(
                    "SELECT SUM(o.totalAmount) FROM Order_24110347 o WHERE o.status = :s", BigDecimal.class)
                    .setParameter("s", OrderStatus_24110347.COMPLETED)
                    .getSingleResult();
            return total == null ? BigDecimal.ZERO : total;
        } finally {
            em.close();
        }
    }
}
