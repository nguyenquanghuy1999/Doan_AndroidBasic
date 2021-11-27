package com.example.managerproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgUser;
    LinearLayout lnMacBook, lnIPhone, lnIPad, lnIWatch, lnIMac, lnAirPods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        imgUser = (ImageView) findViewById(R.id.imgPerson);
        imgUser.setClipToOutline(true);


        lnMacBook = (LinearLayout) findViewById(R.id.lnMacBook);
        lnIPhone = (LinearLayout) findViewById(R.id.lnIPhone);
        lnIPad = (LinearLayout) findViewById(R.id.lnIPad);
        lnIWatch = (LinearLayout) findViewById(R.id.lnIWatch);
        lnIMac = (LinearLayout) findViewById(R.id.lnIMac);
        lnAirPods = (LinearLayout) findViewById(R.id.lnAirPods);

        lnMacBook.setOnClickListener(this);
        lnIPhone.setOnClickListener(this);
        lnIPad.setOnClickListener(this);
        lnIWatch.setOnClickListener(this);
        lnIMac.setOnClickListener(this);
        lnAirPods.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lnMacBook:
                intent = new Intent(this, Products_MacBook.class);
                startActivity(intent);
                break;
            case R.id.lnIPhone:
                intent = new Intent(this, Products_IPhone.class);
                startActivity(intent);
                break;
            case R.id.lnIPad:
                intent = new Intent(this, Products_IPad.class);
                startActivity(intent);
                break;
            case R.id.lnIWatch:
                intent = new Intent(this, Products_IWatch.class);
                startActivity(intent);
                break;
            case R.id.lnIMac:
                intent = new Intent(this, Products_IMac.class);
                startActivity(intent);
                break;
            case R.id.lnAirPods:
                intent = new Intent(this, Products_AirPods.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}