package com.XJB.dao;

import com.XJB.domain.User;
import com.XJB.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

public class UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    //用户注册
    public boolean add(String username, String pwd, String realname,String email,String phone,String address){
        int affectRows = 0;
        try {
            //1.编写sql, deposit默认为0
            String sql = "insert into users(username, pwd, realname,email,phone,address) values(?,?,?,?,?,?)";
            //2.密码文本使用MD5加密
            String pwdMD5= DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
            //3.调用update方法，写入数据库
            affectRows = template.update(sql,username, pwdMD5, realname,email,phone,address);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //用户登录
    public User getByEmailAndPwd(String email, String pwd){
        User user = null;
        try {
            //1.编写sql
            String sql = "select * from users where email = ? and pwd = ?";
            //2.密码文本使用MD5加密
            String pwdMD5= DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
            //3.调用query方法
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>
            (User.class), email,pwdMD5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    public boolean edit(int id,String username, String realname,String email,String phone,String address){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update users set username=?, realname=?, email=?, phone=?, address=? where id=?";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,username, realname,email,phone,address,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //密码修改
    public boolean setPwd(int id,String oldPwd,String newPwd){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update users set pwd=? where id=? and pwd=?";
            //2.密码文本使用MD5加密
            String oldPwdMD5= DigestUtils.md5DigestAsHex(oldPwd.getBytes(StandardCharsets.UTF_8));
            String newPwdMD5= DigestUtils.md5DigestAsHex(newPwd.getBytes(StandardCharsets.UTF_8));
            //3.调用update方法，写入数据库
            affectRows = template.update(sql,newPwdMD5,id,oldPwdMD5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //用户充值
    public boolean recharge(int id,double money){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update users set deposit=deposit+? where id=?";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,money,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }
}

