package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MedicalWorkerRegistration extends AppCompatActivity {

    private Button c_submitBtn, c_signIn;
    TextInputEditText cName, cAddress, tel_phone, a_password;
    static ArrayList<CentreLocation> centre_locations = new ArrayList<CentreLocation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_worker_registration);

        /* Buttons on admin's registration activity */
        c_submitBtn = (Button) findViewById(R.id.centre_submitButton);
        c_signIn = (Button) findViewById(R.id.centre_signinButton);


        /* Admin's data information input fields */
        cName = (TextInputEditText) findViewById(R.id.centre_nameInput);
        cAddress = (TextInputEditText) findViewById(R.id.centreAddressInput);
        tel_phone = (TextInputEditText) findViewById(R.id.tel_numberInput);
        a_password = (TextInputEditText) findViewById(R.id.centre_passwordInput);

        /* Open admin's login activity upon clicking of sign in button. */
        c_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMedicalSignIn();
            }
        });

        /* Register admin upon clicking of submit button. */
        c_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VaccineWasteMitigationApp firebase_db = new VaccineWasteMitigationApp("admin"); // Reference to admins on external database

                String centre_name = cName.getText().toString();    // Centre name from input field
                String centre_Address = cAddress.getText().toString();  // Centre address from input field
                String telNo = tel_phone.getText().toString();  // Centre telephone number from input field
                String adminPassword = a_password.getText().toString(); // Centre password from input field

                CentreAdmin user = new CentreAdmin(centre_name,centre_Address, telNo,adminPassword);    // New centre instance
                CentreLocation centreLocation = new CentreLocation(centre_name, telNo, centre_Address, adminPassword);  // New centre instance
                centre_locations.add(centreLocation);   // Add centre location to list of centre locations
                firebase_db.getRef().child(centre_name).setValue(user); // New database entry of vaccination centre
                registrationCheck();    // Toast for successful registration
                openMedicalSignIn();    // Open admin sign in activity
            }
        });

    }

    // Open admin sign in activity
    public void openMedicalSignIn(){
        Intent intent = new Intent(this, MedicalWorkerSignIn.class);
        startActivity(intent);
    }

    // Toast for successful registration
    public void registrationCheck(){
        Toast.makeText(MedicalWorkerRegistration.this,"Registration successful!", Toast.LENGTH_SHORT).show();
    }

}