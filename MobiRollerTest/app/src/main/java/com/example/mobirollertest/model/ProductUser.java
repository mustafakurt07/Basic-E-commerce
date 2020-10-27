package com.example.mobirollertest.model;

public class ProductUser {
    private  String productId;
    private  String productName;
    private  String productKategory;
    private  String productPrice;
    private  String productDate;
    private  String productDesc;
    private  String productImage;

    public ProductUser() {
    }

    public ProductUser(String productId, String productName, String productKategory, String productPrice, String productDate, String productDesc, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productKategory = productKategory;
        this.productPrice = productPrice;
        this.productDate = productDate;
        this.productDesc = productDesc;
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKategory() {
        return productKategory;
    }

    public void setProductKategory(String productKategory) {
        this.productKategory = productKategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
