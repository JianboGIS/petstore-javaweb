<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="row container">
    <%@ include file="sidebar.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>密码修改</h6>
            </div>
            <div class="card-body">
                <form action="${ctx}/SetPwdServlet" method="post">
                    <div class="form-group col-6">
                        <label class="form-control-label">旧密码</label>
                        <input type="password" class="form-control" name="oldPwd" >
                    </div>
                    <div class="form-group col-6">
                        <label class="form-control-label">新密码</label>
                        <input type="password" class="form-control" name="newPwd" >
                    </div>
                    <div class="form-group col-6">
                        <label class="form-control-label">确认新密码</label>
                        <input type="password" class="form-control" name="newPwd2"  >
                    </div>
                    <div class="form-group col-6">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" class="btn btn-primary form-group col-12" value="保存">
                    </div>
                </form>
                <div class="text-center">
                    <span class="text-danger" id="msg">${msg}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        $(".form-signin").submit(function () {
            $("#msg").text("");
            let pwd1 = $("#newPwd").val();
            let pwd2 = $("#newPwd2").val();
            if (pwd1 != pwd2){
                $("#msg").text("两次密码不一致，请修改。");
                //返回false，表单不提交
                return false;
            }
        });
    });
</script>