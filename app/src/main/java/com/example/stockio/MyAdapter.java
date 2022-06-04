package com.example.stockio;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.content.ContextCompat.getDrawable;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Product> list;
    MyAdapter adapter;

    public MyAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Product product = list.get(holder.getAdapterPosition());
        holder.firstName.setText(product.getNameP());
        holder.lastName.setText(product.getQuantity());
        holder.age.setText(product.getPrice());
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                Product product = list.get(holder.getAdapterPosition());
//                Tools t = new Tools();
//                t.deletefrmdatabase(product.getNameP());
//                adapter.notifyItemRemoved(holder.getAdapterPosition());
//                return true;
//            }
//
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog_layout);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(context,R.drawable.custom_dialog_background));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                dialog.show();


                Button Okay = dialog.findViewById(R.id.btn_okay);
                Button Cancel = dialog.findViewById(R.id.btn_cancel);

                Okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product product = list.get(holder.getAdapterPosition());
                        Tools t = new Tools();
                        t.deletefrmdatabase(product.getNameP());
                        Toast.makeText(context, "Product Is Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent myactivity = new Intent(context.getApplicationContext(), Dashboard.class);
                        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(myactivity);
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });



            }


        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, age;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvfirstName);
            lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.tvage);

        }
    }

}
