package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.entity.User_24110347;
import shop.entity.Video_24110347;
import shop.service.InteractionService_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;

@WebServlet("/share")
public class ShareServlet_24110347 extends HttpServlet {
    private final VideoService_24110347 videoService = new VideoService_24110347();
    private final InteractionService_24110347 interactionService = new InteractionService_24110347();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("videoId"));
        String email = req.getParameter("email");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        Video_24110347 video = videoService.findById(id);
        if (video != null && email != null && !email.isBlank()) {
            HttpSession session = req.getSession(false);
            User_24110347 account = (session != null) ? (User_24110347) session.getAttribute("account") : null;
            interactionService.share(video, account, email.trim());
        }
        resp.sendRedirect(req.getContextPath() + "/video-detail?id=" + id);
    }

    private Integer parseId(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return null;
        }
    }
}
