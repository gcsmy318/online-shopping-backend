// src/main/java/com/example/onlineStore/model/Order.java
package com.example.shopping.model;

import java.util.List;

public class Order {
    private List<OrderItem> cart;
    private String address;
    private double totalPrice;

    // Getters & Setters
    public List<OrderItem> getCart() { return cart; }
    public void setCart(List<OrderItem> cart) { this.cart = cart; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    // คลาสย่อยสำหรับรายการสินค้าในออเดอร์
    public static class OrderItem {
        private String id;
        private String name;
        private int quantity;
        private double price;

        // Getters & Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
