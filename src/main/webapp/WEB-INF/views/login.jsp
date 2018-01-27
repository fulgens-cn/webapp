<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>用户登录</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <form class="col-sm-4 col-sm-offset-4" role="form" action="/login" method="post">
            <%-- spring security默认拦截csrf跨站请求伪造攻击，需要在jsp页面添加如下隐藏域 --%>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <%-- spring security登录认证失败提示信息 --%>
            ${SPRING_SECURITY_LAST_EXCEPTION.message}
            <fieldset>
                <legend>欢迎登录</legend>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-user"></span>
                    <input type="text" class="form-control" id="username" name="username"
                           placeholder="请输入用户名">
                </div>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="请输入密码">
                </div>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-check"></span>
                    <input type="text" class="form-control" id="checkcode" name="checkcode"
                           placeholder="请输入验证码">
                    <span class="input-group-btn">
                        <img src="/getVerifyCode" id="verifyCodeImg" />
                    </span>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="remember-me">自动登录
                    </label>
                </div>
                <div class="form-actions">
                    <div class="col-sm-4 col-sm-offset-4">
                        <button type="submit" class="btn btn-default btn-primary">登录</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        // 验证码图片点击切换
        $("#verifyCodeImg").click(function() {
            $(this).attr("src", "${pageContext.request.contextPath}/getVerifyCode?random=" + Math.random());
        });
        // 鼠标移到验证码图片上显示小手
        $("#verifyCodeImg").mouseover(function() {
            $(this).css("cursor", "pointer");
        });
    });
</script>
</body>
</html>
