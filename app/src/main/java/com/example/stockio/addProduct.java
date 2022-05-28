package com.example.stockio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class addProduct extends AppCompatActivity {
    private EditText editTextId, editTextName, editTextPrice, editTextQte;
    public TextView addProBtn;
//    private FirebaseAuth mAuth;
    DatabaseReference productDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(addProduct.this,R.color.lightblue));
        editTextId = findViewById(R.id.product);
        editTextName = findViewById(R.id.input_name);
        editTextPrice = findViewById(R.id.input_price);
        editTextQte = findViewById(R.id.reinput_qte);
        addProBtn = findViewById(R.id.btn_add_product);
//        mAuth = FirebaseAuth.getInstance();
        productDbRef=FirebaseDatabase.getInstance().getReference().child("Product");

        addProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }

        });
//        @Override
//        protected void onStart() {
//            super.onStart();
//
//            if (mAuth.getCurrentUser() != null) {
//                //handle the already login user
//            }
//        }






        ImageView imgview = (ImageView) findViewById(R.id.btn_back);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(addProduct.this, Dashboard.class);
                startActivity(myIntent);
            }
        });


    }
    private void addItem() {
        String id = editTextId.getText().toString().trim();
        String Pname = editTextName.getText().toString();
        String price = editTextPrice.getText().toString();
        String qte = editTextQte.getText().toString().trim();
        // final String phone = editTextPhone.getText().toString().trim();
        if (id.isEmpty()) {
            editTextId.setError("It's empty");
            editTextId.requestFocus();
            return;
        }
        if (Pname.isEmpty()) {
            editTextName.setError("It's Empty");
            editTextName.requestFocus();
            return;
        }



        if (price.isEmpty()) {
            editTextPrice.setError("Its empty");
            editTextPrice.requestFocus();
            return;
        }
        if (qte.isEmpty()) {
            editTextQte.setError("Its empty");
            editTextQte.requestFocus();
            return;
        }
//        Product product = new Product(id,Pname,price,qte);
//        productDbRef.push().setValue(product);
//        Toast.makeText(addProduct.this, "Data Inserted", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(addProduct.this,addProduct.class));

//        final Product product = new Product(id,Pname,price,qte);
//        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
//        String productID=usernameinfirebase.getEmail();
//        String resultemail = productID.replace(".","");
//        FirebaseDatabase.getInstance().getReference("Product")
//                .child(resultemail).child("ProductDetails")
//                .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()) {
//                    Toast.makeText(addProduct.this, "Registration is Successful", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(addProduct.this,addProduct.class));
//                    finish();
//                } else {
//                    //display a failure message
//                }
//     }

 //       });
    }
}