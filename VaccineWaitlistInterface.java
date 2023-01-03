package com.example.vaccinewastemitigationapp;

public interface VaccineWaitlistInterface {
    void AddUser(User user);

    void removeUser(User user);

    int getPosition(User user);
}
