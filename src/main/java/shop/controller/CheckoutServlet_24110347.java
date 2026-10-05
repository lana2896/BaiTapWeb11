package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.CartItem_24110347;
import shop.entity.User_24110347;
import shop.service.CartService_24110347;
import shop.service.OrderService_24110347;
import shop.util.BusinessException_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet_24110347 extends HttpServlet {
    private final CartService_24110347 cartService = new CartService_24110347();
    private final OrderService_24110347 orderService = new OrderService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User_24110347 account = WebUtil_24110347.currentUser(req);
        List<CartItem_24110347> items = loadValidCart(req, resp, account);
        if (items == null) {
            return;
        }
        req.setAttribute("receiverName", account.getFullname());
        req.setAttribute("phone", account.getPhone());
        showForm(req, resp, items);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User_24110347 account = WebUtil_24110347.currentUser(req);

        String receiverName = WebUtil_24110347.trim(req.getParameter("receiverName"));
        String phone = WebUtil_24110347.trim(req.getParameter("phone"));
        String address = WebUtil_24110347.trim(req.getParameter("address"));
        String note = WebUtil_24110347.trim(req.getParameter("note"));
        String paymentMethod = req.getParameter("paymentMethod");

        List<String> errors = orderService.validateCheckout(receiverName, phone, address, note);
        if (paymentMethod != null && !"COD".equals(paymentMethod)) {
            errors.add("Hiện chỉ hỗ trợ thanh toán khi nhận hàng (COD).");
        }
        if (!errors.isEmpty()) {
            List<CartItem_24110347> items = loadValidCart(req, resp, account);
            if (items == null) {
                return;
            }
            req.setAttribute("errors", errors);
            req.setAttribute("receiverName", receiverName);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            showForm(req, resp, items);
            return;
        }

        try {
            Integer orderId = orderService.placeCodOrder(account.getUsername(), receiverName, phone, address, note);
            WebUtil_24110347.setCartCount(req, 0);
            WebUtil_24110347.success(req, "Đặt hàng thành công! Mã đơn #" + orderId
                    + ". Bạn sẽ thanh toán bằng tiền mặt khi nhận hàng.");
            resp.sendRedirect(req.getContextPath() + "/orders/detail?id=" + orderId);
        } catch (BusinessException_24110347 e) {
            WebUtil_24110347.error(req, e.getMessage());
            WebUtil_24110347.setCartCount(req, cartService.countItems(account.getUsername()));
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private List<CartItem_24110347> loadValidCart(HttpServletRequest req, HttpServletResponse resp,
                                                   User_24110347 account) throws IOException {
        List<CartItem_24110347> items = cartService.getItems(account.getUsername());
        if (items.isEmpty()) {
            WebUtil_24110347.warning(req, "Giỏ hàng đang trống, hãy thêm sản phẩm trước khi thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return null;
        }
        if (cartService.hasProblem(items)) {
            WebUtil_24110347.error(req, "Một số sản phẩm trong giỏ không còn đủ điều kiện mua. Vui lòng kiểm tra lại.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return null;
        }
        return items;
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, List<CartItem_24110347> items)
            throws ServletException, IOException {
        req.setAttribute("items", items);
        req.setAttribute("total", cartService.getTotal(items));
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }
}
