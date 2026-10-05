<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đơn hàng của tôi</title>
</head>
<body>

<h3 class="mb-4">Đơn hàng của tôi</h3>

<c:choose>
    <c:when test="${empty orders}">
        <div class="text-center py-5 bg-white border rounded">
            <p class="text-muted mb-3">Bạn chưa có đơn hàng nào.</p>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Mua sắm ngay</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table table-bordered bg-white align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày đặt</th>
                        <th>Người nhận</th>
                        <th class="text-end">Tổng tiền</th>
                        <th>Thanh toán</th>
                        <th>Trạng thái</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td>#${o.orderId}</td>
                            <td><fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td><c:out value="${o.receiverName}"/></td>
                            <td class="text-end text-nowrap"><fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/> đ</td>
                            <td>
                                COD
                                <c:choose>
                                    <c:when test="${o.paid}"><span class="badge bg-success">Đã thanh toán</span></c:when>
                                    <c:otherwise><span class="badge bg-light text-dark border">Chưa thanh toán</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td><span class="badge bg-${o.status.badge}">${o.status.label}</span></td>
                            <td>
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/orders/detail?id=${o.orderId}">Chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
