package shop.service;

import shop.dao.UserDAO_24110347;
import shop.entity.User_24110347;
import shop.util.MailUtil_24110347;
import shop.util.OtpUtil_24110347;

public class UserService_24110347 {

    private final UserDAO_24110347 userDAO = new UserDAO_24110347();

    public static class RegisterResult {
        public boolean success;
        public String message;
        public RegisterResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    public RegisterResult register(String username, String password, String fullname,
                                    String email, String phone) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return new RegisterResult(false, "Tên đăng nhập và mật khẩu không được để trống");
        }
        if (userDAO.exists(username)) {
            return new RegisterResult(false, "Tên đăng nhập đã tồn tại");
        }

        User_24110347 user = new User_24110347();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdmin(false);
        user.setActive(false);

        String otp = OtpUtil_24110347.generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(OtpUtil_24110347.expiryFromNow());

        userDAO.save(user);
        MailUtil_24110347.sendOtpEmail(email, username, otp);

        return new RegisterResult(true, "Đăng ký thành công. Vui lòng nhập mã OTP đã gửi tới email để kích hoạt.");
    }

    public RegisterResult resendOtp(String username) {
        User_24110347 user = userDAO.findByUsername(username);
        if (user == null) {
            return new RegisterResult(false, "Tài khoản không tồn tại");
        }
        if (user.isActive()) {
            return new RegisterResult(false, "Tài khoản đã được kích hoạt");
        }
        String otp = OtpUtil_24110347.generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(OtpUtil_24110347.expiryFromNow());
        userDAO.update(user);
        MailUtil_24110347.sendOtpEmail(user.getEmail(), username, otp);
        return new RegisterResult(true, "Đã gửi lại mã OTP mới");
    }

    public RegisterResult verifyOtp(String username, String otp) {
        User_24110347 user = userDAO.findByUsername(username);
        if (user == null) {
            return new RegisterResult(false, "Tài khoản không tồn tại");
        }
        if (user.isActive()) {
            return new RegisterResult(true, "Tài khoản đã được kích hoạt trước đó");
        }
        if (user.getOtpCode() == null || !user.getOtpCode().equals(otp)) {
            return new RegisterResult(false, "Mã OTP không đúng");
        }
        if (OtpUtil_24110347.isExpired(user.getOtpExpiry())) {
            return new RegisterResult(false, "Mã OTP đã hết hạn, vui lòng gửi lại mã mới");
        }
        user.setActive(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userDAO.update(user);
        return new RegisterResult(true, "Kích hoạt tài khoản thành công. Bạn có thể đăng nhập.");
    }

    public static class LoginResult {
        public boolean success;
        public String message;
        public User_24110347 user;
        public LoginResult(boolean success, String message, User_24110347 user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
    }

    public LoginResult login(String username, String password) {
        User_24110347 user = userDAO.findByUsername(username);
        if (user == null || user.getPassword() == null || !user.getPassword().equals(password)) {
            return new LoginResult(false, "Sai tên đăng nhập hoặc mật khẩu", null);
        }
        if (!user.isActive()) {
            return new LoginResult(false, "Tài khoản chưa được kích hoạt (OTP)", null);
        }
        return new LoginResult(true, "Đăng nhập thành công", user);
    }
}
