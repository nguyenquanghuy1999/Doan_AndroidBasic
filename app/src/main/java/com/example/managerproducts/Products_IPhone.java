package com.example.managerproducts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Products_IPhone extends AppCompatActivity implements TextWatcher {
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;
    ListView lvDS;
    EditText txtSearch;
    ArrayList<ProductApple> arrProduct;
    AdapterProductApple adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        // sử dụng ActionBar để add mũi tên quay lại
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtSearch = (EditText) findViewById(R.id.txtTimKiem);
        txtSearch.addTextChangedListener(this);



        lvDS = (ListView) findViewById(R.id.lvDS);
        arrProduct = new ArrayList<>();
        adapter = new AdapterProductApple(this, R.layout.activity_row_product, arrProduct);
        lvDS.setAdapter(adapter);

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from IPhone", null);


        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int maSP = cursor.getInt(0);
            String tenSP = cursor.getString(1);
            String dungLuongSP = cursor.getString(2);
            String giaSP = cursor.getString(3);
            byte[] imageSP = cursor.getBlob(4);
            arrProduct.add(new ProductApple(maSP, tenSP, dungLuongSP, giaSP, imageSP));
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(this, ControlActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(this, InsertActivity.class);
                intent.putExtra("tableName", "Iphone");
                intent.putExtra("className", Products_IPhone.class);
                startActivity(intent);
                break;
            case R.id.option:
                database = Database.initDatabase(this, DATABASE_NAME);
                database.delete("Iphone",null,null);
                arrProduct.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Danh sách sản phẩm hiện đang trống!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
