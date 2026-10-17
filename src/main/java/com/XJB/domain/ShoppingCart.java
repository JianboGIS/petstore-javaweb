package com.XJB.domain;

import com.XJB.dao.PetDao;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    //购物车内购物项对象列表
    private List<CartItem> cartItemList;
    public List<CartItem> getCartItemList() {
        return cartItemList;
    }
    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    //购物车构造函数，初始化内部购物项列表对象
    public ShoppingCart() {
        this.cartItemList = new ArrayList<CartItem>();
    }

    //获取购物车内宠物数量
    public int getTotalCount(){
        int count = 0;
        for (CartItem item :
        this.cartItemList) {
            count += item.getQuantity();
        }
        return  count;
    }
    //获取购物车内宠物总金额
    public double getTotalMoney(){
        double money = 0;
        for (CartItem item :
        this.cartItemList) {
            money += item.getSubTotal();
        }
        return  money;
    }
    //将宠物做为购物项添加到购物车内
    public void add(int id,int quantity){
        PetDao petDao = new PetDao();
        Pet pet = petDao.getById(id);
        CartItem cartItem = new CartItem(id,pet.getTitle(),pet.getPrice(),quantity,pet.getPhoto());

        boolean foundFlag = false;
        for (CartItem item:
        this.cartItemList) {
            if (item.getId() == id){
                item.setQuantity(item.getQuantity() + quantity);
                foundFlag = true;
                break;
            }
        }
        if(foundFlag == false){
            this.cartItemList.add(cartItem);
        }
    }

    //从购物车内移除购物项
    public void remove(int id){
        for (CartItem item: this.cartItemList) {
            if (item.getId() == id){
                this.cartItemList.remove(item);
                break;
            }
        }
    }

    //编辑购物项数量 增加或者减少
    public void edit(int id,String type){
        for (CartItem item: this.cartItemList) {
            if (item.getId() == id){
                if("增加".equals(type)){
                    item.setQuantity(item.getQuantity()+1);
                }else{
                    //数量减少到0时，需要从购物项列表中移除
                    if (item.getQuantity() - 1 == 0){
                        this.cartItemList.remove(item);
                    }else{
                        item.setQuantity(item.getQuantity()-1);
                    }
                }
                break;
            }
        }
    }
}

