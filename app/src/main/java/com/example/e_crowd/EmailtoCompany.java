package com.example.e_crowd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmailtoCompany extends AppCompatActivity {
    TextView tomail,tosubject,tomesg;
    Button sndmail,btnetchome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailto_company);
        tomail=findViewById(R.id.tocompemail);
        tosubject=findViewById(R.id.tosubj);
        tomesg=findViewById(R.id.tomsg);
        sndmail=findViewById(R.id.sendEmailbtn);
        btnetchome=findViewById(R.id.etcbtnHome);

        final String mailid=getIntent().getStringExtra("companymail");
        final String subject=getIntent().getStringExtra("subj");
        final String msg=getIntent().getStringExtra("text");

        tomail.setText("  "+mailid);
        tosubject.setText("  "+subject);
        tomesg.setText("  "+msg);

        sndmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{mailid});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,msg);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose Email Client"));

            }
        });

        btnetchome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(EmailtoCompany.this,adminHome.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EmailtoCompany.this,adminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
