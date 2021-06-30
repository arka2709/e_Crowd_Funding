package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class useractivity extends AppCompatActivity {
    private CardView explorebt;
    private CardView mycartbtn;
    private CardView profilebtn;
    private CardView cvmyprofile;
    private CardView logout;
    TextView usert;

    FirebaseAuth myirauth;


    Query dbimode,dbimode2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);
        explorebt=findViewById(R.id.button_explore);
        mycartbtn=findViewById(R.id.button_cart);
        profilebtn=findViewById(R.id.button_profile);
        usert=findViewById(R.id.idinvestor);
        cvmyprofile=findViewById(R.id.btnvprofile);
        logout=findViewById(R.id.invlogOut);

        myirauth=FirebaseAuth.getInstance();



        dbimode= FirebaseDatabase.getInstance().getReference().child("Investors").orderByChild("InvestorEmail").equalTo(myirauth.getCurrentUser().getEmail());
        dbimode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot ds:dataSnapshot.getChildren()) {

                   Map<String,Object> map=  (Map<String, Object>) ds.getValue();

                   Object inId=map.get("InvestorEmail");
                   String i=String.valueOf(inId);
                   usert.setText("Investor Email: "+i);


               }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dbimode2=FirebaseDatabase.getInstance().getReference().child("validateinvestorInfo").orderByChild("investorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        dbimode2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    Map<String,Object> map=  (Map<String, Object>) ds.getValue();

                    Object inemail=map.get("investorEmail");
                    String inve=String.valueOf(inemail);
                    usert.setText("Account under verification");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        explorebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usert.getText().toString().equals("Account under verification")){
                    Toast.makeText(useractivity.this,"Only verified users can start investment",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(useractivity.this, home.class));
                }
            }
        });


        mycartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(usert.getText().toString())){
                    Toast.makeText(useractivity.this,"Create investor Profile",Toast.LENGTH_SHORT).show();
                }
                else if(usert.getText().toString().equals("Account under verification")){
                    Toast.makeText(useractivity.this,"Verified Investors can view their investments",Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(useractivity.this,CartActiv.class));
                }
            }
        });





        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(usert.getText().toString())){
                    startActivity(new Intent(useractivity.this,createinvestorProfile.class));
                }
                else if(usert.getText().toString().equals("Account under verification")){
                    Toast.makeText(useractivity.this,"Account is already under verification",Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(useractivity.this,"Investor Profile Already Exists",Toast.LENGTH_SHORT).show();
                }
            }
        });



        cvmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(usert.getText().toString())){
                    Toast.makeText(useractivity.this,"Create Investor Profile",Toast.LENGTH_SHORT).show();
                }

                else if(usert.getText().toString().equals("Account under verification")){
                    Toast.makeText(useractivity.this,"Only verified Investors can view their profile",Toast.LENGTH_SHORT).show();
                }

                else{
                    startActivity(new Intent(useractivity.this,investorProfile.class));
                }
            }
        });





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                FirebaseAuth.getInstance().signOut();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onBackPressed() {
       AlertDialog.Builder builder=new AlertDialog.Builder(useractivity.this);
       builder.setTitle("Confirmation");
       builder.setMessage("Sign Out?");
       builder.setCancelable(false);
       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               Intent intent = new Intent(getApplicationContext(), Login.class);
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
