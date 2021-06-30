package com.example.e_crowd;

import android.app.DownloadManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

class totalRaisedHolder extends RecyclerView.ViewHolder {

    ImageView picView;
    TextView frdName,frdRaised,frdDate,frdTime;
    View frsdView;


    public totalRaisedHolder(@NonNull View itemView) {
        super(itemView);
        frdRaised=itemView.findViewById(R.id.totalRaisedspc);
        frdDate=itemView.findViewById(R.id.trdDatespc);
        frdTime=itemView.findViewById(R.id.trdTimespc);
        frsdView=itemView;


    }

}
