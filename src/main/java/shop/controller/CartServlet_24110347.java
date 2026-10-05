package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.CartItem_24110347;
import shop.entity.User_24110347;
import shop.service.CartService_24110347;
import shop.util.BusinessException_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;
import java.util.List;

@WebServlet({"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartServlet_24110347 extends HttpServlet {
    private final CartService_24110347 cartService = new CartService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!"/cart".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        User_24110347 account = WebUtil_24110347.currentUser(req);
        List<CartItem_24110347> items = cartService.getItems(account.getUsername());
        int count = 0;
        for (CartItem_24110347 item : items) {
            count += item.getQuantity();
        }
        WebUtil_24110347.setCartCount(req, count);

        req.setAttribute("items", items);
        req.setAttribute("total", cartService.getTotal(items));
        req.setAttribute("hasProblem", cartService.hasProblem(items));
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User_24110347 account = WebUtil_24110347.currentUser(req);
        String username = account.getUsername();
        String path = req.getServletPath();
        Integer videoId = WebUtil_24110347.parseInt(req.getParameter("videoId"));
        Integer quantity = WebUtil_24110347.parseInt(req.getParameter("quantity"));
        String redirect = req.getContextPath() + "/cart";

        try {
            switch (path) {
                case "/cart/add":
                    if (videoId == null) {
                        throw new BusinessException_24110347("Sản phẩm không hợp lệ.");
                    }
                    WebUtil_24110347.success(req, cartService.add(username, videoId, quantity == null ? 1 : quantity));
                    if ("home".equals(req.getParameter("back"))) {
                        redirect = req.getContextPath() + "/home";
                    }
                    break;
                case "/cart/update":
                    if (videoId == null || quantity == null) {
                        throw new BusinessException_24110347("Số lượng không hợp lệ.");
                    }
                    WebUtil_24110347.success(req, cartService.update(username, videoId, quantity));
                    break;
                case "/cart/remove":
                    if (videoId == null) {
                        throw new BusinessException_24110347("Sản phẩm không hợp lệ.");
                    }
                    WebUtil_24110347.success(req, cartService.remove(username, videoId));
                    break;
                case "/cart/clear":
                    WebUtil_24110347.success(req, cartService.clear(username));
                    break;
                default:
                    break;
            }
        } catch (BusinessException_24110347 e) {
            WebUtil_24110347.error(req, e.getMessage());
            if ("/cart/add".equals(path) && "home".equals(req.getParameter("back"))) {
                redirect = req.getContextPath() + "/home";
            }
        }

        WebUtil_24110347.setCartCount(req, cartService.countItems(username));
        resp.sendRedirect(redirect);
    }
}
