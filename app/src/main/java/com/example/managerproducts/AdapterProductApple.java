package com.example.managerproducts;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class AdapterProductApple extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ProductApple> listProduct;
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;


    public AdapterProductApple(Context context, int layout, List<ProductApple> listProduct) {
        this.context = context;
        this.layout = layout;
        this.listProduct = listProduct;
    }


    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(layout, null);

        // ánh xạ
        TextView txtMaSP = (TextView) row.findViewById(R.id.txtMaSP);
        TextView txtTenSP = (TextView) row.findViewById(R.id.txtTenSP);
        TextView txtGiaSP = (TextView) row.findViewById(R.id.txtGiaSP);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imgAnh);
        Button btnSua = (Button) row.findViewById(R.id.btnSua);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);


        ProductApple productApple = listProduct.get(position);
        // gán giá trị
        txtMaSP.setText(productApple.MaSP + "");
        txtTenSP.setText(productApple.TenSP);
        txtGiaSP.setText(productApple.GiaSP);

        Bitmap bitmap = BitmapFactory.decodeByteArray(productApple.imageSP, 0, productApple.imageSP.length);
        imgAnh.setImageBitmap(bitmap);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (context.getClass().equals(ProductsMacBook.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from MacBook where masp = ?");
                    intent.putExtra("tableName", "MacBook");
                    intent.putExtra("className", ProductsMacBook.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(ProductsIPhone.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IPhone where masp = ?");
                    intent.putExtra("tableName", "IPhone");
                    intent.putExtra("className", ProductsIPhone.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(ProductsIPad.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IPad where masp = ?");
                    intent.putExtra("tableName", "IPad");
                    intent.putExtra("className", ProductsIPad.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(ProductsIWatch.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IWatch where masp = ?");
                    intent.putExtra("tableName", "IWatch");
                    intent.putExtra("className", ProductsIWatch.class);
                    context.startActivity(intent);
                }
            }
        });


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this product ?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(productApple.MaSP);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return row;
    }

    private void delete(int maSP) {
        database = Database.initDatabase(context, DATABASE_NAME);

        if (context.getClass().equals(ProductsMacBook.class)) {
            database.delete("MacBook", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from MacBook", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String tenSP = cursor.getString(1);
                String giaSP = cursor.getString(2);
                byte[] anh = cursor.getBlob(3);

                listProduct.add(new ProductApple(id, tenSP, giaSP, anh));
            }

        } else if (context.getClass().equals(ProductsIPhone.class)) {
            database.delete("IPhone", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IPhone", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String tenSP = cursor.getString(1);
                String giaSP = cursor.getString(2);
                byte[] anh = cursor.getBlob(3);

                listProduct.add(new ProductApple(id, tenSP, giaSP, anh));
            }

        } else if (context.getClass().equals(ProductsIPad.class)) {
            database.delete("IPad", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IPad", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String tenSP = cursor.getString(1);
                String giaSP = cursor.getString(2);
                byte[] anh = cursor.getBlob(3);

                listProduct.add(new ProductApple(id, tenSP, giaSP, anh));
            }

        } else if (context.getClass().equals(ProductsIWatch.class)) {
            database.delete("IWatch", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IWatch", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String tenSP = cursor.getString(1);
                String giaSP = cursor.getString(2);
                byte[] anh = cursor.getBlob(3);

                listProduct.add(new ProductApple(id, tenSP, giaSP, anh));
            }
        }
        notifyDataSetChanged();

    }
}
