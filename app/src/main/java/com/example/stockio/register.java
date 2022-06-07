package com.example.stockio;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.sambhav2358.tinydb.TinyDB;
import com.sambhav2358.tinydb.TinyDefaultDB;

import java.util.ArrayList;

public class register extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone,editTextcPassword, editTextfullName;
    public TextView UserRegisterBtn;
    private ProgressBar progressBar;
    ArrayList<datastat> datastats = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(register.this,R.color.lightblue));
        editTextName = findViewById(R.id.dep_name);
        editTextfullName = findViewById(R.id.input_name);
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_password);
        editTextcPassword= findViewById(R.id.reinput_password);
        UserRegisterBtn= findViewById(R.id.continue_signup);

//        editTextPhone = findViewById(R.id.edit_text_phone);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        //  findViewById(R.id.button_register).setOnClickListener(this);

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }
//    public void addStudent(){
//        String studentNameValue = editTextName.getText().toString();
//        String mcneeseIdValue = editTextEmail.getText().toString();
//        if(!TextUtils.isEmpty(studentNameValue)&&!TextUtils.isEmpty(mcneeseIdValue)){
//            String id = FirebaseDatabase.getInstance().getReference("Users").push().getKey();
//            User students = new User(studentNameValue,mcneeseIdValue);
//            // databaseReference.child(bttnName.getText().toString()).push().setValue(students);
//            FirebaseDatabase.getInstance().getReference("Users").setValue(students);
//            editTextName.setText("");
//            editTextEmail.setText("");
//            Toast.makeText(register.this,"Student Details Added",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(register.this,"Please Fill Fields",Toast.LENGTH_SHORT).show();
//        }
//    }


    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString();
        final String fname = editTextfullName.getText().toString();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextcPassword.getText().toString().trim();
        // final String phone = editTextPhone.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("It's empty");
            editTextEmail.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            editTextName.setError("It's Empty");
            editTextName.requestFocus();
            return;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Not a valid emailaddress");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Its empty");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Less length");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            editTextcPassword.setError("Passwords doesn't Match");
            editTextcPassword.requestFocus();
            return;
        }

//        if (phone.isEmpty()) {
//            editTextPhone.setError(getString(R.string.input_error_phone));
//            editTextPhone.requestFocus();
//            return;
//        }
//
//        if (phone.length() != 10) {
//            editTextPhone.setError(getString(R.string.input_error_phone_invalid));
//            editTextPhone.requestFocus();
//            return;
//        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            TinyDefaultDB tinyDB; //in your class

                            tinyDB = TinyDB.getInstance().getDefaultDatabase(register.this); // the context does not need be activity context
                            tinyDB.putList("data",datastats); // and a lot more formats to save
                            final User user = new User(name, email,fname);
                            //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            //important to retrive data and send data based on user email
                            FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
                            String UserID=usernameinfirebase.getEmail();
                            // String result = UserID.substring(0, UserID.indexOf("@"));
                            String resultemail = UserID.replace(".","");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(resultemail).child("UserDetails")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(register.this, "Registration is Successful", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(register.this,Dashboard.class));
                                        finish();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }




//    //Set UserDisplay Name
//    private void userProfile()
//    {
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user!= null)
//        {
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(editTextName.getText().toString().trim())
//                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
//                    .build();
//
//            user.updateProfile(profileUpdates)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//
//                            }
//                        }
//                    });
//        }
//    }



}

