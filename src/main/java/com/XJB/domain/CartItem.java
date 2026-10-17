package com.XJB.domain;

public class CartItem {
    private int id;
    private String title;
    private double price;
    private int quantity;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public CartItem() {
    }

    public CartItem(int id, String title, double price, int quantity, String photo) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.photo = photo;
    }
    //购物项小计金额
    public double getSubTotal() {
        return this.quantity * this.price;
    }
}
