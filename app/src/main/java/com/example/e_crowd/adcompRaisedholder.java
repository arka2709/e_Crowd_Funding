package com.example.e_crowd;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class adcompRaisedholder extends RecyclerView.ViewHolder {

    TextView adrhRaised,adrhdate,adrhtime,adrhemail,adrhname;



    public adcompRaisedholder(@NonNull View itemView) {
        super(itemView);
        adrhRaised=itemView.findViewById(R.id.adcmptotalRaisedspc);
        adrhdate=itemView.findViewById(R.id.adcmptrdDatespc);
        adrhtime=itemView.findViewById(R.id.adcmptrdTimespc);
        adrhemail=itemView.findViewById(R.id.adcmpemailspc);
        adrhname=itemView.findViewById(R.id.adcmpNamespc);



    }
}
