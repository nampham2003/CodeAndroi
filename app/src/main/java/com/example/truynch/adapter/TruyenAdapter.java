package com.example.truynch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.truynch.R;
import com.example.truynch.model.Truyen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TruyenAdapter extends BaseAdapter {
    private  Context context;
    private  ArrayList<Truyen> truyens;

    public TruyenAdapter(Context context, ArrayList<Truyen> truyens) {
        this.context = context;
        this.truyens = truyens;
    }

    @Override
    public int getCount() {
        return truyens.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Khởi tạo view cho mỗi item trong danh sách
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_truyen, parent, false);
        }

        // Lấy truyện tại vị trí này
        Truyen truyen = truyens.get(position);

        // Ánh xạ các thành phần trong layout
        TextView textTenTruyen = convertView.findViewById(R.id.TextTenTruyen);
        TextView textTacGia = convertView.findViewById(R.id.TextTacGia);
        TextView textTrangThai = convertView.findViewById(R.id.TextTrangThai);
        ImageView imageTruyen = convertView.findViewById(R.id.image_TruyenM1);

        // Thiết lập thông tin
        textTenTruyen.setText(truyen.getTenTruyen());
        textTacGia.setText("Tác giả: " + truyen.getTacGia());
        textTrangThai.setText("Trạng thái: " + truyen.getTrangThai());
        Picasso.get().load(truyen.getHinhAnh()).into(imageTruyen);

        return convertView;
    }

    public void filterList(ArrayList<Truyen> filteredList) {
        truyens = filteredList;
        notifyDataSetChanged();
    }
}
