package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addQuantity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextnom;
    private EditText editTextqte;
    public TextView add;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quantity);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        editTextnom = findViewById(R.id.addqteProduct_name);
        editTextqte = findViewById(R.id.editText_add_qte);
        add = findViewById(R.id.qteproduct);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQtev1();
                Toast.makeText(addQuantity.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addQtev1() {
        String namebased = editTextnom.getText().toString();
        int qtebased = Integer.parseInt(editTextqte.getText().toString());

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(namebased)&&qtebased>0){
//
           DatabaseReference myRef = databaseReference.child(resultemail).child("Product").child(namebased);
            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                                task.getResult().child("category").getValue(String.class));
                        int retrievedquantity = Integer.parseInt(product.getQuantity());
                        product.quantity= Integer.toString(retrievedquantity + qtebased);
                        databaseReference.child(resultemail).child("Product").child(namebased).setValue(product);
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    }
                }
            });


        }
    }

}