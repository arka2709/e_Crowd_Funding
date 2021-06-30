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

public class investorProfile extends AppCompatActivity {
    TextView tvmyname,tvmyemail,tvmybank,tvmydob,tvmygender,tvmyifsc,tvmyphone,tvmykyctype;
    Button mykycUrl,mypanUrl;
    Query myprofileQuery;
    String skycurl,spanurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);
        tvmyname=findViewById(R.id.mynamespace);
        tvmyemail=findViewById(R.id.myEmailspace);
        tvmybank=findViewById(R.id.mybanknospace);
        tvmydob=findViewById(R.id.mydobspace);
        tvmygender=findViewById(R.id.mygenderspace);
        tvmyifsc=findViewById(R.id.myifscspace);
        tvmyphone=findViewById(R.id.myphonespace);
        tvmykyctype=findViewById(R.id.mykyctype);
        mykycUrl=findViewById(R.id.btnviewmkyc);
        mypanUrl=findViewById(R.id.btnmypan);
        myprofileQuery= FirebaseDatabase.getInstance().getReference().child("Investors").orderByChild("InvestorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        myprofileQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) d.getValue();
                    Object nobj = map.get("InvestorName");
                    tvmyname.setText(String.valueOf(nobj));

                    Object ebobj = map.get("InvestorEmail");
                    tvmyemail.setText(String.valueOf(ebobj));

                    Object bankobj = map.get("BankAccountNo");
                    tvmybank.setText(String.valueOf(bankobj));

                    Object dobObj = map.get("DOB");
                    tvmydob.setText(String.valueOf(dobObj));

                    Object genObj = map.get("Gender");
                    tvmygender.setText(String.valueOf(genObj));

                    Object ifscObj = map.get("IFSCCode");
                    tvmyifsc.setText(String.valueOf(ifscObj));

                    Object phoneObj = map.get("InvestorPhone");
                    tvmyphone.setText(String.valueOf(phoneObj));

                    Object ktypeobj = map.get("kycType");
                    tvmykyctype.setText("KYC Type: " + String.valueOf(ktypeobj));

                    Object kycobj = map.get("kycUrl");
                    skycurl = String.valueOf(kycobj);

                    Object panObj = map.get("PanUrl");
                    spanurl = String.valueOf(panObj);

                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        mypanUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(spanurl));
                startActivity(intent);
            }
        });

        mykycUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(skycurl));
                startActivity(intent);
            }
        });



    }
}
