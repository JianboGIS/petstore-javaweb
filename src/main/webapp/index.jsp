<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宠物商城</title>
    <link rel="shortcut icon"  href="img/favicon.ico"/>
    <link rel="stylesheet" href="css/bootstrap-4.6.1.min.css">
    <link rel="stylesheet" href="css/font-awesome-3.2.1.min.css">
    <link rel="stylesheet" href="css/site.css">
    <script src="js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class = "d-flex flex-column flex-md-row align-items-center p-3 my-3
px-md-4 mb-3 bg-white border-bottom shadow-sm">
    <img src="img/logo.png" width="64px" height="64px" class = "mb-2">
    <h5 calss="my-0 mr-md-auto font-weight-normal">宠物商城</h5>
    <nav class="p-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="#">首页</a>
        <a class="p-2 text-dark" href="#">购物车</a>
        <a class="p-2 text-dark" href="#">联系客服</a>
    </nav>
</div>

<div class = "container">
<nav class="navbar navbar-expand-lg navbar-light bg-light mb-1">
    <a class="navbar-brand" href="${ctx}/IndexServlet?category_id=0">全部分类</a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:forEach items="${categoryList}" var="category">
                <c:if test="${category.id == current_category_id}">
                    <%--与参数 current_category_id 相等的分类名称，设置为选中状态,添加样式类名 active--%>
                    <li class="nav-item active">
                        <a class="nav-link bg-white" href="${ctx}/IndexServlet?category_id=${category.id}">
                           <b>${category.name}</b>
                        </a>
                    </li>
                </c:if>
                <c:if test="${category.id != current_category_id}">
                    <li class="nav-item">
                        <a class="btn btn-light" href="${ctx}/IndexServlet?category_id=${category.id}">${category.name}</a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="${ctx}/SearchServlet" method="post" >
            <input class="form-control mr-sm-2" type="text" name="key" placeholder="关键字">
            <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">查询</button>
        </form>
    </div>
</nav>
    <div class = "card-deck mb-3 text-center">
        <c:forEach items="${petList}" var="pet">
            <div class = "card mb-4 shadow-sm">
                <div class = "card-header">
                    <a href ="${ctx}/DetailServlet?id = ${pet.id}">
                        <img src = "${ctx}/peting/${pet.photo}" class = "pet-pic"/>
                    </a>
                </div>
                <div class = "card-body">
                    <h1 class = "card-title pricing-card-title">
                        <small class = "text-muted">${pet.title}</small>
                    </h1>
                    <p class="pet-desc">${pet.descs}</p>
                    <p><span class="pet-tag">${pet.tag}</span> </p>
                    <p class = "card-price">￥${pet.price}</p>
                    <a class ="btn btn-lg btn-block btn-outline-primary"
                       href="${ctx}/DetailServlet?id=${pet.id}">查看详情</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<footer class="footer mt-1 py-3">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md">
                <img src="img/logo.png" width="24px" height="24px" class = "mb-2">
                <small  class="d-block mb-3 text-muted">© 2023</small>
            </div>
            <div class="col-6 col-md">
                <h5>备案信息</h5>
                <ul class="list-unstyled text-small">
                    <li><a class="text-muted" href="#">备案号</a></li>
                </ul>
            </div>
            <div class ="list-unstyled text-small">
                <li><a class = "text-muted" href="#">宠物商城有限公司</a> </li>
            </div>
        </div>
    </div>
</footer>
</body>
</html>