package com.example.vaccinewastemitigationapp;

public class User {

    String fName, lName, id, phone, password;

    public User() {
    }

    public User(String fName, String lName, String id, String phone, String password) {
        this.fName = fName;
        this.lName = lName;
        this.id = id;
        this.phone = phone;
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
