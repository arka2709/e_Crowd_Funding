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

public class docInfo extends AppCompatActivity {

    Button btnRegDoc,btnAddDoc,btnBrocDoc,btnFinaDoc;
    Query queryR,queryA,queryB,queryF;
    String regisUrl,addresUrl,brchureUrl,financesUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        btnRegDoc=findViewById(R.id.btnviewodinfo);
        btnAddDoc=findViewById(R.id.btnviewsdinfo);
        btnBrocDoc=findViewById(R.id.btnviewbrdinfo);
        btnFinaDoc=findViewById(R.id.btnviewFininfo);

        String doiEmail=getIntent().getStringExtra("cemailintent");




        queryR= FirebaseDatabase.getInstance().getReference().child("CompanyRegDocs").orderByChild("CompanyEmail").equalTo(doiEmail);
        queryR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object rUrl = map.get("RegistrationUrl");
                    regisUrl = String.valueOf(rUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        queryA=FirebaseDatabase.getInstance().getReference().child("CompanyAddressDocs").orderByChild("CompanyEmail").equalTo(doiEmail);
        queryA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object aUrl = map.get("AddressProofUrl");
                    addresUrl = String.valueOf(aUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        queryB=FirebaseDatabase.getInstance().getReference().child("CompanyBrochureDocs").orderByChild("CompanyEmail").equalTo(doiEmail);
        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object bUrl = map.get("BrochureUrl");
                    brchureUrl = String.valueOf(bUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        queryF=FirebaseDatabase.getInstance().getReference().child("CompanyFinancialDocs").orderByChild("CompanyEmail").equalTo(doiEmail);
        queryF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object fUrl = map.get("FinancialUrl");
                    financesUrl = String.valueOf(fUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        btnRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(regisUrl));
                startActivity(lIntent);

            }
        });


        btnAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adrsIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(addresUrl));
                startActivity(adrsIntent);
            }
        });

        btnBrocDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent brochureIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(brchureUrl));
                startActivity(brochureIntent);
            }
        });

        btnFinaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent financeIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(financesUrl));
                startActivity(financeIntent);
            }
        });
    }
}
