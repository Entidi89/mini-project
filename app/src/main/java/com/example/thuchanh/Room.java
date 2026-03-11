package com.example.thuchanh;

public class Room {
    private String id;
    private String name;
    private double price;
    private String status; // "Available" or "Rented"
    private String tenantName;
    private String phoneNumber;

    public Room(String id, String name, double price, String status, String tenantName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.tenantName = tenantName;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
