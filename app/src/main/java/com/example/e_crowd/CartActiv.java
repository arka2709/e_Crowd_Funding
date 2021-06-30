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
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class CartActiv extends AppCompatActivity {
    RecyclerView cartrv;
    FirebaseRecyclerOptions<CartList> options;
    FirebaseRecyclerAdapter<CartList,CartHolder> adapter;
    DatabaseReference usercartref;
    Query totalRef;
    Button btncalc;
    TextView tvtotalset;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        usercartref= (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Orders");
        cartrv=findViewById(R.id.recyclerCart);
        cartrv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartrv.setHasFixedSize(true);
        tvtotalset=findViewById(R.id.total);


        tvtotalset=(TextView)findViewById(R.id.total);

        totalRef=FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("InvestorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        totalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String,Object> map= (Map<String,Object>) ds.getValue();
                    Object total=map.get("Total");
                    int tvalue=Integer.parseInt(String.valueOf(total));
                    sum+=tvalue;
                    tvtotalset.setText("Rs."+String.valueOf(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        LoadCart();
    }


 private void LoadCart(){
        options=new FirebaseRecyclerOptions.Builder<CartList>().setQuery(usercartref.orderByChild("InvestorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()),CartList.class).build();
        adapter=new FirebaseRecyclerAdapter<CartList, CartHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartHolder holder, final int position, @NonNull CartList model) {
                holder.tvnm.setText(model.getCompanyName());
                holder.tvpr.setText("Rs."+model.getTotal());
                holder.tvtenure.setText(model.getTenure()+" year");
                holder.tvinterest.setText(model.getInterest()+"%");
                Picasso.get().load(model.getImageUrl()).into(holder.odimage);

                holder.v1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent vIntent=new Intent(CartActiv.this,holdingInfo.class);
                        vIntent.putExtra("cKey",getRef(position).getKey());
                        startActivity(vIntent);
                    }
                });


            }

            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlayout,parent,false);
                return new CartHolder(v1);

            }
        };
        adapter.startListening();
        cartrv.setAdapter(adapter);



    }

}
