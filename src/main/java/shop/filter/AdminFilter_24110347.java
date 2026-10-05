package shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.entity.User_24110347;

import java.io.IOException;

/**
 * Chan truy cap truc tiep vao /admin/* neu chua dang nhap hoac khong phai admin.
 */
@WebFilter("/admin/*")
public class AdminFilter_24110347 implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User_24110347 account = (session != null) ? (User_24110347) session.getAttribute("account") : null;

        if (account == null || !account.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, res);
    }
}
