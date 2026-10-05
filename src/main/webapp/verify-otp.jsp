<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kích hoạt OTP - ShopVideo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-card card shadow-sm">
    <div class="card-body p-4">
        <h4 class="mb-3 text-center">Nhập mã OTP</h4>
        <p class="text-muted text-center small">
            Mã kích hoạt đã được gửi tới email của tài khoản <strong>${username}</strong>.
        </p>

        <c:if test="${not empty info}">
            <div class="alert alert-success py-2">${info}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger py-2">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/verify-otp">
            <input type="hidden" name="username" value="${username}">
            <div class="mb-3">
                <label class="form-label">Mã OTP</label>
                <input type="text" name="otp" class="form-control" maxlength="10" required autofocus>
            </div>
            <button type="submit" name="action" value="verify" class="btn btn-primary w-100">Xác thực</button>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/verify-otp" class="mt-2">
            <input type="hidden" name="username" value="${username}">
            <button type="submit" name="action" value="resend" class="btn btn-outline-secondary w-100">Gửi lại mã OTP</button>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
        </div>
    </div>
</div>
</body>
</html>
