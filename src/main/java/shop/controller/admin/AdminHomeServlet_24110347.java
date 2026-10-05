package shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.service.CategoryService_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;

@WebServlet("/admin/home")
public class AdminHomeServlet_24110347 extends HttpServlet {
    private final VideoService_24110347 videoService = new VideoService_24110347();
    private final CategoryService_24110347 categoryService = new CategoryService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("totalVideos", videoService.listForAdmin(1).getTotalItems());
        req.setAttribute("totalCategories", categoryService.getAll().size());
        req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
    }
}
