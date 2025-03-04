package com.example.shopping.model;

public class Product {
    private String id;
    private String name;
    private String code;
    private String info;
    private double price;
    private String image;

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
