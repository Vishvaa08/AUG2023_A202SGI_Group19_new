package com.example.bloodunity;

import android.widget.Spinner;

public class RegisterHelperClass {

    String first_name;
    String last_name;
    String email;
    String password;
    String address;
    String bloodType;
    String user_picture;

    public RegisterHelperClass(String name, String bloodType, String dayAttending) {
    }

    public RegisterHelperClass(Spinner bloodPicker) {

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getUser_picture() {
        return user_picture;
    }


    public RegisterHelperClass(String first_name, String last_name, String email, String password, String address, String bloodType, String user_picture) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.bloodType = bloodType;
        this.user_picture = user_picture;
    }
    public RegisterHelperClass(){

    }
}
