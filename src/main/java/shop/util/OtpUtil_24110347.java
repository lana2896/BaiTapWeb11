package shop.util;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

public class OtpUtil_24110347 {
    private static final SecureRandom RANDOM = new SecureRandom();
    public static final int OTP_EXPIRE_MINUTES = 5;

    private OtpUtil_24110347() {
    }

    public static String generateOtp() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }

    public static Date expiryFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, OTP_EXPIRE_MINUTES);
        return cal.getTime();
    }

    public static boolean isExpired(Date expiry) {
        return expiry == null || expiry.before(new Date());
    }
}
