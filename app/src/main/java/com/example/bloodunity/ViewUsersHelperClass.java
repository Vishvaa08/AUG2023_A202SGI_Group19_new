package com.example.bloodunity;

public class ViewUsersHelperClass {

    String first_name;
    String last_name;
    String user_picture;
    String bloodType;
    String points;

    public ViewUsersHelperClass(String first_name, String last_name, String bloodType, String user_picture, String points) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.bloodType = bloodType;
        this.user_picture = user_picture;
        this.points = points;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name(){return last_name;}

    public void setLast_name(String lastName) {this.last_name = lastName;}

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getPoints(){return points;}

    public void setPoints(String points){this.points = points;}

    ViewUsersHelperClass(){

    }

}
