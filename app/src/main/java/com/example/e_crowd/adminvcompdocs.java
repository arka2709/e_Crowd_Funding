package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class adminvcompdocs extends AppCompatActivity {

    Query qacreg,qacadrs,qacbroc,qacfina;
    Button acdreg,acdadrs,acdbroc,acdfina;
    String adregisUrl,adraddresUrl,adrbrchureUrl,adcfinancesUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminvcompdocs);

        String acdStr=getIntent().getStringExtra("aciemailStr");

        acdreg=findViewById(R.id.adcbtnviewodinfo);
        acdadrs=findViewById(R.id.adcbtnviewsdinfo);
        acdbroc=findViewById(R.id.adcbtnviewbrdinfo);
        acdfina=findViewById(R.id.adcbtnviewFininfo);

        qacreg= FirebaseDatabase.getInstance().getReference().child("CompanyRegDocs").orderByChild("CompanyEmail").equalTo(acdStr);
        qacreg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object rUrl = map.get("RegistrationUrl");
                    adregisUrl = String.valueOf(rUrl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        qacadrs=FirebaseDatabase.getInstance().getReference().child("CompanyAddressDocs").orderByChild("CompanyEmail").equalTo(acdStr);
        qacadrs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object aUrl = map.get("AddressProofUrl");
                    adraddresUrl = String.valueOf(aUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        qacbroc=FirebaseDatabase.getInstance().getReference().child("CompanyBrochureDocs").orderByChild("CompanyEmail").equalTo(acdStr);
        qacbroc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object bUrl = map.get("BrochureUrl");
                    adrbrchureUrl = String.valueOf(bUrl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        qacfina=FirebaseDatabase.getInstance().getReference().child("CompanyFinancialDocs").orderByChild("CompanyEmail").equalTo(acdStr);
        qacfina.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object fUrl = map.get("FinancialUrl");
                    adcfinancesUrl = String.valueOf(fUrl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        acdreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adregisUrl));
                startActivity(lIntent);
            }
        });

        acdadrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adraddresUrl));
                startActivity(lIntent);

            }
        });

        acdbroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adrbrchureUrl));
                startActivity(lIntent);
            }
        });

        acdfina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adcfinancesUrl));
                startActivity(lIntent);
            }
        });


    }
}
