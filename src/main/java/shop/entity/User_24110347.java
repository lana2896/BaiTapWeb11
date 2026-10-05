package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username", length = 50)
    private String username;

    @Column(name = "Password", length = 50)
    private String password;

    @Column(name = "Phone", length = 15)
    private String phone;

    @Column(name = "Fullname", length = 50)
    private String fullname;

    @Column(name = "Email", length = 150)
    private String email;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Images", length = 500)
    private String images;

    @Column(name = "OtpCode", length = 10)
    private String otpCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OtpExpiry")
    private Date otpExpiry;

    public User_24110347() {
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() { return admin != null && admin; }
    public void setAdmin(Boolean admin) { this.admin = admin; }

    public boolean isActive() { return active != null && active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public Date getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(Date otpExpiry) { this.otpExpiry = otpExpiry; }
}
