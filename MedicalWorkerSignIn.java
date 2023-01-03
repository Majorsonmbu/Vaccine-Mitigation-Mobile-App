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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MedicalWorkerSignIn extends AppCompatActivity {

    private Button loginBtn;    // Admin's login button
    private EditText usernameEditText, a_signIn_password;   // Admin's username and password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_worker_sign_in);

        loginBtn = (Button) findViewById(R.id.loginButton);
        usernameEditText = (EditText) findViewById(R.id.usernameField);
        a_signIn_password = (EditText) findViewById(R.id.SignInPassword);

        /* Log existing admin into application upon clicking of login button. */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAdmin();;
            }
        });
    }

    /* Opens activity where admin can perform admin related tasks. */
    public void openMenuPage() {
        Intent intent = new Intent(this,Vacc.class);
        startActivity(intent);
    }

    /* Toast for successful logging in into the application. */
    public void authorization() {
        Toast.makeText(MedicalWorkerSignIn.this,"Authorization successful!", Toast.LENGTH_SHORT).show();
    }

    /* Check validity of username entered by admin. */
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

    /* Check validity of password entered by admin. */
    private Boolean validateUserPassword(){
        String val = a_signIn_password.getText().toString();

        if(val.isEmpty()) {
            a_signIn_password.setError("Field cannot be empty.");
            return false;
        }
        else{
            a_signIn_password.setError(null);
            return true;
        }
    }

    /* Sign the admin into the system, performing validity of login details entered. */
    private void loginAdmin() {
        if(!validateUserUsername() | !validateUserPassword()) {
            return;
        }
        else{
            isAdmin();
        }
    }

    /* Sign the admin into the system, performing validity of login details entered. */
    private void isAdmin() {
        String enteredCentre = usernameEditText.getText().toString().trim();    // Centre name as username
        String enteredCentrePassword = a_signIn_password.getText().toString().trim();   // Centre password


        VaccineWasteMitigationApp firebase_db = new VaccineWasteMitigationApp("admin"); // Refer to admins section on external database
        Query checkUser = firebase_db.getRef().orderByChild("cName").equalTo(enteredCentre);    // Query database using centre name
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* If centre name exists on database */
                if(snapshot.exists()) {
                    String passwordFromDB = snapshot.child(enteredCentre).child("password").getValue(String.class); // Grab corresponding centre password
                    usernameEditText.setError(null);    // Entered centre name valid, don't set error to username field

                    if(passwordFromDB.equals(enteredCentrePassword)) {    // entered username and password correct
                        a_signIn_password.setError(null);

                        // if correct both password and centre name open 'Add to Menu Page' page for the admin
                        authorization();
                        openMenuPage();
                    }
                    else{   // Centre password incorrect
                        a_signIn_password.setError("Wrong Password. Try again");    // Prompt to re-enter centre password
                        a_signIn_password.requestFocus();   // Highlight focus on password field
                    }
                }
                else{   // Username or centre name doesn't exist
                    usernameEditText.setError("No such user exists.");  // Prompt to re-enter centre name
                    usernameEditText.requestFocus();    // Highlight focus on username field
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
