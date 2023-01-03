package com.example.vaccinewastemitigationapp;

public class VaccinesDb {
    String vaccineName;
    String quantity;
    String expiringDate;


    public VaccinesDb(){
    }

    public VaccinesDb(String name, String quantity, String expiringDate) {
        this.vaccineName = name;
        this.quantity = quantity;
        this.expiringDate = expiringDate;
    }


    public String getName() {
        return vaccineName;
    }

    public String getExpiringDate() {
        return expiringDate;
    }

    public String getQuantity() {
        return quantity;
    }


    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }


    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setExpiringDate(String expiringDate) {
        this.expiringDate = expiringDate;
    }

}
