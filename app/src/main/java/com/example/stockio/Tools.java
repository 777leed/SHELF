package com.example.stockio;

import static android.content.ContentValues.TAG;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Tools {
    private static final String TAG ="In case" ;
    public FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    public void deletefrmdatabase(String namebased) {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final FirebaseUser users = mAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(namebased)){
            databaseReference.child(resultemail).child("Product").child(namebased).removeValue();
        }
        else
            Log.i(TAG, "Error In delete from database");

    }

    public void getUserInfo(TextView n, TextView d,TextView e){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
        assert usernameinfirebase != null;
        String UserID=usernameinfirebase.getEmail();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assert UserID != null;
        String resultemail = UserID.replace(".","");
        DatabaseReference myRef = database.getReference("Users").child(resultemail).child("UserDetails");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User value = dataSnapshot.getValue((User.class));
                assert value != null;

                String retrievedDept = value.deptname.toUpperCase() + " DEPARTEMENT";
                String retrievedName =value.fname.toUpperCase() ;
                d.setText(retrievedDept);
                n.setText(retrievedName);
                if(e != null){
                    e.setText(UserID);
                }
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

}
