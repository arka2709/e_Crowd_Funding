package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class custfgpassword extends AppCompatActivity {
    private EditText etmail;
    private Button rstButton;
    private FirebaseAuth custauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custfgpassword);

        etmail=findViewById(R.id.fgemail);
        rstButton=findViewById(R.id.btReset);
        custauth=FirebaseAuth.getInstance();

        rstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=etmail.getText().toString().trim();

                if(userEmail.equals("")){
                    Toast.makeText(custfgpassword.this,"Please enter your registered Email ID",Toast.LENGTH_LONG).show();
                }
                else
                {
                    custauth.sendPasswordResetEmail((userEmail)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(custfgpassword.this,"Password reset link sent",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(custfgpassword.this,Login.class));
                            }
                            else {
                                Toast.makeText(custfgpassword.this,"Incorrect Email ID",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
