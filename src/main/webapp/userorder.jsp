<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<div class="row container">
    <%@ include file="sidebar.jsp"%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <h6>我的订单</h6>
            </div>
            <div class="card-body">
                <c:forEach items="${orderList}" var="order">
                    <div class="card order">
                        <div class="card-header">
                            <h6>
                                编号:<span class="mr-2">${order.id}</span>
                                日期:<span class="mr-2">${order.createdate}</span>
                                金额:¥<span class="mr-2">${order.totalprice}</span>
                                <button type="button" class="btn btn-outline-secondary float-right" data-id="${order.id}">订单详情</button>
                            </h6>
                        </div>
                        <%--订单明细 默认无内容，单击 订单详情 按钮后再加载显示--%>
                       <div class="card-body d-none">
                            <table class="table panel-body" id="detailTable">
                                <tr>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>小计</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        //“订单详情”按钮单击事件，使用jquery的one方法，该事件只执行一次。避免多次点击重复加载数据问题。
        $(".btn").one("click",function () {
            let orderId = $(this).data("id"); //获取订单id
            let $detailTable = $(this).parents(".order").find("#detailTable");//获取用于显示  订单详情 的 table元素
            $.ajax({
                type:"post",
                url:"${ctx}/OrderDetailServlet",
                data:{id:orderId},
                success:function (returnData){//returnData 变量中保存在Servlet返回的数据
                    //debugger;   //用于 开启浏览器JavaScript代码调试，调试完成后删除本行代码
                    //将请求返回的json数据在页面上展示，需要动态构造tr元素，添加到table元素中
                    let detailList = eval(returnData);//json字符串转化为js对象
                    for (let i=0; i<detailList.length; i++){
                        let detail = detailList[i];
                        //字符串拼接
                        let $tr = "<tr><td>"+detail.title+"</td><td>"+detail.price+"</td><td>"+detail.quantity+"</td><td>"+detail.subtotal+"</td></tr>";
                        $detailTable.append($tr);
                    }
                    //显示订单详情，d-none表示display:none,移除该样式即可显示在页面上
                    $detailTable.parent().removeClass("d-none");
                }
            });
        });
    });
</script>