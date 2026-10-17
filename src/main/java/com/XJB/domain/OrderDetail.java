package com.XJB.domain;

public class OrderDetail {
    private int id;
    private int order_id;
    private int pet_id;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderDetail() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getPet_id() {
        return pet_id;
    }
    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
