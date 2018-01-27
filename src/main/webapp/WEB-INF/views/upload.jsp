<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>文件上传</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <%-- There are two options to using CSRF protection with multipart/form-data. --%>
    <%-- 1.Placing MultipartFilter before Spring Security需配置一个MultipartFilter --%>
    <%-- 2.Include CSRF token in action如action="/upload?${_csrf.parameterName}=${_csrf.token} --%>
    <form class="form-horizontal col-md-4" role="form"
          method="post" action="/upload"
          enctype="multipart/form-data">
        <input type="file" class="form-control" name="uploadFile"><br>
        <input type="submit" class="btn btn-default" value="点击上传">
    </form>
</body>
</html>
