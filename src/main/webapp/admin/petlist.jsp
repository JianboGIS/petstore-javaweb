<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header_admin.jsp"%>

<div class="row container">
    <%@ include file="sidebar_admin.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>宠物管理</h6>
            </div>
            <div class="card-body">
                <div class="form-group col-12">
                        <form action="${ctx}/PetServlet" method="get">
                            <select class="form-control-sm offset-2 col-4" id="category_id" name="category_id">
                                <c:forEach items="${categoryList}" var="category">
                                    <c:if test="${category.id == category_id}">
                                        <%--与参数category_id相等的分类，设置为选中状态，selected="selected"--%>
                                        <option value="${category.id}" selected="selected">${category.name}</option>
                                    </c:if>
                                    <c:if test="${category.id != category_id}">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="submit" class="btn btn-primary offset-1" value="添加宠物">
                        </form>
                </div>
                <table class="table panel-body">
                    <tr>
                        <th></th>
                        <th>名称</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${petList}" var="pet">
                    <form action="${ctx}/PetServlet" method="get">
                    <tr>
                        <td>${pet.id}<input type="hidden" name="id" value="${pet.id}">
                            <input type="hidden" name="category_id" value="${category_id}">
                        </td>
                        <td><input type="text" class="form-control" name="title" value="${pet.title}" readonly></td>
                        <td><input type="text" class="form-control" name="price" value="${pet.price}" readonly></td>
                        <td><input type="text" class="form-control" name="stock" value="${pet.stock}" readonly></td>
                        <td><input type="submit" class="btn btn-primary" value="编辑"></td>
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
<script type="text/javascript">
    $(document).ready(function () {
        $("#category_id").change(function () {
            let category_id = $(this).val();//select选中项的值
            //用户选择分类后，页面自动跳转并携带选中分类的id值
            window.location.href = "${ctx}/PetListServlet?category_id="+category_id;
        });
    });
</script>