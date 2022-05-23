package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {


    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Dashboard.this,R.color.lightblue));
        getUserInfo();

    }
    public void getUserInfo(){

        final TextView nameDep = (TextView)findViewById(R.id.ndep);
        final TextView fullName = (TextView)findViewById(R.id.fullname);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
        assert usernameinfirebase != null;
        String UserID=usernameinfirebase.getEmail();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assert UserID != null;
        String resultemail = UserID.replace(".","");
        DatabaseReference myRef = database.getReference("Users").child(resultemail).child("UserDetails");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue((User.class));
                assert value != null;

                String retrievedDept = value.deptname.toUpperCase() + " DEPARTEMENT";
                String retrievedName =value.fname.toUpperCase() ;
                nameDep.setText(retrievedDept);
                fullName.setText(retrievedName);
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