package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class holdingInfo extends AppCompatActivity {
   private ImageView hPic;
   private TextView hName,hPrice,hQuantity,hTotal,hTenure,hRoi,hDate,hdatem,hamtm;
   DatabaseReference href;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holding_info);
        hPic=findViewById(R.id.companyPic);
        hName=findViewById(R.id.tvCompNameSpace);
        hPrice=findViewById(R.id.CmpPriceSpace);
        hRoi=findViewById(R.id.CmpRoiSpace);
        hQuantity=findViewById(R.id.CmpQtySpace);
        hTenure=findViewById(R.id.CmpTenureSpace);
        hTotal=findViewById(R.id.CmpTotalSpace);
        hDate=findViewById(R.id.CmpDate1Space);
        hdatem=findViewById(R.id.CmpDatematuSpace);
        hamtm=findViewById(R.id.CmpAmtmatuSpace);


        href= FirebaseDatabase.getInstance().getReference().child("Orders");

        final String CompanyKey=getIntent().getStringExtra("cKey");

        href.child(CompanyKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    CartList cl=dataSnapshot.getValue(CartList.class);
                    Picasso.get().load(cl.getImageUrl()).into(hPic);
                    hName.setText(cl.getCompanyName());
                    hPrice.setText("Rs."+cl.getPrice());
                    hRoi.setText(cl.getInterest()+"% per year");
                    hQuantity.setText(cl.getQuantity()+" units");
                    hTenure.setText(cl.getTenure()+" Years");
                    hTotal.setText("Rs."+cl.getTotal());
                    hDate.setText(cl.getDate());
                    hdatem.setText(cl.getFuturedate());
                    hamtm.setText("Rs."+cl.getCamt());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
