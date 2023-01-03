package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.example.vaccinewastemitigationapp.InfluenzaVaccine.influenzaAvailable;
import static com.example.vaccinewastemitigationapp.InfluenzaVaccine.iiAvail;
import static com.example.vaccinewastemitigationapp.JandJVaccine.jAndjAvailable;
import static com.example.vaccinewastemitigationapp.JandJVaccine.jjAvail;
import static com.example.vaccinewastemitigationapp.PfizerVaccine.pfizerAvailable;
import static com.example.vaccinewastemitigationapp.PfizerVaccine.pAvail;


import static com.example.vaccinewastemitigationapp.PfizerVaccine.pExp;
import static com.example.vaccinewastemitigationapp.InfluenzaVaccine.iExp;
import static com.example.vaccinewastemitigationapp.JandJVaccine.jExp;

public class VaccineWaitlist extends AppCompatActivity {

    private Button bookBtn, posButton;  // Book for waitlist button and view position on waitlist button.
    TextInputEditText userSuburb, userCountry, userTown;    // User location details comprising of the three.

    private RadioGroup radioGrp;    // Vaccines radiogroup view
    private RadioButton radioButton;    // Individual vaccines' radio buttons

    static String vaccine = ""; // To hold vaccine name / type
    Boolean offer; // Condition for checking eligibility of offer

    ArrayList<User> jAndjBookedUsers = jjAvail;  // All users booked for J&J Vaccine
    ArrayList<User> pfizerBookedUsers = pAvail;  // All users booked for Pfizer Vaccine
    ArrayList<User> influenzaBookedUsers = iiAvail;   // All users booked for Influenza Vaccine

    ArrayList<User> listToCheck = new ArrayList<User>();    // Vaccine waitlist to check for offers and positions

    static ArrayList<User> given_offers = new ArrayList<User>();    // Users to be granted offers to be stored here
    static ArrayList<UserLocation> given_offers_userLocations = new ArrayList<UserLocation>();  // Users given offers have their locations stored here

    int availableJandJ = jAndjAvailable;    // Available quantity of J&J Vaccines
    int availablePfizer = pfizerAvailable;  // Available quantity of Pfizer Vaccines
    int availableInfluenza = influenzaAvailable;    // Available quantity of Influenza Vaccines

    int userInd;    // Stores index position of current user booking for any vaccine type

    static User currentUser;    // Current user making booking
    static UserLocation currentUser_userLocation;   // Current user's location

    static int jandjToGiveOffer = 0;    // Tracks number of users to give J&J offers
    static int pfizerToGiveOffer = 0;   // Tracks number of users to give Pfizer offers
    static int influenzaToGiveOffer = 0;    // Tracks number of users to give Influenza offers

