package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {
    public Button resetbtn;
    public EditText emailresetTxt;
    FirebaseAuth fAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        emailresetTxt = findViewById(R.id.resetPasswordEmail);
        resetbtn=findViewById(R.id.resetLinkbtn);
        fAuth=FirebaseAuth.getInstance();
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailresetTxt.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this, "Check your email to reset password " , Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                           // Toast.makeText(ResetPassword.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
                if(email.isEmpty()){
                    emailresetTxt.setError("Email is required");
                    emailresetTxt.requestFocus();
                }
                if(email.matches(emailPattern))
                {
                    return;
                }
                else
                    {
                    emailresetTxt.setError("Please enter a valid email");
                    emailresetTxt.requestFocus();
                }

            }
        });

        }

    }

