<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2025/12/16
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="container">
    <div class="card">
        <div class="card-header">购物车</div>
        <div class="card-body">
            <table class="table panel-body cart">
    <thead>
    <tr>
        <th></th>
        <th>名称</th>
        <th>价格</th>
        <th class="text-center" colspan="3">数量</th>
        <th>小计</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cart.getCartItemList()}" var="cartItem">
        <tr data-id="${cartItem.id}" class="cartItemTr">
            <td><img src="${ctx}/peting/${cartItem.photo}" width="120" ></td>
            <td>${cartItem.title}</td>
            <td>
                <span> ¥ ${cartItem.price}</span>
            </td>
            <td>
                <form class="form-inline mt-0" action="${ctx}/EditCartServlet" method="post">
                    <input type="hidden" name="id" value="${cartItem.id}">
                    <input type="hidden" name="type" value="增加">
                    <input type="submit" class="btn btn-light" value="+">
                </form>
            </td>
            <td>
                <%-- <input type="text" class="form-control" name="quantity" value="${cartItem.quantity}">--%>
                <input type="text" class="form-control" name="quantity" value="${cartItem.quantity}">
            </td>
            <td>
                <form class="form-inline  mt-0"  action="${ctx}/EditCartServlet" method="post">
                    <input type="hidden" name="id" value="${cartItem.id}">
                    <input type="hidden" name="type" value="减少">
                    <input type="submit" class="btn btn-light" value="-">
                </form>
            </td>
            <td>
                <span> ¥ ${cartItem.getSubTotal()}</span>
            </td>
            <td><a class="text-decoration-none" href="${ctx}/RemoveFromCartServlet?id=${cartItem.id}">X</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
        </div>
        <div class="card-footer">
            <div class="row">
                <div class="offset-6 col-2"><a class="btn btn-warning" href="${ctx}/IndexServlet">继续浏览</a></div>
                <div class="col-2"><a class="btn btn-warning" href="${ctx}/ConfirmOrderServlet">确认订单</a></div>
                <div class="col-2"> 总计: ¥ <span id="totalMoney">${cart.getTotalMoney()}</span></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>