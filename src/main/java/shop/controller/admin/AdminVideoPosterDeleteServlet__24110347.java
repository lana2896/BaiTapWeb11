package shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.Video_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thao tac "Delete" rieng cho anh Poster cua Video (khong xoa ca video):
 * xoa file vat ly trong assets/img/ va gan Video.poster = null trong DB.
 * Dung cho nut "Xóa poster" ngay trong bang danh sach (admin/video-list.jsp),
 * khong can mo form sua video.
 */
@WebServlet("/admin/videos/poster/delete")
public class AdminVideoPosterDeleteServlet__24110347 extends HttpServlet {
    private static final String POSTER_SUBDIR = "/assets/img/";

    private final VideoService_24110347 videoService = new VideoService_24110347();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Video_24110347 video = videoService.findById(id);
            if (video != null && video.getPoster() != null && !video.getPoster().isBlank()) {
                String realDir = getServletContext().getRealPath(POSTER_SUBDIR);
                if (realDir != null) {
                    try {
                        Files.deleteIfExists(Path.of(realDir, video.getPoster()));
                    } catch (IOException ignored) {
                    }
                }
                video.setPoster(null);
                videoService.update(video);
            }
        } catch (Exception ignored) {
        }
        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }
}
