package com.example.myapplication;


//model class to get set values for email and password to pass them to firebase
public class User {

    String email, password;

    public User(String email, String password)
    {
        this.email=email;
        this.password=password;
    }


}
