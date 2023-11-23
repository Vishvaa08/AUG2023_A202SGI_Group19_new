package com.example.bloodunity;

public class viewBloodHelperClass {

    String donorName;
    String donorAge;
    String bloodGroup;
    String hospital;

    public viewBloodHelperClass(String donorName, String donorAge, String bloodGroup, String hospital) {
        this.donorName = donorName;
        this.donorAge = donorAge;
        this.bloodGroup = bloodGroup;
        this.hospital = hospital;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorAge() {
        return donorAge;
    }

    public void setDonorAge(String donorAge) {
        this.donorAge = donorAge;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getHospital(){
        return hospital;
    }

    public void setHospital(String hospital){
        this.hospital = hospital;
    }
    viewBloodHelperClass(){

    }

}
