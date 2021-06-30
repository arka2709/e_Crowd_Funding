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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class RequestinvestorInfo extends AppCompatActivity {
    DatabaseReference vinReff;
    Button btvdata;
    ImageView vikyc;

    Query dpbReff;


    String vName,vDoctype,vDob,vGender,vkycUrl,vemail;
    TextView xtName,xtdocType,xtdob,xtgender,xtemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestinvestor_info);
        vikyc=(ImageView)findViewById(R.id.iviewkyc);


        vinReff=FirebaseDatabase.getInstance().getReference().child("validateinvestorInfo");

        dpbReff=FirebaseDatabase.getInstance().getReference().child("validateInvestorPB").orderByChild("investorEmail").equalTo(vemail);

        xtName=findViewById(R.id.textiNamespc);
        xtdob=findViewById(R.id.textidobspcspc);
        xtgender=findViewById(R.id.textigenderspc);
        xtemail=findViewById(R.id.textiEmailspc);
        xtdocType=findViewById(R.id.textikycpc);
        btvdata=(Button)findViewById(R.id.btnfulldata);

        btvdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ldIntent=new Intent(RequestinvestorInfo.this,RequestinvestorInfofl.class);
                ldIntent.putExtra("iyname",vName);
                ldIntent.putExtra("iydocType",vDoctype);
                ldIntent.putExtra("iydob",vDob);
                ldIntent.putExtra("iygender",vGender);
                ldIntent.putExtra("iyemail",vemail);
                ldIntent.putExtra("iykycUrl",vkycUrl);
                ldIntent.putExtra("inkey",getIntent().getStringExtra("keydata"));
                startActivity(ldIntent);


            }
        });






        final String irInfo=getIntent().getStringExtra("keydata");
        vinReff.child(irInfo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    validateinvestorInfo vin=dataSnapshot.getValue(validateinvestorInfo.class);
                    xtName.setText(vin.getInvestorName());
                    xtdocType.setText(vin.getDocType());
                    xtdob.setText(vin.getDOB());
                    xtgender.setText(vin.getGender());
                    xtemail.setText(vin.getInvestorEmail());
                    Picasso.get().load(vin.getKycUrl()).into(vikyc);

                    vName=vin.getInvestorName();
                    vDoctype=vin.getDocType();
                    vkycUrl=vin.getKycUrl();
                    vDob=vin.getDOB();
                    vGender=vin.getGender();
                    vemail=vin.getInvestorEmail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(RequestinvestorInfo.this,requestInvestoractivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
