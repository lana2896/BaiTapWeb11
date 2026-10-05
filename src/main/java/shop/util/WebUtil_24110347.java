package shop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import shop.entity.User_24110347;

public final class WebUtil_24110347 {
    public static final String FLASH_MESSAGE = "flashMessage";
    public static final String FLASH_TYPE = "flashType";
    public static final String CART_COUNT = "cartCount";

    private WebUtil_24110347() {
    }

    public static User_24110347 currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (User_24110347) session.getAttribute("account");
    }

    public static void success(HttpServletRequest req, String message) {
        flash(req, "success", message);
    }

    public static void error(HttpServletRequest req, String message) {
        flash(req, "danger", message);
    }

    public static void warning(HttpServletRequest req, String message) {
        flash(req, "warning", message);
    }

    private static void flash(HttpServletRequest req, String type, String message) {
        HttpSession session = req.getSession(true);
        session.setAttribute(FLASH_TYPE, type);
        session.setAttribute(FLASH_MESSAGE, message);
    }

    public static void setCartCount(HttpServletRequest req, int count) {
        req.getSession(true).setAttribute(CART_COUNT, count);
    }

    public static Integer parseInt(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String trim(String raw) {
        return raw == null ? null : raw.trim();
    }
}
