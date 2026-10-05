package shop.util;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.logging.Logger;


public class MailUtil_24110347 {
	 private static final Logger LOGGER = Logger.getLogger(MailUtil_24110347.class.getName());
	public static final String SESSION_USERNAME = "username";
	public static final String COOKIE_REMEMBER = "username";
	public static final String DIR = "C:\\upload";
	/** Thư mục con (bên trong DIR) dùng để lưu ảnh đại diện người dùng */
	public static final String AVATAR_SUBDIR = "avatar";

    // Mail
    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final int MAIL_PORT = 587;
    public static final String MAIL_USERNAME = "minhthu.08092006@gmail.com";
    public static final String MAIL_PASSWORD = "biaa cjyk bzmr zjiu";   
    public static final String MAIL_FROM_NAME = "Shop Support";

    public static final int OTP_EXPIRE_MINUTES = 5;
    public static final int HOME_LATEST_PRODUCT_COUNT = 10;
    public static final int PRODUCT_PAGE_SIZE = 6;

    private MailUtil_24110347() {
    }

    public static void sendOtpEmail(String toEmail, String username, String otp) {
        // Luon log ra console/log server de tien demo khi chua cau hinh SMTP that.
        LOGGER.info("[OTP] Gui toi " + toEmail + " (user: " + username + ") - Ma OTP: " + otp
                + " (het han sau " + OtpUtil_24110347.OTP_EXPIRE_MINUTES + " phut)");

        if (MAIL_USERNAME.startsWith("your-email")) {
            // Chua cau hinh SMTP that -> chi dung console log o tren la du de demo.
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", MAIL_HOST);
        props.put("mail.smtp.port", String.valueOf(MAIL_PORT));

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_USERNAME, MAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL_USERNAME, MAIL_FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Ma OTP kich hoat tai khoan ShopVideo");
            message.setText("Xin chao " + username + ",\n\nMa OTP cua ban la: " + otp
                    + "\nMa nay het han sau " + OtpUtil_24110347.OTP_EXPIRE_MINUTES + " phut.");
            Transport.send(message);
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            LOGGER.warning("Khong gui duoc email that (van co the dung OTP da log o console): " + e.getMessage());
        }
    }
}
