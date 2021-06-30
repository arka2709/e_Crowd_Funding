package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class requestCompanyInfo extends AppCompatActivity {
    TextView reCompname,reCompdesc,reCompPrice,reCompinterest,reCompFund,reCompTenure,reCompPhone,reCompEmail;
    ImageView reImageView;
    Button btLD,btApprove,btReject;
    DatabaseReference reqReff,approvedReff,deleteReff;

    String vrireImageUrl,vriName,vriDesc,vriPrice,vriinterest,vritenure,vrifundt,vriPhone,vricompemail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_company_info);
        reCompname=findViewById(R.id.req_tvcopname_info);
        reCompdesc=findViewById(R.id.req_tvcompdesc_info);
        reCompPrice=findViewById(R.id.req_tvprice);
        reCompinterest=findViewById(R.id.req_tvinteres);
        reCompFund=findViewById(R.id.req_fundtg);
        reCompTenure=findViewById(R.id.req_fundduration);
        reCompPhone=findViewById(R.id.req_phSpace);
        reCompEmail=findViewById(R.id.req_email_Space);
        btLD=findViewById(R.id.req_docs);
        btApprove=findViewById(R.id.btnapprove);
        btReject=findViewById(R.id.btnReject);
        reImageView=findViewById(R.id.req_imgsinglview_info);
        reqReff= FirebaseDatabase.getInstance().getReference().child("CompanyRequests");
        approvedReff=FirebaseDatabase.getInstance().getReference().child("Companies");






        btLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requestCompanyInfo.this,docverification.class);
                intent.putExtra("rciemailIntent",vricompemail);
                startActivity(intent);
            }
        });





        String reCompanyKey=getIntent().getStringExtra("rcompKey");

        deleteReff=FirebaseDatabase.getInstance().getReference().child("CompanyRequests").child(reCompanyKey);

        reqReff.child(reCompanyKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    CompanyRequests cr=dataSnapshot.getValue(CompanyRequests.class);
                    reCompname.setText(cr.getCompanyName());
                    reCompdesc.setText("Company Description:"+cr.getCompanyDescription());
                    reCompPrice.setText("Rs."+cr.getPrice());
                    reCompinterest.setText(cr.getInterestOffered()+"% P.A");
                    reCompFund.setText("Rs."+cr.getFundtarget());
                    reCompTenure.setText(cr.getTenure()+" year");
                    reCompPhone.setText(cr.getCompanyPhone());
                    reCompEmail.setText(cr.getRegisteredBy());
                    Picasso.get().load(cr.getImageUrl()).into(reImageView);
                    vrireImageUrl=cr.getImageUrl();
                    vriName=cr.getCompanyName();
                    vriDesc=cr.getCompanyDescription();
                    vriPrice=cr.getPrice();
                    vriinterest=cr.getInterestOffered();
                    vrifundt=cr.getFundtarget();
                    vritenure=cr.getTenure();
                    vriPhone=cr.getCompanyPhone();
                    vricompemail=cr.getRegisteredBy();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String currentTime,currentDate;
                Calendar calfordate= Calendar.getInstance();

                SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
                    currentDate=CurrentDate.format(calfordate.getTime());

                SimpleDateFormat CurrentTime=new SimpleDateFormat("HH:mm:ss a");
                    currentTime=CurrentTime.format(calfordate.getTime());

                HashMap hashMap=new HashMap();
                hashMap.put("CompanyName",vriName);
                hashMap.put("CompanyDescription",vriDesc);
                hashMap.put("Price",vriPrice);
                hashMap.put("InterestOffered",vriinterest);
                hashMap.put("Fundtarget",vrifundt);
                hashMap.put("Tenure",vritenure);
                hashMap.put("Phone",vriPhone);
                hashMap.put("RegisteredBy",vricompemail);
                hashMap.put("RegistrationDate",currentDate);
                hashMap.put("RegistrationTime",currentTime);
                hashMap.put("ImageUrl",vrireImageUrl);



                String aprKey=approvedReff.push().getKey();



                approvedReff.child(aprKey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(requestCompanyInfo.this,"Company Added Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(requestCompanyInfo.this,EmailtoCompany.class);
                        String subg="  Fund Raising request Approved";
                        String msgstr="  Your Company "+vriName+" is approved for Fund Raising through E-Crowd Platform on: "+currentDate+" at: "+currentTime+". \n Thanks and Regards \n E-Crwod Team";
                        intent.putExtra("companymail",vricompemail);
                        intent.putExtra("text",msgstr);
                        intent.putExtra("subj",subg);
                        startActivity(intent);

                        deleteReff.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });


            }
        });


        btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentTime,currentDate;
                Calendar calfordate= Calendar.getInstance();

                SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
                currentDate=CurrentDate.format(calfordate.getTime());

                SimpleDateFormat CurrentTime=new SimpleDateFormat("HH:mm:ss a");
                currentTime=CurrentTime.format(calfordate.getTime());
                Toast.makeText(requestCompanyInfo.this,"Company Request Removed",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(requestCompanyInfo.this,EmailtoCompany.class);
                String subg="Fund Raising request Declined";
                String msgstr="Your Company "+vriName+" is declined for Fund Raising through E-Crowd Platform on: "+currentDate+" at: "+currentTime+". \n Thanks and Regards \n E-Crwod Team";
                intent.putExtra("companymail",vricompemail);
                intent.putExtra("text",msgstr);
                intent.putExtra("subj",subg);
                startActivity(intent);



                deleteReff.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });





    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(requestCompanyInfo.this,requestcompanyActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

