<%@ page contentType="text/html; charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">🎬 ShopVideo</a>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang Chủ</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Sản phẩm</a></li>
            <c:if test="${sessionScope.account != null && sessionScope.account.admin}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/home">Trang quản trị</a></li>
            </c:if>
        </ul>
        <ul class="navbar-nav">
            <c:choose>
                <c:when test="${sessionScope.account != null}">
                    <c:if test="${!sessionScope.account.admin}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng
                                <span class="badge rounded-pill bg-danger">${empty sessionScope.cartCount ? 0 : sessionScope.cartCount}</span>
                            </a>
                        </li>
                    </c:if>
                    <li class="nav-item"><span class="nav-link text-light">Xin chào, ${sessionScope.account.fullname}</span></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
