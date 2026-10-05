<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>${video.title}</title>
</head>
<body>

<div class="row">
    <div class="col-md-7">
        <div class="poster-box mb-3" style="height:320px;">
            <c:choose>
                <c:when test="${not empty video.videoFile}">
                    <video controls style="width:100%; height:100%; background:#000;"
                           poster="${not empty video.poster ? pageContext.request.contextPath.concat('/assets/img/').concat(video.poster) : ''}">
                        <source src="${pageContext.request.contextPath}/assets/video/${video.videoFile}">
                        Trình duyệt của bạn không hỗ trợ thẻ video.
                    </video>
                </c:when>
                <c:when test="${not empty video.poster}">
                    <img src="${pageContext.request.contextPath}/assets/img/${video.poster}" alt="${video.title}">
                </c:when>
                <c:otherwise>Không có ảnh</c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="col-md-5">
        <h3>${video.title}</h3>
        <p class="text-muted">Thể loại: ${video.category.categoryName}</p>
        <div class="mb-2">
            <span class="price-tag fs-4"><fmt:formatNumber value="${video.price}" pattern="#,##0"/> đ</span>
        </div>
        <p class="mb-3">
            <c:choose>
                <c:when test="${!video.active}"><span class="badge bg-secondary">Ngừng bán</span></c:when>
                <c:when test="${video.stock > 0}"><span class="badge bg-success">Còn ${video.stock} sản phẩm</span></c:when>
                <c:otherwise><span class="badge bg-danger">Hết hàng</span></c:otherwise>
            </c:choose>
        </p>
        <p>${video.description}</p>
        <ul class="list-inline">
            <li class="list-inline-item">👁 ${video.views} lượt xem</li>
            <li class="list-inline-item">❤ ${likeCount} lượt thích</li>
            <li class="list-inline-item">↗ ${shareCount} lượt chia sẻ</li>
        </ul>

        <c:if test="${video.purchasable && (empty sessionScope.account || !sessionScope.account.admin)}">
            <form method="post" action="${pageContext.request.contextPath}/cart/add" class="mb-3">
                <input type="hidden" name="videoId" value="${video.videoId}">
                <label class="form-label">Số lượng (tối đa ${video.maxOrderQuantity})</label>
                <div class="input-group" style="max-width:320px;">
                    <input type="number" name="quantity" class="form-control" value="1" min="1"
                           max="${video.maxOrderQuantity}" required>
                    <button type="submit" class="btn btn-success">🛒 Thêm vào giỏ hàng</button>
                </div>
            </form>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/like" class="mb-3">
            <input type="hidden" name="videoId" value="${video.videoId}">
            <button type="submit" class="btn btn-outline-danger">❤ Thích video này</button>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/share">
            <input type="hidden" name="videoId" value="${video.videoId}">
            <label class="form-label">Chia sẻ video tới email</label>
            <div class="input-group">
                <input type="email" name="email" class="form-control" placeholder="ban@gmail.com" required>
                <button type="submit" class="btn btn-primary">Chia sẻ</button>
            </div>
        </form>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/home">&larr; Quay lại trang chủ</a>
        </div>
    </div>
</div>

</body>
</html>
