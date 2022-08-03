package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {
    public EditText mFirstName,mLastName,mEmail,mPhone,mPassword;
    public Button mbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        mFirstName = findViewById(R.id.editTextTextPersonName);
        mLastName= findViewById(R.id.editTextTextPersonName2);
        mEmail= findViewById(R.id.editTextemail);
        mPhone= findViewById(R.id.editTextPhone);
        mPassword= findViewById(R.id.editTextNumberPassword2);
        progressBar= findViewById(R.id.progressBar);
        mbtn = findViewById(R.id.regbtn);
        db = FirebaseFirestore.getInstance();
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname= mFirstName.getText().toString().trim();
                String lname= mLastName.getText().toString().trim();
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String phone= mPhone.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                progressBar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(fname))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    mFirstName.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(lname))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    mLastName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(email) || !email.matches(emailPattern))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    mEmail.setError("Valid email is required.");
                    return;
                }
                if(TextUtils.isEmpty(pass) || (pass.length() < 6))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError("Password must be 6 or more digits");
                    return;
                }

                if(TextUtils.isEmpty(phone) || phone.length()<11)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    mPhone.setError("Enter a valid phone number");
                    return;
                }



                // for storing user data in firebase firestore
                Map<String, String> data = new HashMap<>();
                data.put("First Name", fname);
                data.put("Last Name", lname);
                data.put("Email", email);
                data.put("Password", pass);
                data.put("Contact", phone);

                db.collection("Users").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
                    }
                });

                // for user authentication i.e. user status in application
                fAuth = FirebaseAuth.getInstance();
                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(UserRegistration.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email, pass);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(UserRegistration.this,new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserRegistration.this, "User Registered", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(getApplicationContext(), MainActivity1.class));
                                    } else {
                                        Toast.makeText(UserRegistration.this, "Error, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(UserRegistration.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });


            }
        });




            }




        }



