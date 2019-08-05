package com.e.alcchallenge2.Model;

public class ProductList {
    public   String productid;
    public String productname;
    public   String productprice;
    public String imageuri;
    public String productdscrip;

    public ProductList(String productname, String productprice, String imageuri, String productdscrip, String productid) {
        this.productname = productname;
        this.productprice = productprice;
        this.imageuri = imageuri;
        this.productdscrip = productdscrip;
        this.productid= productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public ProductList() {
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getProductdscrip() {
        return productdscrip;
    }

    public void setProductdscrip(String productdscrip) {
        this.productdscrip = productdscrip;
    }
}
