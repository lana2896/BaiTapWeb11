<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>${empty video ? 'Thêm Video' : 'Cập nhật Video'}</title>
</head>
<body>

<h3 class="mb-4">${empty video ? 'Thêm Video mới' : 'Cập nhật Video'}</h3>

<div class="card shadow-sm" style="max-width:600px;">
    <div class="card-body">
        <form method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}${empty video ? '/admin/videos/add' : '/admin/videos/edit'}">
            <c:if test="${not empty video}">
                <input type="hidden" name="id" value="${video.videoId}">
            </c:if>

            <div class="mb-3">
                <label class="form-label">Tiêu đề</label>
                <input type="text" name="title" class="form-control" value="${video.title}" required>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Giá bán (VNĐ)</label>
                    <input type="number" name="price" class="form-control" min="0" step="1"
                           value="${empty video ? 0 : video.price}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Số lượng tồn kho</label>
                    <input type="number" name="stock" class="form-control" min="0" step="1"
                           value="${empty video ? 0 : video.stock}" required>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">Ảnh Poster</label>
                <c:if test="${not empty video.poster}">
                    <div class="mb-2">
                        <img src="${pageContext.request.contextPath}/assets/img/${video.poster}"
                             alt="poster hiện tại" style="max-height:120px; display:block;">
                    </div>
                </c:if>
                <input type="file" name="posterFile" class="form-control" accept="image/*">
                <c:if test="${not empty video.poster}">
                    <div class="form-text">
                        Đang có poster: ${video.poster}. Chọn ảnh mới để thay thế, để trống nếu muốn giữ nguyên.
                    </div>
                    <div class="form-check mt-2">
                        <input type="checkbox" class="form-check-input" id="removePoster" name="removePoster">
                        <label class="form-check-label" for="removePoster">Xóa poster hiện tại (không thay ảnh mới)</label>
                    </div>
                </c:if>
            </div>
            <div class="mb-3">
                <label class="form-label">File video</label>
                <input type="file" name="videoFile" class="form-control" accept="video/*">
                <c:if test="${not empty video.videoFile}">
                    <div class="form-text">
                        Đang có video: ${video.videoFile}. Chọn file mới để thay thế, để trống nếu muốn giữ nguyên.
                    </div>
                </c:if>
            </div>
            <div class="mb-3">
                <label class="form-label">Mô tả</label>
                <textarea name="description" class="form-control" rows="3">${video.description}</textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Thể loại</label>
                <select name="categoryId" class="form-select" required>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.categoryId}"
                            ${not empty video && video.category.categoryId == c.categoryId ? 'selected' : ''}>
                            ${c.categoryName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="active" name="active"
                       ${empty video || video.active ? 'checked' : ''}>
                <label class="form-check-label" for="active">Hiển thị video này</label>
            </div>

            <button type="submit" class="btn btn-primary">Lưu</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/videos">Hủy</a>
        </form>
    </div>
</div>

</body>
</html>
