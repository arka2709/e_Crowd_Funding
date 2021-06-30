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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class adminverifiedInvestors extends AppCompatActivity {
    RecyclerView rvaviView;
    DatabaseReference dbadvinreff;
    FirebaseRecyclerOptions<Investors>options;
    FirebaseRecyclerAdapter<Investors,veriInvestors>adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminverified_investors);
        rvaviView=findViewById(R.id.rvavi);
        dbadvinreff= FirebaseDatabase.getInstance().getReference().child("Investors");
        rvaviView.setHasFixedSize(true);
        rvaviView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        options=new FirebaseRecyclerOptions.Builder<Investors>().setQuery(dbadvinreff,Investors.class).build();
        adapter=new FirebaseRecyclerAdapter<Investors, veriInvestors>(options) {
            @Override
            protected void onBindViewHolder(@NonNull veriInvestors holder, final int position, @NonNull Investors model) {
                Picasso.get().load(model.getKycUrl()).into(holder.verinImage);
                holder.verinEmail.setText(model.getInvestorEmail());
                holder.verinDob.setText(model.getDOB());
                holder.verinGender.setText(model.getGender());
                holder.verinName.setText(model.getInvestorName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(adminverifiedInvestors.this,admininvinfo.class);
                        intent.putExtra("avinvestorposition",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public veriInvestors onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View aviView= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminvinvestorlayout,parent,false);
                return new veriInvestors(aviView);
            }
        };

        adapter.startListening();
        rvaviView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(adminverifiedInvestors.this,adminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
