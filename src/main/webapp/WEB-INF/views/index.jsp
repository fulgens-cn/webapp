<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>webapp</title>
</head>
<body>
    <%-- 展现认证用户信息（参见spring实战p279） --%>
    <%-- 注意：security:authentication标签必须为空，否则会报一下错误 --%>
    <%-- org.apache.jasper.JasperException: ... According to TLD, tag security:authentication must be empty, but is not --%>
    <security:authorize access="isAuthenticated()" >
        <security:authentication property="principal.username" var="loginUsername"/>
    </security:authorize>
    <%-- 使用${loginUsername}引用var="loginUsername"定义的已认证用户用户名 --%>
    <h1>Hi ${loginUsername}, Welcome to fulgens.cn!</h1>

    <%-- 根据权限条件性渲染内容（参见spring实战p280） --%>
    <security:authorize url="/upload" >
        <s:url value="/upload" var="uploadUrl" />
        <a href="${uploadUrl}" >上传文件</a>
    </security:authorize>
    <security:authorize access="isAuthenticated()" >
        <s:url value="/logout" var="logoutUrl" />
        <a href="${logoutUrl}" >退出登录</a>
    </security:authorize>
    <h1>${data}</h1>
</body>
</html>
