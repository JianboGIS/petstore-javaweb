<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2025/12/16
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>宠物商城</title>
    <link rel="shortcut icon" href="img/favicon.ico" />
    <link rel="stylesheet" href="css/bootstrap-4.6.1.min.css" />
    <link rel="stylesheet" href="css/font-awesome-3.2.1.min.css" />
    <link rel="stylesheet" href="css/site.css" />
    <script src="js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class ="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <img src ="img/logo.png" width = "64" height = "64" class="mb-2" />
    <h5 class="my-0 mr-md-auto font-weight-normal">宠物商城</h5>
<nav class="my-2 my-md-0 mr-md-3">
    <a class="p-2 text-dark" href="${ctx}/IndexServlet">首页</a>
    <c:if test="${empty user}">
        <a class="p-2 text-dark" href="${ctx}/login.jsp">请登录</a>
    </c:if>
    <c:if test="${!empty user}">
        <a class="p-2 text-dark" href="${ctx}/UserServlet">欢迎：<b>${user.username}</b></a>
    </c:if>
        <a class="p-2 text-dark" href="${ctx}/cart.jsp">购物车</a>
    <a class="p-2 text-dark" href="#">联系客服</a>
</nav>
</div>