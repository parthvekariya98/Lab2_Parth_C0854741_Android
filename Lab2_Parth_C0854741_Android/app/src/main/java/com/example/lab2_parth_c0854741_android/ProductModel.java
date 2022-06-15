package com.example.lab2_parth_c0854741_android;

public class ProductModel {
    private String productId;
    private String productName;
    private String productDescription;
    private String productPrice;

    //product model constructor
    public ProductModel(String productId, String productName, String productDescription, String productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    //product details setter methods
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }
}
