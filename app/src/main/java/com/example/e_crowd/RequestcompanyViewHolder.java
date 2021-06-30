package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RequestcompanyViewHolder extends RecyclerView.ViewHolder {
    ImageView imagerView;
    TextView textrView;
    TextView fundrPrice,fundrInterest,fundrTenure;
    View rv;

    public RequestcompanyViewHolder(@NonNull View itemView) {
        super(itemView);
        imagerView=itemView.findViewById(R.id.re_imgsinglview);
        textrView=itemView.findViewById(R.id.re_tvcopname);
        fundrPrice=itemView.findViewById(R.id.re_fundPice_db);
        fundrInterest=itemView.findViewById(R.id.re_fundInterest_db);
        fundrTenure=itemView.findViewById(R.id.re_fundTenure_db);
        rv=itemView;
    }
}
