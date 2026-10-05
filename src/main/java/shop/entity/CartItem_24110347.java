package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CartItems", uniqueConstraints = @UniqueConstraint(columnNames = {"Username", "VideoId"}))
public class CartItem_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_QUANTITY_PER_ITEM = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemId")
    private Integer cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Username", nullable = false)
    private User_24110347 user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VideoId", nullable = false)
    private Video_24110347 video;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AddedDate", nullable = false)
    private Date addedDate;

    public CartItem_24110347() {
    }

    public Integer getCartItemId() { return cartItemId; }
    public void setCartItemId(Integer cartItemId) { this.cartItemId = cartItemId; }

    public User_24110347 getUser() { return user; }
    public void setUser(User_24110347 user) { this.user = user; }

    public Video_24110347 getVideo() { return video; }
    public void setVideo(Video_24110347 video) { this.video = video; }

    public Integer getQuantity() { return quantity == null ? 0 : quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }

    public BigDecimal getSubtotal() {
        if (video == null) {
            return BigDecimal.ZERO;
        }
        return video.getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }

    public int getMaxQuantity() {
        return video == null ? 0 : video.getMaxOrderQuantity();
    }

    public boolean isAvailable() {
        return video != null && video.isPurchasable() && getQuantity() <= video.getStock();
    }

    public String getProblem() {
        if (video == null) {
            return "Sản phẩm không còn tồn tại";
        }
        if (!video.isActive()) {
            return "Sản phẩm đã ngừng bán";
        }
        if (video.getStock() <= 0) {
            return "Sản phẩm đã hết hàng";
        }
        if (getQuantity() > video.getStock()) {
            return "Chỉ còn " + video.getStock() + " sản phẩm trong kho";
        }
        return null;
    }
}
