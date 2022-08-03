package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationBottomNavigation extends AppCompatActivity {
    SupportMapFragment supportMapFragment; // declaring mapfragment variable to show map on activity
    Button getlocation; // declaring button to get location of person from  raspberry pi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_bottom_navigation);

        //declaring bottom navigation bar and button and allocating their id's to the variables
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        getlocation= findViewById(R.id.getloc);

        //allocating mapfragment variable the id of fragment that is on xml file
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        //adding click listener on get location button
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when user clicks on refresh button the toast will be showed and he will
                //be redirected to the next activity that is liveloc
                //where he can see the live location of person from raspberry pi
                Toast.makeText(LocationBottomNavigation.this, "Please wait, while system fetches location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),liveloc.class));

            }
        });


        // setting action when location is selected, funtionality is for bottom navigation
        bnv.setSelectedItemId(R.id.location);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.location:
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.address:
                        startActivity(new Intent(getApplicationContext(), AddressPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(),LogoutPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}