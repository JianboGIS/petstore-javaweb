package com.XJB.servlet;

import com.XJB.dao.CategoryDao;
import com.XJB.domain.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/EditCategoryServlet")
public class EditCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、name 请求参数中包含中文，需设置request的编码字符集为utf-8
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        //2.使用模型（M）中的对象执行业务方法
        CategoryDao categoryDao = new CategoryDao();
        if (id == null){ //id为null时，执行添加分类
            if (categoryDao.add(name)){
                request.setAttribute("msg","分类添加成功！");
            }else{
                request.setAttribute("msg","分类添加失败！");
            }
        }else{ //id不为null时，执行编辑分类
            if (categoryDao.edit(Integer.parseInt(id),name)){
                request.setAttribute("msg","分类修改成功！");
            }else{
                request.setAttribute("msg","分类修改失败！");
            }
        }
        List<Category> categoryList = categoryDao.getList();
        request.setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/admin/categorylist.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
