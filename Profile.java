package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.vaccinewastemitigationapp.SignIn.userName;
import static com.example.vaccinewastemitigationapp.SignIn.userLastname;
import static com.example.vaccinewastemitigationapp.SignIn.userID;
import static com.example.vaccinewastemitigationapp.SignIn.userPhone;

public class Profile extends AppCompatActivity {
TextView profName, profID, profPhone, profUsernameHeader;
String name = userName; // Current user firstname (from sign in)
String lastname = userLastname; // Current user lastname (from sign in)
String id = userID; // Current user ID number (from sign in)
String phone = userPhone;   // Current user phone number (from sign in)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Text place holders for currently signed in user */
        profName = (TextView)  findViewById(R.id.profileUsername);
        profID = (TextView) findViewById(R.id.profileID);
        profPhone = (TextView)  findViewById(R.id.profilePhone);
        profUsernameHeader = (TextView)  findViewById(R.id.profileUsernameHeader);

        showUserData();
    }

    private void showUserData() {
        Intent intent = getIntent();
        String user_fName = intent.getStringExtra("user_firstname");
        String user_lName = intent.getStringExtra("user_lastname");
        String user_fullname = user_fName + " " + user_lName;
        String user_phone = intent.getStringExtra("user_phone_number");
        String user_id = intent.getStringExtra("user_id_number");

        profName.setText(name);
        profID.setText(id);
        profPhone.setText(phone);
        String full = name + " " + lastname;
        profUsernameHeader.setText(full);
    }
}