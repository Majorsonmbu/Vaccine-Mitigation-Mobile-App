package com.example.vaccinewastemitigationapp;

public class CentreLocation {

    String centreName, centreTelephone, centreAddress, centrePassword;

    public CentreLocation(String name, String tel, String addr, String pass) {
        centreName = name;
        centreTelephone = tel;
        centreAddress = addr;
        centrePassword = pass;
    }

    public String getCentreAddress() {
        return centreAddress;
    }

    public String getCentreName() {
        return centreName;
    }

    public String getCentrePassword() {
        return centrePassword;
    }

    public String getCentreTelephone() {
        return centreTelephone;
    }
}
