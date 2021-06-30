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

public class requestcompanyActivity extends AppCompatActivity {
    RecyclerView rvRequest;
    DatabaseReference requestReff;
    FirebaseRecyclerOptions<CompanyRequests> options;
    FirebaseRecyclerAdapter<CompanyRequests, RequestcompanyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        requestReff= FirebaseDatabase.getInstance().getReference().child("CompanyRequests");
        rvRequest=(RecyclerView)findViewById(R.id.recyclerViewrequest);
        rvRequest.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvRequest.setHasFixedSize(true);


        LoadRequest();

    }

    private void LoadRequest()
    {
        options=new FirebaseRecyclerOptions.Builder<CompanyRequests>().setQuery(requestReff,CompanyRequests.class).build();
        adapter=new FirebaseRecyclerAdapter<CompanyRequests, RequestcompanyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestcompanyViewHolder holder, final int position, @NonNull CompanyRequests model) {
                holder.textrView.setText(model.getCompanyName());
                Picasso.get().load(model.getImageUrl()).into(holder.imagerView);
                holder.fundrPrice.setText("Rs."+model.getPrice());
                holder.fundrInterest.setText(model.getInterestOffered()+"% P.A");
                holder.fundrTenure.setText(model.getTenure()+"year");
                holder.rv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent rintent=new Intent(requestcompanyActivity.this,requestCompanyInfo.class);
                        rintent.putExtra("rcompKey",getRef(position).getKey());
                        startActivity(rintent);

                    }
                });
            }

            @NonNull
            @Override
            public RequestcompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View rv= LayoutInflater.from(parent.getContext()).inflate(R.layout.requestview_layout,parent,false);
                return new RequestcompanyViewHolder(rv);
            }
        };

        adapter.startListening();
        rvRequest.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(requestcompanyActivity.this,adminHome.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
