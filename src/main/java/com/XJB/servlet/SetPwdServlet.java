package com.XJB.servlet;

import com.XJB.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SetPwdServlet")
public class SetPwdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、oldPwd、newPwd
        int id = Integer.parseInt(request.getParameter("id"));
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        //2.使用模型（M）中的对象执行业务方法
        UserDao userDao = new UserDao();
        if (userDao.setPwd(id,oldPwd,newPwd)){
            //3.1修改密码成功
            request.setAttribute("msg","密码修改成功！");
            request.getRequestDispatcher("/userpwd.jsp").forward(request,response);
        }else{
            //3.2修改密码失败
            request.setAttribute("msg","密码修改失败！");
            request.getRequestDispatcher("/userpwd.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
