<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý Đơn hàng</title>
</head>
<body>

<h3 class="mb-3">Quản lý Đơn hàng (${pageResult.totalItems} đơn)</h3>

<ul class="nav nav-pills mb-3 flex-wrap">
    <li class="nav-item">
        <a class="nav-link ${empty currentStatus ? 'active' : ''}"
           href="${pageContext.request.contextPath}/admin/orders">Tất cả (${totalOrders})</a>
    </li>
    <c:forEach var="entry" items="${statusCounts}">
        <li class="nav-item">
            <a class="nav-link ${currentStatus == entry.key ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/orders?status=${entry.key.name}">${entry.key.label} (${entry.value})</a>
        </li>
    </c:forEach>
</ul>

<table class="table table-bordered bg-white align-middle">
    <thead class="table-light">
        <tr>
            <th>Mã đơn</th>
            <th>Ngày đặt</th>
            <th>Tài khoản</th>
            <th>Người nhận / SĐT</th>
            <th class="text-end">Tổng tiền</th>
            <th>Thanh toán</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty pageResult.items}">
                <tr><td colspan="8" class="text-center text-muted">Chưa có đơn hàng nào.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="o" items="${pageResult.items}">
                    <tr>
                        <td>#${o.orderId}</td>
                        <td><fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td>${o.user.username}</td>
                        <td><c:out value="${o.receiverName}"/><div class="small text-muted"><c:out value="${o.phone}"/></div></td>
                        <td class="text-end text-nowrap"><fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/> đ</td>
                        <td>
                            COD
                            <c:choose>
                                <c:when test="${o.paid}"><span class="badge bg-success">Đã thu</span></c:when>
                                <c:otherwise><span class="badge bg-light text-dark border">Chưa thu</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td><span class="badge bg-${o.status.badge}">${o.status.label}</span></td>
                        <td>
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/orders/detail?id=${o.orderId}">Xử lý</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<c:if test="${pageResult.totalPages > 1}">
    <c:set var="statusQuery" value="${empty currentStatus ? '' : '&status='.concat(currentStatus.name)}" />
    <div class="pager">
        <c:if test="${pageResult.hasPrev}">
            <a href="${pageContext.request.contextPath}/admin/orders?page=${pageResult.currentPage - 1}${statusQuery}">&laquo; Trước</a>
        </c:if>
        <c:forEach begin="1" end="${pageResult.totalPages}" var="p">
            <c:choose>
                <c:when test="${p == pageResult.currentPage}">
                    <span class="active">${p}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/orders?page=${p}${statusQuery}">${p}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${pageResult.hasNext}">
            <a href="${pageContext.request.contextPath}/admin/orders?page=${pageResult.currentPage + 1}${statusQuery}">Sau &raquo;</a>
        </c:if>
    </div>
</c:if>

</body>
</html>
