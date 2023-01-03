package com.example.vaccinewastemitigationapp;

import java.util.ArrayList;
import static com.example.vaccinewastemitigationapp.Vacc.influenzaVaccQuantity;
import static com.example.vaccinewastemitigationapp.Vacc.influenzaExpiry;

public class InfluenzaVaccine implements VaccineWaitlistInterface{

    User aUser; // New booked user

    static int influenzaAvailable = influenzaVaccQuantity;  // Available Influenza Vaccines
    static ArrayList<User> iiAvail = new ArrayList<User>();   // Influenza waitlist


    static int iExp = influenzaExpiry;

    InfluenzaVaccine(User u){
        aUser = u;
    }

    @Override
    public void AddUser(User user) {
        iiAvail.add(user);
    }    // Add newly booked user.

    @Override
    public void removeUser(User user) {
        iiAvail.remove(user);
    }   // Remove user from waitlist

    @Override
    public int getPosition(User user) {

        return iiAvail.indexOf(user); // Position of user on waitlist
    }

    public ArrayList<User> getUsers() {
        return iiAvail;
    }   // Return waitlist

    /* Determines if number of booked users are greater than the number of available of vaccines. */
    public Boolean greaterThanAvailable(User user) {
        if(iiAvail.size() > influenzaAvailable){
            return true;
        }
        else{
            return false;
        }
    }

}