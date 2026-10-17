package com.XJB.dao;

import com.XJB.domain.Pet;
import com.XJB.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class PetDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 获取最新上架12只宠物对象列表
    public List<Pet> getNewList() {
        List<Pet> petList = null;
        try {
            String sql = "select * from pets order by ondate desc limit 12";
            petList = template.query(sql, new BeanPropertyRowMapper<>(Pet.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return petList;
        }
    }

    // 获取单个宠物对象
    public Pet getById(int id){
        Pet pet = null;
        try {
            String sql = "select * from pets where id = ?";
            pet = template.queryForObject(sql,new BeanPropertyRowMapper<>(Pet.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return pet;
        }
    }

    // 根据分类Id获取宠物对象列表
    public List<Pet> getListByCategoryId(int category_id){
        List<Pet> petList = null;
        try {
            String sql = "select * from pets where category_id=? order by id desc";
            petList = template.query(sql, new BeanPropertyRowMapper<>(Pet.class),category_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return petList;
        }
    }

    //宠物添加
    public boolean add(int category_id,String title,String tag,String photo,double price,int stock,String descs){
        int affectRows = 0;
        try {
            //1.编写sql, ondate字段的值 使用数据库系统时间函数 NOW()
            String sql = "insert into pets(ondate, category_id, title, tag, photo, price, stock, descs) values(NOW(),?,?,?,?,?,?,?)";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,category_id, title, tag, photo, price, stock, descs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //宠物修改
    public boolean edit(int id,int category_id,String title,String tag,String photo,double price,int stock,String descs){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update pets set category_id=?, title=?, tag=?, photo=?, price=?, stock=?, descs=? where id=?";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,category_id, title, tag, photo, price, stock, descs,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    // 第12章 根据宠物分类获取最新上架12只宠物对象列表
    public List<Pet> getNewListByCategoryId(int category_id){
        List<Pet> petList = null;
        String sql = "";
        try {
            if (category_id == 0){ //  category_id == 0 无分类情况
                sql = "select * from pets order by ondate desc limit 12";
                petList = template.query(sql, new BeanPropertyRowMapper<>(Pet.class));
            }else{ // category_id != 0 有分类情况
                sql = "select * from pets where category_id = ? order by ondate desc limit 12";
                petList = template.query(sql, new BeanPropertyRowMapper<>(Pet.class),category_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return petList;
        }
    }

    // 第12章 根据关键字获取最新上架12只宠物对象列表
    public List<Pet> getNewListByKey(String key){
        List<Pet> petList = null;
        try {
            String sql = "select * from pets where title like concat('%',?,'%') order by ondate desc limit 12";
            petList = template.query(sql, new BeanPropertyRowMapper<>(Pet.class),key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return petList;
        }
    }
}

