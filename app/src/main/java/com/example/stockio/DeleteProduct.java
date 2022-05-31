package com.example.stockio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteProduct extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextnom;
    public TextView delProBtn;
    DatabaseReference databaseReference;
    Task<Void> ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        editTextnom = findViewById(R.id.delProduct);
        delProBtn = findViewById(R.id.del_product);

        delProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdatabase();
                Toast.makeText(DeleteProduct.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });


            }



    private void deletefrmdatabase() {
            String namebased = editTextnom.getText().toString();
            final FirebaseUser users = firebaseAuth.getCurrentUser();
            String finaluser=users.getEmail();
            String resultemail = finaluser.replace(".","");
            if(!TextUtils.isEmpty(namebased)){
               databaseReference.child(resultemail).child("Product").child(namebased).removeValue();
            }
            else
                Toast.makeText(DeleteProduct.this,"Insert name",Toast.LENGTH_SHORT).show();

    }
}

