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

public class adminverifiedCompanies extends AppCompatActivity {
    DatabaseReference actcompReff;
    RecyclerView rvactcompanies;
    FirebaseRecyclerOptions<Companies>options;
    FirebaseRecyclerAdapter<Companies,vericompanies>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminverified_companies);
        rvactcompanies=findViewById(R.id.rvactcomp);
        rvactcompanies.setHasFixedSize(true);
        rvactcompanies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        actcompReff=FirebaseDatabase.getInstance().getReference().child("Companies");

        Loadadvfc();

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(adminverifiedCompanies.this,adminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void Loadadvfc() {
        options=new FirebaseRecyclerOptions.Builder<Companies>().setQuery(actcompReff,Companies.class).build();
        adapter=new FirebaseRecyclerAdapter<Companies, vericompanies>(options) {
            @Override
            protected void onBindViewHolder(@NonNull vericompanies holder, final int position, @NonNull Companies model) {
                holder.vcoName.setText(model.getCompanyName());
                holder.vcoprice.setText("Rs."+model.getPrice());
                holder.vcointerest.setText(model.getInterestOffered()+"% P.A");
                holder.vcoduration.setText(model.getTenure()+"year");
                Picasso.get().load(model.getImageUrl()).into(holder.vcoimg);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(adminverifiedCompanies.this,adminvCompanyInfo.class);
                        intent.putExtra("avcpos",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public vericompanies onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View avfView= LayoutInflater.from(parent.getContext()).inflate(R.layout.verifiedcompanies,parent,false);
                return new vericompanies(avfView);
            }
        };

        adapter.startListening();
        rvactcompanies.setAdapter(adapter);

    }
}
