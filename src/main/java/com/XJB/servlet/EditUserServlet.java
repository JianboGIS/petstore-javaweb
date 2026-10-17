package com.XJB.servlet;

import com.XJB.dao.UserDao;
import com.XJB.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数id、username、pwd、realname、email、phone、address
        //请求参数中包含中文，需设置request的编码字符集为utf-8
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String realname = request.getParameter("realname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        //2.使用模型（M）中的类执行业务方法
        UserDao userDao = new UserDao();
        if (userDao.edit(id,username,realname,email,phone,address)){
            //3.1修改用户信息成功 同时更新Session中user对象信息
            User user = (User)request.getSession().getAttribute("user");
            user.setUsername(username);
            user.setRealname(realname);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            request.setAttribute("msg","信息修改成功！");
            request.getRequestDispatcher("/userinfo.jsp").forward(request,response);
        }else{
            //3.2修改用户信息失败
            request.setAttribute("msg","信息修改失败！");
            request.getRequestDispatcher("/userinfo.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
