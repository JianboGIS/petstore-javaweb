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

@WebServlet("/EditPetServlet")
public class EditPetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 id、category_id, title, tag, photo, price, stock, descs
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        String title = request.getParameter("title");
        String tag = request.getParameter("tag");
        String photo = request.getParameter("photo");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String descs = request.getParameter("descs");
        //2.使用模型（M）中的对象执行业务方法
        PetDao petDao = new PetDao();
        if (id == null || "0".equals(id) ){ //id==null 或者 id==0 时，执行 添加宠物
            if (petDao.add(category_id, title, tag, photo, price, stock, descs)){
                request.setAttribute("msg","宠物添加成功！");
            }else{
                request.setAttribute("msg","宠物添加失败！");
            }
        }else{ //id不为null时，执行编辑宠物
            if (petDao.edit(Integer.parseInt(id),category_id, title, tag, photo, price, stock, descs)){
                request.setAttribute("msg","宠物修改成功！");
            }else{
                request.setAttribute("msg","宠物修改失败！");
            }
        }

        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getList();
        List<Pet> petList = petDao.getListByCategoryId(category_id);
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
