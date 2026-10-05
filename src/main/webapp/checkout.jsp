<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán</title>
</head>
<body>

<h3 class="mb-4">Thanh toán đơn hàng</h3>

<c:if test="${not empty errors}">
    <div class="alert alert-danger">
        <ul class="mb-0">
            <c:forEach var="e" items="${errors}">
                <li>${e}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/checkout">
    <div class="row g-4">
        <div class="col-lg-7">
            <div class="card shadow-sm mb-3">
                <div class="card-header bg-white fw-semibold">Thông tin nhận hàng</div>
                <div class="card-body">
                    <div class="mb-3">
                        <label class="form-label">Họ tên người nhận <span class="text-danger">*</span></label>
                        <input type="text" name="receiverName" class="form-control" maxlength="100"
                               value="<c:out value='${receiverName}'/>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                        <input type="tel" name="phone" class="form-control" pattern="0[0-9]{9,10}" maxlength="11"
                               value="<c:out value='${phone}'/>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Địa chỉ giao hàng <span class="text-danger">*</span></label>
                        <textarea name="address" class="form-control" rows="2" maxlength="255" required><c:out value="${address}"/></textarea>
                    </div>
                    <div class="mb-0">
                        <label class="form-label">Ghi chú</label>
                        <textarea name="note" class="form-control" rows="2" maxlength="500"
                                  placeholder="Ví dụ: giao giờ hành chính"><c:out value="${note}"/></textarea>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header bg-white fw-semibold">Phương thức thanh toán</div>
                <div class="card-body">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod" id="pmCod" value="COD" checked>
                        <label class="form-check-label" for="pmCod">
                            <strong>Thanh toán khi nhận hàng (COD)</strong>
                            <div class="small text-muted">Bạn thanh toán bằng tiền mặt cho nhân viên giao hàng khi nhận sản phẩm.</div>
                        </label>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-5">
            <div class="card shadow-sm">
                <div class="card-header bg-white fw-semibold">Đơn hàng (${items.size()} sản phẩm)</div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="item" items="${items}">
                        <li class="list-group-item d-flex justify-content-between">
                            <div>
                                <div><c:out value="${item.video.title}"/></div>
                                <small class="text-muted">
                                    <fmt:formatNumber value="${item.video.price}" pattern="#,##0"/> đ x ${item.quantity}
                                </small>
                            </div>
                            <div class="text-nowrap"><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/> đ</div>
                        </li>
                    </c:forEach>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Phí vận chuyển</span><span>Miễn phí</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between fw-bold">
                        <span>Tổng thanh toán</span>
                        <span class="price-tag fs-5"><fmt:formatNumber value="${total}" pattern="#,##0"/> đ</span>
                    </li>
                </ul>
                <div class="card-body">
                    <button type="submit" class="btn btn-success w-100">Đặt hàng (COD)</button>
                    <a class="btn btn-link w-100 mt-1" href="${pageContext.request.contextPath}/cart">&larr; Quay lại giỏ hàng</a>
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>
