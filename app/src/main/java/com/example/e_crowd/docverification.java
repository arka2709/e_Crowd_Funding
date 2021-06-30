package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class docverification extends AppCompatActivity {
    Button viewRegDoc,viewAddDoc,viewBrocDoc,viewFinaDoc;
    Query qr,qa,qb,qf;
    String regUrl,addrsUrl,brochureUrl,financeUrl;
    TextView tvchk;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docverification);
        viewRegDoc=findViewById(R.id.btnviewod);
        viewAddDoc=findViewById(R.id.btnviewsd);
        viewBrocDoc=findViewById(R.id.btnviewbrd);
        viewFinaDoc=findViewById(R.id.btnviewFin);

        String dvemail=getIntent().getStringExtra("rciemailIntent");


        qr=FirebaseDatabase.getInstance().getReference().child("CompanyRegDocs").orderByChild("CompanyEmail").equalTo(dvemail);
        qr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object rUrl = map.get("RegistrationUrl");
                    regUrl = String.valueOf(rUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        qa=FirebaseDatabase.getInstance().getReference().child("CompanyAddressDocs").orderByChild("CompanyEmail").equalTo(dvemail);
        qa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object aUrl = map.get("AddressProofUrl");
                    addrsUrl = String.valueOf(aUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        qb=FirebaseDatabase.getInstance().getReference().child("CompanyBrochureDocs").orderByChild("CompanyEmail").equalTo(dvemail);
        qb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object bUrl = map.get("BrochureUrl");
                    brochureUrl = String.valueOf(bUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        qf=FirebaseDatabase.getInstance().getReference().child("CompanyFinancialDocs").orderByChild("CompanyEmail").equalTo(dvemail);
        qf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object bUrl = map.get("FinancialUrl");
                    financeUrl = String.valueOf(bUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        viewRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(regUrl));
                startActivity(lIntent);

            }
        });


        viewAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adrsIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(addrsUrl));
                startActivity(adrsIntent);
            }
        });

        viewBrocDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent brochureIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(brochureUrl));
                startActivity(brochureIntent);
            }
        });

        viewFinaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent financeIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(financeUrl));
                startActivity(financeIntent);
            }
        });


    }
}
