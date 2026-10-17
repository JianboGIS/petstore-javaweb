<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2025/12/16
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp"%>
<div class="container">
    <div class="row no-gutters boder rounded flex-md-row mb-4 shadow-sm h-md-250">
        <div class="col-auto d-none d-lg-block">
            <img src="${ctx}/peting/${pet.photo}" width="300" height="400" class="mb-2">
        </div>
        <div class="col p-4 d-flex flex-column">
            <h3 class="d-inline-block mb-2 text-dark">${pet.title}</h3>
            <div class="mb-2 text-muted">
                <span class="pet-tag">${pet.tag}</span>
            </div>
            <p class="card-text">${pet.descs}</p>
            <p>价格：￥<span id="pet-price">${pet.price}</span></p>
            <p>库存：<span id="pet-stock">${pet.stock}</span></p>
            <form action="${ctx}/AddToCartServlet" method="post">
                <p>
                    <label for="pet-quantity">数量：</label>
                    <input type="text" id="pet-quantity" name="quantity" value="1" />
                    <input type="hidden" name ="id" value="${pet.id}" />
                </p>
                <nav>
                    <input class="btn btn-warning" type="submit" value="加入购物车">
                    <a class="btn btn-warning" href="${ctx}/IndexServlet">
                        返回首页
                    </a>
                </nav>
            </form>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>