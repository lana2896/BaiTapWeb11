package shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.Category_24110347;
import shop.service.CategoryService_24110347;
import shop.service.PageResult_24110347;
import shop.entity.Video_24110347;
import shop.service.VideoService_24110347;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Trang chu vai tro User: hien thi tat ca video theo tung Category, moi
 * Category phan trang rieng 3 video/trang (Cau 4), kem so luong video cua
 * tung Category (Cau 5). Tham so phan trang cua moi category duoc truyen qua
 * query string dang "p{CategoryId}", vi du: ?p3=2 nghia la Category co id=3
 * dang o trang 2.
 */
@WebServlet("/home")
public class HomeServlet__24110347 extends HttpServlet {
    private final CategoryService_24110347 categoryService = new CategoryService_24110347();
    private final VideoService_24110347 videoService = new VideoService_24110347();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category_24110347> categories = categoryService.getAll();

        Map<Category_24110347, PageResult_24110347<Video_24110347>> data = new LinkedHashMap<>();
        for (Category_24110347 c : categories) {
            int page = parsePage(req.getParameter("p" + c.getCategoryId()));
            PageResult_24110347<Video_24110347> pageResult = videoService.listByCategory(c.getCategoryId(), page);
            data.put(c, pageResult);
        }

        req.setAttribute("categoryData", data);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    private int parsePage(String raw) {
        try {
            int p = Integer.parseInt(raw);
            return Math.max(p, 1);
        } catch (Exception e) {
            return 1;
        }
    }
}
