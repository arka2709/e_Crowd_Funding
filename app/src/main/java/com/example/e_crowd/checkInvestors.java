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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class checkInvestors extends AppCompatActivity {
    RecyclerView rvviewivlist;
    FirebaseRecyclerOptions<order>options;
    FirebaseRecyclerAdapter<order, cinvestorvholder> adapter;
    DatabaseReference iReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_investors);
        rvviewivlist=findViewById(R.id.recyclerinv);
        rvviewivlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvviewivlist.setHasFixedSize(true);
        iReff= FirebaseDatabase.getInstance().getReference().child("Orders");


        LoadInvestors();
    }

    private void LoadInvestors() {

        options=new FirebaseRecyclerOptions.Builder<order>().setQuery(iReff.orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()),order.class).build();
        adapter=new FirebaseRecyclerAdapter<order, cinvestorvholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final cinvestorvholder holder, final int position, @NonNull order model) {
                holder.tiemail.setText(model.getInvestorEmail());
                holder.tiname.setText(model.getInvestorName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent checkintent=new Intent(checkInvestors.this,checkInvestordetails.class);
                        checkintent.putExtra("investitem",getRef(position).getKey());
                        startActivity(checkintent);
                    }
                });
            }

            @NonNull
            @Override
            public cinvestorvholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View iView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcinvestor,parent,false);
                return new cinvestorvholder(iView);
            }
        };

        adapter.startListening();
        rvviewivlist.setAdapter(adapter);

    }
}
