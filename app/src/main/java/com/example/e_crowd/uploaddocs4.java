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

public class uploaddocs4 extends AppCompatActivity {

    private Button chfinfile, upfinfile;
    private static int finCode = 101;
    TextView finpath;
    ProgressBar finbar;
    DatabaseReference dbfinreff;
    StorageReference stfinreff;
    Uri finUri;
    private Button btnupdback4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaddocs4);
        chfinfile = findViewById(R.id.choosefindoc);
        upfinfile = findViewById(R.id.upldfindoc);
        finpath = findViewById(R.id.pathfindoc);
        finbar = findViewById(R.id.progrfin);
        btnupdback4=findViewById(R.id.updback4);
        dbfinreff = FirebaseDatabase.getInstance().getReference().child("CompanyFinancialDocs");
        stfinreff = FirebaseStorage.getInstance().getReference().child("Financial Documents");

        finbar.setVisibility(View.GONE);

        final String cfinemail=getIntent().getStringExtra("brochureemail");
        final String cfinename=getIntent().getStringExtra("brochurename");
        final String cfinephone=getIntent().getStringExtra("brochurephone");

        chfinfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, finCode);

            }
        });


        btnupdback4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(uploaddocs4.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(uploaddocs4.this,companyMode.class);
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




        upfinfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String financepath=finpath.getText().toString();
                if(TextUtils.isEmpty(financepath))
                {
                    Toast.makeText(uploaddocs4.this,"Please select Financial Documents",Toast.LENGTH_SHORT).show();
                }


                else
                {
                    finbar.setVisibility(View.VISIBLE);
                    final String finKey=dbfinreff.push().getKey();
                    stfinreff.child(finKey+".pdf").putFile(finUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            stfinreff.child(finKey+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap hashmp=new HashMap();
                                    hashmp.put("CompanyEmail",cfinemail);
                                    hashmp.put("CompanyName",cfinename);
                                    hashmp.put("FinancialUrl",uri.toString());
                                    hashmp.put("Companyphone",cfinephone);

                                    dbfinreff.child(finKey).setValue(hashmp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(uploaddocs4.this,"Document Uploaded",Toast.LENGTH_SHORT).show();
                                            Intent finintent=new Intent(uploaddocs4.this,uploadfinished.class);
                                            finintent.putExtra("coName",cfinename);
                                            startActivity(finintent);
                                        }
                                    });

                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                            finbar.setProgress((int) progress);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(uploaddocs4.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(uploaddocs4.this,companyMode.class);
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
        if(requestCode==finCode && data!=null){
            finUri=data.getData();
            finpath.setText(finUri.getPath());
        }
    }
}
