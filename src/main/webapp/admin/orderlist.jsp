<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header_admin.jsp"%>

<div class="row container">
    <%@ include file="sidebar_admin.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>订单管理</h6>
            </div>
            <div class="card-body">
                <div class="form-group col-12">
                    <label class="col-sm-2 col-form-label">订单状态</label>
                    <select class="form-control-sm col-4" id="state" name="state">
                        <c:forEach items="${stateList}" var="state">
                            <c:if test="${state == current_state}">
                                <%--与参数 current_state 相等的状态，设置为选中状态，selected="selected"--%>
                                <option value="${state}" selected="selected">${state}</option>
                            </c:if>
                            <c:if test="${state != current_state}">
                                <option value="${state}">${state}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <table class="table panel-body">
                    <tr>
                        <th>编号</th>
                        <th>金额</th>
                        <th>日期</th>
                        <th>收货人</th>
                        <th colspan="2"></th>
                    </tr>
                    <c:forEach items="${orderList}" var="order">

                    <tr>
                        <td>${order.id}</td>
                        <td>￥<span>${order.totalprice}</span></td>
                        <td><span>${order.createdate}</span></td>
                        <td><span>${order.name}</span></td>
                        <td>
                            <c:if test="${current_state == '已付款'}">
                            <form action="${ctx}/EditOrderServlet" method="post">
                                <input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="state" value="${current_state}">
                                <input type="hidden" name="type" value="发货">
                                <input type="submit" class="btn btn-primary" value="发货">
                            </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${current_state == '已付款'}">
                            <form action="${ctx}/EditOrderServlet" method="post">
                                <input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="state" value="${current_state}">
                                <input type="hidden" name="type" value="删除">
                                <input type="submit" class="btn btn-primary" value="删除">
                            </form>
                            </c:if>
                        </td>
                    </tr>
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
        $("#state").change(function () {
            let state = $(this).val();//select选中项的值
            //用户选择订单状态后，页面跳转并携带选中状态的值
            window.location.href = "${ctx}/OrderListServlet?state="+state;
        });
    });
</script>