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

@WebServlet("/PetServlet")
public class PetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数 category_id 和 id,
        //  若请求参数 id != null，则表示为编辑宠物；否则表示新增宠物
        String category_id = request.getParameter("category_id");
        String id = request.getParameter("id");

        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        PetDao petDao = new PetDao();
        Pet pet;
        if (id != null){//编辑宠物，获取宠物对象
            pet = petDao.getById(Integer.parseInt(id));
        }else{         //新增宠物，构造新宠物对象
            pet = new Pet();
            pet.setCategory_id(1);//新增宠物默认分类id为1
        }
        //3.将数据传递给视图（V）并展示（请求转发，浏览器的URL无变化）
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getList();
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("pet",pet);
        request.setAttribute("category_id",category_id);
        request.getRequestDispatcher("/admin/petedit.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
