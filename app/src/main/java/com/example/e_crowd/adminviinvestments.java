package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class adminviinvestments extends AppCompatActivity {
    RecyclerView rvinvestment;
    DatabaseReference dinvestReff;
    FirebaseRecyclerOptions<order>options;
    FirebaseRecyclerAdapter<order,investmentholder>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviinvestments);
        rvinvestment=findViewById(R.id.rvinvested);
        rvinvestment.setHasFixedSize(true);
        rvinvestment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String admivemailString=getIntent().getStringExtra("advinvemailString");
        dinvestReff= FirebaseDatabase.getInstance().getReference().child("Orders");


        options=new FirebaseRecyclerOptions.Builder<order>().setQuery(dinvestReff.orderByChild("InvestorEmail").equalTo(admivemailString),order.class).build();
        adapter=new FirebaseRecyclerAdapter<order, investmentholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull investmentholder holder, int position, @NonNull order model) {
                Picasso.get().load(model.getImageUrl()).into(holder.ihimg);
                holder.ihmatamnt.setText("Rs."+model.getCamt());
                holder.ihmatdate.setText(model.getFuturedate());
                holder.ihdate.setText(model.getDate());
                holder.ihtenure.setText(model.getTenure()+" year");
                holder.ihname.setText(model.getCompanyName());
                holder.ihinvestment.setText("Rs."+model.getTotal());
                holder.ihemail.setText(model.getRegisteredBy());
                holder.ihinterest.setText(model.getInterest()+"% P.A");
            }

            @NonNull
            @Override
            public investmentholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.investmentlayout,parent,false);
                return new investmentholder(view);
            }
        };

        adapter.startListening();
        rvinvestment.setAdapter(adapter);

    }


}
