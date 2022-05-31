package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allProducts extends AppCompatActivity {
    public FirebaseAuth mAuth;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Product> list;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(allProducts.this,R.color.lightblue));
        recyclerView = findViewById(R.id.recycl);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
        assert usernameinfirebase != null;
        String UserID=usernameinfirebase.getEmail();
        assert UserID != null;
        String resultemail = UserID.replace(".","");
        database = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){

//                    Product product = ds.getValue(Product.class);
//                    assert product != null;
                    Product product = new Product(
                            ds.child("idP").getValue(String.class),
                            ds.child("nameP").getValue(String.class),
                            ds.child("price").getValue(String.class),
                            ds.child("quantity").getValue(String.class),
                            ds.child("category").getValue(String.class));
                    list.add(product);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}