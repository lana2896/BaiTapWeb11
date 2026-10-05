<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- SiteMesh 3: <sitemesh:write .../> khong phai custom taglib - SiteMesh la 1 Filter
     doc lai HTML da render cua trang goc va thay the cac the nay, nen khong can khai bao. --%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ShopVideo - <sitemesh:write property='title'/></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<sitemesh:write property="head"/>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    <main class="container my-4">
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
    <%@ include file="/common/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
