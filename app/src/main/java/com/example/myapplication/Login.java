package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //declaring textviews, edittexts to get data from them
    public TextView register;
    public TextView resetPass;
    public TextView login;
    public EditText mEmail,mPassword;
    public FirebaseAuth fAuth; //calling firebase authentication class and creating it's object
    ProgressBar pb2; //progress bar object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //giving id's of the corresponding fields that we declared earlier
        register= findViewById(R.id.signup);
        pb2=findViewById(R.id.pb2);


        //adding click listener on register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user clicks on register button, it starts new activity named user registeration where he can register himself
                startActivity(new Intent(Login.this,UserRegistration.class));

            }
        });

        //setting id of resetpassword text view to variable resetpass
        resetPass= findViewById(R.id.resetPassword);

        //adding click listener on reset password text view
        resetPass.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user taps on reset password text view, he will be directed to registration page
                startActivity(new Intent(Login.this,ResetPassword.class));
            }
        }));


        //alocating login button id to login button type object
        login = findViewById(R.id.loginbtn);

        //allocating edittexts for email and password to the edittext type object
        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPass);
        fAuth = FirebaseAuth.getInstance();   //getting instance of firebase, it is entry point for authentication in database

        //setting click listener on login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when button is pressed make progress bar visible
                pb2.setVisibility(View.VISIBLE);

                //getting email and passwords from edittexts and converting them into string
                //because default value from a textfield is int
               String email= mEmail.getText().toString().trim();  //trim used to save data in proper format in database
               String password= mPassword.getText().toString().trim();
               String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

               //adding restriction to make sure user don't use blank fields to login
                if(TextUtils.isEmpty(email))
                {
                    pb2.setVisibility(View.INVISIBLE);
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pb2.setVisibility(View.INVISIBLE);
                    mPassword.setError("Password is required.");
                    return;
                }


                // it authenticates login user whether he is already registered or not
                //by taking his entered email and password
                fAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //complete listener checks if task is completed successfully or not
                                if(task.isSuccessful())
                                {

                                    //if task is successfull disappear progress bar and start
                                    // new activity named locationbotttomnavigation
                                    pb2.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Login.this,LocationBottomNavigation.class));

                                }
                                else
                                {
                                    //else make progress bar invisible and show toast
                                    //user is invalid
                                    pb2.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Login.this, "Invalid User/Credentials" , Toast.LENGTH_LONG).show();
                                }
                            }
                        });


             //  add restrictions to login
                if(email.matches(emailPattern))
                {
                    return;
                }
                else
                {
                    pb2.setVisibility(View.INVISIBLE);
                    mEmail.setError("Please enter a valid email");
                }

            }
        });
    }
}

