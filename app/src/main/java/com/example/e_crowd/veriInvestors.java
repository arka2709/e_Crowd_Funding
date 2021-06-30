package com.example.e_crowd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class veriInvestors extends RecyclerView.ViewHolder {
    ImageView verinImage;
    TextView verinName,verinGender,verinDob,verinEmail;



    public veriInvestors(@NonNull View itemView) {
        super(itemView);
        verinName=itemView.findViewById(R.id.adivInvestorNamespc);
        verinGender=itemView.findViewById(R.id.adivInvestorGenderspc);
        verinDob=itemView.findViewById(R.id.adivInvestordobspc);
        verinEmail=itemView.findViewById(R.id.adivInvestoremailspc);
        verinImage=itemView.findViewById(R.id.adivimgid);

    }
}
