package shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.Video_24110347;
import shop.service.PageResult_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;

@WebServlet("/admin/videos")
public class AdminVideoListServlet__24110347 extends HttpServlet {
    private final VideoService_24110347 videoService = new VideoService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int page = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }
        PageResult_24110347<Video_24110347> result = videoService.listForAdmin(Math.max(page, 1));
        req.setAttribute("pageResult", result);
        req.getRequestDispatcher("/admin/video-list.jsp").forward(req, resp);
    }
}
