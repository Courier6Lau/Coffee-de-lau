package com.example.coffeedelau;

public class Order {
    private String customerName;
    private String coffeeName;
    private int quantity;

    public Order(String customerName, String coffeeName, int quantity) {
        this.customerName = customerName;
        this.coffeeName = coffeeName;
        this.quantity = quantity;
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
