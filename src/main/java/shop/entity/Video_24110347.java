package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Videos")
public class Video_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VideoId")
    private Integer videoId;

    @Column(name = "Title", length = 200)
    private String title;

    @Column(name = "Poster", length = 50)
    private String poster;

    @Column(name = "VideoFile", length = 255)
    private String videoFile;

    @Column(name = "Views")
    private Integer views;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Price", precision = 18, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId")
    private Category_24110347 category;

    public Video_24110347() {
    }

    public Integer getVideoId() { return videoId; }
    public void setVideoId(Integer videoId) { this.videoId = videoId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getVideoFile() { return videoFile; }
    public void setVideoFile(String videoFile) { this.videoFile = videoFile; }

    public Integer getViews() { return views == null ? 0 : views; }
    public void setViews(Integer views) { this.views = views; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return active == null || active; }
    public void setActive(Boolean active) { this.active = active; }

    public BigDecimal getPrice() { return price == null ? BigDecimal.ZERO : price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock == null ? 0 : stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public boolean isPurchasable() { return isActive() && getStock() > 0; }

    public int getMaxOrderQuantity() { return Math.min(getStock(), CartItem_24110347.MAX_QUANTITY_PER_ITEM); }

    public Category_24110347 getCategory() { return category; }
    public void setCategory(Category_24110347 category) { this.category = category; }
}
