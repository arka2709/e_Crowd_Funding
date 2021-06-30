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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonReset;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        editTextEmail=findViewById(R.id.etfgemail);
        buttonReset=findViewById(R.id.btnReset);
        fbAuth=FirebaseAuth.getInstance();

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminemail=editTextEmail.getText().toString().trim();

                if(editTextEmail.equals("")){
                    Toast.makeText(forgotpassword.this,"Please enter your registered email",Toast.LENGTH_SHORT).show();
                }
                else {
                    fbAuth.sendPasswordResetEmail((adminemail)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotpassword.this,"Password reset email sent",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(forgotpassword.this,company_login.class));
                            }
                            else {
                                Toast.makeText(forgotpassword.this,"Incorrect Email ID",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
