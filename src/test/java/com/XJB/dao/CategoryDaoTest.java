package com.XJB.dao;

import com.XJB.domain.*;

import com.XJB.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {
    private  CategoryDao categoryrDao;

    private void printCategory(){
        List<Category> categoryList = categoryrDao.getList();
        for (Category category : categoryList) {
            System.out.println("分类编号："+category.getId()+ " 分类名称："+category.getName());
        }
    }

    @BeforeEach
    public void init(){
        categoryrDao = new CategoryDao();
    }

    @Test
    void add() {
        Boolean result = categoryrDao.add("昆虫");
        assertEquals(true,result);
        printCategory();
    }

    @Test
    void edit() {
        Category category = categoryrDao.getById(1); //编号id=1 名称name=猫
        String nameBeforeModify = category.getName();

        categoryrDao.edit(category.getId(),"被修改的名称");
        category = categoryrDao.getById(1);
        assertEquals("被修改的名称",category.getName());
        printCategory();
        //宠物名称恢复为修改前名称
        categoryrDao.edit(category.getId(),nameBeforeModify);
    }

    @Test
    void getById() {
        Category category = categoryrDao.getById(1); //编号id=1 名称name=猫
        assertEquals("猫",category.getName());
    }

    @Test
    void getList() {
        List<Category> categoryList = categoryrDao.getList();
        assertEquals(false,categoryList.isEmpty());//断言 列表categoryList不为空
        printCategory();
    }
}
