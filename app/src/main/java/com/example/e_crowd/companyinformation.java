

package com.example.e_crowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class companyinformation extends AppCompatActivity {
    private ImageView imageView;
    private EditText qty;
    private TextView tvname, tvdesc, tvprice, tvinterest, tvfundt, tvfundDura,tvPhone_no;
    private Button btnfraised;
    private Button adtoccartbtn,btnd;
    private CheckBox checkDocx;
    DatabaseReference inforef;

    Query qrin;
    String imagUrl,regBy,priceval,valTenure,valInterest,valFundTg,valCompanyDesc,valcname,Rby;
    String ioemail,ionm,iobnk,ioifsc,iophone,cinfemail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinformation);



        qrin=FirebaseDatabase.getInstance().getReference().child("Investors").orderByChild("InvestorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        imageView = findViewById(R.id.imgsinglview_info);
        tvname = findViewById(R.id.tvcopname_info);
        tvdesc = findViewById(R.id.tvcompdesc_info);
        tvprice = findViewById(R.id.tvprice);
        tvinterest = findViewById(R.id.tvinteres);
        tvfundt = findViewById(R.id.fundtg);
        tvfundDura = findViewById(R.id.fundduration);
        adtoccartbtn = findViewById(R.id.add_cart);
        inforef = FirebaseDatabase.getInstance().getReference().child("Companies");
        qty = findViewById(R.id.etquantity);
        btnfraised=findViewById(R.id.btraised);
        btnd=findViewById(R.id.docs);
        tvPhone_no=(TextView)findViewById(R.id.tvphone);
        checkDocx=findViewById(R.id.checkDoc);
        adtoccartbtn.setEnabled(false);







        qrin.addValueEventListener(new ValueEventListener() {  //for Investor existence checking
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();

                    Object oemail=map.get("InvestorEmail");
                    ioemail=String.valueOf(oemail);

                    Object oname=map.get("InvestorName");
                    ionm=String.valueOf(oname);

                    Object obnk=map.get("BankAccountNo");
                    iobnk=String.valueOf(obnk);

                    Object oifsc=map.get("IFSCCode");
                    ioifsc=String.valueOf(oifsc);

                    Object oiphone=map.get("InvestorPhone");
                    iophone=String.valueOf(oiphone);









                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkDocx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    adtoccartbtn.setEnabled(true);
                }
                else
                    {
                        adtoccartbtn.setEnabled(false);
                    }
            }
        });









        final String compkey = getIntent().getStringExtra("compkey");

        inforef.child(compkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Companies cmp = dataSnapshot.getValue(Companies.class);
                    tvname.setText(cmp.getCompanyName());
                    tvdesc.setText("  Company Description: "+cmp.getCompanyDescription());
                    tvprice.setText("Rs."+cmp.getPrice());
                    tvinterest.setText(cmp.getInterestOffered()+"% P.A");
                    tvfundt.setText("Rs."+cmp.getFundtarget());
                    tvfundDura.setText(cmp.getTenure()+" year");
                    Picasso.get().load(cmp.getImageUrl()).into(imageView);
                    imagUrl=cmp.getImageUrl();
                    tvPhone_no.setText("Phone: "+cmp.getPhone());
                    regBy=cmp.getRegisteredBy();
                    priceval=cmp.getPrice();
                    valTenure=cmp.getTenure();
                    valInterest=cmp.getInterestOffered();
                    valFundTg=cmp.getFundtarget();
                    valCompanyDesc=cmp.getCompanyDescription();
                    valcname=cmp.getCompanyName();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        adtoccartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quantity=qty.getText().toString();


                if(quantity.equals("")){
                    Toast.makeText(companyinformation.this,"Enter Quantity",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(ioemail)){
                    Toast.makeText(companyinformation.this,"Create your profile for investing",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(companyinformation.this,createinvestorProfile.class));
                }



                else {


                    int qty = Integer.parseInt(quantity);
                    final int pr = Integer.parseInt(priceval);
                    final int total = qty * pr;

                    DatabaseReference cartreff;
                    cartreff = FirebaseDatabase.getInstance().getReference().child("Orders");
                    String cartKey = cartreff.push().getKey();
                    int ftenure = Integer.parseInt(valTenure);

                    final String savecurrentTime, savecurrentDate;
                    Calendar calfordate = Calendar.getInstance();

                    SimpleDateFormat CurrentDate = new SimpleDateFormat("MMM dd,yyyy");
                    savecurrentDate = CurrentDate.format(calfordate.getTime());

                    final String fdate;
                    Calendar callfuturedate = Calendar.getInstance();
                    callfuturedate.add(Calendar.YEAR, ftenure);
                    SimpleDateFormat futureDate = new SimpleDateFormat("MMM dd,yyyy");
                    fdate = futureDate.format(callfuturedate.getTime());

                    SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm:ss a");
                    savecurrentTime = CurrentTime.format(calfordate.getTime());


                    final String Companynm = tvname.getText().toString();
                    final String quantityvar = quantity;
                    final String totalvar = String.valueOf(total);
                    final String customer = FirebaseAuth.getInstance().getCurrentUser().getEmail();



                    //Compound Interest
                    Double dtotal = Double.parseDouble(String.valueOf(total));
                    Double ratedouble = Double.parseDouble(valInterest);
                    Double timedouble = Double.parseDouble(valTenure);
                    Double CI = dtotal * (Math.pow((1 + ratedouble / 100), timedouble));
                    DecimalFormat df = new DecimalFormat("#.##");
                    String compoundamt=df.format(CI);


                    Intent intent = new Intent(companyinformation.this, paymentact.class);
                    intent.putExtra("CompanyName",Companynm);
                    intent.putExtra("CompanyDesc", valCompanyDesc);
                    intent.putExtra("ImageUrl", imagUrl);
                    intent.putExtra("Price", priceval);
                    intent.putExtra("Quantity", quantityvar);
                    intent.putExtra("Total", totalvar);
                    intent.putExtra("Duration", valTenure);
                    intent.putExtra("FundTarget",valFundTg);
                    intent.putExtra("custid", ioemail);
                    intent.putExtra("custname",ionm);
                    intent.putExtra("custbankno",iobnk);
                    intent.putExtra("custifsc",ioifsc);
                    intent.putExtra("custphone",iophone);
                    intent.putExtra("date", savecurrentDate);
                    intent.putExtra("time", savecurrentTime);
                    intent.putExtra("interestrt", valInterest);
                    intent.putExtra("compound", compoundamt);
                    intent.putExtra("futureDate", fdate);
                    intent.putExtra("RegisteredBy",regBy);


                    startActivity(intent);

                }
            }
        });


        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bintent=new Intent(companyinformation.this,docInfo.class);
                bintent.putExtra("cemailintent",regBy);
                startActivity(bintent);
            }
        });

        btnfraised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rrntent=new Intent(companyinformation.this,compvfrsd.class);
                rrntent.putExtra("rdemail",regBy);
                rrntent.putExtra("ftg",valFundTg);
                startActivity(rrntent);
            }
        });

    }
}
