package com.example.vaccinewastemitigationapp;

import java.util.ArrayList;
import java.util.HashMap;
import static com.example.vaccinewastemitigationapp.Vacc.pfizerVaccQuantity;
import static com.example.vaccinewastemitigationapp.Vacc.pfizerExpiry;

public class PfizerVaccine implements VaccineWaitlistInterface{

    User aUser;  // New booked user

    static int pfizerAvailable = pfizerVaccQuantity;    // Available Pfizer Vaccines
    static ArrayList<User> pAvail = new ArrayList<User>();   // Pfizer waitlist

    static int pExp = pfizerExpiry;

    PfizerVaccine(User u){
        aUser = u;
    }

    @Override
    public void AddUser(User user) {
        pAvail.add(user);
    }   // Add newly booked user.

    @Override
    public void removeUser(User user) {
        pAvail.remove(user);
    }   // Remove user from waitlist

    @Override
    public int getPosition(User user) {

        return pAvail.indexOf(user); // Position of user on waitlist
    }

    public ArrayList<User> getUsers() {
        return pAvail;
    }   // Return waitlist

    /* Determines if number of booked users are greater than the number of available of vaccines. */
    public Boolean greaterThanAvailable(User user) {
        if(pAvail.size() > pfizerAvailable){
            return true;
        }
        else{
            return false;
        }
    }


}