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

public class uploadcdocs2 extends AppCompatActivity {

    private Button chadfile, upadfile;
    private static int adCode = 101;
    TextView adpath;
    ProgressBar pgadbar;
    DatabaseReference dbadregreff;
    StorageReference stadregreff;
    Uri adUri;

    private Button btnupdback2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcdocs2);
        chadfile = findViewById(R.id.chooseaddoc);
        upadfile = findViewById(R.id.upldaddoc);
        adpath = findViewById(R.id.pathaddoc);
        pgadbar = findViewById(R.id.prograd);
        btnupdback2=findViewById(R.id.updback2);
        dbadregreff = FirebaseDatabase.getInstance().getReference().child("CompanyAddressDocs");
        stadregreff = FirebaseStorage.getInstance().getReference().child("Address Documents");

        pgadbar.setVisibility(View.GONE);

        final String cademail=getIntent().getStringExtra("updregmail");

        final String cadname=getIntent().getStringExtra("updrename");

        final String cadphone=getIntent().getStringExtra("updrephone");


        chadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, adCode);

            }
        });


        btnupdback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs2.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(uploadcdocs2.this,companyMode.class);
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



        upadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adrspath=adpath.getText().toString();
                if(TextUtils.isEmpty(adrspath))
                {
                    Toast.makeText(uploadcdocs2.this,"Please select Address Documents",Toast.LENGTH_SHORT).show();
                }


                else
                {
                    pgadbar.setVisibility(View.VISIBLE);
                    final String adKey=dbadregreff.push().getKey();
                    stadregreff.child(adKey+".pdf").putFile(adUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            stadregreff.child(adKey+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap hashmp=new HashMap();
                                    hashmp.put("CompanyEmail",cademail);
                                    hashmp.put("CompanyName",cadname);
                                    hashmp.put("AddressProofUrl",uri.toString());
                                    hashmp.put("Companyphone",cadphone);

                                    dbadregreff.child(adKey).setValue(hashmp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(uploadcdocs2.this,"Document Uploaded",Toast.LENGTH_SHORT).show();
                                            Intent aintent=new Intent(uploadcdocs2.this,uploadcdocs3.class);
                                            aintent.putExtra("adrproofemail",cademail);
                                            aintent.putExtra("adrpoofname",cadname);
                                            aintent.putExtra("adrproofphone",cadphone);
                                            startActivity(aintent);
                                        }
                                    });

                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                            pgadbar.setProgress((int) progress);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(uploadcdocs2.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(uploadcdocs2.this,companyMode.class);
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
        if(requestCode==adCode && data!=null)
        {
            adUri=data.getData();
            adpath.setText(adUri.getPath());
        }
    }
}
