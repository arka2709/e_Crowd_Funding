package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class reqInvestorHolder extends RecyclerView.ViewHolder {
    ImageView ivinvestor;
    TextView teinName,teinGender,teinDob,teinEmail;
    View viewiv;

    public reqInvestorHolder(@NonNull View itemView) {
        super(itemView);
        teinName=itemView.findViewById(R.id.ttInvestorNamespc);
        teinGender=itemView.findViewById(R.id.ttInvestorGenderspc);
        teinDob=itemView.findViewById(R.id.ttInvestordobspc);
        teinEmail=itemView.findViewById(R.id.ttInvestoremailspc);
        ivinvestor=itemView.findViewById(R.id.ttimgid);
        viewiv=itemView;

    }
}
