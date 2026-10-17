package com.XJB.dao;

import com.XJB.domain.*;


import com.XJB.domain.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetDaoTest {
    private PetDao petDao;

    //每个测试方法执行前，初始化 petDao 对象
    @BeforeEach
    public void init(){
        petDao = new PetDao();
    }

    @Test
    void getNewList() {
        List<Pet> newPetList = petDao.getNewList();//执行被测试方法
        assertEquals(12,newPetList.size());//断言：测试结果列表大小是否为12

        for (Pet pet : newPetList) {  //输出宠物信息，非必须代码，
            System.out.println(pet.getTitle());
        }
    }

    @Test
    void getById() {
        Pet pet = petDao.getById(1);
        assertEquals(1,pet.getId());
        System.out.println(pet.getTitle());//输出宠物名称，非必须代码，
    }

    @Test
    void getListByCategoryId() {
        List<Pet> petList = petDao.getListByCategoryId(1);//类别编号 1，类别名称 猫
        assertEquals(false,petList.isEmpty());
        assertEquals(1,petList.get(0).getCategory_id());
        System.out.println(petList.get(0).getTitle());//输出宠物名称，非必须代码，
    }

    @Test
    void add() {
        Boolean result= petDao.add(1,"玩具猫","活泼可爱",
        "cat_n.jpg",200,5,"玩具猫的简介信息...");
        assertEquals(true,result);
    }

    @Test
    void edit(){
        Pet pet= petDao.getById(1); //宠物Id：1  宠物名称：金丝雀
        //修改宠物的单价为 100，库存为 12 其他信息保持不变
        Boolean result = petDao.edit(pet.getId(),pet.getCategory_id(),pet.getTitle(),pet.getTag(),
        pet.getPhoto(),100,12,pet.getDescs());
        assertEquals(true,result);
        //再次获取 宠物 1
        pet= petDao.getById(1); //宠物Id：1  宠物名称：金丝雀
        assertEquals(100,pet.getPrice());
        assertEquals(12,pet.getStock());
    }

    @Test
    void getNewListByCategoryId(){
        List<Pet> petList = petDao.getNewListByCategoryId(1);
        assertEquals(false,petList.isEmpty());
        assertEquals(1,petList.get(0).getCategory_id());
        petList = petDao.getNewListByCategoryId(0);
        assertEquals(false,petList.isEmpty());
    }

    @Test
    void getNewListByKey(){
        List<Pet> petList = petDao.getNewListByKey ("雀");
        //断言 列表中宠物对象数量不为空
        assertEquals(false,petList.isEmpty());
        //断言 列表中宠物对象的名称包含关键字 雀
        for (Pet pet : petList) {
            assertEquals(true,pet.getTitle().indexOf("雀") > -1);
        }
    }
}

