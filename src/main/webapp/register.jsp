<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký - ShopVideo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-card card shadow-sm">
    <div class="card-body p-4">
        <h4 class="mb-3 text-center">Đăng ký tài khoản</h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger py-2">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" value="${username}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Họ tên</label>
                <input type="text" name="fullname" class="form-control" value="${fullname}">
            </div>
            <div class="mb-3">
                <label class="form-label">Email (để nhận mã OTP)</label>
                <input type="email" name="email" class="form-control" value="${email}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Số điện thoại</label>
                <input type="text" name="phone" class="form-control" value="${phone}">
            </div>
            <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
        </form>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
        </div>
    </div>
</div>
</body>
</html>
