package com.example.managerproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnMb, btnIP, btnIPad, btnWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        btnMb = (Button) findViewById(R.id.btnMB);
        btnIP = (Button) findViewById(R.id.btnIP);
        btnIPad = (Button) findViewById(R.id.btnIPad);
        btnWatch = (Button) findViewById(R.id.btnWatch);

        btnMb.setOnClickListener(this);
        btnIP.setOnClickListener(this);
        btnIPad.setOnClickListener(this);
        btnWatch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnMB:
                intent = new Intent(this, ProductsMacBook.class);
                startActivity(intent);
                break;
            case R.id.btnIP:
                intent = new Intent(this, ProductsIPhone.class);
                startActivity(intent);
                break;
            case R.id.btnIPad:
                intent = new Intent(this, ProductsIPad.class);
                startActivity(intent);
                break;
            case R.id.btnWatch:
                intent = new Intent(this, ProductsIWatch.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}