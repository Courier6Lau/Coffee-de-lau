package com.example.coffeedelau;

public class Staff {
    private String name;
    private String age;
    private String dob;
    private String address;

    public Staff(String name, String age, String dob, String address) {
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.address = address;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }
}
