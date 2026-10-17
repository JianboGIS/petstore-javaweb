package com.XJB.servlet;

import com.XJB.dao.CategoryDao;
import com.XJB.dao.PetDao;
import com.XJB.domain.Category;
import com.XJB.domain.Pet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 查询关键字 key
        request.setCharacterEncoding("utf-8");
        String key = request.getParameter("key");
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        PetDao petDao = new PetDao();
        List<Pet> petList = petDao.getNewListByKey(key);
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getList();
        //3.将数据传递给视图（V）并展示（请求转发，浏览器的URL无变化）
        request.setAttribute("petList",petList);
        request.setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
