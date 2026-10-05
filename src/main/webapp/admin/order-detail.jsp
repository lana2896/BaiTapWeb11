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
    <a href="${pageContext.request.contextPath}/admin/orders">&larr; Danh sách đơn hàng</a>
</div>

<div class="row g-4">
    <div class="col-xl-8">
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
                            <c:out value="${d.videoTitle}"/>
                            <c:if test="${empty d.video}"><span class="badge bg-secondary">Đã xóa khỏi hệ thống</span></c:if>
                        </td>
                        <td class="text-end text-nowrap"><fmt:formatNumber value="${d.price}" pattern="#,##0"/> đ</td>
                        <td class="text-center">${d.quantity}</td>
                        <td class="text-end text-nowrap"><fmt:formatNumber value="${d.subtotal}" pattern="#,##0"/> đ</td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th colspan="3" class="text-end">Tổng tiền cần thu (COD)</th>
                    <th class="text-end text-nowrap price-tag fs-5"><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/> đ</th>
                </tr>
            </tfoot>
        </table>
    </div>
    <div class="col-xl-4">
        <div class="card shadow-sm mb-3">
            <div class="card-header bg-white fw-semibold">Khách hàng</div>
            <div class="card-body small">
                <div><strong>Tài khoản:</strong> ${order.user.username} (<c:out value="${order.user.fullname}"/>)</div>
                <div><strong>Người nhận:</strong> <c:out value="${order.receiverName}"/></div>
                <div><strong>Điện thoại:</strong> <c:out value="${order.phone}"/></div>
                <div><strong>Địa chỉ:</strong> <c:out value="${order.address}"/></div>
                <c:if test="${not empty order.note}">
                    <div><strong>Ghi chú:</strong> <c:out value="${order.note}"/></div>
                </c:if>
                <div class="mt-2"><strong>Ngày đặt:</strong> <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></div>
                <c:if test="${not empty order.updatedDate}">
                    <div><strong>Cập nhật:</strong> <fmt:formatDate value="${order.updatedDate}" pattern="dd/MM/yyyy HH:mm"/></div>
                </c:if>
            </div>
        </div>
        <div class="card shadow-sm mb-3">
            <div class="card-header bg-white fw-semibold">Thanh toán</div>
            <div class="card-body small">
                <div>${order.paymentMethodLabel}</div>
                <div class="mt-1">
                    <c:choose>
                        <c:when test="${order.paid}">
                            <span class="badge bg-success">Đã thu tiền</span>
                            <fmt:formatDate value="${order.paidDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </c:when>
                        <c:otherwise><span class="badge bg-light text-dark border">Chưa thu tiền</span></c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="card shadow-sm">
            <div class="card-header bg-white fw-semibold">Cập nhật trạng thái</div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty order.status.nextStatusList}">
                        <p class="text-muted small mb-0">Đơn hàng đã kết thúc, không thể chuyển trạng thái.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="d-grid gap-2">
                            <c:forEach var="s" items="${order.status.nextStatusList}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/orders/status"
                                      onsubmit="return confirm('Chuyển đơn hàng sang trạng thái: ${s.label}?');">
                                    <input type="hidden" name="id" value="${order.orderId}">
                                    <input type="hidden" name="status" value="${s.name}">
                                    <button type="submit"
                                            class="btn w-100 ${s.name == 'CANCELLED' ? 'btn-outline-danger' : 'btn-'.concat(s.badge)}">
                                        <c:choose>
                                            <c:when test="${s.name == 'COMPLETED'}">Đã giao &amp; đã thu tiền COD</c:when>
                                            <c:when test="${s.name == 'CANCELLED'}">Hủy đơn (hoàn kho)</c:when>
                                            <c:otherwise>${s.label}</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

</body>
</html>
