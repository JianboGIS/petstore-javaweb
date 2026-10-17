package com.XJB.servlet;

import com.XJB.domain.ShoppingCart;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、quantity
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        //2.1 初始化购物车对象cart：若Session已存在该用户的cart则使用之，否则实例化cart对象
        ShoppingCart cart ;
        if(request.getSession().getAttribute("cart") != null){
            cart = (ShoppingCart)request.getSession().getAttribute("cart");
        }
        else{
            cart = new ShoppingCart();
        }
        //2.2 调用cart对象的add方法，将宠物加入购物车，具体业务逻辑在add方法中实现
        cart.add(id,quantity);
        //3.将数据传递给视图（V）并展示
        //3.1 传递给视图的数据写入Session
        request.getSession().setAttribute("cart",cart);
        //3.2 重定向，浏览器的URL有变化
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
