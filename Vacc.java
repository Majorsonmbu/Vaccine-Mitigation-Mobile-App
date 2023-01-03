package com.example.vaccinewastemitigationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Vacc extends AppCompatActivity {

    //initialise variables
    EditText vaccineName;
    EditText quantity;
    EditText expiringTime;
    ListView listView;
    private Button buttonAdd, verifyUsersBtn;

    String targetVaccine;
    static int pfizerVaccQuantity;
    static int jandjVaccQuantity;
    static int influenzaVaccQuantity;

    static int pfizerExpiry;
    static int jandjExpiry;
    static int influenzaExpiry;


    static ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacc);

        // Assign variables
        vaccineName = findViewById(R.id.vaccine_name);
        quantity = findViewById(R.id.vaccine_quantity);
        expiringTime = findViewById(R.id.expiring_date);
        buttonAdd = findViewById(R.id.button_add);
        listView = findViewById(R.id.list_view);
        verifyUsersBtn = findViewById(R.id.verifyBtn);


        verifyUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVerificationList();
            }
        });

        //initialize adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);


        listView.setAdapter(adapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaccineWasteMitigationApp firebaseDb = new VaccineWasteMitigationApp("vaccineList");  // Refer to vaccineList section on external database


                String VaccineName = vaccineName.getText().toString();  // vaccine name/type
                String Quantity =quantity.getText().toString(); // vaccine quantity
                String ExpiringDate = expiringTime.getText().toString(); // vaccine expiry time

                VaccinesDb vac = new VaccinesDb(VaccineName,Quantity,ExpiringDate);  // create instance of VaccineDb
                firebaseDb.getRef().child(VaccineName).setValue(vac); // New database entry of vaccine
                String vaccInfo = VaccineName + " " + Quantity;
                String time = ExpiringDate;
                arrayList.add("Name: "+VaccineName+" "+"Quantity: "+Quantity+" "+"Expiry time: "+ExpiringDate); // add vaccine, quantity and expiry date to the list
                VaccineAdded(); // shows toast message

                // clear the editTexts
                vaccineName.setText("");
                quantity.setText("");
                expiringTime.setText("");

                // take quantity of the vaccine
                int firstSpace = vaccInfo.indexOf(" ");
                if(vaccInfo.substring(0,vaccInfo.indexOf(" ")).equalsIgnoreCase("influenza")) {
                    influenzaVaccQuantity = Integer.parseInt(vaccInfo.substring(firstSpace+1));
                    influenzaExpiry = Integer.parseInt(time);

                }
                else if(vaccInfo.substring(0,vaccInfo.indexOf(" ")).equalsIgnoreCase("pfizer")) {
                    pfizerVaccQuantity = Integer.parseInt(vaccInfo.substring(firstSpace+1));
                    pfizerExpiry = Integer.parseInt(time);
                }
                else if(vaccInfo.substring(0,vaccInfo.indexOf(" ")).equalsIgnoreCase("jandj")) {
                    jandjVaccQuantity = Integer.parseInt(vaccInfo.substring(firstSpace+1));
                    jandjExpiry = Integer.parseInt(time);
                }

            }
        });
    }

    /* Opens the user verification activity for the admin. */
    public void openVerificationList() {
        Intent intent = new Intent(this, MedicalWorkerWaitlist.class);
        startActivity(intent);
    }

    /* Toast message for vaccine entry added. */
    public void VaccineAdded(){
        Toast.makeText(Vacc.this,"Vaccine Added!", Toast.LENGTH_SHORT).show();
    }

}
