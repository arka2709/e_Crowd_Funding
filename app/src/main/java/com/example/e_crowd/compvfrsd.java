package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class compvfrsd extends AppCompatActivity {

    FirebaseRecyclerOptions<order> options;
    FirebaseRecyclerAdapter<order,FundRaisedHolder>adapter;

    Query cfundraisedreff;

     RecyclerView rvfundrsd;
     Query rdQuery;
    TextView tvraisedt,tvraisedtgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compvfrsd);
        tvraisedt=findViewById(R.id.viewtRaised);
        tvraisedtgt=findViewById(R.id.viewtRaisedtgt);
        rvfundrsd=findViewById(R.id.recyclerRaised);



        rvfundrsd.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvfundrsd.setHasFixedSize(true);




        String rd=getIntent().getStringExtra("rdemail");

        rdQuery= FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("RegisteredBy").equalTo(rd);

        tvraisedtgt.setText("Raising Target: Rs."+getIntent().getStringExtra("ftg"));


        rdQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object otot=map.get("Total");
                    int tsum=Integer.parseInt(String.valueOf(otot));
                    sum+=tsum;
                    tvraisedt.setText("Fund Raised:  Rs."+(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Loadraised();

    }

    private void Loadraised() {



        options=new FirebaseRecyclerOptions.Builder<order>().setQuery(rdQuery,order.class).build();
        adapter=new FirebaseRecyclerAdapter<order, FundRaisedHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FundRaisedHolder holder, int position, @NonNull order model) {

                holder.abfundraised.setText(model.getTotal());
                holder.abdate.setText(model.getDate());
            }

            @NonNull
            @Override
            public FundRaisedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View fview= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfundraised,parent,false);
                return new FundRaisedHolder(fview);
            }
        };

        adapter.startListening();
        rvfundrsd.setAdapter(adapter);

    }
}
