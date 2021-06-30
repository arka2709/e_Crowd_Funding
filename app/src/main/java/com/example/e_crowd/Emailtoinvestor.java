package com.example.e_crowd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Emailtoinvestor extends AppCompatActivity {

    private TextView toimail,toisubject,toimsg;
    private Button btisend,btiHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailtoinvestor);
        toimail=findViewById(R.id.itoinvemail);
        toisubject=findViewById(R.id.itosubj);
        toimsg=findViewById(R.id.itomsg);

        btisend=findViewById(R.id.isendEmailbtn);
        btiHome=findViewById(R.id.isendhomebtn);


        btiHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Emailtoinvestor.this,adminHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        final String ivmailid=getIntent().getStringExtra("investormail");
        final String ivsubject=getIntent().getStringExtra("isubj");
        final String ivmsg=getIntent().getStringExtra("itext");

        toimail.setText("  "+ivmailid);
        toisubject.setText("  "+ivsubject);
        toimsg.setText("  "+ivmsg);


        btisend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{ivmailid});
                intent.putExtra(Intent.EXTRA_SUBJECT,ivsubject);
                intent.putExtra(Intent.EXTRA_TEXT,ivmsg);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose Email Client"));
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Emailtoinvestor.this,adminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
