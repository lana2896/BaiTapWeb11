<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý Video</title>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Quản lý Video (${pageResult.totalItems} video)</h3>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/admin/videos/add">+ Thêm Video</a>
</div>

<table class="table table-bordered bg-white align-middle">
    <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Poster</th>
            <th>Tiêu đề</th>
            <th>Thể loại</th>
            <th>Giá</th>
            <th>Tồn kho</th>
            <th>Lượt xem</th>
            <th>Trạng thái</th>
            <th style="width:160px;">Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty pageResult.items}">
                <tr><td colspan="9" class="text-center text-muted">Chưa có video nào.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="video" items="${pageResult.items}">
                    <tr>
                        <td>${video.videoId}</td>
                        <td style="width:90px;">
                            <div class="poster-box" style="height:60px;">
                                <c:choose>
                                    <c:when test="${not empty video.poster}">
                                        <img src="${pageContext.request.contextPath}/assets/img/${video.poster}" alt="">
                                    </c:when>
                                    <c:otherwise><small>Không có</small></c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty video.poster}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/videos/poster/delete"
                                      class="d-inline" onsubmit="return confirm('Xóa poster của video này?');">
                                    <input type="hidden" name="id" value="${video.videoId}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger mt-1" style="font-size:11px;">Xóa poster</button>
                                </form>
                            </c:if>
                        </td>
                        <td>${video.title}</td>
                        <td>${video.category.categoryName}</td>
                        <td class="text-nowrap"><fmt:formatNumber value="${video.price}" pattern="#,##0"/> đ</td>
                        <td>
                            <c:choose>
                                <c:when test="${video.stock > 0}">${video.stock}</c:when>
                                <c:otherwise><span class="text-danger">0</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${video.views}</td>
                        <td>
                            <c:choose>
                                <c:when test="${video.active}"><span class="badge bg-success">Hiển thị</span></c:when>
                                <c:otherwise><span class="badge bg-secondary">Ẩn</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/videos/edit?id=${video.videoId}">Sửa</a>
                            <form method="post" action="${pageContext.request.contextPath}/admin/videos/delete"
                                  class="d-inline" onsubmit="return confirm('Xóa video này?');">
                                <input type="hidden" name="id" value="${video.videoId}">
                                <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<c:if test="${pageResult.totalPages > 1}">
    <div class="pager">
        <c:if test="${pageResult.hasPrev}">
            <a href="${pageContext.request.contextPath}/admin/videos?page=${pageResult.currentPage - 1}">&laquo; Trước</a>
        </c:if>
        <c:forEach begin="1" end="${pageResult.totalPages}" var="p">
            <c:choose>
                <c:when test="${p == pageResult.currentPage}">
                    <span class="active">${p}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/videos?page=${p}">${p}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${pageResult.hasNext}">
            <a href="${pageContext.request.contextPath}/admin/videos?page=${pageResult.currentPage + 1}">Sau &raquo;</a>
        </c:if>
    </div>
</c:if>

</body>
</html>
