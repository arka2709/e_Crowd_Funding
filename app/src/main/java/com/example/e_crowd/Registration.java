package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class Registration extends AppCompatActivity {
    private EditText tuemail,  tupassword, tuconfpass;
    FirebaseAuth userAuth;
    private TextView adsignup,adLogin;
    private Button signup;
    private Button log;
    DatabaseReference userReff;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String regEmailString;

    ProgressBar irprogbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signup = (Button) findViewById(R.id.signuser);
        tuemail = (EditText) findViewById(R.id.editTextEmail);
        tupassword = (EditText) findViewById(R.id.editTextpu);
        tuconfpass = (EditText) findViewById(R.id.editTextcpu);
        log = (Button) findViewById(R.id.loginus);
        adsignup = (TextView) findViewById(R.id.signupCompany);
        userAuth=FirebaseAuth.getInstance();
        adLogin=findViewById(R.id.tvadminlogin);

        irprogbar=findViewById(R.id.progressBar4);

        irprogbar.setVisibility(View.INVISIBLE);





        adsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, company_signup.class));
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

        adLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Adminlogin.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    final String cust_email=tuemail.getText().toString();
                    final String cust_password=tupassword.getText().toString();
                    final String cust_cpasswd=tuconfpass.getText().toString();


                    userAuth.createUserWithEmailAndPassword(cust_email,cust_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                sendEmailverific();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof FirebaseAuthUserCollisionException ){
                                Toast.makeText(Registration.this,"Email already exists",Toast.LENGTH_LONG).show();
                                irprogbar.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(Registration.this, "Error in registration", Toast.LENGTH_LONG).show();
                                irprogbar.setVisibility(View.GONE);
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

    private Boolean validate(){
        Boolean result=false;
         String email=tuemail.getText().toString();
         String password=tupassword.getText().toString();
         String cpasswd=tuconfpass.getText().toString();


         if(TextUtils.isEmpty(email))
        {
            Toast.makeText(Registration.this,"Enter email",Toast.LENGTH_LONG).show();
        }
        else if(!email.matches(emailpattern)){
            Toast.makeText(Registration.this,"Invalid Email",Toast.LENGTH_LONG).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(Registration.this,"Enter Password",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(cpasswd))
        {
            Toast.makeText(Registration.this,"Please reenter password",Toast.LENGTH_LONG).show();
        }
        else if(password.length()<6)
        {
            Toast.makeText(Registration.this,"Password length should be greater than 6 characters",Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(cpasswd))
        {
            Toast.makeText(Registration.this,"Password in both field should be same",Toast.LENGTH_LONG).show();
        }
        else if(!email.matches(emailpattern))
        {
            Toast.makeText(Registration.this,"Incorrect Email",Toast.LENGTH_LONG).show();
        }
        else{
            irprogbar.setVisibility(View.VISIBLE);
            result=true;
        }
        return result;

    }


  private void sendEmailverific(){
      FirebaseUser fbuser=userAuth.getCurrentUser();
      if(fbuser!=null)
      {
          fbuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful()){
                      irprogbar.setVisibility(View.GONE);
                      Toast.makeText(Registration.this,"Registration success\nEmail Verification sent",Toast.LENGTH_LONG).show();
                      userAuth.signOut();


                  }
                  else {
                      irprogbar.setVisibility(View.GONE);
                      Toast.makeText(Registration.this,"Email Verification could not be done",Toast.LENGTH_LONG).show();

                  }

              }
          });
      }
  }

}




