package com.example.vaccinewastemitigationapp;

public class UserLocation {

    String suburb;
    String country;
    String town;

    public UserLocation(String s, String c, String t) {
        suburb = s;
        country = c;
        town = t;
    }

    public String getCountry() {
        return country;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getTown() {
        return town;
    }
}
