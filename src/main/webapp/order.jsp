<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="container">
    <div class="card">
        <div class="card-header">订单明细</div>
        <div class="card-body">
            <table class="table panel-body cart">
                <thead>
                <tr>
                    <th></th>
                    <th>名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>小计</th>
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
                            <span> ${cartItem.quantity}</span>
                        </td>
                        <td>
                            <span> ¥ ${cartItem.getSubTotal()}</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="card-footer">
            <div class="row">
                <div class="col-2"> <b>收货人信息</b></div>
                <div class="col-3"> <span>${user.realname}</span></div>
                <div class="col-4"> <span>${user.phone}</span></div>
                <div class="col-3"> 订单总金额: ¥ <span id="totalMoney">${cart.getTotalMoney()}</span></div>
                <div class="offset-2 col-8 mt-2"> <span>${user.address}</span></div>
                <%--账户金额 和 宠物库存检查通过时，msg为空，显示”立即结算“按钮--%>
                <c:if test="${empty msg}">
                    <div class="col-2 mt-2"><a class="btn btn-warning" href="${ctx}/CreateOrderServlet">立即结算</a></div>
                </c:if>
                <c:if test="${msg=='账户余额不足'}">
                    <div class="col-2 mt-2"><span class="text-danger">${msg}</span><a class="btn btn-warning" href="${ctx}/UserServlet?view=userdeposit">账户充值</a></div>
                </c:if>
                <c:if test="${msg=='宠物库存不足' || msg=='无订单明细'}">
                    <div class="col-2 mt-2"><span class="text-danger">${msg}</span><a class="btn btn-warning" href="${ctx}/cart.jsp">查看购物车</a></div>
                </c:if>
                <c:if test="${msg=='订单创建失败'}">
                    <div class="col-2 mt-2"><span class="text-danger">${msg}</span></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>