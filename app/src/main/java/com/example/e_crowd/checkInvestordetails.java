package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkInvestordetails extends AppCompatActivity {

    DatabaseReference idetailreff;

    TextView tdetname,tdetbank,tdetifsc,tdetphone,tdetemail,tdettotal,tdetinterest,tdetcompounded,tdetpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_investordetails);
        final String investorKey=getIntent().getStringExtra("investitem");
        idetailreff= FirebaseDatabase.getInstance().getReference().child("Orders").child(investorKey);
        tdetname=findViewById(R.id.texttvNamespace);
        tdetbank=findViewById(R.id.texttvbankspace);
        tdetifsc=findViewById(R.id.texttvIFSCspace);
        tdetphone=findViewById(R.id.texttvphonespace);
        tdetemail=findViewById(R.id.texttvemailspace);
        tdettotal=findViewById(R.id.texttvtotalinvstmntspace);
        tdetinterest=findViewById(R.id.texttvinterestofrdspace);
        tdetcompounded=findViewById(R.id.texttvamtpblspace);
        tdetpdate=findViewById(R.id.texttvdatespace);

        idetailreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    order odobj=dataSnapshot.getValue(order.class);
                    tdetname.setText(odobj.getInvestorName());
                    tdetemail.setText(odobj.getInvestorEmail());
                    tdetbank.setText(odobj.getInvestorAccountNo());
                    tdetifsc.setText(odobj.getIFSCCode());
                    tdetphone.setText(odobj.getInvestorPhone());
                    tdettotal.setText("Rs."+odobj.getTotal());
                    tdetinterest.setText(odobj.getInterest()+"% P.A");
                    tdetcompounded.setText("Rs."+odobj.getCamt());
                    tdetpdate.setText(odobj.getFuturedate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
