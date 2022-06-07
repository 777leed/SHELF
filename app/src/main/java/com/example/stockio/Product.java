package com.example.stockio;

import android.widget.EditText;

import java.util.Locale;

public class Product {
    public String idP, nameP,price,quantity,category,sales;


//    public Product(){
//
//    }

    public Product(String idP, String nameP, String price, String quantity, String category, String sales) {

        this.idP = idP;
        this.nameP = nameP;
        this.price= price;
        this.quantity= quantity;
        this.category= category;
        this.sales= sales;

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
    public String getCategory() {
        return category;
    }


}
