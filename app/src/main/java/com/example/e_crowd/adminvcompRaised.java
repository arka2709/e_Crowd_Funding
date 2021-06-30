package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class adminvcompRaised extends AppCompatActivity {
    FirebaseRecyclerOptions<order>options;
    FirebaseRecyclerAdapter<order,adcompRaisedholder>adapter;

    TextView tvadcraised,tvadcraisedtgt;
    RecyclerView rvadcRaised;
    Query adctotalReff;
    DatabaseReference adcraisedReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminvcomp_raised);
        String adcraisedString=getIntent().getStringExtra("aciemailStr");
        String adcftargetString=getIntent().getStringExtra("acitarget");
        rvadcRaised=findViewById(R.id.rcadcTotalRaised);
        tvadcraised=findViewById(R.id.adcraised);
        tvadcraisedtgt=findViewById(R.id.adcraisedtgt);

        tvadcraisedtgt.setText("Raising Target Rs."+adcftargetString);

        rvadcRaised.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvadcRaised.setHasFixedSize(true);


        adcraisedReff=FirebaseDatabase.getInstance().getReference().child("Orders");

        adctotalReff= FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("RegisteredBy").equalTo(adcraisedString);
        adctotalReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String,Object> map= (Map<String,Object>) ds.getValue();
                    Object total=map.get("Total");
                    int tvalue=Integer.parseInt(String.valueOf(total));
                    sum+=tvalue;
                    tvadcraised.setText("Fund Raised   Rs."+String.valueOf(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        options=new FirebaseRecyclerOptions.Builder<order>().setQuery(adcraisedReff.orderByChild("RegisteredBy").equalTo(adcraisedString),order.class).build();
        adapter=new FirebaseRecyclerAdapter<order, adcompRaisedholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull adcompRaisedholder holder, int position, @NonNull order model) {
                holder.adrhname.setText(model.getInvestorName());
                holder.adrhemail.setText(model.getInvestorEmail());
                holder.adrhRaised.setText(model.getTotal());
                holder.adrhdate.setText(model.getDate());
                holder.adrhtime.setText(model.getTime());
            }

            @NonNull
            @Override
            public adcompRaisedholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admncompraisedlayout,parent,false);
                return new adcompRaisedholder(view);
            }
        };

        adapter.startListening();
        rvadcRaised.setAdapter(adapter);







    }
}
