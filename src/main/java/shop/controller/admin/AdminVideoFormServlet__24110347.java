package shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import shop.entity.Category_24110347;
import shop.entity.Video_24110347;
import shop.service.CategoryService_24110347;
import shop.service.VideoService_24110347;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Xu ly ca them moi (khong co tham so id) va cap nhat (co tham so id) Video -
 * gop chung 1 servlet + 1 JSP dung chung form de gon nhe.
 *
 * Ho tro upload file video that (truong "videoFile"), luu vat ly vao thu muc
 * assets/video/ trong webapp dang deploy, ten cot/field tuong ung la
 * Videos.VideoFile / Video.videoFile (can chay ALTER TABLE truoc khi dung).
 *
 * Ho tro CRUD anh Poster (truong "posterFile"): upload khi them moi (Create),
 * hien thi anh hien tai (Read) va cho phep thay the (Update) khi sua video
 * ngay tren form nay; xoa han poster (Delete) do AdminVideoPosterDeleteServlet
 * hoac checkbox "removePoster" dam nhiem.
 */
@WebServlet({"/admin/videos/add", "/admin/videos/edit"})
@MultipartConfig(
        maxFileSize = 200L * 1024 * 1024,      // 200MB / 1 video
        maxRequestSize = 220L * 1024 * 1024,
        fileSizeThreshold = 1024 * 1024
)
public class AdminVideoFormServlet__24110347 extends HttpServlet {
    private static final String UPLOAD_SUBDIR = "/assets/video/";
    private static final String POSTER_SUBDIR = "/assets/img/";

    private final VideoService_24110347 videoService = new VideoService_24110347();
    private final CategoryService_24110347 categoryService = new CategoryService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));
        if (id != null) {
            Video_24110347 video = videoService.findById(id);
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
            req.setAttribute("video", video);
        }
        req.setAttribute("categories", categoryService.getAll());
        req.getRequestDispatcher("/admin/video-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Integer categoryId = parseId(req.getParameter("categoryId"));
        boolean active = "on".equals(req.getParameter("active"));
        boolean removePoster = "on".equals(req.getParameter("removePoster"));
        BigDecimal price = parsePrice(req.getParameter("price"));
        int stock = parseStock(req.getParameter("stock"));

        Category_24110347 category = categoryId != null ? categoryService.findById(categoryId) : null;

        if (id == null) {
            Video_24110347 video = new Video_24110347();
            video.setTitle(title);
            video.setDescription(description);
            video.setCategory(category);
            video.setActive(active);
            video.setViews(0);
            video.setPrice(price);
            video.setStock(stock);
            saveUploadedVideoIfPresent(req, video);
            saveUploadedPosterIfPresent(req, video);
            videoService.create(video);
        } else {
            Video_24110347 video = videoService.findById(id);
            if (video != null) {
                video.setTitle(title);
                video.setDescription(description);
                video.setCategory(category);
                video.setActive(active);
                video.setPrice(price);
                video.setStock(stock);
                // Neu admin khong chon file video moi khi sua thi giu nguyen file cu,
                // khong ghi de thanh rong.
                saveUploadedVideoIfPresent(req, video);
                // Neu admin tick "xoa poster" va khong chon anh moi thi xoa han poster
                // hien tai (ca file vat ly lan gia tri trong DB, thao tac Delete).
                if (removePoster && isPosterPartEmpty(req)) {
                    deletePosterFile(video.getPoster());
                    video.setPoster(null);
                }
                saveUploadedPosterIfPresent(req, video);
                videoService.update(video);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }

    /**
     * Doc phan file "videoFile" trong request (neu admin co chon file), luu
     * vao {webapp}/assets/video/ voi ten duy nhat, roi gan ten file do vao
     * video.videoFile. Neu khong chon file nao (Part rong, size = 0) thi bo
     * qua, giu nguyen gia tri videoFile hien co cua video.
     */
    private void saveUploadedVideoIfPresent(HttpServletRequest req, Video_24110347 video) throws IOException, ServletException {
        Part part = req.getPart("videoFile");
        if (part == null || part.getSize() <= 0) {
            return;
        }
        String originalName = extractFileName(part);
        if (originalName == null || originalName.isBlank()) {
            return;
        }
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            ext = originalName.substring(dot);
        }
        String storedName = UUID.randomUUID() + ext;

        String realDir = getServletContext().getRealPath(UPLOAD_SUBDIR);
        File dir = new File(realDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path target = Path.of(realDir, storedName);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        video.setVideoFile(storedName);
    }

    /**
     * Doc phan file "posterFile" trong request (neu admin co chon anh moi),
     * luu vao {webapp}/assets/img/ voi ten duy nhat, roi gan ten file do vao
     * video.poster (Create khi them moi / Update khi sua). Neu khong chon
     * anh nao thi bo qua, giu nguyen poster hien co cua video. Khi thay the
     * poster cu bang poster moi, file cu se bi xoa khoi dia (don dep).
     */
    private void saveUploadedPosterIfPresent(HttpServletRequest req, Video_24110347 video) throws IOException, ServletException {
        if (isPosterPartEmpty(req)) {
            return;
        }
        Part part = req.getPart("posterFile");
        String originalName = extractFileName(part);
        if (originalName == null || originalName.isBlank()) {
            return;
        }
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            ext = originalName.substring(dot);
        }
        String storedName = UUID.randomUUID() + ext;

        String realDir = getServletContext().getRealPath(POSTER_SUBDIR);
        File dir = new File(realDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path target = Path.of(realDir, storedName);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Co poster cu (dang Update) thi xoa file vat ly cu di truoc khi gan file moi.
        String oldPoster = video.getPoster();
        if (oldPoster != null && !oldPoster.isBlank() && !oldPoster.equals(storedName)) {
            deletePosterFile(oldPoster);
        }

        video.setPoster(storedName);
    }

    private boolean isPosterPartEmpty(HttpServletRequest req) throws IOException, ServletException {
        Part part = req.getPart("posterFile");
        return part == null || part.getSize() <= 0;
    }

    /**
     * Xoa file poster vat ly trong assets/img/ (thao tac Delete cua CRUD
     * poster). Bo qua neu ten file rong hoac file khong ton tai.
     */
    private void deletePosterFile(String posterFileName) {
        if (posterFileName == null || posterFileName.isBlank()) {
            return;
        }
        String realDir = getServletContext().getRealPath(POSTER_SUBDIR);
        if (realDir == null) {
            return;
        }
        try {
            Files.deleteIfExists(Path.of(realDir, posterFileName));
        } catch (IOException ignored) {
        }
    }

    private String extractFileName(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) return null;
        for (String token : header.split(";")) {
            token = token.trim();
            if (token.startsWith("filename")) {
                String name = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                // Loai bo duong dan (mot so trinh duyet cu gui ca duong dan) chi lay ten file.
                int slash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
                return slash >= 0 ? name.substring(slash + 1) : name;
            }
        }
        return null;
    }

    private BigDecimal parsePrice(String raw) {
        try {
            BigDecimal value = new BigDecimal(raw.trim().replace(",", "").replace(".", ""));
            return value.signum() < 0 ? BigDecimal.ZERO : value;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private int parseStock(String raw) {
        try {
            return Math.max(0, Integer.parseInt(raw.trim()));
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer parseId(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return null;
        }
    }
}
