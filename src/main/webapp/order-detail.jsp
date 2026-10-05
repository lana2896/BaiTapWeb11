<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đơn hàng #${order.orderId}</title>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
    <h3 class="mb-0">Đơn hàng #${order.orderId}
        <span class="badge bg-${order.status.badge} fs-6 align-middle">${order.status.label}</span>
    </h3>
    <a href="${pageContext.request.contextPath}/orders">&larr; Danh sách đơn hàng</a>
</div>

<div class="row g-4">
    <div class="col-lg-8">
        <div class="table-responsive">
            <table class="table table-bordered bg-white align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Sản phẩm</th>
                        <th class="text-end">Đơn giá</th>
                        <th class="text-center">SL</th>
                        <th class="text-end">Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${order.details}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty d.video}">
                                        <a href="${pageContext.request.contextPath}/video-detail?id=${d.video.videoId}"
                                           class="text-decoration-none"><c:out value="${d.videoTitle}"/></a>
                                    </c:when>
                                    <c:otherwise><c:out value="${d.videoTitle}"/></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end text-nowrap"><fmt:formatNumber value="${d.price}" pattern="#,##0"/> đ</td>
                            <td class="text-center">${d.quantity}</td>
                            <td class="text-end text-nowrap"><fmt:formatNumber value="${d.subtotal}" pattern="#,##0"/> đ</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-end">Tổng thanh toán</th>
                        <th class="text-end text-nowrap price-tag fs-5"><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/> đ</th>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="card shadow-sm mb-3">
            <div class="card-header bg-white fw-semibold">Thông tin giao hàng</div>
            <div class="card-body small">
                <div><strong>Người nhận:</strong> <c:out value="${order.receiverName}"/></div>
                <div><strong>Điện thoại:</strong> <c:out value="${order.phone}"/></div>
                <div><strong>Địa chỉ:</strong> <c:out value="${order.address}"/></div>
                <c:if test="${not empty order.note}">
                    <div><strong>Ghi chú:</strong> <c:out value="${order.note}"/></div>
                </c:if>
                <div class="mt-2"><strong>Ngày đặt:</strong> <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></div>
            </div>
        </div>
        <div class="card shadow-sm mb-3">
            <div class="card-header bg-white fw-semibold">Thanh toán</div>
            <div class="card-body small">
                <div>${order.paymentMethodLabel}</div>
                <div class="mt-1">
                    <c:choose>
                        <c:when test="${order.paid}">
                            <span class="badge bg-success">Đã thanh toán</span>
                            <fmt:formatDate value="${order.paidDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </c:when>
                        <c:when test="${order.status.name == 'CANCELLED'}">
                            <span class="badge bg-secondary">Không thu tiền (đơn đã hủy)</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-warning text-dark">Thanh toán
                                <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/> đ khi nhận hàng</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <c:if test="${order.cancellableByUser}">
            <form method="post" action="${pageContext.request.contextPath}/orders/cancel"
                  onsubmit="return confirm('Bạn chắc chắn muốn hủy đơn hàng này?');">
                <input type="hidden" name="id" value="${order.orderId}">
                <button type="submit" class="btn btn-outline-danger w-100">Hủy đơn hàng</button>
            </form>
        </c:if>
    </div>
</div>

</body>
</html>
