package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.service.UserService_24110347;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet_24110347 extends HttpServlet {
    private final UserService_24110347 userService = new UserService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        UserService_24110347.RegisterResult result = userService.register(username, password, fullname, email, phone);

        if (!result.success) {
            req.setAttribute("error", result.message);
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("username", username);
        req.setAttribute("info", result.message);
        req.getRequestDispatcher("/verify-otp.jsp").forward(req, resp);
    }
}
