<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
</head>
<body>

<h3 class="mb-4">Danh sách Video theo Thể loại</h3>

<c:forEach var="entry" items="${categoryData}">
    <c:set var="category" value="${entry.key}" />
    <c:set var="pageResult" value="${entry.value}" />
    <div class="category-section">
        <h5 class="mb-3">
            ${category.categoryName}
            <span class="badge bg-secondary">${pageResult.totalItems} video</span>
        </h5>

        <c:choose>
            <c:when test="${empty pageResult.items}">
                <p class="text-muted">Chưa có video nào trong thể loại này.</p>
            </c:when>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-3 g-3">
                    <c:forEach var="video" items="${pageResult.items}">
                        <div class="col">
                            <div class="video-card">
                                <div class="poster-box">
                                    <c:choose>
                                        <c:when test="${not empty video.poster}">
                                            <img src="${pageContext.request.contextPath}/assets/img/${video.poster}" alt="${video.title}">
                                        </c:when>
                                        <c:otherwise>Không có ảnh</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="body">
                                    <h6 class="mb-1">${video.title}</h6>
                                    <div class="small text-muted mb-1">👁 ${video.views} lượt xem</div>
                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                        <span class="price-tag"><fmt:formatNumber value="${video.price}" pattern="#,##0"/> đ</span>
                                        <c:choose>
                                            <c:when test="${video.stock > 0}"><small class="text-success">Còn ${video.stock}</small></c:when>
                                            <c:otherwise><small class="text-danger">Hết hàng</small></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="d-flex gap-2">
                                        <a class="btn btn-sm btn-outline-primary flex-fill"
                                           href="${pageContext.request.contextPath}/video-detail?id=${video.videoId}">Xem chi tiết</a>
                                        <c:if test="${empty sessionScope.account || !sessionScope.account.admin}">
                                            <form method="post" action="${pageContext.request.contextPath}/cart/add" class="flex-fill">
                                                <input type="hidden" name="videoId" value="${video.videoId}">
                                                <input type="hidden" name="quantity" value="1">
                                                <input type="hidden" name="back" value="home">
                                                <button type="submit" class="btn btn-sm btn-success w-100" ${video.purchasable ? '' : 'disabled'}>🛒 Thêm vào giỏ</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${pageResult.totalPages > 1}">
                    <div class="pager mt-3">
                        <c:forEach begin="1" end="${pageResult.totalPages}" var="p">
                            <c:choose>
                                <c:when test="${p == pageResult.currentPage}">
                                    <span class="active">${p}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/home?p${category.categoryId}=${p}">${p}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</c:forEach>

</body>
</html>
