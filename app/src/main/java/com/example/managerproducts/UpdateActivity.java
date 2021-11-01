package com.example.managerproducts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class UpdateActivity extends AppCompatActivity {
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;
    ImageView imgAnhSua;
    EditText txtTenSua, txtGiaSua;
    Button btnChupHinh, btnChonHinh, btnSave, btnCancel;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int maSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControl();

        loadData();

        addEvent();
    }


    private void addControl() {
        imgAnhSua = (ImageView) findViewById(R.id.imgAnhSua);
        txtTenSua = (EditText) findViewById(R.id.txtTenSua);
        txtGiaSua = (EditText) findViewById(R.id.txtGiaSua);
        btnChupHinh = (Button) findViewById(R.id.btnChupHinh);
        btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }


    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        maSP = bundle.getInt("ID");
        String Query = bundle.getString("Query");

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery(Query, new String[]{maSP + ""});

        cursor.moveToFirst();
        String tenSp = cursor.getString(1);
        String giaSp = cursor.getString(2);
        byte[] anh = cursor.getBlob(3);

        if (anh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            imgAnhSua.setImageBitmap(bitmap);
        }
        txtTenSua.setText(tenSp);
        txtGiaSua.setText(giaSp);
    }


    private void addEvent() {
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {
        String tenSP = txtTenSua.getText().toString();
        String giaSP = txtGiaSua.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhSua);

        Bundle bundle = getIntent().getExtras();
        String tableName = bundle.getString("tableName");
        Serializable className = getIntent().getSerializableExtra("className");

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenSP", tenSP);
        contentValues.put("GiaSP", giaSP);
        contentValues.put("ImageSP", anh);

        database = Database.initDatabase(this, DATABASE_NAME);
        database.update(tableName, contentValues, "masp = ?", new String[]{maSP + ""});

        Intent intent = new Intent(this, (Class<?>) className);
        startActivity(intent);

    }


    public byte[] getByteArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnhSua.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhSua.setImageBitmap(bitmap);
            }
        }
    }
}