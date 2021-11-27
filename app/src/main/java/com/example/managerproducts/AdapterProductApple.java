package com.example.managerproducts;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AdapterProductApple extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<ProductApple> listProduct;
    List<ProductApple> arrayList;
    String DATABASE_NAME = "QLSP.db";
    SQLiteDatabase database;


    public AdapterProductApple(Context context, int layout, List<ProductApple> listProduct) {
        this.context = context;
        this.layout = layout;
        this.listProduct = listProduct;
        this.arrayList = listProduct;

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
        TextView txtTenSP = (TextView) row.findViewById(R.id.txtTenSP);
        TextView txtDungLuongSP = (TextView) row.findViewById(R.id.txtDungLuongSP);
        TextView txtGiaSP = (TextView) row.findViewById(R.id.txtGiaSP);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imgAnh);
        Button btnSua = (Button) row.findViewById(R.id.btnSua);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);


        ProductApple productApple = listProduct.get(position);
        // gán giá trị
        txtTenSP.setText(productApple.TenSP);
        txtDungLuongSP.setText(productApple.DungLuongSP);
        txtGiaSP.setText(productApple.GiaSP);

        Bitmap bitmap = BitmapFactory.decodeByteArray(productApple.imageSP, 0, productApple.imageSP.length);
        imgAnh.setImageBitmap(bitmap);

        Drawable myIcon = context.getResources().getDrawable(R.drawable.icon_add);
        ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.WHITE);
        myIcon.setColorFilter(filter);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (context.getClass().equals(Products_MacBook.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from MacBook where masp = ?");
                    intent.putExtra("tableName", "MacBook");
                    intent.putExtra("className", Products_MacBook.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(Products_IPhone.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IPhone where masp = ?");
                    intent.putExtra("tableName", "IPhone");
                    intent.putExtra("className", Products_IPhone.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(Products_IPad.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IPad where masp = ?");
                    intent.putExtra("tableName", "IPad");
                    intent.putExtra("className", Products_IPad.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(Products_IWatch.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IWatch where masp = ?");
                    intent.putExtra("tableName", "IWatch");
                    intent.putExtra("className", Products_IWatch.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(Products_IMac.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from IMac where masp = ?");
                    intent.putExtra("tableName", "IMac");
                    intent.putExtra("className", Products_IMac.class);
                    context.startActivity(intent);

                } else if (context.getClass().equals(Products_AirPods.class)) {
                    intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", productApple.MaSP);
                    intent.putExtra("Query", "Select * from AirPods where masp = ?");
                    intent.putExtra("tableName", "AirPods");
                    intent.putExtra("className", Products_AirPods.class);
                    context.startActivity(intent);
                }
            }
        });


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn xóa " + productApple.TenSP + " ?");
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

        if (context.getClass().equals(Products_MacBook.class)) {
            database.delete("MacBook", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from MacBook", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }

        } else if (context.getClass().equals(Products_IPhone.class)) {
            database.delete("IPhone", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IPhone", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }

        } else if (context.getClass().equals(Products_IPad.class)) {
            database.delete("IPad", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IPad", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }

        } else if (context.getClass().equals(Products_IWatch.class)) {
            database.delete("IWatch", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IWatch", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }

        } else if (context.getClass().equals(Products_IMac.class)) {
            database.delete("IMac", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from IMac", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }

        } else if (context.getClass().equals(Products_AirPods.class)) {
            database.delete("AirPods", "masp = ?", new String[]{maSP + ""});
            Cursor cursor = database.rawQuery("Select * from AirPods", null);
            listProduct.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String dungluong = cursor.getString(2);
                String gia = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);

                listProduct.add(new ProductApple(id, ten, dungluong, gia, anh));
            }
        }

        notifyDataSetChanged();

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    listProduct = arrayList;
                } else {
                    List<ProductApple> list = new ArrayList<>();
                    for (ProductApple productApple : arrayList) {
                        if (productApple.TenSP.toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(productApple);
                        }
                    }
                    listProduct = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listProduct = (List<ProductApple>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
