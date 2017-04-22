package com.drbt.energyhackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class payBillsActivity extends AppCompatActivity {

    Button prepaid, postpaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bills);

        prepaid = (Button) findViewById(R.id.prepaid);
        postpaid = (Button) findViewById(R.id.postpaid);

        prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(payBillsActivity.this, PrepaidActivity.class));
            }
        });

        postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(payBillsActivity.this, PostpaidActivity.class));
            }
        });
    }
}
