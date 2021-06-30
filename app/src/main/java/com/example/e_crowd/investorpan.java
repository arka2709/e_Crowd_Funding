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

public class investorpan extends AppCompatActivity {
    ImageView panView;
    private static int rpancode=02;
    Uri panUri;
    TextView panpath;
    DatabaseReference panReff;
    StorageReference panstReff;
    EditText edaccno,edifsc,etimobileno;
    Button btnP;
    Button ipbackbt;
    ProgressBar pgpanBar;
    String ifscPattern = "^[A-Z]{4}0[A-Z0-9]{6}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investorpan);
        panView=findViewById(R.id.imgPan);
        panpath=findViewById(R.id.pathpanreg);
        ipbackbt=findViewById(R.id.ivpnback);
        panReff= FirebaseDatabase.getInstance().getReference().child("validateInvestorPB");
        panstReff= FirebaseStorage.getInstance().getReference().child("Investor Pan");
        edaccno=(EditText)findViewById(R.id.etbankaccnt);
        edifsc=(EditText)findViewById(R.id.etifsc);
        etimobileno=(EditText)findViewById(R.id.etmobileno);
        btnP=findViewById(R.id.upldpdoc);
        pgpanBar=(ProgressBar)findViewById(R.id.progpan);
        pgpanBar.setVisibility(View.GONE);
        panView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panIntent=new Intent();
                panIntent.setType("image/*");
                panIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(panIntent,rpancode);

            }
        });
        final String inm=getIntent().getStringExtra("investorshName");


        ipbackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(investorpan.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(investorpan.this,useractivity.class);
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


        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=panpath.getText().toString();
                final String ano=edaccno.getText().toString();
                final String ifsccd=edifsc.getText().toString();
                final String mno=etimobileno.getText().toString();
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(investorpan.this,"Select PAN image file",Toast.LENGTH_SHORT).show();
                }
                else if(ano.length()<9){
                    Toast.makeText(investorpan.this,"Account Number should be 9-18 characters long",Toast.LENGTH_SHORT).show();
                }
                else if(ano.length()>18){
                    Toast.makeText(investorpan.this,"Account Number should be 9-18 characters long",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ano)){
                    Toast.makeText(investorpan.this,"Enter Account Number",Toast.LENGTH_SHORT).show();
                }
                else if(!ifsccd.matches("^[A-Z]{4}0[A-Z0-9]{6}$")){
                    Toast.makeText(investorpan.this,"Incorrect IFSC Code, Please check again",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(ifsccd)){
                    Toast.makeText(investorpan.this,"Enter IFSC Code",Toast.LENGTH_SHORT).show();
                }

                else if(mno.length()!=10)
                {
                    Toast.makeText(investorpan.this,"Mobile number should have 10 digits",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mno))
                {
                    Toast.makeText(investorpan.this,"Enter Mobile Number",Toast.LENGTH_SHORT).show();
                }

                else
                {

                pgpanBar.setVisibility(View.VISIBLE);
                final String pankey=panReff.push().getKey();
                panstReff.child(pankey+".jpg").putFile(panUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        panstReff.child(pankey+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap panMap=new HashMap();
                                panMap.put("PanUrl",uri.toString());
                                panMap.put("investorEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                panMap.put("InvestorName",inm);
                                panMap.put("BankAccountNo",ano);
                                panMap.put("IFSCCode",ifsccd);
                                panMap.put("MobileNo",mno);
                                panMap.put("pKey",pankey);
                                panReff.child(pankey).setValue(panMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(investorpan.this,"PAN image added successfully",Toast.LENGTH_SHORT).show();
                                        Intent intentp= new Intent(investorpan.this,investorDone.class);
                                        intentp.putExtra("InvName",inm);
                                        startActivity(intentp);
                                    }
                                });
                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                        pgpanBar.setProgress((int) progress);


                    }
                });

            }
            }
        });




    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(investorpan.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(investorpan.this,useractivity.class);
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
        if (requestCode==rpancode &&data!=null){
            panUri=data.getData();
            panView.setImageURI(panUri);
            panpath.setText(panUri.getPath());



        }
    }
}
