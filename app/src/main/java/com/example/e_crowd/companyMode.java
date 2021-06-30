package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class companyMode extends AppCompatActivity {
    CardView addNewComp,csignout,cvprofile;
    CardView ExplorefrsComp,btnactinv;
    Query compquery,comp2query,compRaisedquery;
    TextView tvcomid;
    String rsdStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companymode);
        addNewComp=findViewById(R.id.addyrCompany);
        ExplorefrsComp=findViewById(R.id.viewbtnfrsd);
        btnactinv=findViewById(R.id.activeInvestors);
        csignout=findViewById(R.id.comlogOut);
        cvprofile=findViewById(R.id.vCompanyProfile);
        tvcomid=findViewById(R.id.companyideml);

        compquery= FirebaseDatabase.getInstance().getReference().child("Companies").orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        compquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object> map=(Map<String, Object>)ds.getValue();
                    Object object=map.get("RegisteredBy");
                    String str=String.valueOf(object);
                    tvcomid.setText("Company Email:"+str);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        comp2query=FirebaseDatabase.getInstance().getReference().child("CompanyRequests").orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        comp2query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>)ds.getValue();
                    Object obmap=map.get("RegisteredBy");
                    tvcomid.setText("Account under verification");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        compRaisedquery=FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        compRaisedquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object orsdmap = map.get("RegisteredBy");
                    rsdStr=String.valueOf(orsdmap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        addNewComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(tvcomid.getText().toString())){
                   startActivity(new Intent(companyMode.this,companyact.class));
               }
               else if(tvcomid.getText().toString().equals("Account under verification")){

                   Toast.makeText(companyMode.this,"Account under verification",Toast.LENGTH_SHORT).show();

               }
               else{
                   Toast.makeText(companyMode.this,"Company Already Registered",Toast.LENGTH_SHORT).show();
               }
            }
        });

        ExplorefrsComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvcomid.getText().toString())) {
                    Toast.makeText(companyMode.this,"Register your company to see Fund Raised",Toast.LENGTH_SHORT).show();
                }

                else if(tvcomid.getText().toString().equals("Account under verification")){

                    Toast.makeText(companyMode.this,"Account under verification",Toast.LENGTH_SHORT).show();

                }

                else if(TextUtils.isEmpty(rsdStr))
                {
                  Toast.makeText(companyMode.this,"Fund raising record is empty till now.",Toast.LENGTH_LONG).show();
                }

                else{
                    startActivity(new Intent(companyMode.this, totalraised.class));
                }
            }
        });

        btnactinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tvcomid.getText().toString())){
                    Toast.makeText(companyMode.this,"Register your company to view Investor Details",Toast.LENGTH_SHORT).show();
                }

                else if(tvcomid.getText().toString().equals("Account under verification")){

                    Toast.makeText(companyMode.this,"Account under verification",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(rsdStr))
                {
                    Toast.makeText(companyMode.this,"No Active Investors till now",Toast.LENGTH_LONG).show();
                }

                else {
                    startActivity(new Intent(companyMode.this, checkInvestors.class));
                }
            }
        });

        cvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tvcomid.getText().toString())){
                    Toast.makeText(companyMode.this,"Company not registered",Toast.LENGTH_SHORT).show();
                }

                else if(tvcomid.getText().toString().equals("Account under verification")){

                    Toast.makeText(companyMode.this,"Account under verification",Toast.LENGTH_SHORT).show();

                }

                else {
                    startActivity(new Intent(companyMode.this, companyprofileactivity.class));
                }
            }
        });

        csignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), company_login.class);
                FirebaseAuth.getInstance().signOut();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(companyMode.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Sign Out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), company_login.class);
                FirebaseAuth.getInstance().signOut();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
