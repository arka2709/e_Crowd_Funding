package com.example.e_crowd;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class FundRaisedHolder extends RecyclerView.ViewHolder {
    TextView abfundraised,abdate;

    public FundRaisedHolder(@NonNull View itemView) {
        super(itemView);
        abfundraised=itemView.findViewById(R.id.fundraisedtxt);
        abdate=itemView.findViewById(R.id.fundraiseddatetxt);


    }
}
