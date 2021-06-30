package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    TextView fundPrice,fundInterest,fundTenure;
    View v;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imgsinglview);
        textView=itemView.findViewById(R.id.tvcopname);
        fundPrice=itemView.findViewById(R.id.fundPice_db);
        fundInterest=itemView.findViewById(R.id.fundInterest_db);
        fundTenure=itemView.findViewById(R.id.fundTenure_db);
        v=itemView;
    }
}
