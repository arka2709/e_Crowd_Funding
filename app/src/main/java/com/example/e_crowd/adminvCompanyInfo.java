package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class adminvCompanyInfo extends AppCompatActivity {
    DatabaseReference aciReff;
    TextView aciName,aciDesc,aciPrice,aciInterest,acitarget,acitenure,aciphone,aciemail;
    Button btnaci,btnaciRaised;
    String aciemailstr,acitargetstr;
    ImageView aciimgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminv_company_info);
        aciName=findViewById(R.id.adci_tvcopname_info);
        aciDesc=findViewById(R.id.adci_tvcompdesc_info);
        aciPrice=findViewById(R.id.adci_tvprice);
        aciInterest=findViewById(R.id.adci_tvinteres);
        acitarget=findViewById(R.id.adci_fundtg);
        acitenure=findViewById(R.id.adci_fundduration);
        aciphone=findViewById(R.id.adci_phSpace);
        aciemail=findViewById(R.id.adci_email_Space);
        aciimgView=findViewById(R.id.adci_imgsinglview_info);

        btnaci=findViewById(R.id.btadci_docs);
        btnaciRaised=findViewById(R.id.btadci_fRaised);


        aciReff= FirebaseDatabase.getInstance().getReference().child("Companies").child(getIntent().getStringExtra("avcpos"));

        aciReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Companies cmp=dataSnapshot.getValue(Companies.class);
                aciName.setText(cmp.getCompanyName());
                aciDesc.setText(cmp.getCompanyDescription());
                aciPrice.setText("Rs."+cmp.getPrice());
                aciInterest.setText(cmp.getInterestOffered()+"% P.A");
                acitarget.setText("Rs."+cmp.getFundtarget());
                acitenure.setText(cmp.getTenure()+"year");
                aciphone.setText(cmp.getPhone());
                aciemail.setText(cmp.getRegisteredBy());
                aciemailstr=cmp.getRegisteredBy();
                acitargetstr=cmp.getFundtarget();
                Picasso.get().load(cmp.getImageUrl()).into(aciimgView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminvCompanyInfo.this,adminvcompdocs.class);
                intent.putExtra("aciemailStr",aciemailstr);
                startActivity(intent);
            }
        });


        btnaciRaised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminvCompanyInfo.this,adminvcompRaised.class);
                intent.putExtra("aciemailStr",aciemailstr);
                intent.putExtra("acitarget",acitargetstr);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(adminvCompanyInfo.this,adminverifiedCompanies.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
