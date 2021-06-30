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

public class Login extends AppCompatActivity {
    EditText tuemail,tupassword;
    Button loginbt;
    TextView tvforgot;
    FirebaseAuth uslogauth;
    ProgressBar loginpgbar;
    DatabaseReference userloginReff;
    String liEmailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tuemail=(EditText)findViewById(R.id.editlogemail);
        tupassword=(EditText)findViewById(R.id.editTextpu);
        loginbt=(Button)findViewById(R.id.login);
        tvforgot=(TextView)findViewById(R.id.forgotbtn);
        uslogauth=FirebaseAuth.getInstance();
        loginpgbar=findViewById(R.id.progressBar2);


        loginpgbar.setVisibility(View.INVISIBLE);


        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,custfgpassword.class));
            }
        });


        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=tuemail.getText().toString();
                String password=tupassword.getText().toString();
                validatecredentials(email,password);


            }
        });

    }

    private void validatecredentials(final String email, final String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(Login.this,"Enter email",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(Login.this,"Enter password",Toast.LENGTH_LONG).show();
        }


        else {
            loginpgbar.setVisibility(View.VISIBLE);
            uslogauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkVerify();
                    }

                    else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                        loginpgbar.setVisibility(View.GONE);
                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(Login.this, "Incorrect Email", Toast.LENGTH_LONG).show();
                        loginpgbar.setVisibility(View.GONE);
                    }

                    else {
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        loginpgbar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    private void checkVerify(){
                loginpgbar.setVisibility(View.VISIBLE);
                FirebaseUser fbuser=uslogauth.getCurrentUser();
                Boolean flag=fbuser.isEmailVerified();
                if(flag){
                    startActivity(new Intent(Login.this,useractivity.class));

                }

                else {
                    loginpgbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this,"Verify Email",Toast.LENGTH_SHORT).show();
                    uslogauth.signOut();
                }

            }

    }

