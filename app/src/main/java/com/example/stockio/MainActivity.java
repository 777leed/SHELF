package com.example.stockio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.lightblue));
    }
    public void gotologin(View view)
            {
                Intent intent = new Intent(this, login_s.class);
                startActivity(intent);
             }


    public void gotosignup(View view)
        {
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        }
}