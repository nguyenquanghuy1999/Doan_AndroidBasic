package com.example.managerproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtUser;
    TextView txtPassWord;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassWord = (EditText) findViewById(R.id.txtPW);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString();
                String password = txtPassWord.getText().toString();
                if (user.equals("admin") && password.equals("123456")) {
                    Toast.makeText(getApplicationContext(),"hi! " + user , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Login failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}