package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class investmentholder extends RecyclerView.ViewHolder {
    ImageView ihimg;
    TextView ihname,ihemail,ihinterest,ihinvestment,ihtenure,ihdate,ihmatdate,ihmatamnt;


    public investmentholder(@NonNull View itemView) {
        super(itemView);
        ihimg=itemView.findViewById(R.id.ivilimg);
        ihname=itemView.findViewById(R.id.ilacnamespc);
        ihemail=itemView.findViewById(R.id.ilacemailspc);
        ihinterest=itemView.findViewById(R.id.ilainterestspc);
        ihinvestment=itemView.findViewById(R.id.ilainvestmentspc);
        ihtenure=itemView.findViewById(R.id.ilatenurespc);
        ihdate=itemView.findViewById(R.id.iladatespc);
        ihmatdate=itemView.findViewById(R.id.ilamatudatespc);
        ihmatamnt=itemView.findViewById(R.id.ilamatuamtspc);
    }
}
