package com.example.stockio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sambhav2358.tinydb.TinyDB;
import com.sambhav2358.tinydb.TinyDefaultDB;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddSales extends AppCompatActivity {
   public FirebaseAuth firebaseAuth;
    private EditText editTextnom;
    private EditText editTextsold;
    public TextView sold;
    DatabaseReference databaseReference;
    public static int idsale= 0;
    ArrayList<datastat> datastats = new ArrayList<>();
    FirebaseUser users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        firebaseAuth = FirebaseAuth.getInstance();
        users = firebaseAuth.getCurrentUser();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(AddSales.this,R.color.lightblue));
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
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(namebased)&&soldbased>0){
//
            DatabaseReference myRef = databaseReference.child(resultemail);
            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Product product = new Product(
                                task.getResult().child("Product").child(namebased).child("idP").getValue(String.class),
                                task.getResult().child("Product").child(namebased).child("nameP").getValue(String.class),
                                task.getResult().child("Product").child(namebased).child("price").getValue(String.class),
                                task.getResult().child("Product").child(namebased).child("quantity").getValue(String.class),
                                task.getResult().child("Product").child(namebased).child("category").getValue(String.class),
                                "0");
                        int retrievedquantity = Integer.parseInt(product.getQuantity());


                        if (retrievedquantity >= soldbased){
                            TinyDefaultDB tinyDB;
                            tinyDB = TinyDB.getInstance().getDefaultDatabase(AddSales.this);
                            datastats=tinyDB.getList("data",null);
                            if (!datastats.isEmpty()){
                                for( int i = 0; i < datastats.size(); i++ )
                                {
                                    datastat lValue = datastats.get( i );
                                    if(lValue.name.equals(product.nameP))
                                    {
                                        datastats.remove(lValue);
                                        i--;
                                    }
                                }
                            }

                            product.quantity= Integer.toString(retrievedquantity - soldbased);
                            int newvalue =Integer.parseInt(Objects.requireNonNull(task.getResult().child("Product").child(namebased).child("sales").getValue(String.class)))+ + soldbased;
                            product.sales= Integer.toString(newvalue) ;
                            databaseReference.child(resultemail).child("Product").child(namebased).setValue(product);
                            Sales s = new Sales(idsale++,product,java.time.LocalDateTime.now());
                            databaseReference.child(resultemail).child("sales").setValue(s);
                            Log.d("Done", String.valueOf(task.getResult().getValue()));

                            datastats.add(new datastat(product.nameP, Integer.parseInt(product.sales)));
                            tinyDB.putList("data",datastats); // and a lot more formats to save




//
//                            try
//                            {
//
//                                File myData = new File(getFilesDir(), "data.bin");
//                                FileOutputStream out = new FileOutputStream(myData);
//                                ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(out));
//                                oout.writeObject(datastats);
//                                oout.close();
//                                oout.close();
//                            }
//                            catch (IOException ioe)
//                            {
//                                ioe.printStackTrace();
//                                Log.d("Error de serialization", String.valueOf(task.getResult().getValue()));
//                            }
                        }
                        else{

                            Log.d("Error 789", String.valueOf(task.getResult().getValue()));
                        }
                        }


                    }

            });


        }
    }

//    public void getStats(List<datastat> data){
//      data = new ArrayList<>();
//
//        try
//        {
//            File myData = new File(getFilesDir(), "data.bin");
//            FileInputStream fis = new FileInputStream(myData);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            data = (ArrayList<datastat>) ois.readObject();
//
//            ois.close();
//            fis.close();
//        }
//        catch (IOException ioe)
//        {
//            ioe.printStackTrace();
//            System.out.println("Erreur f desert");
//            return;
//        }
//        catch (ClassNotFoundException c)
//        {
//            System.out.println("Class not found");
//            c.printStackTrace();
//            return;
//        }
//
//        //Verify list data
//        for (datastat d : data) {
//            System.out.println(d);
//        }
//    }




    }





