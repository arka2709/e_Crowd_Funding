package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class home extends AppCompatActivity {
    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Companies>options;
    FirebaseRecyclerAdapter<Companies,MyViewHolder> adapter;
    DatabaseReference Dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Dataref= FirebaseDatabase.getInstance().getReference().child("Companies");
        inputSearch=findViewById(R.id.input_search);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);


        LoadData("");


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    LoadData(s.toString());
                }
                else
                {
                    LoadData("");
                }

            }
        });



    }

    private void LoadData(String data) {
        Query squery=Dataref.orderByChild("CompanyName").startAt(data).endAt(data+"\uf8ff");
        options=new FirebaseRecyclerOptions.Builder<Companies>().setQuery(squery,Companies.class).build();
        adapter=new FirebaseRecyclerAdapter<Companies, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Companies model) {
                holder.textView.setText(model.getCompanyName());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.fundPrice.setText("Rs. "+model.getPrice());
                holder.fundInterest.setText(model.getInterestOffered()+"% P.A");
                holder.fundTenure.setText(model.getTenure()+" years");
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iview=new Intent(home.this,companyinformation.class);
                        iview.putExtra("compkey",getRef(position).getKey());
                        startActivity(iview);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
