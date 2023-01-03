package com.example.vaccinewastemitigationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button regButton;   // User register button
    private Button signinBtn;   // User sign in button
    // Initialize variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        /* Open user registration activity upon clicking of register button. */
        regButton = (Button) findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        });

        /* Open user sign in activity upon clicking of sign in button. */
        signinBtn = (Button) findViewById(R.id.signInButton);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignIn();
            }
        });

    }


    /* For opening the user's sign in activity. */
     public void openSignIn() {
         Intent intent = new Intent(this, SignIn.class);
         startActivity(intent);
     }

     /* For opening the user's registration activity. */
    public void openRegistration() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }


    /* Menu icon which when pressed opens the navigation drawer */
    public void ClickMenu(View view){ // Clicking menu icon
        // Open drawer
        openDrawer(drawerLayout);
    }

    /* Clicking logo on navigation drawer closes the drawer */
    public void ClickLogo(View view){
        // Close drawer
        closeDrawer(drawerLayout);
    }


    private static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        // Close drawer layout
        // Check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            // Whrn drawer is open
            // close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /* Clicking home on navigation drawer takes one back to the home page */
    public void ClickHome(View view){
        // Recreate activity
        recreate();
    }

    /* When clicked on navigation drawer,shows a Person's profile currently signed in to the app */
    public void ClickProfile(View view){
        // Redirect activity to Profile
        redirectActivity(this, Profile.class);
    }

    /* Allows medical workers to sign in as the app's admin to access admin related functionalities */
    public void ClickAdmin(View view){
        // Start medical worker registration activity
        redirectActivity(this, MedicalWorkerRegistration.class);
    }

    /* Opens 'about' activity for the application. */
    public void ClickAboutUs(View view){

        redirectActivity(this, AboutUs.class);
    }

    private static void redirectActivity(Activity activity, Class aClass) {
        // Initialize intent
        Intent intent = new Intent(activity,aClass);
        // Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Start activity
        activity.startActivity(intent);
    }

}