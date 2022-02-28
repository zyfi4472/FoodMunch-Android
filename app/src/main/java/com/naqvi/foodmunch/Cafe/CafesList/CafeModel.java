package com.naqvi.foodmunch.Cafe.CafesList;

public class CafeModel {
    public String Id;
    public String Name;
    public String Description;
    public String Phone;
    public String Image;
    public String isVerified;

    public CafeModel(String id, String name, String description, String phone, String image, String isVerified) {
        Id = id;
        Name = name;
        Description = description;
        Phone = phone;
        Image = image;
        this.isVerified = isVerified;
    }

    public CafeModel() {
    }
}
