package com.example.vaccinewastemitigationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    Button loginBtn;
    EditText usernameEditText, u_signIn_password;
    static String userName;
    static String userLastname;
    static String userID;
    static String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        loginBtn = (Button) findViewById(R.id.loginButton);
        usernameEditText = (EditText)  findViewById(R.id.usernameField);
        u_signIn_password = (EditText) findViewById(R.id.SignInPassword);

        /* Upon clicking, sign in user to the system. */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    /* Opens vaccine waitlist booking activity. */
    public void openBooking() {
        Intent intent = new Intent(this,VaccineWaitlist.class);
        startActivity(intent);
    }

    /* Toast message for successful authorization sign in */
    public void authorization() {
        Toast.makeText(SignIn.this,"Authorization successful!",Toast.LENGTH_SHORT).show();
    }

    /* Check validity of username entered by user. */
    private Boolean validateUserUsername(){
        String val = usernameEditText.getText().toString();

        if(val.isEmpty()){
            usernameEditText.setError("Field cannot be empty");
            return false;
        }
        else{
            usernameEditText.setError(null);
            return true;
        }
    }

    /* Check validity of password entered by user. */
    private Boolean validateUserPassword(){
        String val = u_signIn_password.getText().toString();

        if(val.isEmpty()) {
            u_signIn_password.setError("Field cannot be empty.");
            return false;
        }
        else{
            u_signIn_password.setError(null);
            return true;
        }
    }

    /* Validate and sign in user to the system. */
    private void loginUser() {
        if(!validateUserUsername() | !validateUserPassword()) {
            return;
        }
        else{
            isUser();
        }
    }

    /* Checks existence of user on database */
    private void isUser() {
        String enteredUserUsername = usernameEditText.getText().toString().trim();  // User's username from input field
        String enteredUserPassword = u_signIn_password.getText().toString().trim(); // User's password from input field

        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("fName").equalTo(enteredUserUsername);      // if error come change here (use 'fName')*/

        VaccineWasteMitigationApp firebase_db = new VaccineWasteMitigationApp("users"); // Reference to users on external database
        Query checkUser = firebase_db.getRef().orderByChild("fName").equalTo(enteredUserUsername);  // Check username on database

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* If username exists on database */
                if(snapshot.exists()) {
                    String passwordFromDB = snapshot.child(enteredUserUsername).child("password").getValue(String.class);   // Grab corresponding user password
                    usernameEditText.setError(null);    // Entered user name valid, don't set error to username field

                    if(passwordFromDB.equals(enteredUserPassword)) {    // entered username and password correct
                        u_signIn_password.setError(null);

                        /* Get more user information to update 'Profile' page */
                        String user_idFromDB = snapshot.child(enteredUserUsername).child("id").getValue(String.class);  // // Grab corresponding user id
                        String user_phoneFromDB = snapshot.child(enteredUserUsername).child("phone").getValue(String.class);    // Grab corresponding user phone number
                        String user_lastnameFromDB = snapshot.child(enteredUserUsername).child("lName").getValue(String.class);  // Grab corresponding user lastname

                        /* Pass user information to 'Profile' activity.*/
                        Intent intent = new Intent(getApplicationContext(),Profile.class);
                        intent.putExtra("user_firstname",enteredUserUsername);
                        intent.putExtra("user_id_number", user_idFromDB);
                        intent.putExtra("user_phone_number",user_phoneFromDB);
                        intent.putExtra("user_lastname",user_lastnameFromDB);

                        userName = enteredUserUsername; // Assign user's fistname
                        userLastname = user_lastnameFromDB; // Assign user's lastname
                        userID = user_idFromDB; // Assign user's ID number
                        userPhone = user_phoneFromDB; // Assign user's phone number

                        /* Pass user information to 'VaccineWaitlist' activity.*/
                        Intent secondIntent = new Intent(getApplicationContext(), VaccineWaitlist.class);
                        secondIntent.putExtra("user_firstname", enteredUserUsername);
                        secondIntent.putExtra("user_lastname", user_lastnameFromDB);
                        secondIntent.putExtra("user_id_number", user_idFromDB);
                        secondIntent.putExtra("user_phone_number", user_phoneFromDB);
                        secondIntent.putExtra("user_password", enteredUserPassword);


                        //startActivity(intent);
                        // if correct open 'Book for vaccine' page for the user
                        authorization();
                        openBooking();
                    }
                    else{
                        u_signIn_password.setError("Wrong Password. Try again");     // Prompt to re-enter user password
                        u_signIn_password.requestFocus();   // Highlight focus on password field
                    }
                }
                else{   // username doesn't exist
                    usernameEditText.setError("No such user exists.");   // Prompt to re-enter username
                    usernameEditText.requestFocus();    // / Highlight focus on username field
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}