package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestinvestorInfofl extends AppCompatActivity {
    TextView yvName,yvdob,yvGender,yvEmail,yvKyctype,yvbnkno,yvifsc,yvmobno;
    Query panqu;
    String zvName,zvdob,zvgender,zvEmail,zvKyctype,zvkycUrl;
    String oPanUrl,pnk;
    String bkno,ifscno,mobno;
    Button btnviKyc,btnvipan;
    Button btniApprove,btniReject;
    DatabaseReference iApprovedReff;
    DatabaseReference iDeleteReff;
    DatabaseReference iDeletePbReff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestinvestor_infofl);
        yvName=findViewById(R.id.textiNamespcfl);
        yvdob=findViewById(R.id.textidobspcspcfl);
        yvGender=findViewById(R.id.textigenderspcfl);
        yvEmail=findViewById(R.id.textiEmailspcfl);
        yvKyctype=findViewById(R.id.textikycpcfl);
        yvbnkno=findViewById(R.id.textibaactflspc);
        yvifsc=findViewById(R.id.textiifscspcfl);
        yvmobno=findViewById(R.id.textiiphonespcfl);
        btnviKyc=findViewById(R.id.btnviewKyc);
        btnvipan=findViewById(R.id.btnviewPan);
        btniApprove=findViewById(R.id.btnaprvInv);
        btniReject=findViewById(R.id.btnrejectInv);

        iApprovedReff=FirebaseDatabase.getInstance().getReference().child("Investors");
        String invKey=getIntent().getStringExtra("inkey");
        String rifemail=getIntent().getStringExtra("iyemail");



        iDeleteReff=FirebaseDatabase.getInstance().getReference().child("validateinvestorInfo").child(invKey);


        panqu= FirebaseDatabase.getInstance().getReference().child("validateInvestorPB").orderByChild("investorEmail").equalTo(rifemail);


        panqu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object bk = map.get("BankAccountNo");
                    Object ifk=map.get("IFSCCode");
                    Object mno=map.get("MobileNo");
                    Object purl=map.get("PanUrl");
                    Object panK=map.get("pKey");

                    pnk=String.valueOf(panK);






                     bkno= String.valueOf(bk);
                     yvbnkno.setText(bkno);

                     ifscno=String.valueOf(ifk);
                     yvifsc.setText(ifscno);

                      mobno=String.valueOf(mno);
                     yvmobno.setText(mobno);

                     oPanUrl=String.valueOf(purl);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



         zvName=getIntent().getStringExtra("iyname");
         zvdob=getIntent().getStringExtra("iydob");
         zvgender=getIntent().getStringExtra("iygender");
         zvEmail=getIntent().getStringExtra("iyemail");
         zvKyctype=getIntent().getStringExtra("iydocType");
         zvkycUrl=getIntent().getStringExtra("iykycUrl");


        yvName.setText(zvName);
        yvdob.setText(zvdob);
        yvGender.setText(zvgender);
        yvEmail.setText(zvEmail);
        yvKyctype.setText(zvKyctype);

        btniApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kInv=iApprovedReff.push().getKey();
                HashMap invmap=new HashMap();
                invmap.put("InvestorName",zvName);
                invmap.put("DOB",zvdob);
                invmap.put("Gender",zvgender);
                invmap.put("kycType",zvKyctype);
                invmap.put("kycUrl",zvkycUrl);
                invmap.put("BankAccountNo",bkno);
                invmap.put("PanUrl",oPanUrl);
                invmap.put("IFSCCode",ifscno);
                invmap.put("InvestorPhone",mobno);
                invmap.put("InvestorEmail",zvEmail);

                iApprovedReff.child(kInv).setValue(invmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RequestinvestorInfofl.this,"Investor Data Approved",Toast.LENGTH_SHORT).show();
                        final String currentTime,currentDate;
                        Calendar calfordate= Calendar.getInstance();

                        SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
                        currentDate=CurrentDate.format(calfordate.getTime());

                        SimpleDateFormat CurrentTime=new SimpleDateFormat("HH:mm:ss a");
                        currentTime=CurrentTime.format(calfordate.getTime());

                        Intent intent=new Intent(RequestinvestorInfofl.this,Emailtoinvestor.class);
                        String subg="  Investor account opening  request Approved";
                        String msgstr="  Your profile is approved for investment through E-Crowd Platform on: "+currentDate+" at: "+currentTime+". \n Thanks and Regards \n E-Crwod Team";
                        intent.putExtra("investormail",zvEmail);
                        intent.putExtra("itext",msgstr);
                        intent.putExtra("isubj",subg);
                        startActivity(intent);


                        iDeleteReff.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }
                        });

                    }
                });



            }
        });


        btniReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDeleteReff.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(RequestinvestorInfofl.this,"Investor Data Rejected",Toast.LENGTH_SHORT).show();

                        final String currentTime,currentDate;
                        Calendar calfordate= Calendar.getInstance();

                        SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
                        currentDate=CurrentDate.format(calfordate.getTime());

                        SimpleDateFormat CurrentTime=new SimpleDateFormat("HH:mm:ss a");
                        currentTime=CurrentTime.format(calfordate.getTime());

                        Intent intent=new Intent(RequestinvestorInfofl.this,Emailtoinvestor.class);
                        String subg="  Investor account opening  request Declined";
                        String msgstr="  Your profile is rejected by the verification team for investment through E-Crowd Platform on: "+currentDate+" at: "+currentTime+". \n Thanks and Regards \n E-Crwod Team";
                        intent.putExtra("investormail",zvEmail);
                        intent.putExtra("itext",msgstr);
                        intent.putExtra("isubj",subg);
                        startActivity(intent);

                    }
                });
            }
        });


        btnviKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kycIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(zvkycUrl));
                startActivity(kycIntent);
            }
        });


        btnvipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(oPanUrl));
                startActivity(panIntent);
            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(RequestinvestorInfofl.this,RequestinvestorInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
