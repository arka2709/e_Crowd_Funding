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

public class uploadcdocs3 extends AppCompatActivity {

    private Button chbrfile, upbrfile;
    private static int brocCode = 101;
    TextView brpath;
    ProgressBar pgbrbar;
    DatabaseReference dbbrocreff;
    StorageReference stbrocreff;
    Uri brUri;

    private Button btnupdback3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcdocs3);
        chbrfile = findViewById(R.id.choosebrdoc);
        upbrfile = findViewById(R.id.upldbrdoc);
        brpath = findViewById(R.id.pathbrdoc);
        pgbrbar = findViewById(R.id.progrbroch);
        btnupdback3=findViewById(R.id.updback3);
        dbbrocreff = FirebaseDatabase.getInstance().getReference().child("CompanyBrochureDocs");
        stbrocreff = FirebaseStorage.getInstance().getReference().child("Brochure Documents");

        pgbrbar.setVisibility(View.GONE);

        final String cbremail=getIntent().getStringExtra("adrproofemail");
        final String cbrname=getIntent().getStringExtra("adrpoofname");
        final String cbrphone=getIntent().getStringExtra("adrproofphone");

        chbrfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, brocCode);

            }
        });


        btnupdback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs3.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(uploadcdocs3.this,companyMode.class);
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






        upbrfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String brocpath=brpath.getText().toString();
                if(TextUtils.isEmpty(brocpath))
                {
                    Toast.makeText(uploadcdocs3.this,"Please select Brochure Documents",Toast.LENGTH_SHORT).show();
                }


                else
                {
                    pgbrbar.setVisibility(View.VISIBLE);
                    final String brKey=dbbrocreff.push().getKey();
                    stbrocreff.child(brKey+".pdf").putFile(brUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            stbrocreff.child(brKey+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap hashmp=new HashMap();
                                    hashmp.put("CompanyEmail",cbremail);
                                    hashmp.put("CompanyName",cbrname);
                                    hashmp.put("BrochureUrl",uri.toString());
                                    hashmp.put("Companyphone",cbrphone);

                                    dbbrocreff.child(brKey).setValue(hashmp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(uploadcdocs3.this,"Document Uploaded",Toast.LENGTH_SHORT).show();
                                            Intent brintent=new Intent(uploadcdocs3.this,uploaddocs4.class);
                                            brintent.putExtra("brochureemail",cbremail);
                                            brintent.putExtra("brochurename",cbrname);
                                            brintent.putExtra("brochurephone",cbrphone);
                                            startActivity(brintent);
                                        }
                                    });

                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                            pgbrbar.setProgress((int) progress);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs3.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(uploadcdocs3.this,companyMode.class);
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
        if(requestCode==brocCode && data!=null)
        {
            brUri=data.getData();
            brpath.setText(brUri.getPath());
        }
    }
}
