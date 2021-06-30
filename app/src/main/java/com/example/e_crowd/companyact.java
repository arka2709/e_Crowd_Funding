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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class companyact extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE =101 ;
    private ImageView com_image;
    private EditText cname,cdesc,cinteres,cfund,cprice,cTenure,cPhone;
    private Button adcmpbt;
    private Button cabackbtn;
    private ProgressBar progressBar;
    private CheckBox cb;

    Uri imageuri;
    boolean isImageAdded=false;
    DatabaseReference Dataref;
    StorageReference Storageref;
    TextView tvifilename;
    TextView tvTandc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyact);
        com_image=(ImageView)findViewById(R.id.image_add);
        cname=(EditText) findViewById(R.id.etcompany_name);
        cdesc=(EditText) findViewById(R.id.comp_description);
        cinteres=(EditText) findViewById(R.id.roi);
        cfund=(EditText) findViewById(R.id.fund_target);
        cprice=(EditText) findViewById(R.id.price_share);
        cTenure=(EditText)findViewById(R.id.editTenure);
        cPhone=findViewById(R.id.etphone);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        adcmpbt=(Button)findViewById(R.id.Addbt);
        tvifilename=findViewById(R.id.imageFilename);
        tvTandc=findViewById(R.id.tandc);
        cb=findViewById(R.id.check);
        cabackbtn=findViewById(R.id.comacback);
        adcmpbt.setEnabled(false);



        tvTandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(companyact.this,termsandconditions.class));
            }
        });


        cabackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(companyact.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(companyact.this,companyMode.class);
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



        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    adcmpbt.setEnabled(true);
                }
                else{
                    adcmpbt.setEnabled(false);
                }
            }
        });

        progressBar.setVisibility(View.GONE);

        Dataref= FirebaseDatabase.getInstance().getReference().child("CompanyRequests");
        Storageref= FirebaseStorage.getInstance().getReference().child("Company images");



        com_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);

            }
        });
        adcmpbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String compname=cname.getText().toString();
                final String compdescrip=cdesc.getText().toString();
                final String cinterestpk=cinteres.getText().toString();
                final String cfunding=cfund.getText().toString();
                final String cpricepk=cprice.getText().toString();
                final String ctenureyrs=cTenure.getText().toString();
                final String cphhoneno=cPhone.getText().toString();
                final String imgfilepath=tvifilename.getText().toString();

                if(TextUtils.isEmpty(compname))
                {
                    Toast.makeText(companyact.this,"Enter Company Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(compdescrip))
                {
                    Toast.makeText(companyact.this,"Enter Company Name",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cinterestpk))
                {
                    Toast.makeText(companyact.this,"Enter Interest Rate",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cfunding))
                {
                    Toast.makeText(companyact.this,"Enter Funding Target",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cpricepk))
                {
                    Toast.makeText(companyact.this,"Enter Price per unit",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(ctenureyrs))
                {
                    Toast.makeText(companyact.this,"Enter Company Name",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cphhoneno) )
                {
                    Toast.makeText(companyact.this,"Enter Company Name",Toast.LENGTH_SHORT).show();
                }

                else if(cPhone.length()!=10)
                {
                    Toast.makeText(companyact.this,"Phone number should have 10 characters ",Toast.LENGTH_SHORT).show();
                }


                else if(TextUtils.isEmpty(imgfilepath))
                {
                    Toast.makeText(companyact.this,"Please choose an image",Toast.LENGTH_SHORT).show();
                }




                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    final String key=Dataref.push().getKey();
                    Storageref.child(key+".jpg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Storageref.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {

                                    HashMap hashMap=new HashMap();
                                    hashMap.put("CompanyName",compname);
                                    hashMap.put("CompanyDescription",compdescrip);
                                    hashMap.put("CompanyPhone",cphhoneno);
                                    hashMap.put("InterestOffered",cinterestpk);
                                    hashMap.put("Fundtarget",cfunding);
                                    hashMap.put("Tenure",ctenureyrs);
                                    hashMap.put("Price",cpricepk);
                                    hashMap.put("ImageUrl",uri.toString());
                                    hashMap.put("RegisteredBy", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                    Dataref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(companyact.this,"Details Added",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(companyact.this, uploadcdocs.class);
                                            intent.putExtra("cactcEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                            intent.putExtra("cactName",compname);
                                            intent.putExtra("cactPhone",cphhoneno);
                                            startActivity(intent);
                                        }
                                    });


                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                            progressBar.setProgress((int) progress);

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(companyact.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(companyact.this,companyMode.class);
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
        if(requestCode==REQUEST_CODE_IMAGE && data!=null)
        {
            imageuri=data.getData();
            isImageAdded=true;
            com_image.setImageURI(imageuri);
            tvifilename.setText(imageuri.getPath());
        }
    }
}