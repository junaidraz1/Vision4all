package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class liveloc extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveloc);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.liveloc);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        FirebaseDatabase fbd = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fbd.getReference("Location");

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //hashmap to get values and keys from database
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                //storing value in object because map has object type
                Object lat= map.get("Latitude");
                Object longi =map.get("Longitude");

                //no direct conversion from object to double that is why first converted into string
                String s=lat.toString();
                String s2 = longi.toString();

                //storing values of lat long into latlng class to draw them on google maps
                LatLng loc = new LatLng(Double.valueOf(s),Double.valueOf(s2)); // then into double

                //plotting it on map
                mMap.addMarker(new MarkerOptions().position(loc).title("Person is here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,18F)); // zoom into the map

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bnv.setSelectedItemId(R.id.location);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.location:
                        startActivity(new Intent(getApplicationContext(), LocationBottomNavigation.class));
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