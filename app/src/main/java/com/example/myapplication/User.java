package com.example.myapplication;


//model class to get set values for email and password to pass them to firebase
public class User {

    String email, password;

    public User(String email, String password)
    {
        this.email=email;
        this.password=password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
