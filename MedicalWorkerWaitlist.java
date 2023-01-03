package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import static com.example.vaccinewastemitigationapp.Offers.offer_accepted_users;


import java.util.ArrayList;

public class MedicalWorkerWaitlist extends AppCompatActivity {

    ListView listView;  // Listview for user's who accepted offers for verification purposes.
    //SearchView searchView;  // Searchview for searching a particular user in the list of those accepted their offers.


    ArrayAdapter<String> arrayAdapter;
    ArrayList<User> offered_users = offer_accepted_users;   // All users having accepted their offers stored here.
    ArrayList<String> all_offered_users_details = new ArrayList<String>(); // Stores a string of users' firstname, lastname & id to be displayed on listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_worker_waitlist);

       // searchView = (SearchView) findViewById(R.id.search_bar);
        listView = (ListView) findViewById(R.id.verificationList);

        /* Extracting users' firstname, lastname & id for display purposes. */
        for(int i = 0 ; i < offered_users.size(); i++) {
            String userDetails = offered_users.get(i).getfName() + " " + offered_users.get(i).getlName() + " ID: " + offered_users.get(i).getId();
            all_offered_users_details.add(userDetails);
        }

        /* Adding the listview to an array adapter for display. */
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,all_offered_users_details);
        listView.setAdapter(arrayAdapter);

    }

}