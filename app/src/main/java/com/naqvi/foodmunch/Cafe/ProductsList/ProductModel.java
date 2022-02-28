package com.naqvi.foodmunch.Cafe.ProductsList;

public class ProductModel {
    public String Id;
    public String ParentId;
    public String Title;
    public String Price;
    public String Description;
    public String Image;

    public ProductModel(String id, String parentId, String title, String price, String description, String image) {
        Id = id;
        ParentId = parentId;
        Title = title;
        Price = price;
        Description = description;
        Image = image;
    }

    public ProductModel() {
    }
}
