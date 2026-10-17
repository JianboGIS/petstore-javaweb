package com.XJB.dao;

import com.XJB.domain.Category;
import com.XJB.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    //分类添加
    public boolean add(String name){
        int affectRows = 0;
        try {
            //1.编写sql,
            String sql = "insert into category(name) values(?)";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //分类修改
    public boolean edit(int id,String name){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update category set name=? where id=?";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    // 根据id获取分类
    public Category getById(int id){
        Category category = null;
        try {
            String sql = "select * from category where id = ?";
            category = template.queryForObject(sql,new BeanPropertyRowMapper<>(Category.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return category;
        }
    }

    //获取分类列表
    public List<Category> getList(){
        List<Category> categoryList = null;
        try {
            //1.编写sql
            String sql = "select * from category";
            categoryList = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return categoryList;
        }
    }
}
