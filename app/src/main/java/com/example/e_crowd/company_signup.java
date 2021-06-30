package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class company_signup extends AppCompatActivity {
    EditText adem,adps,adcps;
    Button adsignu,adlogin;
    TextView tvuser;
    FirebaseAuth adminauth;
    DatabaseReference adminreff;
    ProgressBar cprogbar;
    String coEmailstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_signup);
        adem=(EditText)findViewById(R.id.admemail);
        adps=(EditText)findViewById(R.id.adpassword);
        adcps=(EditText)findViewById(R.id.adconpassword);
        tvuser=(TextView)findViewById(R.id.usermd);
        adsignu=(Button)findViewById(R.id.signupadmin);
        adlogin=(Button)findViewById(R.id.loginadmin);
        cprogbar=findViewById(R.id.progressBar5);

        cprogbar.setVisibility(View.INVISIBLE);

        adminauth=FirebaseAuth.getInstance();


        tvuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(company_signup.this,dashboardHome.class));
            }
        });

        adlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(company_signup.this, company_login.class));
            }
        });





        adsignu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    final String admin_email=adem.getText().toString().trim();
                    final String admin_pass=adps.getText().toString().trim();


                    adminauth.createUserWithEmailAndPassword(admin_email,admin_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful())
                      {
                         sendEmailVerification();
                      }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(company_signup.this,"Email already exists",Toast.LENGTH_LONG).show();
                                cprogbar.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(company_signup.this, "Error in registration", Toast.LENGTH_LONG).show();
                                cprogbar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });




    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), dashboardHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private Boolean validate()
    {
        Boolean result=false;
        String eml=adem.getText().toString();
        String pass=adps.getText().toString();
        String cps=adcps.getText().toString();
        if(TextUtils.isEmpty(eml))
        {
            Toast.makeText(company_signup.this,"Enter email", Toast.LENGTH_LONG).show();
        }

        else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(company_signup.this,"Enter password",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(cps))
        {
            Toast.makeText(company_signup.this,"Enter password again",Toast.LENGTH_LONG).show();
        }

        else if(pass.length()<6)
        {
            Toast.makeText(company_signup.this,"Weak Password",Toast.LENGTH_LONG).show();
        }
        else if(!pass.equals(cps))
        {
            Toast.makeText(company_signup.this,"Password in both field should be same",Toast.LENGTH_LONG).show();
        }

        else{

            cprogbar.setVisibility(View.VISIBLE);
            result=true;
        }
        return result;


    }

    private  void sendEmailVerification(){
        FirebaseUser firebaseUser=adminauth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        cprogbar.setVisibility(View.GONE);
                        Toast.makeText(company_signup.this,"Successfully Registered\nEmail Verification Sent",Toast.LENGTH_SHORT).show();
                        adminauth.signOut();


                    }else
                    {
                        cprogbar.setVisibility(View.GONE);
                        Toast.makeText(company_signup.this,"Email Verification unsuccessful",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}

