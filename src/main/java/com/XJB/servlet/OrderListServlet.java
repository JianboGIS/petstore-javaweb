package com.XJB.servlet;

import com.XJB.dao.OrderDao;
import com.XJB.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 state 订单状态：默认值 已付款
        String state = request.getParameter("state");
        if (state == null || "".equals(state)){
            state = "已付款";
        }
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        OrderDao orderDao = new OrderDao();
        List<Order> orderList = orderDao.getListByState(state);
        List<String> stateList = orderDao.getStateList();
        //3.将数据传递给视图（V）
        request.setAttribute("orderList",orderList);
        request.setAttribute("stateList",stateList);
        request.setAttribute("current_state",state);
        request.getRequestDispatcher("/admin/orderlist.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
