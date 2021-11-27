package com.example.managerproducts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class InsertActivity extends AppCompatActivity {

    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;
    ImageView imgAnhThem;
    EditText txtMaSP, txtTenThem, txtGiaThem, txtDungLuongThem;
    Button btnChupHinh, btnChonHinh, btnLuu, btnHuy;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        Drawable drawable = getResources().getDrawable(R.drawable.icon_close);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(drawable);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtMaSP = (EditText) findViewById(R.id.txtMaSPThem);
        imgAnhThem = (ImageView) findViewById(R.id.imgAnhThem);
        txtTenThem = (EditText) findViewById(R.id.txtTenThem);
        txtGiaThem = (EditText) findViewById(R.id.txtGiaThem);
        txtDungLuongThem = (EditText) findViewById(R.id.txtDLThem);
        btnChupHinh = (Button) findViewById(R.id.btnChupHinhThem);
        btnChonHinh = (Button) findViewById(R.id.btnChonHinhThem);
        btnLuu = (Button) findViewById(R.id.btnLuuThem);
        btnHuy = (Button) findViewById(R.id.btnHuyThem);
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

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    private void insert() {
        String maSP = txtMaSP.getText().toString();
        String tenSP = txtTenThem.getText().toString();
        String dungLuongSP = txtDungLuongThem.getText().toString();
        String giaSP = txtGiaThem.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhThem);

        Intent intent = getIntent();
        String tableName = intent.getStringExtra("tableName");
        Serializable className = intent.getSerializableExtra("className");

        if (!maSP.isEmpty() && !tenSP.isEmpty() && !dungLuongSP.isEmpty() && !giaSP.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("MaSP", maSP);
            contentValues.put("TenSP", tenSP);
            contentValues.put("DungLuongSP", dungLuongSP);
            contentValues.put("GiaSP", giaSP);
            contentValues.put("ImageSP", anh);

            database = Database.initDatabase(this, DATABASE_NAME);
            database.insert(tableName, null, contentValues);

            Intent add = new Intent(this, (Class<?>) className);
            startActivity(add);
            Toast.makeText(getApplicationContext(), "Đã thêm một sản phẩm mới!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }

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
                    imgAnhThem.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhThem.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}