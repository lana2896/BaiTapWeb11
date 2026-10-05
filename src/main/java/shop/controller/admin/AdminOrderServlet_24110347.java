package shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.OrderStatus_24110347;
import shop.entity.Order_24110347;
import shop.service.OrderService_24110347;
import shop.util.BusinessException_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet({"/admin/orders", "/admin/orders/detail", "/admin/orders/status"})
public class AdminOrderServlet_24110347 extends HttpServlet {
    private final OrderService_24110347 orderService = new OrderService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/orders/detail".equals(path)) {
            Order_24110347 order = orderService.findById(WebUtil_24110347.parseInt(req.getParameter("id")));
            if (order == null) {
                WebUtil_24110347.error(req, "Không tìm thấy đơn hàng.");
                resp.sendRedirect(req.getContextPath() + "/admin/orders");
                return;
            }
            req.setAttribute("order", order);
            req.getRequestDispatcher("/admin/order-detail.jsp").forward(req, resp);
            return;
        }

        if ("/admin/orders/status".equals(path)) {
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }

        OrderStatus_24110347 status = OrderStatus_24110347.parse(req.getParameter("status"));
        Integer page = WebUtil_24110347.parseInt(req.getParameter("page"));

        Map<OrderStatus_24110347, Long> statusCounts = new LinkedHashMap<>();
        for (OrderStatus_24110347 s : OrderStatus_24110347.values()) {
            statusCounts.put(s, orderService.count(s));
        }

        req.setAttribute("statusCounts", statusCounts);
        req.setAttribute("totalOrders", orderService.count(null));
        req.setAttribute("currentStatus", status);
        req.setAttribute("pageResult", orderService.listForAdmin(status, page == null ? 1 : page));
        req.getRequestDispatcher("/admin/order-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!"/admin/orders/status".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }
        Integer orderId = WebUtil_24110347.parseInt(req.getParameter("id"));
        OrderStatus_24110347 target = OrderStatus_24110347.parse(req.getParameter("status"));
        if (orderId == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }
        try {
            if (target == null) {
                throw new BusinessException_24110347("Trạng thái không hợp lệ.");
            }
            orderService.changeStatusByAdmin(orderId, target);
            WebUtil_24110347.success(req, "Đơn #" + orderId + " đã chuyển sang \"" + target.getLabel() + "\".");
        } catch (BusinessException_24110347 e) {
            WebUtil_24110347.error(req, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/orders/detail?id=" + orderId);
    }
}
