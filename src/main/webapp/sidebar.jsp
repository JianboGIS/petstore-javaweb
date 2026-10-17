<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="col-3">
    <ul class="list-group">
       <%--使用了Font Awesome字体图标--%>
       <li class="list-group-item"><i class="icon-user mr-2"></i><a href="${ctx}/UserServlet?view=userinfo">个人信息</a></li>
       <li class="list-group-item"><i class="icon-list mr-2"></i><a href="${ctx}/UserServlet?view=userorder">我的订单</a></li>
       <li class="list-group-item"><i class="icon-key mr-2"></i><a href="${ctx}/UserServlet?view=userpwd">密码修改</a></li>
       <li class="list-group-item"><i class="icon-money mr-2"></i><a href="${ctx}/UserServlet?view=userdeposit">账户充值</a></li>
       <li class="list-group-item"><i class="icon-spinner mr-2"></i><a href="${ctx}/LogoutServlet">退出系统</a></li>
    </ul>
</div>