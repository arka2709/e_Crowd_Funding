package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admininvinfo extends AppCompatActivity {
    TextView textadviName,textadviGender,textadviDob,textadviemail,textadvikyc,textadvibnkno,textadviifsc,textadviphone;
    DatabaseReference dbadviinfoReff;
    String advikycUrl,advipanUrl,adviemailString;
    Button btadvikyc,btadvipan,btadviinvest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admininvinfo);
        String adviinfoStr=getIntent().getStringExtra("avinvestorposition");

        textadviName=findViewById(R.id.advintextiNamespcfl);
        textadviGender=findViewById(R.id.advintextigenderspcfl);
        textadviDob=findViewById(R.id.advintextidobspcspcfl);
        textadviemail=findViewById(R.id.advintextiEmailspcfl);
        textadvikyc=findViewById(R.id.advintextikycpcfl);
        textadvibnkno=findViewById(R.id.advintextibaactflspc);
        textadviifsc=findViewById(R.id.advintextiifscspcfl);
        textadviphone=findViewById(R.id.advintextiiphonespcfl);
        btadvikyc=findViewById(R.id.btnadvikyc);
        btadvipan=findViewById(R.id.btnadvipan);
        btadviinvest=findViewById(R.id.btnadviinvestment);



        dbadviinfoReff= FirebaseDatabase.getInstance().getReference().child("Investors").child(adviinfoStr);
        dbadviinfoReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Investors investors=dataSnapshot.getValue(Investors.class);
                textadviName.setText(investors.getInvestorName());
                textadviGender.setText(investors.getGender());
                textadviDob.setText(investors.getDOB());
                textadviemail.setText(investors.getInvestorEmail());
                textadvikyc.setText(investors.getKycType());
                textadvibnkno.setText(investors.getBankAccountNo());
                textadviifsc.setText(investors.getIFSCCode());
                textadviphone.setText(investors.getInvestorPhone());
                advikycUrl= investors.getKycUrl();
                advipanUrl=investors.getPanUrl();

                adviemailString=investors.getInvestorEmail();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btadvikyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(advikycUrl));
                startActivity(intent);
            }
        });

        btadvipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(advipanUrl));
                startActivity(intent);
            }
        });


        btadviinvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admininvinfo.this,adminviinvestments.class);
                intent.putExtra("advinvemailString",adviemailString);
                startActivity(intent);

            }
        });
    }
}
