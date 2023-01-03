package com.example.vaccinewastemitigationapp;

public class CentreAdmin {

    String cName, cAddress, tel_phone, password;

    public CentreAdmin() {
    }

    public CentreAdmin(String fName, String cAddress, String tel_phone, String password) {
        this.cName = fName;
        this.cAddress = cAddress;
        this.tel_phone = tel_phone;
        this.password = password;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAdress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String gettelPhone() {
        return tel_phone;
    }

    public void settelPhone(String tel_phone) {
        this.tel_phone = tel_phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
