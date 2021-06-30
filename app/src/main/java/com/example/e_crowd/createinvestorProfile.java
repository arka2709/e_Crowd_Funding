package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class createinvestorProfile extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private static int requestid=01;
    Uri idUri;
    String idUrl;


    TextView tvdocselect;

    Button cibackbtn;


    ImageView idView;

    DatabaseReference profileReff;
    StorageReference spreff;

    RadioGroup radioGroupkyc;



    Spinner spinGender;
    String genderSelected;

    String gender[]={"Male","Female"};

    ArrayAdapter<String>adapter;

    DatePickerDialog.OnDateSetListener dateSetListener;
    TextView tvDate;
    String date;

    Button btnsubmit;
    EditText edtName;

    ProgressBar iProgbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createinvestor_profile);
        radioGroupkyc=(RadioGroup) findViewById(R.id.rgType);
        spinGender=findViewById(R.id.genderSpinner);
        tvDate=findViewById(R.id.dob);
        profileReff= FirebaseDatabase.getInstance().getReference().child("validateinvestorInfo");
        spreff= FirebaseStorage.getInstance().getReference().child("InvestorID");
        edtName=findViewById(R.id.iName);
        idView=(ImageView)findViewById(R.id.imgviewID);
        btnsubmit=(Button)findViewById(R.id.btnsub);
        iProgbar=findViewById(R.id.iprog);
        tvdocselect=findViewById(R.id.docSelected);
        iProgbar.setVisibility(View.GONE);
        cibackbtn=findViewById(R.id.ivrback);
        radioGroupkyc.setOnCheckedChangeListener(this);


        cibackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(createinvestorProfile.this);
                builder.setMessage("Cancel Registration?");
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(createinvestorProfile.this,useractivity.class);
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



        idView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent,requestid);
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String varName=edtName.getText().toString();
                final String doctype=tvdocselect.getText().toString();
                if(TextUtils.isEmpty(varName))
                {
                    Toast.makeText(createinvestorProfile.this,"Enter your Name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(date))
                {
                    Toast.makeText(createinvestorProfile.this,"Select Birth Date",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(genderSelected))
                {
                    Toast.makeText(createinvestorProfile.this,"Choose Gender",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(idUrl))
                {
                    Toast.makeText(createinvestorProfile.this,"Choose ID image to upload",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(doctype))
                {
                    Toast.makeText(createinvestorProfile.this,"Select ID Type",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    iProgbar.setVisibility(View.VISIBLE);
                    final String iKey=profileReff.push().getKey();
                    spreff.child(iKey+".jpg").putFile(idUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            spreff.child(iKey+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    HashMap iMap=new HashMap();
                                    iMap.put("InvestorName",varName);
                                    iMap.put("kycUrl",uri.toString());
                                    iMap.put("docType",doctype);
                                    iMap.put("DOB",date);
                                    iMap.put("Gender",genderSelected);
                                    iMap.put("investorEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                    profileReff.child(iKey).setValue(iMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                                 Toast.makeText(createinvestorProfile.this,"Details uploaded successfully",Toast.LENGTH_SHORT).show();
                                                 Intent panIntent=new Intent(createinvestorProfile.this,investorpan.class);
                                                 panIntent.putExtra("investorkey",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                                 panIntent.putExtra("investorshName",varName);
                                                 startActivity(panIntent);
                                        }
                                    });

                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                            iProgbar.setProgress((int)progress);
                        }
                    });



                }

            }
        });





        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog=new DatePickerDialog(createinvestorProfile.this,dateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;
               date=dayOfMonth+"/"+month+"/"+year;
               tvDate.setText(date);
            }
        };




        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gender);

        spinGender.setAdapter(adapter);

        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSelected=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(createinvestorProfile.this);
        builder.setMessage("Exit Registration?");
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(createinvestorProfile.this,useractivity.class);
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
        if(requestCode==requestid && data!=null)

        {
            idUri=data.getData();
            idView.setImageURI(idUri);
            idUrl=idUri.getPath();

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){

            case R.id.adhr:
                tvdocselect.setText("Aadhar Card");
                break;

            case R.id.vid:
                tvdocselect.setText("VoterID");
                break;

            case R.id.drl:
                tvdocselect.setText("Driving License");
                break;

            case R.id.psprt:
                tvdocselect.setText("Passport");
                break;


        }
    }


}
