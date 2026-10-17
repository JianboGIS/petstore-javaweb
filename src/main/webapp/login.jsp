<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2025/12/20
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="container">
    <div class="row">
        <div class="offset-3 col-6">
            <form class="form-signin" action="${ctx}/LoginServlet" method="post">
                <input class="form-control mb-2" name="email" autofocus="" required="" type="email" placeholder="用户邮箱">
                <input class="form-control mb-2" name="pwd" required="" type="password" placeholder="用户密码">
                <input class="btn btn-lg btn-warning btn-block" type="submit" value="登录">
                <div class="text-center">
                    <span class="text-danger">${msg}</span>
                </div>
                <a class="btn btn-lg btn-muted btn-block mt-4" href="${ctx}/RegServlet">没有账号？点击注册</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer.jsp"%>