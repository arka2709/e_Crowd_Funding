package com.example.e_crowd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class termsandconditions extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroupdoc;
    TextView Tvtac,tvdetails;
    String l1,l2,l3,l4,l5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsandconditions);
        radioGroupdoc=(RadioGroup)findViewById(R.id.rdGroup);
        tvdetails=findViewById(R.id.Tvdoc_desc);
        Tvtac=findViewById(R.id.tadndcpoints);
        l1="  1. Filling all the details in the 1st screen and uploading attractive Logo is mandatory.";
        l2="  2. Next step is uploading all the documents required: Registration Documents, Address Proof,Company Balance Sheet";
        l3="  3. Your company will be registered with this platform only after verifying your documents, Verification process requires upto thee days";
        l4="  4. If details and documents provided are found faulty then your application will be rejected.";
        l5="  5. Any intent of forgery can lead to lawsuit against the company and owners.";
        Tvtac.setText(l1+ "\n" +l2+ "\n" +l3+ "\n" +l4+ "\n" +l5);

        radioGroupdoc.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId)
        {
            case R.id.blicense:
                tvdetails.setText("Upload Business registration documents depending upon your company such as sole proprietorship, Private Limited, one Person Company.");
                break;
            case R.id.owndoc:
                tvdetails.setText("Telephone Bill/ Lease agreement/ Electricity Bill/Trade license /Sales Tax certificate ");
                break;
            case R.id.cBrochure:
                tvdetails.setText("A company brochure is a print publication used to highlight a company's benefits, products and services for customers.");
                break;
            case R.id.cfinance:
                tvdetails.setText("Last Year's Financial statements: Balance Sheet, Profit and Loss statement, Sales Statement audited by a Chartered Accountant registered by ICAI");
                break;
        }


    }
}
