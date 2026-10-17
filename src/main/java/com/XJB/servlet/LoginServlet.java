package com.XJB.servlet;

import com.XJB.dao.UserDao;
import com.XJB.domain.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 email、pwd
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        //2.使用模型（M）中的对象执行业务方法
        UserDao userDao = new UserDao();
        User user = userDao.getByEmailAndPwd(email,pwd);
        if (user != null){
            //3.1登录成功 user对象写入Session 重定向
            request.getSession().setAttribute("user",user);
            //如果是管理员，进入管理页面
            if ("admin@qq.com".equals(email)){
                response.sendRedirect(request.getContextPath() + "/CategoryListServlet");
            }else{ //普通用户，进入网站首页
                response.sendRedirect(request.getContextPath() + "/IndexServlet");
            }
        }else{
            //3.2登录失败 重定向 登录页面
            request.setAttribute("msg","邮箱或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
