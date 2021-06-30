package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class totalraised extends AppCompatActivity {
    RecyclerView rvTotal;
    TextView tvTotalrsd;
    FirebaseRecyclerOptions<CartList> options;
    FirebaseRecyclerAdapter<CartList,totalRaisedHolder> adapter;
    Query queryRaised;
    Query querytotalRaised;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalraised);
        rvTotal=findViewById(R.id.rcViewTotalRaised);
        tvTotalrsd=findViewById(R.id.tvTotalfrsd);
        String CregId=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        queryRaised= FirebaseDatabase.getInstance().getReference().child("Orders");
        rvTotal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvTotal.setHasFixedSize(true);
        querytotalRaised=FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("RegisteredBy").equalTo(CregId);

        querytotalRaised.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int sum=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String,Object> map= (Map<String,Object>) ds.getValue();
                    Object total=map.get("Total");
                    int tvalue=Integer.parseInt(String.valueOf(total));
                    sum+=tvalue;
                    tvTotalrsd.setText("Total Raised Rs."+String.valueOf(sum));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Loadfrdata();

    }

    private void Loadfrdata() {
        options=new FirebaseRecyclerOptions.Builder<CartList>().setQuery(queryRaised.orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()),CartList.class).build();
        adapter=new FirebaseRecyclerAdapter<CartList, totalRaisedHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull totalRaisedHolder holder, int position, @NonNull CartList model) {
                holder.frdRaised.setText("Rs."+model.getTotal());
                holder.frdDate.setText(model.getDate());
                holder.frdTime.setText(model.getTime());

            }

            @NonNull
            @Override
            public totalRaisedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View frsdView= LayoutInflater.from(parent.getContext()).inflate(R.layout.totalraiseview,parent,false);
                return new totalRaisedHolder(frsdView);
            }
        };

        adapter.startListening();
        rvTotal.setAdapter(adapter);

    }
}
