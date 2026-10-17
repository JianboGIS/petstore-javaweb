package com.XJB.servlet;

import com.XJB.dao.OrderDao;
import com.XJB.domain.Order;
import com.XJB.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.判断Session中的user对象
        if(request.getSession().getAttribute("user") != null){
            String viewName = request.getParameter("view");
            //默认展示用户个人信息视图
            if (viewName == null){
                viewName = "userinfo";
            }
            switch (viewName){
            case "userinfo":
                response.sendRedirect(request.getContextPath() + "/userinfo.jsp");
                break;
            case "userpwd":
                response.sendRedirect(request.getContextPath() + "/userpwd.jsp");
                break;
            case "userdeposit":
                response.sendRedirect(request.getContextPath() + "/userdeposit.jsp");
                break;
            case "userorder":
                OrderDao orderDao = new OrderDao();
                User user = (User)request.getSession().getAttribute("user");
                List<Order> orderList = orderDao.getListByUserId(user.getId());
                request.setAttribute("orderList",orderList);
                request.getRequestDispatcher("userorder.jsp").forward(request,response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/userinfo.jsp");
                break;
            }
        }else{
            //Session中user对象不存在，表示用户未登录或者Session超时，需要重新登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
