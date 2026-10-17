<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header_admin.jsp"%>

<div class="row container">
    <%@ include file="sidebar_admin.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>分类管理</h6>
            </div>
            <div class="card-body">
                <div class="form-group col-12">
                        <form action="${ctx}/EditCategoryServlet" method="post">
                            <input type="text" class="form-control-sm offset-2 col-4" name="name" placeholder="请输入分类名称" >
                            <input type="submit" class="btn btn-primary offset-1" value="添加分类">
                        </form>
                </div>
                <table class="table panel-body">
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${categoryList}" var="category">
                    <form action="${ctx}/EditCategoryServlet" method="post">
                    <tr>
                        <td>${category.id}<input type="hidden" name="id" value="${category.id}"></td>
                        <td><input type="text" class="form-control" name="name" value="${category.name}"></td>
                        <td><input type="submit" class="btn btn-primary" value="保存"></td>
                    </tr>
                    </form>
                    </c:forEach>
                </table>
            </div>
            <div class="card-footer text-center">
                <span class="text-danger">${msg}</span>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer_admin.jsp"%>