package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

CheckBox ageCheckbox;
private Button submitBtn;
private Button backBtn;
TextInputEditText u_fName, u_lName, u_idNo, u_phone, u_password;

FirebaseDatabase rootNode;
DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /* Age checkbox & submit and back buttons on user's registration page */
        ageCheckbox = (CheckBox) findViewById(R.id.checkBox);
        submitBtn = (Button) findViewById(R.id.submitButton);
        backBtn = (Button) findViewById(R.id.backButton);

        /* User's data/personal information input fields */
        u_fName = (TextInputEditText) findViewById(R.id.nameInput);
        u_lName = (TextInputEditText) findViewById(R.id.surnameInput);
        u_idNo = (TextInputEditText) findViewById(R.id.idInput);
        u_phone = (TextInputEditText) findViewById(R.id.phoneInput);
        u_password = (TextInputEditText) findViewById(R.id.passwordInput);

        /* Register user upon clicking of submit button. */
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ageCheckbox.isChecked()){

                    VaccineWasteMitigationApp firebase_db = new VaccineWasteMitigationApp("users"); // Reference to users on external database

                    String name = u_fName.getText().toString(); // User's firstname from input field
                    String surname = u_lName.getText().toString();  // User's lastname from input field
                    String u_id = u_idNo.getText().toString();  // User's id from input field
                    String cellNo = u_phone.getText().toString();   // User's phone number from input field
                    String userPassword = u_password.getText().toString();  // User's firstname from input field

                    /* If entered user id number and phone number is valid/correct format */
                    if(validateID(u_id) && validateCellNumber(cellNo)) {
                        User user = new User(name,surname,u_id,cellNo,userPassword);    // Create new user instance
                        firebase_db.getRef().child(name).setValue(user);    // Add / push user to database
                        registrationCheck();
                        openSignIn();
                    }

                    else{
                        if(!validateID(u_id) && validateCellNumber(cellNo)) {
                            u_idNo.setError("ID should contain 13 digits.");
                            u_idNo.requestFocus();
                        }
                        else if(!validateCellNumber(cellNo) && validateID(u_id)) {
                            u_phone.setError("Phone should contain 10 digits.");
                            u_phone.requestFocus();
                        }
                        else if(!validateID(u_id) && !validateCellNumber(cellNo)) {
                            u_idNo.setError("ID should contain 13 digits.");
                            u_idNo.requestFocus();

                            u_phone.setError("Phone should contain 10 digits.");
                            u_phone.requestFocus();
                        }
                    }


                }
                else{
                        checkboxToast();
                }
            }
        });

        /* Take user back to splash screen */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySplash();
            }
        });
    }

    /* Open user's sign in activity */
    public void openSignIn(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    /* Open splash screen activity */
    public void displaySplash() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /* Toast message to ensure age checkbox is checked. */
    public void checkboxToast(){
        Toast.makeText(Registration.this,"Click the checkbox to verify you are 18 years or older.",Toast.LENGTH_LONG).show();
    }

    /* Toast message for successful registration */
    public void registrationCheck(){
        Toast.makeText(Registration.this,"Registration successful!",Toast.LENGTH_SHORT).show();
    }

    /* Validate entered user ID number */
    private Boolean validateID(String id){
        String val = id;

        if(val.length() > 13 || val.length() < 13) {
           // u_idNo.setError("ID should contain 13 digits.");
            return false;
        }
        else{
            u_idNo.setError(null);
            return true;
        }
    }

    /* Validate user phone number */
    private Boolean validateCellNumber(String cellNumber){
        String val = cellNumber ;

        if(val.length() > 10 || val.length() < 10) {
           // u_phone.setError("Phone should contain 10 digits.");
            return false;
        }
        else{
            u_phone.setError(null);
            return true;
        }
    }


}