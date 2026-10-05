package shop.service;

import shop.dao.OrderDAO_24110347;
import shop.entity.OrderStatus_24110347;
import shop.entity.Order_24110347;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OrderService_24110347 {
    public static final int ADMIN_PAGE_SIZE = 10;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9,10}$");

    private final OrderDAO_24110347 orderDAO = new OrderDAO_24110347();

    public List<String> validateCheckout(String receiverName, String phone, String address, String note) {
        List<String> errors = new ArrayList<>();
        if (receiverName == null || receiverName.isBlank()) {
            errors.add("Vui lòng nhập họ tên người nhận.");
        } else if (receiverName.length() > 100) {
            errors.add("Họ tên người nhận tối đa 100 ký tự.");
        }
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            errors.add("Số điện thoại phải bắt đầu bằng 0 và gồm 10-11 chữ số.");
        }
        if (address == null || address.isBlank()) {
            errors.add("Vui lòng nhập địa chỉ giao hàng.");
        } else if (address.length() > 255) {
            errors.add("Địa chỉ tối đa 255 ký tự.");
        }
        if (note != null && note.length() > 500) {
            errors.add("Ghi chú tối đa 500 ký tự.");
        }
        return errors;
    }

    public Integer placeCodOrder(String username, String receiverName, String phone, String address, String note) {
        String cleanNote = (note == null || note.isBlank()) ? null : note;
        return orderDAO.placeCodOrder(username, receiverName, phone, address, cleanNote);
    }

    public List<Order_24110347> listByUser(String username) {
        return orderDAO.findByUser(username);
    }

    public Order_24110347 findById(Integer orderId) {
        return orderId == null ? null : orderDAO.findByIdWithDetails(orderId);
    }

    public Order_24110347 findForUser(Integer orderId, String username) {
        Order_24110347 order = findById(orderId);
        if (order == null || order.getUser() == null || !order.getUser().getUsername().equals(username)) {
            return null;
        }
        return order;
    }

    public void cancelByUser(Integer orderId, String username) {
        orderDAO.changeStatus(orderId, OrderStatus_24110347.CANCELLED, username);
    }

    public void changeStatusByAdmin(Integer orderId, OrderStatus_24110347 target) {
        orderDAO.changeStatus(orderId, target, null);
    }

    public PageResult_24110347<Order_24110347> listForAdmin(OrderStatus_24110347 status, int page) {
        long total = orderDAO.count(status);
        int totalPages = (int) Math.max(1, Math.ceil(total / (double) ADMIN_PAGE_SIZE));
        int current = Math.min(Math.max(page, 1), totalPages);
        List<Order_24110347> items = orderDAO.findPage(status, current, ADMIN_PAGE_SIZE);
        return new PageResult_24110347<>(items, current, ADMIN_PAGE_SIZE, total);
    }

    public long count(OrderStatus_24110347 status) {
        return orderDAO.count(status);
    }

    public BigDecimal revenue() {
        return orderDAO.sumRevenue();
    }
}
