package com.example.vaccinewastemitigationapp;

import java.util.ArrayList;
import static com.example.vaccinewastemitigationapp.Vacc.jandjVaccQuantity;
import static com.example.vaccinewastemitigationapp.Vacc.jandjExpiry;

public class JandJVaccine implements VaccineWaitlistInterface{

    User aUser; // New booked user


    static int jAndjAvailable = jandjVaccQuantity;  // Available J&J Vaccines
    static ArrayList<User> jjAvail = new ArrayList<User>();  // J&J waitlist

    static int jExp = jandjExpiry;

    JandJVaccine(User u){
        aUser = u;
    }

    @Override
    public void AddUser(User user) {
        jjAvail.add(user);
    }   // Add newly booked user.

    @Override
    public void removeUser(User user) {
        jjAvail.remove(user);
    }   // Remove user from waitlist

    @Override
    public int getPosition(User user) {

        return jjAvail.indexOf(user); // Position of user on waitlist
    }

    public ArrayList<User> getUsers() {
        return jjAvail;
    } // Return waitlist

    /* Determines if number of booked users are greater than the number of available of vaccines. */
    public Boolean greaterThanAvailable(User user) {
        if(jjAvail.size() > jAndjAvailable){
            return true;
        }
        else{
            return false;
        }
    }

}