    static int jjExpiry = jExp;
    static int ppExpiry = pExp;
    static int iiExpiry = iExp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_waitlist);

        radioGrp = (RadioGroup) findViewById(R.id.radioGroup);

        userSuburb = (TextInputEditText) findViewById(R.id.userSuburbTextField);
        userCountry = (TextInputEditText) findViewById(R.id.userCountryTextField);
        userTown = (TextInputEditText) findViewById(R.id.userTownTextField);

        /* Action event for 'book' button */
        bookBtn = (Button) findViewById(R.id.bookingButton);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u_Suburb = userSuburb.getText().toString();  // User's suburb from input field.
                String u_Country = userCountry.getText().toString();    // User's country from input field.
                String u_Town = userTown.getText().toString();  // User's town from input field.
                UserLocation userLocation = new UserLocation(u_Suburb, u_Country, u_Town);  // Instance of user's location object
                currentUser_userLocation = userLocation;

                int radioID = radioGrp.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioID);
                String text = radioButton.getText().toString();

                /* If selection made for J&J Vaccine */
                if(text.equalsIgnoreCase("JandJ")){
                    String fname =  UserName();
                    String lname = UserLastName();
                    String id = UserID();
                    String phone = UserPhone();
                    String passw = UserPassword();
                    User user = new User(fname, lname, id, phone, passw);
                    JandJVaccine jBooking = new JandJVaccine(user);
                    jBooking.AddUser(user); // Add user to J&J booking waitlist
                    setVaccine("JJ Vaccine"); // Set vaccine identification name to J&J
                    vaccineWaitlistName("JJ Vaccine");  // Set waitlist to view as the J&J list
                    offer = jBooking.greaterThanAvailable(user);    // Checks if current users in booking list > than available vaccine
                    userInd = jAndjBookedUsers.indexOf(user);   // Store user's index in booking list

                    /* If user can't be given an offer (number of users booked now > available vaccines) */
                    if(offer == true) {
                        for(int i = 0; i < jandjToGiveOffer; i++) {
                            jBooking.getUsers().remove(jBooking.getUsers().get(i)); // Remove from waitlist users that can be given a vaccine offer.
                        }
                        jandjToGiveOffer = 0;   // Reset number of users to give offers to 0.
                        userInd = userInd - availableJandJ;
                    }

                    /* If user can be granted a vaccine offer. */
                    if(offer == false) {
                        jandjToGiveOffer++; // Increment number of user's that can be given offers
                        given_offers.add(user); // Add user to a list of those granted offers
                        given_offers_userLocations.add(userLocation);   // Add user's location to the locations list
                    }
                    currentUser = user;
                }

                else if(text.equalsIgnoreCase("Pfizer")){
                    String fname =  UserName();
                    String lname = UserLastName();
                    String id = UserID();
                    String phone = UserPhone();
                    String passw = UserPassword();
                    User user = new User(fname, lname, id, phone, passw);
                    PfizerVaccine pBooking = new PfizerVaccine(user);
                    pBooking.AddUser(user); // Add user to Pfizer booking waitlist
                    setVaccine("Pfizer Vaccine");    // Set vaccine identification name to Pfizer
                    vaccineWaitlistName("Pfizer Vaccine");  // Set waitlist to view as the Pfizer list
                    offer = pBooking.greaterThanAvailable(user);    // Checks if current users in booking list > than available vaccine
                    userInd = pfizerBookedUsers.indexOf(user);  // Store user's index in booking list

                    /* If user can't be given an offer (number of users booked now > available vaccines) */
                        if(offer == true) {
                            for(int i = 0; i < pfizerToGiveOffer; i++) {
                                pBooking.getUsers().remove(pBooking.getUsers().get(i)); // Remove from waitlist users that can be given a vaccine offer.
                            }
                            pfizerToGiveOffer = 0;  // Reset number of users to give offers to 0.
                            userInd = userInd - availablePfizer;
                        }

                    /* If user can be granted a vaccine offer. */
                        if(offer == false) {
                            pfizerToGiveOffer++;     // Increment number of user's that can be given offers
                            given_offers.add(user); // Add user to a list of those granted offers
                            given_offers_userLocations.add(userLocation);   // Add user's location to the locations list
                        }

                    currentUser = user;

                }

                else if(text.equalsIgnoreCase("Flu")){
                    String fname =  UserName();
                    String lname = UserLastName();
                    String id = UserID();
                    String phone = UserPhone();
                    String passw = UserPassword();
                    User user = new User(fname, lname, id, phone, passw);
                    InfluenzaVaccine iBooking = new InfluenzaVaccine(user);
                    iBooking.AddUser(user); // Add user to Influenza booking waitlist
                    setVaccine("Influenza Vaccine");    // Set vaccine identification name to Influenza
                    vaccineWaitlistName("Influenza vaccine");   // Set waitlist to view as the Influenza list
                    offer = iBooking.greaterThanAvailable(user);    // Checks if current users in booking list > than available vaccine
                    userInd = influenzaBookedUsers.indexOf(user);    // Store user's index in booking list

                    /* If user can't be given an offer (number of users booked now > available vaccines) */
                    if(offer == true) {
                        for(int i = 0; i < influenzaToGiveOffer; i++) {
                            iBooking.getUsers().remove(iBooking.getUsers().get(i)); // Remove from waitlist users that can be given a vaccine offer
                        }
                        influenzaToGiveOffer = 0;   // Reset number of users to give offers to 0.
                        userInd = userInd - availableInfluenza;

                    }

                    /* If user can be granted a vaccine offer. */
                    if(offer == false) {
                        influenzaToGiveOffer++; // Increment number of user's that can be given offers
                        given_offers.add(user); // Add user to a list of those granted offers
                        given_offers_userLocations.add(userLocation);   // Add user's location to the locations list
                    }

                    currentUser = user;
                }
                bookingSuccess(); // Toast for successful booking.
            }
        });

        /* When clicked, either checks for granted vaccine offer or displays back to the user their position on the vaccine waitlist booked for. */
        posButton = (Button) findViewById(R.id.viewWaitlistButton);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(offer == true) {
                    if(vaccine.equalsIgnoreCase("JJ Vaccine")) {
                        viewPosition(userInd);
                    }
                    else if(vaccine.equalsIgnoreCase("Pfizer Vaccine")) {
                        viewPosition(userInd);
                    }
                    else if(vaccine.equalsIgnoreCase("Influenza Vaccine")) {
                        viewPosition(userInd);
                    }

                }
                else{
                    hasOffer();
                    //openOffer();
                }

            }
        });
    }

    /* Toast for successful booking upon clicking 'book' button */
    public void bookingSuccess(){
            Toast.makeText(VaccineWaitlist.this,"Booking successful!",Toast.LENGTH_SHORT).show();
    }

    /* User's firstname obtained when sigining in into system. */
    private String UserName() {
        Intent intent = getIntent();
        return intent.getStringExtra("user_firstname");
    }

    /* User's lastname obtained when sigining in into system. */
    private String UserLastName() {
        Intent intent = getIntent();
        return intent.getStringExtra("user_lastname");
    }

    /* User's ID number obtained when sigining in into system. */
    private String UserID() {
        Intent intent = getIntent();
        return intent.getStringExtra("user_id_number");
    }

    /* User's phone number obtained when sigining in into system. */
    private String UserPhone() {
        Intent intent = getIntent();
        return intent.getStringExtra("user_phone_number");
    }

    /* User's password obtained when sigining in into system. */
    private String UserPassword() {
        Intent intent = getIntent();
        return intent.getStringExtra("user_password");
    }

    /* Sets vaccine identification name according to selection made. */
    public void setVaccine(String vacc) {
        vaccine = vacc;
    }

    /* Returns vaccine booked for. */
    public String getVaccine() {
        return vaccine;
    }

    /* Returns distinct vaccine waitlist */
    public void vaccineWaitlistName(String s) {
        if(s.equalsIgnoreCase("influenza vaccine")){
            listToCheck = influenzaBookedUsers;
        }

        else if(s.equalsIgnoreCase("jj vaccine")) {
            listToCheck = jAndjBookedUsers;
        }

        else if(s.equalsIgnoreCase("pfizer vaccine")) {
            listToCheck = pfizerBookedUsers;
        }
    }

    /* Gives a user information about the position they are in from the vaccine waitlist booked for. */
    private void viewPosition(int userIndex) {
        AlertDialog dialog = new AlertDialog.Builder(VaccineWaitlist.this)
                .setTitle("Vaccine Waitlist Position")
                .setMessage("Your current position on the " + getVaccine() + " waitlist is " + (userIndex +1) + ".")
                .setPositiveButton(
                        "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    /* Notifies user of vaccination offer granted to them. */
    private void hasOffer() {
        AlertDialog dialog = new AlertDialog.Builder(VaccineWaitlist.this)
                .setTitle("Offer Granted")
                .setMessage("You have been granted with a vaccine offer for your booking. Click 'OK' below.")
                .setPositiveButton(
                        "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openOffer();
                            }
                        })
                .create();
        dialog.show();
    }

    /* Opens 'Offer' activity for user to accept or reject offer. */
    private void openOffer() {
        Intent intent = new Intent(this, Offers.class);
        startActivity(intent);
    }
    
    
    private void offerNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                VaccineWaitlist.this
        )
                 .setSmallIcon(R.drawable.ic_message)
                 .setContentTitle("New notification")
                 .setContentText("You have been granted an offer for your " + getVaccine() + " booking.")
                . setAutoCancel(true);

        Intent intent = new Intent(VaccineWaitlist.this,
                Offers.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(VaccineWaitlist.this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        notificationManager.notify(0, builder.build());

    }
}