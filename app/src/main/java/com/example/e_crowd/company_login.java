package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class company_login extends AppCompatActivity {
    EditText ademl,adpass;
    Button logadmin,regcompbtn;
    Button Btnforgot;
    FirebaseAuth adminlogauth;
    ProgressBar clogpgbar;
    DatabaseReference clreff;
    String clEmailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        ademl=(EditText)findViewById(R.id.editemail);
        adpass=(EditText)findViewById(R.id.editpass);
        logadmin=(Button)findViewById(R.id.loginadbt);
        Btnforgot=(Button)findViewById(R.id.btnfgpwd);
        regcompbtn=(Button)findViewById(R.id.regcompbt);
        adminlogauth=FirebaseAuth.getInstance();
        FirebaseUser user=adminlogauth.getCurrentUser();
        clogpgbar=findViewById(R.id.progressBar3);
        clogpgbar.setVisibility(View.INVISIBLE);



        Btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(company_login.this,forgotpassword.class));
            }
        });


        logadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=ademl.getText().toString();
                String password=adpass.getText().toString();
                validateUser(email,password);
            }
        });


        regcompbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(company_login.this,company_signup.class));
            }
        });


}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), company_signup.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void validateUser(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(company_login.this,"Enter email",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(company_login.this,"Enter password",Toast.LENGTH_LONG).show();
        }

        else if(password.length()<6)
        {
            Toast.makeText(company_login.this,"Weak Password",Toast.LENGTH_LONG).show();
        }



        else {

            clogpgbar.setVisibility(View.VISIBLE);


            adminlogauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                     checkEmailVerification();

                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(company_login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                        clogpgbar.setVisibility(View.GONE);
                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(company_login.this, "Incorrect Email", Toast.LENGTH_LONG).show();
                        clogpgbar.setVisibility(View.GONE);
                    }


                    else {
                        Toast.makeText(company_login.this, "Login Error", Toast.LENGTH_SHORT).show();
                        clogpgbar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }


    }

    private  void checkEmailVerification(){
        FirebaseUser firebaseUser=adminlogauth.getCurrentUser();
        Boolean flag=firebaseUser.isEmailVerified();
        if(flag){
            startActivity(new Intent(company_login.this, companyMode.class));
        }
        else {
            clogpgbar.setVisibility(View.INVISIBLE);
            Toast.makeText(company_login.this,"Verify Email",Toast.LENGTH_SHORT).show();
            adminlogauth.signOut();
        }
    }


}

