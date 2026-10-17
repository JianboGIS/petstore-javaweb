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

@WebServlet("/EditOrderServlet")
public class EditOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、type、state
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String state = request.getParameter("state");

        //2.使用模型（M）中的对象执行业务方法
        OrderDao orderDao = new OrderDao();
        if ("发货".equals(type)){
            if (orderDao.setState(Integer.parseInt(id),"已发货")){
                request.setAttribute("msg","订单发货成功！");
            }else{
                request.setAttribute("msg","订单发货失败！");
            }
        }else if ("删除".equals(type)){
            if (orderDao.delete(Integer.parseInt(id))){
                request.setAttribute("msg","订单删除成功！");
            }else{
                request.setAttribute("msg","订单删除失败！");
            }
        }
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
