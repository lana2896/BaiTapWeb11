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

@WebServlet("/admin/videos/delete")
public class AdminVideoDeleteServlet__24110347 extends HttpServlet {
    private static final String POSTER_SUBDIR = "/assets/img/";
    private static final String VIDEO_SUBDIR = "/assets/video/";

    private final VideoService_24110347 videoService = new VideoService_24110347();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            // Lay thong tin file poster/video truoc khi xoa ban ghi, de con
            // don dep file vat ly tren dia, tranh rac file mo coi.
            Video_24110347 video = videoService.findById(id);
            if (video != null) {
                deletePhysicalFile(POSTER_SUBDIR, video.getPoster());
                deletePhysicalFile(VIDEO_SUBDIR, video.getVideoFile());
            }
            videoService.delete(id);
        } catch (Exception ignored) {
        }
        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }

    private void deletePhysicalFile(String subDir, String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return;
        }
        String realDir = getServletContext().getRealPath(subDir);
        if (realDir == null) {
            return;
        }
        try {
            Files.deleteIfExists(Path.of(realDir, fileName));
        } catch (IOException ignored) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }
}
