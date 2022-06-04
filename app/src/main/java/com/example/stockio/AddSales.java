package com.example.stockio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.time.LocalDateTime;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Objects;

public class AddSales extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextnom;
    private EditText editTextsold;
    public TextView sold;
    DatabaseReference databaseReference;
    public static int idsale= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(AddSales.this,R.color.lightblue));
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        editTextnom = findViewById(R.id.addsalesProduct_name);
        editTextsold = findViewById(R.id.editText_add_sales);
        sold = findViewById(R.id.salesproduct);

        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSales();
                Toast.makeText(AddSales.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addSales() {
        String namebased = editTextnom.getText().toString();
        int soldbased = Integer.parseInt(editTextsold.getText().toString());

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(namebased)&&soldbased>0){
//
            DatabaseReference myRef = databaseReference.child(resultemail).child("Product").child(namebased);
            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Product product = new Product(
                                task.getResult().child("idP").getValue(String.class),
                                task.getResult().child("nameP").getValue(String.class),
                                task.getResult().child("price").getValue(String.class),
                                task.getResult().child("quantity").getValue(String.class),
                                task.getResult().child("category").getValue(String.class),
                                "0");
                        int retrievedquantity = Integer.parseInt(product.getQuantity());
                        if (retrievedquantity >= soldbased){
                            product.quantity= Integer.toString(retrievedquantity - soldbased);
                            product.sales= Integer.toString(Integer.parseInt(Objects.requireNonNull(task.getResult().child("sales").getValue(String.class)))  + soldbased) ;
                            databaseReference.child(resultemail).child("Product").child(namebased).setValue(product);
                            Sales s = new Sales(idsale++,product,java.time.LocalDateTime.now());
                            databaseReference.child(resultemail).child("sales").child(namebased).setValue(s);
                            Log.d("Done", String.valueOf(task.getResult().getValue()));
                        }
                        else{

                            Log.d("Error", String.valueOf(task.getResult().getValue()));
                        }

                    }
                }
            });


        }
    }
}