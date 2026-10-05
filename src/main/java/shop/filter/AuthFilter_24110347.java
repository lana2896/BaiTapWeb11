package shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.entity.User_24110347;
import shop.util.WebUtil_24110347;

import java.io.IOException;

@WebFilter({"/cart", "/cart/*"})
public class AuthFilter_24110347 implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        User_24110347 account = WebUtil_24110347.currentUser(request);
        if (account == null) {
            WebUtil_24110347.warning(request, "Vui lòng đăng nhập để sử dụng giỏ hàng.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (account.isAdmin()) {
            WebUtil_24110347.warning(request, "Tài khoản quản trị chỉ được xem sản phẩm, không sử dụng giỏ hàng.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        chain.doFilter(req, res);
    }
}
