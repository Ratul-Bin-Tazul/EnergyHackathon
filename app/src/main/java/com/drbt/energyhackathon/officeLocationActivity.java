package com.drbt.energyhackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class officeLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_location);
    }

    public void mapView(View v) {
        startActivity(new Intent(officeLocationActivity.this, MapActivity.class));
    }
}
