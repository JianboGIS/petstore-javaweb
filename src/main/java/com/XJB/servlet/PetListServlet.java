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

@WebServlet("/PetListServlet")
public class PetListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 category_id 若请求参数值为null或者0，则设置 category_id 默认值 1
        String category_id = request.getParameter("category_id");
        if (category_id == null || "0".equals(category_id)){
            category_id = "1";
        }
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        PetDao petDao = new PetDao();
        List<Pet> petList = petDao.getListByCategoryId(Integer.parseInt(category_id));
        //3.将数据传递给视图（V）并展示（请求转发，浏览器的URL无变化）
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getList();
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("petList",petList);
        request.setAttribute("category_id",category_id);
        request.getRequestDispatcher("/admin/petlist.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
