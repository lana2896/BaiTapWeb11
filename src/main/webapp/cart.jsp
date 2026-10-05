<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
</head>
<body>

<h3 class="mb-4">🛒 Giỏ hàng của bạn</h3>

<c:choose>
    <c:when test="${empty items}">
        <div class="text-center py-5 bg-white border rounded">
            <p class="text-muted mb-3">Giỏ hàng đang trống.</p>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Tiếp tục mua sắm</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table table-bordered bg-white align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Sản phẩm</th>
                        <th class="text-end">Đơn giá</th>
                        <th style="width:260px;">Số lượng</th>
                        <th class="text-end">Thành tiền</th>
                        <th style="width:80px;"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <c:set var="video" value="${item.video}" />
                        <tr class="${item.available ? '' : 'table-warning'}">
                            <td>
                                <div class="d-flex align-items-center gap-2">
                                    <div class="cart-thumb flex-shrink-0">
                                        <c:choose>
                                            <c:when test="${not empty video.poster}">
                                                <img src="${pageContext.request.contextPath}/assets/img/${video.poster}" alt=""
                                                     style="width:56px; height:36px; object-fit:cover; display:block;">
                                            </c:when>
                                            <c:otherwise><small style="font-size:10px;">Không có</small></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/video-detail?id=${video.videoId}"
                                           class="fw-semibold text-decoration-none"><c:out value="${video.title}"/></a>
                                        <div class="small text-muted">${video.category.categoryName}</div>
                                        <c:if test="${not empty item.problem}">
                                            <div class="small text-danger">⚠ ${item.problem}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                            <td class="text-end text-nowrap"><fmt:formatNumber value="${video.price}" pattern="#,##0"/> đ</td>
                            <td>
                                <c:choose>
                                    <c:when test="${video.purchasable}">
                                        <div class="d-flex align-items-center gap-1">
                                            <form method="post" action="${pageContext.request.contextPath}/cart/update">
                                                <input type="hidden" name="videoId" value="${video.videoId}">
                                                <input type="hidden" name="quantity" value="${item.quantity - 1}">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary"
                                                        ${item.quantity <= 1 ? 'disabled' : ''}>−</button>
                                            </form>
                                            <form method="post" action="${pageContext.request.contextPath}/cart/update"
                                                  class="d-flex gap-1">
                                                <input type="hidden" name="videoId" value="${video.videoId}">
                                                <input type="number" name="quantity" class="form-control form-control-sm cart-qty"
                                                       value="${item.quantity}" min="1" max="${item.maxQuantity}" required>
                                                <button type="submit" class="btn btn-sm btn-outline-primary">Lưu</button>
                                            </form>
                                            <form method="post" action="${pageContext.request.contextPath}/cart/update">
                                                <input type="hidden" name="videoId" value="${video.videoId}">
                                                <input type="hidden" name="quantity" value="${item.quantity + 1}">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary"
                                                        ${item.quantity >= item.maxQuantity ? 'disabled' : ''}>+</button>
                                            </form>
                                        </div>
                                        <div class="small text-muted mt-1">Tối đa ${item.maxQuantity} (kho còn ${video.stock})</div>
                                    </c:when>
                                    <c:otherwise>
                                        <span>${item.quantity}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end text-nowrap fw-semibold"><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/> đ</td>
                            <td class="text-center">
                                <form method="post" action="${pageContext.request.contextPath}/cart/remove"
                                      onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?');">
                                    <input type="hidden" name="videoId" value="${video.videoId}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-end">Tổng cộng</th>
                        <th class="text-end text-nowrap price-tag fs-5"><fmt:formatNumber value="${total}" pattern="#,##0"/> đ</th>
                        <th></th>
                    </tr>
                </tfoot>
            </table>
        </div>

        <c:if test="${hasProblem}">
            <div class="alert alert-warning">
                Có sản phẩm không còn đủ điều kiện mua (đã ngừng bán, hết hàng hoặc vượt tồn kho).
                Vui lòng điều chỉnh số lượng hoặc xóa sản phẩm đó để giỏ hàng hợp lệ.
            </div>
        </c:if>

        <div class="d-flex justify-content-between flex-wrap gap-2">
            <div class="d-flex gap-2">
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/home">&larr; Tiếp tục mua sắm</a>
                <form method="post" action="${pageContext.request.contextPath}/cart/clear"
                      onsubmit="return confirm('Xóa toàn bộ giỏ hàng?');">
                    <button type="submit" class="btn btn-outline-danger">Xóa toàn bộ</button>
                </form>
            </div>

        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
