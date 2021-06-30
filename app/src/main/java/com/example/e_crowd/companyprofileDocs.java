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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class companyprofileDocs extends AppCompatActivity {



    Button bcpreg,bcpadr,bcpbroch,bcpbalance;

    Query cregquery,cadrquery,cbrocquery,cbalancequery;

    String scpRegurl,scpadrUrl,scpbrocUrl,scpbalanceUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyprofile_docs);
        String cpdstr=getIntent().getStringExtra("cpaEmail");

      bcpreg=findViewById(R.id.cpdbtnviewod);
      bcpadr=findViewById(R.id.cpdbtnviewsd);
      bcpbroch=findViewById(R.id.cpdbtnviewbrd);
      bcpbalance=findViewById(R.id.cpdbtnviewFin);




        cregquery= FirebaseDatabase.getInstance().getReference().child("CompanyRegDocs").orderByChild("CompanyEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        cregquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>)ds.getValue();
                    Object regobj=map.get("RegistrationUrl");
                    scpRegurl=String.valueOf(regobj);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cadrquery=FirebaseDatabase.getInstance().getReference().child("CompanyAddressDocs").orderByChild("CompanyEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        cadrquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>)ds.getValue();
                    Object adrobj=map.get("AddressProofUrl");
                    scpadrUrl=String.valueOf(adrobj);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        cbrocquery=FirebaseDatabase.getInstance().getReference().child("CompanyBrochureDocs").orderByChild("CompanyEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        cbrocquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>)ds.getValue();
                    Object brocobj=map.get("BrochureUrl");
                    scpbrocUrl=String.valueOf(brocobj);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cbalancequery=FirebaseDatabase.getInstance().getReference().child("CompanyFinancialDocs").orderByChild("CompanyEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        cbalancequery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>)ds.getValue();
                    Object balanobj=map.get("FinancialUrl");
                    scpbalanceUrl=String.valueOf(balanobj);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bcpreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scpRegurl));
                startActivity(intent);
            }
        });



        bcpadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scpadrUrl));
                startActivity(intent);
            }
        });


        bcpbroch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scpbrocUrl));
                startActivity(intent);
            }
        });

        bcpbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scpbalanceUrl));
                startActivity(intent);
            }
        });



    }
}
