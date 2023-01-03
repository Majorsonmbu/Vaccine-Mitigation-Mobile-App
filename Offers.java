package com.example.vaccinewastemitigationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import static com.example.vaccinewastemitigationapp.VaccineWaitlist.given_offers;
import static com.example.vaccinewastemitigationapp.VaccineWaitlist.currentUser;
import static com.example.vaccinewastemitigationapp.VaccineWaitlist.given_offers_userLocations;
import static com.example.vaccinewastemitigationapp.MedicalWorkerRegistration.centre_locations;
import static com.example.vaccinewastemitigationapp.VaccineWaitlist.vaccine;
import static com.example.vaccinewastemitigationapp.Vacc.pfizerExpiry;
import static com.example.vaccinewastemitigationapp.Vacc.influenzaExpiry;
import static com.example.vaccinewastemitigationapp.Vacc.jandjExpiry;

import static com.example.vaccinewastemitigationapp.VaccineWaitlist.ppExpiry;
import static com.example.vaccinewastemitigationapp.VaccineWaitlist.iiExpiry;
import static com.example.vaccinewastemitigationapp.VaccineWaitlist.jjExpiry;


import java.util.ArrayList;

public class Offers extends AppCompatActivity {

    Button accBtn, rejBtn;  // Offer 'accept' or 'reject' buttons
    User theUser;           // Current user given offer
    String vaccineOffered = vaccine;    // Vaccine given offer for
    TextView offerMessage;              // Vaccine offer message

    static int pExpiry = pfizerExpiry;
    static int iExpiry = influenzaExpiry;       // BUYELA KULEZI !!!!!!!!!!!!!!!!!!111
    static int jExpiry = jandjExpiry;

    static ArrayList<User> users_given_offers = given_offers;   // Users given offers while booking stored locally here.
    static ArrayList<UserLocation> users_given_offers_locations = given_offers_userLocations;   // Users given offers have their locations stored here.

    static ArrayList<User> offer_accepted_users = new ArrayList<User>();    // Users to accept offer to be stored here

    static ArrayList<CentreLocation> centreLocations = centre_locations;    // Vaccination centres locations stored here for user's convenience

    static int pppExpiry = ppExpiry;
    static int iiiExpiry = iiExpiry;
    static int jjjExpiry = jjExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        offerMessage = (TextView) findViewById(R.id.offerText);
        offerMessage.setText("You currently have an offer for your " +  vaccine  + " booking. Please accept the offer by clicking the buttons below:");

        rejBtn = (Button) findViewById(R.id.rejectButton);
        rejBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMain();
            }
        });

        theUser = currentUser;
        accBtn = (Button) findViewById(R.id.acceptButton);
        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexInList = users_given_offers.indexOf(theUser);  // Get user's index in array list.
                UserLocation curr_userLocation = users_given_offers_locations.get(indexInList); // User's location
                offer_accepted_users.add(theUser);  // Add user to those who accept offers

                for(CentreLocation location : centreLocations) {
                    String targetCentre;
                    String targetLocation;

                    /* If user's suburb matches the vaccination centre location */
                    if(curr_userLocation.getSuburb().equalsIgnoreCase(location.getCentreAddress()))
                    {
                        targetCentre = location.getCentreName();
                        targetLocation = curr_userLocation.getSuburb();

                        if(vaccineOffered.equalsIgnoreCase("influenza vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, iiiExpiry);  // Set appointment to include user's suburb
                        }
                        else if(vaccineOffered.equalsIgnoreCase("pfizer vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, pppExpiry);  // Set appointment to include user's suburb
                        }
                        else if(vaccineOffered.equalsIgnoreCase("jj vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, jjjExpiry);  // Set appointment to include user's suburb
                        }
                    }

                    /* If user's town matches the vaccination centre location */
                    else if(curr_userLocation.getTown().equalsIgnoreCase(location.getCentreAddress()))
                    {
                        targetCentre = location.getCentreName();
                        targetLocation = curr_userLocation.getTown();

                        if(vaccineOffered.equalsIgnoreCase("influenza vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, iiiExpiry);  // Set appointment to include user's town
                        }
                        else if(vaccineOffered.equalsIgnoreCase("pfizer vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, pppExpiry);  // Set appointment to include user's town
                        }
                        else if(vaccineOffered.equalsIgnoreCase("jj vaccine")) {
                            appointmentDetails(targetCentre, targetLocation, jjjExpiry);  // Set appointment to include user's town
                        }

                    }
                }
                offerAccepted();
            }
        });
    }


    /* Method that has user's vaccination appointment details message */

    private void appointmentDetails(String centre, String loc, int exp) {
        AlertDialog dialog = new AlertDialog.Builder(Offers.this)
                .setTitle("Appointment Details")
                .setMessage("Your " + vaccineOffered + " appointment is scheduled at " + centre + " centre in " + loc + " within " + exp + "hours before vaccination goes to waste.")
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

    /* Open splash screen */
    public void openMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /* Toast for successful rejection of offer. */
    public void offerRejected() {
        Toast.makeText(Offers.this,"Offer rejected!", Toast.LENGTH_SHORT).show();
    }

    /* Toast for successful rejection of offer. */
    public void offerAccepted() {
        Toast.makeText(Offers.this,"Offer accepted!", Toast.LENGTH_SHORT).show();
    }

}