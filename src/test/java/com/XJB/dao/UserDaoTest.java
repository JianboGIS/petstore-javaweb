package com.XJB.dao;

import com.XJB.domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void init() {//每个测试方法执行前，初始化UserDao对象
        userDao = new UserDao();
    }

    @Test
    void add() {
        Boolean reusult = userDao.add("Tommy", "1234@qwer", "童米米", "Tommy@qq.com", "13305718899", "测试地址");
        assertEquals(true, reusult);
    }

    @Test
    void getByEmailAndPwd() {
        User user= userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        assertEquals("童米米",user.getRealname());
    }

    @Test
    void edit(){
        //获取邮箱为 tommy@qq.com 的用户
        User user= userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        print(user,true);
        //修改用户的 realname，phone，address等信息，断言edit方法返回结果为 true
        Boolean result = userDao.edit(user.getId(),"Tommy2","童米米2",
        "tommy@qq.com","13505718899","测试地址2");
        assertEquals(true,result);
        //再次获取邮箱为 tommy@qq.com 的用户，断言 用户姓名为修改后信息
        user = userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        assertEquals("童米米2",user.getRealname());
        print(user,false);
    }

    @Test
    void setPwd(){
        //获取邮箱为 tommy@qq.com 的用户
        User user= userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        //设置该用户新密码，断言setPwd方法返回结果为 true
        Boolean result = userDao.setPwd(user.getId(),"1234@qwer","12@qw");
        assertEquals(true,result);
        //再次获取邮箱为 tommy@qq.com 的用户，密码参数用新密码，断言获取的user对象不为null
        user = userDao.getByEmailAndPwd("tommy@qq.com","12@qw");
        assertNotNull(user);
        //最后还原该用户密码为修改前的密码
        userDao.setPwd(user.getId(),"12@qw","1234@qwer");
    }

    @Test
    void recharge(){
        //获取邮箱为 tommy@qq.com 的用户
        User user= userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        double oldDeposit = user.getDeposit();
        print(user,true);
        //为该用户充值 1000.50 元，断言 recharge 方法返回结果为 true
        Boolean result = userDao.recharge(user.getId(),1000.50);
        assertEquals(true,result);
        //再次获取邮箱为 tommy@qq.com 的用户，断言 用户的账余额为增加1000.50元后的数量
        user= userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        assertEquals(oldDeposit + 1000.50,user.getDeposit());
        print(user,false);
    }

    public void print(User user,Boolean isBefore){
        if (isBefore == true){
            System.out.println("-----------修改前----------");
        }else{
            System.out.println("-----------修改后----------");
        }
        System.out.println("邮箱："+user.getEmail()+" 姓名："+user.getRealname()+" 余额："+user.getDeposit());
    }
}

