package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CartHolder extends RecyclerView.ViewHolder {
    TextView tvnm,tvpr,tvdatedb,tvtimedb,tvtenure,tvinterest;
    ImageView odimage;
    View v1;
    public CartHolder(@NonNull View itemView) {
        super(itemView);
        tvnm=itemView.findViewById(R.id.compname);
        tvpr=itemView.findViewById(R.id.tvp);
        tvtenure=itemView.findViewById(R.id.tv_tenure_db);
        tvinterest=itemView.findViewById(R.id.tv_interest_db);
        odimage=itemView.findViewById(R.id.order_image);
        v1=itemView;

    }
}
