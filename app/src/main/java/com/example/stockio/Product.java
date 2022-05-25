package com.example.stockio;

public class Product {
    public String idP, nameP,price,quantity;

//    public Product(){
//
//    }

    public Product(String idP, String nameP,String price,String quantity) {
        this.idP = idP;
        this.nameP = nameP;
        this.price= price;
        this.quantity= quantity;

    }

    public String getIdP() {
        return idP;
    }

    public String getNameP() {
        return nameP;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
