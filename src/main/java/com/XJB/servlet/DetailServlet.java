package com.XJB.servlet;

import com.XJB.dao.PetDao;
import com.XJB.domain.Pet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id
        String id = request.getParameter("id");
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        PetDao petDao = new PetDao();
        Pet pet = petDao.getById(Integer.parseInt(id));
        //3.将数据传递给视图（V）并展示（请求转发，浏览器的URL无变化）
        request.setAttribute("pet",pet);
        request.getRequestDispatcher("/detail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
