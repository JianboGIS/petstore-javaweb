package com.XJB.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private ShoppingCart cart;

    public void print(){
        for (CartItem item:
        cart.getCartItemList()) {
            System.out.println(item.getId()+" "+item.getTitle()+" 单价："+item.getPrice()+" 数量："+item.getQuantity() +" 小计:"+item.getSubTotal());
        }
    }

    @BeforeEach
    public void init(){
        cart = new ShoppingCart();//每个测试方法执行前，初始化购物车对象
    }

    @Test
    void getTotalCount() {
        cart.add(1,3);//购物车添加 编号id为1的宠物，数量quantity为3
        assertEquals(3,cart.getTotalCount());
        print();
    }

    @Test
    void getTotalMoney() {
        cart.add(15,3);//购物车添加 编号id为15的宠物，数量quantity为2,单价为1700
        assertEquals(5100.00,cart.getTotalMoney());
        print();
    }

    @Test
    void add() {
        cart.add(15,2);//购物车添加 编号id为15的宠物，数量quantity为2,单价为1700
        assertEquals(2,cart.getTotalCount());
        assertEquals(3400.00,cart.getTotalMoney());
        System.out.println("-----------第1次调用add方法----------");
        print();

        cart.add(10,3);//购物车添加 编号id为10的宠物，数量quantity为3,单价为90
        assertEquals(5,cart.getTotalCount());
        assertEquals(3670.00,cart.getTotalMoney());
        System.out.println("-----------第2次调用add方法----------");
        print();

        cart.add(15,1);//购物车添加 编号id为15的宠物，数量quantity为2,单价为1700
        assertEquals(6,cart.getTotalCount());
        assertEquals(5370.00,cart.getTotalMoney());
        System.out.println("-----------第3次调用add方法----------");
        print();
    }

    @Test
    void remove() {
        cart.add(15,2);//购物车添加 编号id为15的宠物，数量为2
        cart.add(10,3);//购物车添加 编号id为10的宠物，数量为3
        cart.add(22,1);//购物车添加 编号id为22的宠物，数量为1
        System.out.println("-----------初始化购物车内购物项---------");
        print();

        cart.remove(10);//购物车删除 编号id为10购物项
        assertEquals(3,cart.getTotalCount());//剩余2项，剩余数量为3
        System.out.println("-----第1次调用remove方法 删除id为10购物项-----");
        print();

        cart.remove(15);//购物车删除 编号id为15购物项
        assertEquals(1,cart.getTotalCount());//剩余1项，剩余数量为31
        System.out.println("-----第2次调用remove方法 删除id为15购物项-----");
        print();
    }

    @Test
    void edit() {
        cart.add(15,2);//购物车添加 编号id为15的宠物，数量为2
        cart.add(10,3);//购物车添加 编号id为10的宠物，数量为3
        cart.add(22,1);//购物车添加 编号id为22的宠物，数量为1
        System.out.println("-----------初始化购物车内购物项---------");
        print();

        cart.edit(10,"增加");//购物车 编号id为10购物项 数量加1
        assertEquals(7,cart.getTotalCount());//购物车 购物项数量 6+1=7
        assertEquals(3,cart.getCartItemList().size());//购物车 购物项列表长度为3
        System.out.println("-----第1次调用edit方法 编号id为10购物项 数量加1-----");
        print();

        cart.edit(22,"减少");//购物车 编号id为22购物项 数量减1
        assertEquals(6,cart.getTotalCount());//购物车 购物项数量 7+1=6
        assertEquals(2,cart.getCartItemList().size());//购物车 购物项列表长度为2
        System.out.println("-----第2次调用edit方法 编号id为22购物项 数量减1-----");
        print();
    }
}

