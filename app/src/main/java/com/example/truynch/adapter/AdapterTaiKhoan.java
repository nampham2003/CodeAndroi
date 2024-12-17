package com.example.truynch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.truynch.R;
import com.example.truynch.model.TaiKhoan;

import java.util.List;

public class AdapterTaiKhoan extends BaseAdapter {

    private Context context;
    private int layout;
    //Tạo mảng
    private List<TaiKhoan> taiKhoanList;

    public AdapterTaiKhoan(Context context, List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.taiKhoanList = taiKhoanList;
    }

    @Override
    public int getCount() {
        return taiKhoanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_taikhoan, parent, false);
        }

        TaiKhoan taiKhoan = taiKhoanList.get(i);

        TextView txttaikhoan = convertView.findViewById(R.id.textView4);
        TextView txtemail = convertView.findViewById(R.id.textView5);
        TextView txtpq = convertView.findViewById(R.id.textView6);


        txttaikhoan.setText("Tài khoản: " + taiKhoan.getmTenTaiKhoan());
        txtemail.setText("Email: " + taiKhoan.getmEmail());
        if (taiKhoan.getmPhanQuyen() == 1) {
            txtpq.setText("Chức danh: Khách");
        } else if (taiKhoan.getmPhanQuyen() == 2) {
            txtpq.setText("Chức danh: Admin");
        } else {
            txtpq.setText("Chức danh: Khác");
        }
        return convertView;
    }
}
