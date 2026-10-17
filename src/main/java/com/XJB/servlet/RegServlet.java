package com.XJB.servlet;

import com.XJB.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 username、pwd、realname、email、phone、address
        //请求参数中包含中文，需设置request的编码字符集为utf-8
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String realname = request.getParameter("realname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        //2.使用模型（M）中的对象执行业务方法
        UserDao userDao = new UserDao();
        if (userDao.add(username, pwd, realname, email, phone, address)) {
            //3.1注册成功 重定向 登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            //3.2注册失败 重定向 注册页面
            response.sendRedirect(request.getContextPath() + "/reg.jsp");
        }
    }
}
