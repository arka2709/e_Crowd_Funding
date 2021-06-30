package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class vericompanies extends RecyclerView.ViewHolder {

    TextView vcoName,vcoprice,vcointerest,vcoduration;
    ImageView vcoimg;




    public vericompanies(@NonNull View itemView) {
        super(itemView);
        vcoName=itemView.findViewById(R.id.vfc_tvcopname);
        vcoprice=itemView.findViewById(R.id.vfc_fundPice_db);
        vcointerest=itemView.findViewById(R.id.vfc_fundInterest_db);
        vcoduration=itemView.findViewById(R.id.vfc_fundTenure_db);
        vcoimg=itemView.findViewById(R.id.vfc_imgsinglview);


    }
}
