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

public class requestInvestoractivity extends AppCompatActivity {
    RecyclerView rvinvestor;
   FirebaseRecyclerOptions<validateinvestorInfo> options;
   FirebaseRecyclerAdapter<validateinvestorInfo,reqInvestorHolder> adapter;
    DatabaseReference invReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_investor);
        rvinvestor=findViewById(R.id.recyclerinvestor);
        rvinvestor.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvinvestor.setHasFixedSize(true);
        invReff= FirebaseDatabase.getInstance().getReference().child("validateinvestorInfo");
        
        loadInvdata();

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(requestInvestoractivity.this,adminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void loadInvdata() {
        options=new FirebaseRecyclerOptions.Builder<validateinvestorInfo>().setQuery(invReff, validateinvestorInfo.class).build();
        adapter=new FirebaseRecyclerAdapter<validateinvestorInfo, reqInvestorHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final reqInvestorHolder holder, final int position, @NonNull final validateinvestorInfo model) {
                holder.teinName.setText(model.getInvestorName());
                holder.teinGender.setText(model.getGender());
                holder.teinDob.setText(model.getDOB());
                holder.teinEmail.setText(model.getInvestorEmail());
                Picasso.get().load(model.getKycUrl()).into(holder.ivinvestor);
                holder.viewiv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent invinfo=new Intent(requestInvestoractivity.this,RequestinvestorInfo.class);
                            invinfo.putExtra("keydata",getRef(position).getKey());
                            startActivity(invinfo);
                    }
                });

            }

            @NonNull
            @Override
            public reqInvestorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inView=LayoutInflater.from(parent.getContext()).inflate(R.layout.investorholder,parent,false);
                return new reqInvestorHolder(inView);
            }
        };

        adapter.startListening();
        rvinvestor.setAdapter(adapter);
    }
}
