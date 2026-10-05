package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Favorites")
public class Favorite_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private Integer favoriteId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LikedDate")
    private Date likedDate;

    @ManyToOne
    @JoinColumn(name = "VideoId")
    private Video_24110347 video;

    @ManyToOne
    @JoinColumn(name = "Username")
    private User_24110347 user;

    public Favorite_24110347() {
    }

    public Integer getFavoriteId() { return favoriteId; }
    public void setFavoriteId(Integer favoriteId) { this.favoriteId = favoriteId; }

    public Date getLikedDate() { return likedDate; }
    public void setLikedDate(Date likedDate) { this.likedDate = likedDate; }

    public Video_24110347 getVideo() { return video; }
    public void setVideo(Video_24110347 video) { this.video = video; }

    public User_24110347 getUser() { return user; }
    public void setUser(User_24110347 user) { this.user = user; }
}
