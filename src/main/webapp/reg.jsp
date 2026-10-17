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
            <form class="form-signin" action="${ctx}/RegServlet" method="post">
                <input class="form-control mb-2" name="username" required="" type="text" placeholder="用户名">
                <input class="form-control mb-2" name="email" required="" type="email" placeholder="邮箱">
                <input class="form-control mb-2" id="pwd" name="pwd" required="" type="password" placeholder="密码">
                <input class="form-control mb-2" id="pwd2" name="pwd2" required="" type="password" placeholder="确认密码">
                <input class="form-control mb-2" name="realname" required="" type="text" placeholder="真实姓名">
                <input class="form-control mb-2" name="phone" required="" type="text" placeholder="手机号码">
                <input class="form-control mb-2" name="address" required="" type="text" placeholder="联系地址">
                <input class="btn btn-lg btn-warning btn-block mb-2" type="submit" value="注册">
                <div class="text-center">
                    <span id="msg" class="text-danger"></span>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>

<script type="text/javascript">
    $(document).ready(function () {
        $(".form-signin").submit(function () {
            $("#msg").text("");
            let pwd1 = $("#pwd").val();
            let pwd2 = $("#pwd2").val();
            if (pwd1 != pwd2){
                $("#msg").text("两次密码不一致，请修改。");
                //返回false，表单不提交
                return false;
            }
        });
    });
</script>