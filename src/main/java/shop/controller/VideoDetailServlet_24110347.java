package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.Video_24110347;
import shop.service.InteractionService_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;

@WebServlet("/video-detail")
public class VideoDetailServlet_24110347 extends HttpServlet {
    private final VideoService_24110347 videoService = new VideoService_24110347();
    private final InteractionService_24110347 interactionService = new InteractionService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Video_24110347 video = videoService.findById(id);
        if (video == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Moi lan xem chi tiet -> tang view (Cau 3).
        videoService.increaseView(id);
        video = videoService.findById(id);

        long shareCount = interactionService.countShares(id);
        long likeCount = interactionService.countLikes(id);

        req.setAttribute("video", video);
        req.setAttribute("shareCount", shareCount);
        req.setAttribute("likeCount", likeCount);
        req.getRequestDispatcher("/video-detail.jsp").forward(req, resp);
    }

    private Integer parseId(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return null;
        }
    }
}
