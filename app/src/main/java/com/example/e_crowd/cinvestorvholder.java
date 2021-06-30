package com.example.e_crowd;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class cinvestorvholder extends RecyclerView.ViewHolder {

    TextView tiemail,tiname;


    public cinvestorvholder(@NonNull View itemView) {
        super(itemView);
        tiemail=itemView.findViewById(R.id.iiemail);
        tiname=itemView.findViewById(R.id.iiname);
    }
}
