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

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.判断Session中的user对象和cart对象
        if(request.getSession().getAttribute("user") != null && request.getSession().getAttribute("cart") != null){
            ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("cart");
            User user =(User)request.getSession().getAttribute("user");
            OrderDao orderDao = new OrderDao();
            String msg = orderDao.add(user,cart);
            if (msg.equals("订单创建成功")){
                cart.getCartItemList().clear();//购物车中的购物项列表清空
                request.getRequestDispatcher("/UserServlet?view=userorder").forward(request,response);
            }else{
                request.setAttribute("msg",msg);
                request.getRequestDispatcher("/order.jsp").forward(request,response);
            }
        }else{
            //Session中user对象或者cart对象不存在，表示用户未登录或者Session超时，需要重新登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
