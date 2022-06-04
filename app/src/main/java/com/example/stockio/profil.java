package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(profil.this,R.color.lightblue));
        getUserInfo();
        TextView edit = (TextView) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Switcher();
            }
        });


    }
    public void getUserInfo(){
        final TextView fullNamep = (TextView)findViewById(R.id.prname);
        final TextView nameDepp = (TextView)findViewById(R.id.prdep);
        final TextView premail = (TextView)findViewById(R.id.premail);
        Tools t = new Tools();
        t.getUserInfo(fullNamep,nameDepp,premail);
        TextView txtloc = (TextView) findViewById(R.id.loc);
        TextView txtemail = (TextView) findViewById(R.id.premail);
        TextView txttele = (TextView) findViewById(R.id.tele);
        t.getAdditional(txtloc,txttele,txtemail);



    }

    public void Switcher() {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        ViewSwitcher switcher1 = (ViewSwitcher) findViewById(R.id.secondswitcher);
        ViewSwitcher switcher2 = (ViewSwitcher) findViewById(R.id.thirdswitcher);

        EditText edittele = (EditText) switcher.findViewById(R.id.hidden_edit_tele);
        TextView txttele = (TextView) switcher.findViewById(R.id.tele);

        EditText editemail = (EditText) switcher1.findViewById(R.id.hidden_edit_email);
        TextView txtemail = (TextView) switcher1.findViewById(R.id.premail);

        EditText editloc = (EditText) switcher2.findViewById(R.id.hidden_edit_location);
        TextView txtloc = (TextView) switcher2.findViewById(R.id.loc);

        TextView editbtn = (TextView) findViewById(R.id.editbtn);
        editbtn.setVisibility(View.VISIBLE);
        switcher.showNext();
        switcher1.showNext();
        switcher2.showNext();
        edittele.setText(txttele.getText().toString());
        editloc.setText(txtloc.getText().toString());
        String txtw = txtemail.getText().toString();
        editemail.setText(txtw);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(editemail.getText().toString(),edittele.getText().toString(),editloc.getText().toString());
                finish();
                startActivity(getIntent());
                Tools t = new Tools();
                t.getAdditional(txttele,txtloc,txtemail);
                switcher.showPrevious();
                switcher1.showPrevious();
                switcher2.showPrevious();
            }
        });


    }

    public void updateProfile(String e,String n,String loc) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://peakyblinders99.online/wp-content/uploads/2021/03/Thomas-Shelby0-2.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

        user.updateEmail(e)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
        String UserID=user.getEmail();
        String resultemail = UserID.replace(".","");
        Additional ad = new Additional(e,n,loc);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(resultemail).child("Additional Info")
                .setValue(ad);


    }



}

