package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.Order_24110347;
import shop.entity.User_24110347;
import shop.service.OrderService_24110347;
import shop.util.BusinessException_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;

@WebServlet({"/orders", "/orders/detail", "/orders/cancel"})
public class OrderServlet_24110347 extends HttpServlet {
    private final OrderService_24110347 orderService = new OrderService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User_24110347 account = WebUtil_24110347.currentUser(req);
        String path = req.getServletPath();

        if ("/orders/detail".equals(path)) {
            Order_24110347 order = orderService.findForUser(
                    WebUtil_24110347.parseInt(req.getParameter("id")), account.getUsername());
            if (order == null) {
                WebUtil_24110347.error(req, "Không tìm thấy đơn hàng.");
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }
            req.setAttribute("order", order);
            req.getRequestDispatcher("/order-detail.jsp").forward(req, resp);
            return;
        }

        if ("/orders/cancel".equals(path)) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        req.setAttribute("orders", orderService.listByUser(account.getUsername()));
        req.getRequestDispatcher("/orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!"/orders/cancel".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        User_24110347 account = WebUtil_24110347.currentUser(req);
        Integer orderId = WebUtil_24110347.parseInt(req.getParameter("id"));
        if (orderId == null) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        try {
            orderService.cancelByUser(orderId, account.getUsername());
            WebUtil_24110347.success(req, "Đã hủy đơn hàng #" + orderId + ".");
        } catch (BusinessException_24110347 e) {
            WebUtil_24110347.error(req, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/orders/detail?id=" + orderId);
    }
}
