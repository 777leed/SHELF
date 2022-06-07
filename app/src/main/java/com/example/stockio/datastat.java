package com.example.stockio;

import java.io.Serializable;

public class datastat implements Serializable {
    public String name;
    public int value;


    public datastat(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
