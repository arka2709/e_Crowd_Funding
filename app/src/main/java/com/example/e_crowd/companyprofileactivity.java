package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class companyprofileactivity extends AppCompatActivity {

    Query cpQuery;
    ImageView ivcpimage;
    TextView tvcpname,tvcpdesc,tvcpprice,tvcpinterest,tvcpfundtg,tvcptenure,tvcpphone,tvcpemail;
    Button btncpdocs;
    String cpaemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyprofileactivity);


        ivcpimage=findViewById(R.id.cp_imgsinglview_info);
        tvcpname=findViewById(R.id.cp_tvcopname_info);
        tvcpdesc=findViewById(R.id.cp_tvcompdesc_info);
        tvcpprice=findViewById(R.id.cp_tvprice);
        tvcpinterest=findViewById(R.id.cp_tvinteres);
        tvcpfundtg=findViewById(R.id.cp_fundtg);
        tvcptenure=findViewById(R.id.cp_fundduration);
        tvcpphone=findViewById(R.id.cp_phSpace);
        tvcpemail=findViewById(R.id.cp_email_Space);

        btncpdocs=findViewById(R.id.btcreq_docs);

        cpQuery= FirebaseDatabase.getInstance().getReference().child("Companies").orderByChild("RegisteredBy").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        cpQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object>map=(Map<String, Object>) ds.getValue();
                    Object opname=map.get("CompanyName");
                    String copname=String.valueOf(opname);
                    tvcpname.setText(copname);

                    Object opdesc=map.get("CompanyDescription");
                    String copdesc=String.valueOf(opdesc);
                    tvcpdesc.setText("Company Descripion: "+copdesc);

                    Object opprice=map.get("Price");
                    String copprice=String.valueOf(opprice);
                    tvcpprice.setText("Rs."+copprice);

                    Object opinterest=map.get("InterestOffered");
                    String copinterest=String.valueOf(opinterest);
                    tvcpinterest.setText(copinterest+"% pa");

                    Object opfundt=map.get("Fundtarget");
                    String copfundt=String.valueOf(opfundt);
                    tvcpfundtg.setText("Rs."+copfundt);


                    Object optenure=map.get("Tenure");
                    String coptenure=String.valueOf(optenure);
                    tvcptenure.setText(coptenure+"years");


                    Object opphone=map.get("Phone");
                    String copphone=String.valueOf(opphone);
                    tvcpphone.setText(copphone);


                    Object opemail=map.get("RegisteredBy");
                    String copemail=String.valueOf(opemail);
                    tvcpemail.setText(copemail);

                    cpaemail=String.valueOf(opemail);


                    Object opimageUrl=map.get("ImageUrl");
                    String copimgUrl=String.valueOf(opimageUrl);
                    Picasso.get().load(copimgUrl).into(ivcpimage);





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btncpdocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(companyprofileactivity.this,companyprofileDocs.class);
               intent.putExtra("cpaEmail",cpaemail);
               startActivity(intent);
            }
        });





    }
}
