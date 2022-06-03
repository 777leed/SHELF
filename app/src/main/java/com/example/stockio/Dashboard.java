package com.example.stockio;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getUserInfo();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Dashboard.this,R.color.lightblue));
        RelativeLayout pr =(RelativeLayout)findViewById(R.id.db_profile);
        RelativeLayout vp =(RelativeLayout)findViewById(R.id.view_products);
        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this, profil.class);
                startActivity(myIntent);
            }
        });

        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this, allProducts.class);
                startActivity(myIntent);
            }
        });

        LinearLayout bg = (LinearLayout) findViewById(R.id.bg);
        LinearLayout dp = (LinearLayout) findViewById(R.id.deldash);
        RelativeLayout aq = (RelativeLayout) findViewById(R.id.rl_add_quantity);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this, addProduct.class);
                startActivity(myIntent);
            }
        });
        aq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this, addQuantity.class);
                startActivity(myIntent);
            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this,DeleteProduct.class);
                startActivity(myIntent);
            }
        });
    }
    public void getUserInfo(){

        final TextView nameDep = (TextView)findViewById(R.id.ndep);
        final TextView fullName = (TextView)findViewById(R.id.fullname);
        Tools t = new Tools();
        t.getUserInfo(fullName,nameDep,null);

    }
}