package com.example.stockio;

import java.time.LocalDateTime;
import java.util.Date;

public class Sales {
  public  int Idsale;
    public   Product product;
    public LocalDateTime datesale;

    public Sales() {
    }

    public Sales(int IdSale, Product product,  LocalDateTime datesale) {
        this.Idsale = IdSale;
        this.product = product;
        this.datesale = datesale;
    }
}


