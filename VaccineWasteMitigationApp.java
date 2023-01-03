package com.example.vaccinewastemitigationapp;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VaccineWasteMitigationApp  {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String refName;

    public VaccineWasteMitigationApp(String ref) {
        rootNode = FirebaseDatabase.getInstance("https://save-vaccidose-default-rtdb.firebaseio.com/");
        reference = rootNode.getReference(ref);
    }

    public DatabaseReference getRef() {
        return reference;
    }


}
