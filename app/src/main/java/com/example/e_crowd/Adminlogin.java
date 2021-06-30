package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Adminlogin extends AppCompatActivity {
    private EditText Etuid,Etpwd;
    private Button btlogin;
    DatabaseReference adreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        Etuid=findViewById(R.id.etUsername);
        Etpwd=findViewById(R.id.etpassword);
        btlogin=findViewById(R.id.btnLoginad);
        adreff=FirebaseDatabase.getInstance().getReference().child("AdminCred");


        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String uid=dataSnapshot.child("username").getValue().toString();
                    String pwd=dataSnapshot.child("password").getValue().toString();
                        String uname=Etuid.getText().toString();
                        String upassword=Etpwd.getText().toString();

                        if(TextUtils.isEmpty(uname)){
                            Toast.makeText(Adminlogin.this,"Enter User-Id",Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(upassword)){
                            Toast.makeText(Adminlogin.this,"Enter password",Toast.LENGTH_SHORT).show();
                        }
                        else if(!uname.equals(uid)){
                            Toast.makeText(Adminlogin.this,"Incorrect User-Id",Toast.LENGTH_SHORT).show();
                        }
                        else if(!upassword.equals(pwd)){
                            Toast.makeText(Adminlogin.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Adminlogin.this,"Login Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Adminlogin.this,adminHome.class));
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), dashboardHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
