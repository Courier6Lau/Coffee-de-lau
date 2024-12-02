package com.example.coffeedelau;

public class Coffee {
    private int imageResource;
    private String name;
    private double price;

    public Coffee(int imageResource, String name, double price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

    public int getImageResource() { return imageResource; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
