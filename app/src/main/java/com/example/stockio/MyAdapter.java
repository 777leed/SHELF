package com.example.stockio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        Product product = list.get(holder.getAdapterPosition());
        holder.firstName.setText(product.getNameP());
        holder.lastName.setText(product.getQuantity());
        holder.age.setText(product.getPrice());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            private void showPositionx(int position) {
                Toast.makeText(context, "Long Clicked and Position is " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public boolean onLongClick(View v) {
                Product product = list.get(holder.getAdapterPosition());
                Tools t = new Tools();
                t.deletefrmdatabase(product.getNameP());
                adapter.notifyItemRemoved(holder.getAdapterPosition());
                return true;
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
