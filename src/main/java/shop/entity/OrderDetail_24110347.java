package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailId")
    private Integer orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = false)
    private Order_24110347 order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VideoId")
    private Video_24110347 video;

    @Column(name = "VideoTitle", length = 200, nullable = false)
    private String videoTitle;

    @Column(name = "Price", precision = 18, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    public OrderDetail_24110347() {
    }

    public Integer getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(Integer orderDetailId) { this.orderDetailId = orderDetailId; }

    public Order_24110347 getOrder() { return order; }
    public void setOrder(Order_24110347 order) { this.order = order; }

    public Video_24110347 getVideo() { return video; }
    public void setVideo(Video_24110347 video) { this.video = video; }

    public String getVideoTitle() { return videoTitle; }
    public void setVideoTitle(String videoTitle) { this.videoTitle = videoTitle; }

    public BigDecimal getPrice() { return price == null ? BigDecimal.ZERO : price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity == null ? 0 : quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() {
        return getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }
}
