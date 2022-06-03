package com.example.stockio;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
        Switcher();

    }
    public void getUserInfo(){
        final TextView fullNamep = (TextView)findViewById(R.id.prname);
        final TextView nameDepp = (TextView)findViewById(R.id.prdep);
        final TextView premail = (TextView)findViewById(R.id.premail);
        Tools t = new Tools();
        t.getUserInfo(fullNamep,nameDepp,premail);

    }
    public void Switcher() {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        ViewSwitcher switcher1 = (ViewSwitcher) findViewById(R.id.secondswitcher);
        ViewSwitcher switcher2 = (ViewSwitcher) findViewById(R.id.thirdswitcher);
        switcher.showNext(); //or switcher.showPrevious();

        EditText edittele = (EditText) switcher.findViewById(R.id.hidden_edit_tele);
        TextView txttele = (TextView) switcher.findViewById(R.id.tele);

        EditText editemail = (EditText) switcher1.findViewById(R.id.hidden_edit_email);
        TextView txtemail = (TextView) switcher1.findViewById(R.id.premail);

        EditText editloc = (EditText) switcher.findViewById(R.id.hidden_edit_location);
        TextView txtloc = (TextView) switcher.findViewById(R.id.loc);

    }

    public void updateProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
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

    }



}

