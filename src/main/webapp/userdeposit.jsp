<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="row container">
    <%@ include file="sidebar.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>账户充值</h6>
            </div>
            <div class="card-body">
                <form action="${ctx}/RechargeServlet" method="post">
                    <div class="form-group col-6">
                        <label class="form-control-label">账户余额</label>
                        <input type="text" class="form-control" name="deposit" value="${user.deposit}" readonly>
                    </div>
                    <div class="form-group col-6">
                        <label class="form-control-label">充值金额</label>
                        <input type="number" class="form-control" name="money" >
                    </div>
                    <div class="form-group col-6">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" class="btn btn-primary form-group col-12" value="保存">
                    </div>
                </form>
                <div class="text-center">
                    <span class="text-danger">${msg}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>