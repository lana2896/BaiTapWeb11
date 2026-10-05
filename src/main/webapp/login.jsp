<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - ShopVideo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="auth-card card shadow-sm">
    <div class="card-body p-4">
        <h4 class="mb-3 text-center">Đăng nhập</h4>

        <c:if test="${not empty sessionScope.flashMessage}">
            <div class="alert alert-${empty sessionScope.flashType ? 'info' : sessionScope.flashType} py-2"><c:out value="${sessionScope.flashMessage}"/></div>
            <c:remove var="flashMessage" scope="session"/>
            <c:remove var="flashType" scope="session"/>
        </c:if>
        <c:if test="${param.verified == '1'}">
            <div class="alert alert-success py-2">Kích hoạt tài khoản thành công, mời bạn đăng nhập.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger py-2">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" value="${username}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
        </form>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký</a>
        </div>
        <div class="text-center mt-2">
            <small class="text-muted"></small>
        </div>
    </div>
</div>
</body>
</html>
