package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.service.CartService_24110347;
import shop.service.UserService_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet_24110347 extends HttpServlet {
    private final UserService_24110347 userService = new UserService_24110347();
    private final CartService_24110347 cartService = new CartService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService_24110347.LoginResult result = userService.login(username, password);

        if (!result.success) {
            req.setAttribute("error", result.message);
            req.setAttribute("username", username);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("account", result.user);
        if (!result.user.isAdmin()) {
            WebUtil_24110347.setCartCount(req, cartService.countItems(result.user.getUsername()));
        }

        // Dang nhap voi vai tro admin thanh cong -> trang chu Admin.
        // Nguoc lai (vai tro user) -> trang chu User.
        if (result.user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
