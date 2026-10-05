package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.service.UserService_24110347;

import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOtpServlet_24110347 extends HttpServlet {
    private final UserService_24110347 userService = new UserService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("username", req.getParameter("username"));
        req.getRequestDispatcher("/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String otp = req.getParameter("otp");
        String action = req.getParameter("action");

        if ("resend".equals(action)) {
            UserService_24110347.RegisterResult r = userService.resendOtp(username);
            req.setAttribute("username", username);
            req.setAttribute(r.success ? "info" : "error", r.message);
            req.getRequestDispatcher("/verify-otp.jsp").forward(req, resp);
            return;
        }

        UserService_24110347.RegisterResult result = userService.verifyOtp(username, otp);
        if (!result.success) {
            req.setAttribute("username", username);
            req.setAttribute("error", result.message);
            req.getRequestDispatcher("/verify-otp.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login?verified=1");
    }
}
