package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Shares")
public class Share_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShareId")
    private Integer shareId;

    @Column(name = "Emails", length = 50)
    private String emails;

    @Temporal(TemporalType.DATE)
    @Column(name = "SharedDate")
    private Date sharedDate;

    @ManyToOne
    @JoinColumn(name = "Username")
    private User_24110347 user;

    @ManyToOne
    @JoinColumn(name = "VideoId")
    private Video_24110347 video;

    public Share_24110347() {
    }

    public Integer getShareId() { return shareId; }
    public void setShareId(Integer shareId) { this.shareId = shareId; }

    public String getEmails() { return emails; }
    public void setEmails(String emails) { this.emails = emails; }

    public Date getSharedDate() { return sharedDate; }
    public void setSharedDate(Date sharedDate) { this.sharedDate = sharedDate; }

    public User_24110347 getUser() { return user; }
    public void setUser(User_24110347 user) { this.user = user; }

    public Video_24110347 getVideo() { return video; }
    public void setVideo(Video_24110347 video) { this.video = video; }
}
