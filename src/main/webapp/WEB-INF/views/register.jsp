<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title><s:message code="registerView.title"/></title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
    .error {
        color: #FF0000;
    }
</style>
<body>
<div class="container">
    <div class="row">
        <%--<form method="post" action="/register">
            &lt;%&ndash;用户名：<input type="text" name="username">
            密码：<input type="password" name="password">
            邮箱：<input type="email" name="email">&ndash;%&gt;
            <input type="submit" value="提交注册">
            <input type="reset" value="重置">
        </form>--%>

        <%--注意表单form上添加了modelAttribute="user"或commandName="user"
            在展示注册页面时处理器方法需往模型中添加一个User对象
            否则会报 Neither BindingResult nor plain target object for bean name 'username' available as request attribute错误--%>
        <sf:form cssClass="col-md-4 col-md-offset-4" role="form" action="/register"
                 method="post" commandName="user">
            <fieldset>
                <legend><s:message code="registerView.welcome" /></legend>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-user"></span>
                    <sf:input path="username" cssClass="form-control" id="username"
                           placeholder="请输入用户名"/>
                    <div><sf:errors path="username" cssClass="error" /></div>
                </div>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <sf:password path="password" cssClass="form-control" id="password"
                              placeholder="请输入密码"/>
                    <div><sf:errors path="password" cssClass="error" /></div>
                </div>
                <div class="input-group form-group">
                    <span class="input-group-addon glyphicon glyphicon-envelope"></span>
                    <sf:input path="email" cssClass="form-control" id="email"
                              placeholder="请输入邮箱账号"/>
                    <div><sf:errors path="email" cssClass="error" /></div>
                </div>
                <div class="form-group">
                    <div class="col-md-6 col-md-offset-3">
                        <button type="reset" class="btn btn-default btn-primary">重置</button>
                        <button type="submit" class="btn btn-default btn-primary">注册</button>
                    </div>
                </div>
            </fieldset>
        </sf:form>
    </div>
</div>
</body>
</html>
