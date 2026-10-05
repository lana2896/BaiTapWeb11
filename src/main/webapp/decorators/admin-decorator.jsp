<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ShopVideo Admin - <sitemesh:write property='title'/></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<sitemesh:write property="head"/>
</head>
<body>
    <nav class="navbar navbar-dark bg-dark px-3">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/home">🛠 ShopVideo - Quản trị</a>
        <span class="navbar-text text-light">
            Xin chào, ${sessionScope.account.fullname}
            &nbsp;|&nbsp;
            <a class="text-light" href="${pageContext.request.contextPath}/home">Về trang User</a>
            &nbsp;|&nbsp;
            <a class="text-light" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </span>
    </nav>
    <div class="d-flex">
        <div class="bg-secondary bg-opacity-10 border-end p-3" style="width:220px; min-height:calc(100vh - 56px);">
            <a class="d-block py-2 text-decoration-none" href="${pageContext.request.contextPath}/admin/home">📊 Tổng quan</a>
            <a class="d-block py-2 text-decoration-none" href="${pageContext.request.contextPath}/admin/videos">🎬 Quản lý Video</a>
        </div>
        <main class="container-fluid p-4">
            <c:if test="${not empty sessionScope.flashMessage}">
                <div class="alert alert-${empty sessionScope.flashType ? 'info' : sessionScope.flashType} alert-dismissible fade show" role="alert">
                    <c:out value="${sessionScope.flashMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Đóng"></button>
                </div>
                <c:remove var="flashMessage" scope="session"/>
                <c:remove var="flashType" scope="session"/>
            </c:if>
            <sitemesh:write property="body"/>
        </main>
    </div>
    <%@ include file="/common/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
