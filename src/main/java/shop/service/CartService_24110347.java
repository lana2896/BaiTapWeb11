package shop.service;

import jakarta.persistence.PersistenceException;
import shop.dao.CartDAO_24110347;
import shop.dao.VideoDAO_24110347;
import shop.entity.CartItem_24110347;
import shop.entity.Video_24110347;
import shop.util.BusinessException_24110347;

import java.math.BigDecimal;
import java.util.List;

public class CartService_24110347 {
    private final CartDAO_24110347 cartDAO = new CartDAO_24110347();
    private final VideoDAO_24110347 videoDAO = new VideoDAO_24110347();

    public List<CartItem_24110347> getItems(String username) {
        return cartDAO.findByUser(username);
    }

    public int countItems(String username) {
        return cartDAO.sumQuantity(username);
    }

    public BigDecimal getTotal(List<CartItem_24110347> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24110347 item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    public boolean hasProblem(List<CartItem_24110347> items) {
        for (CartItem_24110347 item : items) {
            if (!item.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public String add(String username, Integer videoId, int quantity) {
        if (quantity < 1) {
            throw new BusinessException_24110347("Số lượng thêm vào giỏ phải từ 1 trở lên.");
        }
        Video_24110347 video = requirePurchasable(videoId);
        int max = video.getMaxOrderQuantity();

        CartItem_24110347 existing = cartDAO.findByUserAndVideo(username, videoId);
        int current = existing == null ? 0 : existing.getQuantity();
        if (current >= max) {
            throw new BusinessException_24110347("\"" + video.getTitle() + "\" trong giỏ đã đạt số lượng tối đa ("
                    + max + ").");
        }

        int target = current + quantity;
        boolean capped = false;
        if (target > max) {
            target = max;
            capped = true;
        }

        if (existing == null) {
            try {
                cartDAO.insert(username, videoId, target);
            } catch (PersistenceException e) {
                CartItem_24110347 again = cartDAO.findByUserAndVideo(username, videoId);
                if (again == null) {
                    throw e;
                }
                cartDAO.updateQuantity(again.getCartItemId(), Math.min(again.getQuantity() + quantity, max));
            }
        } else {
            cartDAO.updateQuantity(existing.getCartItemId(), target);
        }

        if (capped) {
            return "Đã thêm \"" + video.getTitle() + "\" vào giỏ. Số lượng được giới hạn tối đa " + max
                    + " nên giỏ hàng hiện có " + target + " sản phẩm này.";
        }
        return "Đã thêm " + quantity + " x \"" + video.getTitle() + "\" vào giỏ hàng.";
    }

    public String update(String username, Integer videoId, int quantity) {
        CartItem_24110347 item = cartDAO.findByUserAndVideo(username, videoId);
        if (item == null) {
            throw new BusinessException_24110347("Sản phẩm không có trong giỏ hàng.");
        }
        if (quantity < 1) {
            throw new BusinessException_24110347("Số lượng tối thiểu là 1. Dùng nút Xóa nếu muốn bỏ sản phẩm.");
        }
        Video_24110347 video = requirePurchasable(videoId);
        int max = video.getMaxOrderQuantity();
        if (quantity > max) {
            throw new BusinessException_24110347("Số lượng tối đa cho \"" + video.getTitle() + "\" là " + max + ".");
        }
        cartDAO.updateQuantity(item.getCartItemId(), quantity);
        return "Đã cập nhật số lượng \"" + video.getTitle() + "\" thành " + quantity + ".";
    }

    public String remove(String username, Integer videoId) {
        int count = cartDAO.deleteByUserAndVideo(username, videoId);
        if (count == 0) {
            throw new BusinessException_24110347("Sản phẩm không có trong giỏ hàng.");
        }
        return "Đã xóa sản phẩm khỏi giỏ hàng.";
    }

    public String clear(String username) {
        cartDAO.deleteByUser(username);
        return "Đã xóa toàn bộ giỏ hàng.";
    }

    private Video_24110347 requirePurchasable(Integer videoId) {
        Video_24110347 video = videoId == null ? null : videoDAO.findById(videoId);
        if (video == null) {
            throw new BusinessException_24110347("Không tìm thấy sản phẩm.");
        }
        if (!video.isActive()) {
            throw new BusinessException_24110347("\"" + video.getTitle() + "\" đã ngừng bán.");
        }
        if (video.getStock() <= 0) {
            throw new BusinessException_24110347("\"" + video.getTitle() + "\" đã hết hàng.");
        }
        return video;
    }
}
