package com.example.managerproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;
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

                database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                Cursor cursor = database.rawQuery("Select * from User", null);

                if (!user.isEmpty() && !password.isEmpty()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        String userDB = cursor.getString(1);
                        String passDB = cursor.getString(2);
                        if (user.equals(userDB) && password.equals(passDB)) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập vào các trường phía trên!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}