package com.XJB.dao;

import com.XJB.domain.*;

import com.XJB.domain.ShoppingCart;
import com.XJB.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {
    private ShoppingCart cart;
    private UserDao userDao;
    private User user;
    private OrderDao orderDao;
    private void print(){
        System.out.println("-----账户余额:"+user.getDeposit()+" 购物车总金额："+cart.getTotalMoney()+"------");
    }
    //每个测试方法执行前，初始化购物车对象和用户对象
    @BeforeEach
    public void init(){
        orderDao = new OrderDao();
        userDao = new UserDao();
        user = userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        cart = new ShoppingCart();
        cart.add(15,1);//购物车添加 编号id为15的宠物，数量quantity为2,单价为1700
        cart.add(10,1);//购物车添加 编号id为10的宠物，数量quantity为3,单价为90
    }

    @Test
    void checkDeposit() {
        //账户余额充足 checkDeposit方法返回true
        user.setDeposit(1790);
        print();
        Boolean result = orderDao.checkDeposit(user,cart);
        assertEquals(true,result);

        //账户余额不足 checkDeposit方法返回false
        user.setDeposit(1789);
        print();
        result = orderDao.checkDeposit(user,cart);
        assertEquals(false,result);
    }

    @Test
    void checkStock() {
        //宠物库存充足 checkStock方法返回true
        Boolean result = orderDao.checkStock(cart);
        assertEquals(true,result);

        //宠物库存不足 checkStock方法返回false
        cart.add(10,10);//购物车添加 编号id为10的宠物，数量quantity为10
        result = orderDao.checkStock(cart);
        assertEquals(false,result);
    }

    @Test
    void add() {
        userDao.recharge(user.getId(),1790);
        user = userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        print();
        String msg = orderDao.add(user,cart);
        assertEquals("订单创建成功",msg);
        printOrder();
    }

    @Test
    void getListByUserId() {
        List<Order> orderList = orderDao.getListByUserId(user.getId());
        assertEquals(false,orderList.isEmpty());//断言 列表orderList不为空
        printOrder();
    }

    private void printOrder(){
        List<Order> orderList = orderDao.getListByUserId(user.getId());
        for (Order order : orderList) {
            System.out.println("订单："+order.getId()+ " 总金额："+order.getTotalprice()+" 创建日期："+order.getCreatedate());
        }
    }

    @Test
    void getListByState(){
        List<Order> orderList = orderDao.getListByState("已付款");
        assertEquals(false,orderList.isEmpty());//断言 列表orderList不为空
    }

    @Test
    void getById(){
        Order order = orderDao.getById(1);
        assertEquals(1,order.getId());//断言 order对象id属性为1
    }

    @Test
    void getOrderDetailListById(){
        List<OrderDetail> orderDetailList = orderDao.getOrderDetailListById(1);
        assertEquals(false,orderDetailList.isEmpty());//断言 列表orderDetailList不为空
    }

    @Test
    void setState(){
        boolean result = orderDao.setState(1,"已付款");
        Order order = orderDao.getById(1);
        assertEquals("已付款",order.getState());//断言 order对象状态属性为 已发货
    }

    @Test
    void delete(){
        //删除订单前的信息
        Order order = orderDao.getById(1);
        double returnMoney = order.getTotalprice();
        UserDao userDao = new UserDao();
        User user = userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        double userMoney = user.getDeposit();

        //执行删除
        boolean result = orderDao.delete(1);

        order = orderDao.getById(1);
        user = userDao.getByEmailAndPwd("tommy@qq.com","1234@qwer");
        assertEquals(true,result);//断言 delete方法执行结果为true
        assertEquals("已取消",order.getState());//断言 order对象状态属性为 已取消
        assertEquals(userMoney+returnMoney,user.getDeposit());//断言 用户的订单金额已返回账户余额
    }

    @Test
    void getStateList(){
        List<String> stateList = orderDao.getStateList();
        assertEquals(false,stateList.isEmpty());//断言 列表 stateList 不为空
        assertEquals("已付款",stateList.get(0));//断言 列表 stateList 第一项为 “已付款”
    }

    @Test
    void getPetListById(){
        //获取编号id为1的订单详情列表
        List<OrderDetail> orderDetailList = orderDao.getOrderDetailListById(1);
        //获取编号id为1的订单详情列表 对应的宠物列表
        List<Pet> petList = orderDao.getPetListById(1);
        for (int i=0; i<orderDetailList.size(); i++){
            //断言 两个列表的宠物id是一一对应相等的
            assertEquals(orderDetailList.get(i).getPet_id(),petList.get(i).getId());
        }
    }
}

