package com.example.e_crowd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

public class paymentact extends AppCompatActivity implements PaymentResultListener {
    private Button startpayment;
    private TextView orderamount,tvorderinterest,tvordertenure,tvordermat;
    private String TAG =" main";
    String totalamt,spinterest,sptenure,spmaturiy;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentact);

         totalamt=getIntent().getStringExtra("Total");
         spinterest=getIntent().getStringExtra("interestrt");
         sptenure=getIntent().getStringExtra("Duration");
         spmaturiy=getIntent().getStringExtra("compound");
        startpayment = (Button) findViewById(R.id.startpayment);
        orderamount = (TextView) findViewById(R.id.orderamount);
        orderamount.setText("Total Rs."+totalamt);
        tvorderinterest=findViewById(R.id.orderinterest);
        tvordertenure=findViewById(R.id.ordertenure);
        tvordermat=findViewById(R.id.ordercompound);

        tvorderinterest.setText("Interest Rate: "+spinterest+"%");
        tvordertenure.setText("Tenure: "+sptenure+" years");
        tvordermat.setText("Amount after maturity: Rs."+spmaturiy);








        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    startPayment();

            }
        });
    }

    private void startPayment() {
        Activity activity=this;
        Checkout co=new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "E-Crowd");
            options.put("description", "Payment Page");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = totalamt;

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "arka.datta.prabha@gmail.com");
            preFill.put("contact", "9707870287");
            options.put("prefill", preFill);

            co.open(activity, options);

        }catch (Exception e){
            Log.e("error","error"+e.toString());
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        final String coname=getIntent().getStringExtra("CompanyName");
        final String codesc=getIntent().getStringExtra("CompanyDesc");
        final String coprice=getIntent().getStringExtra("Price");
        final String cointerest=getIntent().getStringExtra("interestrt");
        final String cotarget=getIntent().getStringExtra("FundTarget");
        final String cocustomer=getIntent().getStringExtra("custid");
        final String cocustomername=getIntent().getStringExtra("custname");
        String cocustomerbnk=getIntent().getStringExtra("custbankno");
        String cocustomerifs=getIntent().getStringExtra("custifsc");
        String cocustomerphone=getIntent().getStringExtra("custphone");
        final String codate=getIntent().getStringExtra("date");
        final String cotime=getIntent().getStringExtra("time");
        final String coimageUrl=getIntent().getStringExtra("ImageUrl");
        final String coquantity=getIntent().getStringExtra("Quantity");
        final String coTotal=getIntent().getStringExtra("Total");
        final String coDuration=getIntent().getStringExtra("Duration");
        final String compound=getIntent().getStringExtra("compound");
        final String futureDateformat=getIntent().getStringExtra("futureDate");
        final String rby=getIntent().getStringExtra("RegisteredBy");


        Log.e(TAG, " payment successful "+ s.toString());
        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
        DatabaseReference cartreff;

        cartreff=FirebaseDatabase.getInstance().getReference().child("Orders");
        String cartKey=cartreff.push().getKey();

        HashMap cartmap=new HashMap();
        cartmap.put("CompanyName",coname);
        cartmap.put("CompanyDesc",codesc);
        cartmap.put("Price",coprice);
        cartmap.put("Interest",cointerest);
        cartmap.put("FundTarget",cotarget);
        cartmap.put("InvestorEmail",cocustomer);
        cartmap.put("InvestorName",cocustomername);
        cartmap.put("InvestorAccountNo",cocustomerbnk);
        cartmap.put("IFSCCode",cocustomerifs);
        cartmap.put("InvestorPhone",cocustomerphone);
        cartmap.put("date",codate);
        cartmap.put("time",cotime);
        cartmap.put("imageUrl",coimageUrl);
        cartmap.put("quantity",coquantity);
        cartmap.put("Total",coTotal);
        cartmap.put("tenure",coDuration);
        cartmap.put("Camt",compound);
        cartmap.put("Futuredate",futureDateformat);
        cartmap.put("RegisteredBy",rby);





        cartreff.child(cartKey).setValue(cartmap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
           Toast.makeText(paymentact.this,"Order Successful",Toast.LENGTH_LONG).show();






                Intent intent=new Intent(paymentact.this,recieptActivity.class);
                intent.putExtra("CompanyName",coname);
                intent.putExtra("Price",coprice);
                intent.putExtra("interestrt",cointerest);
                intent.putExtra("custid",cocustomer);
                intent.putExtra("custpName",cocustomername);
                intent.putExtra("date",codate);
                intent.putExtra("time",cotime);
                intent.putExtra("Quantity",coquantity);
                intent.putExtra("Total",coTotal);
                intent.putExtra("Duration",coDuration);
                intent.putExtra("compInterest",compound);
                intent.putExtra("FutDate",futureDateformat);
                startActivity(intent);
            }
        });
    }




    @Override
    public void onPaymentError(int i, String s) {

        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }

    }
}
