package com.example.managerproducts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;

public class ProductsMacBook extends AppCompatActivity implements View.OnClickListener {
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;
    ListView lvDS;
    Button btnThemSP;
    ArrayList<ProductApple> arrProduct;
    AdapterProductApple adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        // sử dụng ActionBar để add mũi tên quay lại
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnThemSP = (Button) findViewById(R.id.btnThem);
        btnThemSP.setOnClickListener(this);

        lvDS = (ListView) findViewById(R.id.lvDS);
        arrProduct = new ArrayList<>();
        adapter = new AdapterProductApple(this, R.layout.activity_row_product, arrProduct);
        lvDS.setAdapter(adapter);

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from MacBook", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int maSP = cursor.getInt(0);
            String tenSP = cursor.getString(1);
            String giaSP = cursor.getString(2);
            byte[] imageSP = cursor.getBlob(3);
            arrProduct.add(new ProductApple(maSP, tenSP, giaSP, imageSP));
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, InsertActivity.class);
        intent.putExtra("tableName", "MacBook");
        intent.putExtra("className", ProductsMacBook.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);

        if (itemSearch != null) {
            SearchView searchView = (SearchView) itemSearch.getActionView();
            searchView.setQueryHint("Search...");
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);

                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, CategoriesActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}