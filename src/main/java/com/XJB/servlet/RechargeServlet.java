package com.XJB.servlet;

import com.XJB.dao.UserDao;
import com.XJB.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RechargeServlet")
public class RechargeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、money
        int id = Integer.parseInt(request.getParameter("id"));
        double money = Double.parseDouble(request.getParameter("money"));
        //2.使用模型（M）中的对象执行业务方法
        UserDao userDao = new UserDao();
        if (userDao.recharge(id,money)){
            //3.1充值成功 同时更新Session中user对象信息
            User user = (User)request.getSession().getAttribute("user");
            user.setDeposit(user.getDeposit() + money);
            request.setAttribute("msg","充值成功！");
            request.getRequestDispatcher("/userdeposit.jsp").forward(request,response);
        }else{
            //3.2充值失败
            request.setAttribute("msg","充值失败！");
            request.getRequestDispatcher("/userdeposit.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
