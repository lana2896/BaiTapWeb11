<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Tổng quan</title>
</head>
<body>

<h3 class="mb-4">Tổng quan hệ thống</h3>

<div class="row g-3">
    <div class="col-md-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h6 class="text-muted">Tổng số Video</h6>
                <h2>${totalVideos}</h2>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h6 class="text-muted">Tổng số Thể loại</h6>
                <h2>${totalCategories}</h2>
            </div>
        </div>
    </div>
</div>

<div class="mt-4">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/videos">Quản lý Video</a>
</div>

</body>
</html>
