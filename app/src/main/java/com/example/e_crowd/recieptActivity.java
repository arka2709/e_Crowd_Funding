package com.example.e_crowd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class recieptActivity extends AppCompatActivity {
    TextView vtcompany,vtprice,vtquantity,vtTotal,vtTenure,vtcustid,vtdate,vttime;
    TextView vtrate,vtci,vtcustName;
    TextView vtmDate;
    Button btnGenerate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);
        vtcompany=findViewById(R.id.tcname);
        vtprice=findViewById(R.id.tprice);
        vtquantity=findViewById(R.id.tcqty);
        vtTotal=findViewById(R.id.tctotal);
        vtTenure=findViewById(R.id.tctenure);
        vtcustid=findViewById(R.id.tccustid);
        vtdate=findViewById(R.id.tcdate);
        vttime=findViewById(R.id.tctime);
        vtrate=findViewById(R.id.tcrate);
        vtci=findViewById(R.id.maturityamtSpace);
        vtmDate=findViewById(R.id.maturityDateSpace);
        vtcustName=findViewById(R.id.tccustname);
        btnGenerate=findViewById(R.id.generatePDF);



        ActivityCompat.requestPermissions(recieptActivity.this,new String[]{
        Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);






        final String ir=getIntent().getStringExtra("interestrt");
        final String cn=getIntent().getStringExtra("CompanyName");
        final String pri=getIntent().getStringExtra("Price");
        final String qua=getIntent().getStringExtra("Quantity");
        final String dur=getIntent().getStringExtra("Duration");
        final String cus=getIntent().getStringExtra("custid");
        final String cusname=getIntent().getStringExtra("custpName");
        final String date=getIntent().getStringExtra("date");
        final String time=getIntent().getStringExtra("time");
        final String Total=getIntent().getStringExtra("Total");
        final String compoundedAmt=getIntent().getStringExtra("compInterest");
        final String mdate=getIntent().getStringExtra("FutDate");

         vtrate.setText(ir+"% P.A");
        vtcompany.setText(cn);
        vtprice.setText("Rs."+pri);
        vtquantity.setText(qua+"units");
        vtTenure.setText(dur+" yrs");
        vtcustid.setText(cus);
        vtcustName.setText(cusname);
        vtdate.setText(date);
        vttime.setText(time);
        vtTotal.setText("Rs."+Total);
        vtci.setText("Rs."+compoundedAmt);
        vtmDate.setText(mdate);


    btnGenerate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(recieptActivity.this,"E-Receipt saved",Toast.LENGTH_SHORT).show();
            PdfDocument myPdfDocument=new PdfDocument();
            Paint heading=new Paint();
            Paint customerid=new Paint();
            Paint custName=new Paint();
            Paint comname=new Paint();
            Paint invoice=new Paint();
            Paint price=new Paint();
            Paint interest=new Paint();
            Paint nos=new Paint();
            Paint tenure=new Paint();
            Paint total=new Paint();
            Paint maturedAmt=new Paint();
            Paint date1=new Paint();
            Paint time1=new Paint();
            Paint matDate=new Paint();
            Paint contact=new Paint();
            long millis= System.currentTimeMillis();


            PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page myPage1=myPdfDocument.startPage(myPageInfo1);
            Canvas canvas=myPage1.getCanvas();

            heading.setTextAlign(Paint.Align.CENTER);
            heading.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            heading.setTextSize(70);
            canvas.drawText("ECrowd Funding",600,270,heading);

            invoice.setTextAlign(Paint.Align.CENTER);
            invoice.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            invoice.setTextSize(70);
            canvas.drawText("Invoice",580,400,invoice);



            customerid.setTextAlign(Paint.Align.LEFT);
            customerid.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            customerid.setTextSize(50f);
            canvas.drawText("Customer ID:",80,540,customerid);
            canvas.drawText(cus,470,540,customerid);

            custName.setTextAlign(Paint.Align.LEFT);
            custName.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            custName.setTextSize(50f);
            canvas.drawText("Customer Name:",80,640,custName);
            canvas.drawText(cusname,470,640,customerid);



            comname.setTextAlign(Paint.Align.LEFT);
            comname.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            comname.setTextSize(50f);
            canvas.drawText("Company Name:",80,740,comname);
            canvas.drawText(cn,472,740,comname);


            price.setTextAlign(Paint.Align.LEFT);
            price.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            price.setTextSize(50f);
            canvas.drawText("Price per fund:",80,840,price);
            canvas.drawText("Rs."+pri,470,840,price);


            interest.setTextAlign(Paint.Align.LEFT);
            interest.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            interest.setTextSize(50f);
            canvas.drawText("Interest rate:",80,940,interest);
            canvas.drawText(ir+"% per year",470,940,interest);




            nos.setTextAlign(Paint.Align.LEFT);
            nos.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            nos.setTextSize(50f);
            canvas.drawText("  Quantity:",90,1040,nos);
            canvas.drawText(qua+" Units",470,1040,nos);



            tenure.setTextAlign(Paint.Align.LEFT);
            tenure.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            tenure.setTextSize(50f);
            canvas.drawText("  Tenure:",90,1140,nos);
            canvas.drawText(dur+" years",470,1140,nos);

            total.setTextAlign(Paint.Align.LEFT);
            total.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            total.setTextSize(50f);
            canvas.drawText("    Total:",90,1240,total);
            canvas.drawText("Rs."+Total,470,1240,total);

            maturedAmt.setTextAlign(Paint.Align.LEFT);
            maturedAmt.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            maturedAmt.setTextSize(50f);
            canvas.drawText("  Matured Amount:",90,1340,maturedAmt);
            canvas.drawText("Rs."+compoundedAmt,520,1340,maturedAmt);


            matDate.setTextAlign(Paint.Align.LEFT);
            matDate.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            matDate.setTextSize(50f);
            canvas.drawText("  Maturity Date:",90,1440,matDate);
            canvas.drawText(mdate,500,1440,matDate);


            date1.setTextAlign(Paint.Align.LEFT);
            date1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            date1.setTextSize(50f);
            canvas.drawText("    Date:",90,1640,date1);
            canvas.drawText(date,280,1640,date1);


            time1.setTextAlign(Paint.Align.LEFT);
            time1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            time1.setTextSize(50f);
            canvas.drawText("    Time:",90,1710,time1);
            canvas.drawText(time,280,1710,time1);



            contact.setTextAlign(Paint.Align.RIGHT);
            contact.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            contact.setTextSize(35f);
            canvas.drawText("Contact Us:",750,1800,contact);
            canvas.drawText("Email:",670,1900,contact);
            canvas.drawText("ecrowd.fund@gmail.com",1090,1900,contact);

            canvas.drawText("Phone:",690,1950,contact);
            canvas.drawText("+919706870287",980,1950,contact);

            Intent intent=new Intent(recieptActivity.this,thanks.class);

            intent.putExtra("company",cn);
            intent.putExtra("total",Total);
            startActivity(intent);




            myPdfDocument.finishPage(myPage1);

            File file=new File(Environment.getExternalStorageDirectory(),millis+"receipt.pdf");

            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            myPdfDocument.close();
        }
    });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(recieptActivity.this,"Please generate your Receipt",Toast.LENGTH_LONG).show();
    }
}
