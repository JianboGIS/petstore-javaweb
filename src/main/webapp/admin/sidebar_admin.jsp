<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="col-3">
    <ul class="list-group">
       <%--使用了Font Awesome字体图标--%>
       <li class="list-group-item"><i class="icon-th-large mr-2"></i><a href="${ctx}/CategoryListServlet">分类管理</a></li>
       <li class="list-group-item"><i class="icon-github-alt mr-2"></i><a href="${ctx}/PetListServlet">宠物管理</a></li>
       <li class="list-group-item"><i class="icon-list mr-2"></i><a href="${ctx}/OrderListServlet">订单管理</a></li>
       <li class="list-group-item"><i class="icon-spinner mr-2"></i><a href="${ctx}/LogoutServlet">退出系统</a></li>
    </ul>
</div>