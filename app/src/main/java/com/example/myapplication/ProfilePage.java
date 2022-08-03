package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {
    Button updatebtn ;
    EditText proFname,proLname,proEmail,proPhone,proPass;
    FirebaseFirestore updatedb;
//    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        proFname = findViewById(R.id.fnameProfileEditText);
        proLname = findViewById(R.id.lnameProfileEditText);
        proEmail = findViewById(R.id.EmailProfileEditText);
        proPhone = findViewById(R.id.phoneProfileEditText);
        proPass = findViewById(R.id.passwordProfileEditText);
        updatebtn = findViewById(R.id.btnProfileUpdate);
        updatedb = FirebaseFirestore.getInstance();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profname= proFname.getText().toString().trim();
                String prolname= proLname.getText().toString().trim();
                String proemail = proEmail.getText().toString();
                String propass = proPass.getText().toString();
                String prophone= proPhone.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                HashMap<String, Object> hm = new HashMap<>();
                hm.put("First Name", profname);
                hm.put("Last Name", prolname);
                hm.put("Email", proemail);
                hm.put("Password", propass);
                hm.put("Contact", prophone);

                 updatedb.collection("Users").document().update(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(getApplicationContext()," Profile Updated ", Toast.LENGTH_LONG).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getApplicationContext()," Error Updating Profile ", Toast.LENGTH_SHORT).show();
                     }
                 });



                if(!proemail.matches(emailPattern))
                {
                    proEmail.setError("Valid email is required.");
                    return;
                }
                if((propass.length() < 6))
                {
                    proPass.setError("Password must be 6 or more digits");
                    return;
                }

                if(prophone.length()<11) {
                    proPhone.setError("Enter a valid phone number");
                    return;
                }

            }
        });


        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);


        // setting action when address selected
        bnv.setSelectedItemId(R.id.profile);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.location:
                        startActivity(new Intent(getApplicationContext(),LocationBottomNavigation.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.address:
                        startActivity(new Intent(getApplicationContext(),AddressPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
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












