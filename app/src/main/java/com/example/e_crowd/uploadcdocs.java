package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class uploadcdocs extends AppCompatActivity {
    private Button chrfile, uprfile;
    private static int regCode = 101;
    TextView frpath;
    ProgressBar pgrbar;
    DatabaseReference dbdocregreff;
    StorageReference stdocregreff;
    Uri dregUri;
    String regUrl;
    private Button btnupdback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcdocs);
        chrfile = findViewById(R.id.chooseRdoc);
        uprfile = findViewById(R.id.upldRdoc);
        frpath = findViewById(R.id.pathreg);
        pgrbar = findViewById(R.id.progreg);
        dbdocregreff = FirebaseDatabase.getInstance().getReference().child("CompanyRegDocs");
        stdocregreff = FirebaseStorage.getInstance().getReference().child("Registration Documents");
        btnupdback=findViewById(R.id.updback);
        pgrbar.setVisibility(View.GONE);

        final String cupregemail=getIntent().getStringExtra("cactcEmail");

        final String cuprename=getIntent().getStringExtra("cactName");

        final String cuprephone=getIntent().getStringExtra("cactPhone");


        chrfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, regCode);

            }
        });


        btnupdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(uploadcdocs.this,companyMode.class);
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
        });



        uprfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rpath=frpath.getText().toString();
                if(TextUtils.isEmpty(rpath))
                {
                    Toast.makeText(uploadcdocs.this,"Please select Registration Documents",Toast.LENGTH_SHORT).show();
                }


                else
                    {
                        pgrbar.setVisibility(View.VISIBLE);
                        final String regKey=dbdocregreff.push().getKey();
                        stdocregreff.child(regKey+".pdf").putFile(dregUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                stdocregreff.child(regKey+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        HashMap hashmp=new HashMap();
                                        hashmp.put("CompanyEmail",cupregemail);
                                        hashmp.put("CompanyName",cuprename);
                                        hashmp.put("RegistrationUrl",uri.toString());
                                        hashmp.put("Companyphone",cuprephone);

                                        dbdocregreff.child(regKey).setValue(hashmp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(uploadcdocs.this,"Document Uploaded",Toast.LENGTH_SHORT).show();
                                                Intent rintent=new Intent(uploadcdocs.this,uploadcdocs2.class);
                                                rintent.putExtra("updregmail",cupregemail);
                                                rintent.putExtra("updrename",cuprename);
                                                rintent.putExtra("updrephone",cuprephone);
                                                startActivity(rintent);
                                            }
                                        });

                                    }
                                });

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                                pgrbar.setProgress((int) progress);
                            }
                        });
                    }
            }
        });



    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(uploadcdocs.this,companyMode.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==regCode && data!=null)
        {
            dregUri=data.getData();
            frpath.setText(dregUri.getPath());

        }
    }
}


