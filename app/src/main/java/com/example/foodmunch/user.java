package com.example.foodmunch;

public class user {
    public String fullName, email, phone, password;

    public user(){

    }

    public user(String ownerName, String cafeName, String email, String phonenumber, String password){

    }

    public user(String fullName, String email, String phone, String password){
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
