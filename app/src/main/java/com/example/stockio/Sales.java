package com.example.stockio;

import com.anychart.chart.common.dataentry.DataEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


