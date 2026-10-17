package com.XJB.servlet;

import com.XJB.dao.OrderDao;
import com.XJB.domain.ShoppingCart;
import com.XJB.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.判断Session中的user对象和cart对象
        if(request.getSession().getAttribute("user") != null){
            ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("cart");
            User user =(User)request.getSession().getAttribute("user");
            OrderDao orderDao = new OrderDao();
            String msg = "";
            if(cart == null || cart.getTotalMoney() == 0){
                msg = "无订单明细";
            }else if (orderDao.checkDeposit(user,cart) == false){
                msg = "账户余额不足";
            }else if (orderDao.checkStock(cart) == false){
                msg = "宠物库存不足";
            }
            request.setAttribute("msg",msg);
            request.getRequestDispatcher("/order.jsp").forward(request,response);
        }else{
            //Session中user对象或者cart对象不存在，表示用户未登录或者Session超时，需要重新登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
