package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.sambhav2358.tinydb.TinyDB;
import com.sambhav2358.tinydb.TinyDefaultDB;


import java.util.ArrayList;
import java.util.List;

public class Stats extends AppCompatActivity {

    private static final String TAG ="In case" ;
    public TextView erase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Stats.this,R.color.lightblue));
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        erase = findViewById(R.id.reset);



        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(Stats.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        TinyDefaultDB tinyDB;
        tinyDB = TinyDB.getInstance().getDefaultDatabase(Stats.this);
        List<DataEntry> data = new ArrayList<>();
        List<datastat> d = new ArrayList<>();
        d= tinyDB.getList("data",null);
        System.out.println(d);
        for (datastat dd : d){
            data.add(new ValueDataEntry(dd.name, dd.value));
            System.out.println("777" + dd.name + dd.value);
        }
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            data.clear();
            tinyDB.clearAll();
                List<datastat> d = new ArrayList<>();
                tinyDB.putList("data",d);
                erase.setVisibility(View.GONE);
                Intent myIntent = new Intent(Stats.this, Dashboard.class);
                startActivity(myIntent);
            }
        });




        pie.data(data);

        pie.title("Most Sold Products");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Products")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

}