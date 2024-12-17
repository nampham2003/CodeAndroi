package com.example.truynch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.truynch.model.chuyenmuc;
import com.example.truynch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterchuyenmuc extends BaseAdapter { // Kế thừa từ BaseAdapter

    private Context context;
    private int layout;
    private List<chuyenmuc> listchuyenmuc;

    public adapterchuyenmuc(Context context, int layout, List<chuyenmuc> listchuyenmuc) {
        this.context = context;
        this.layout = layout;
        this.listchuyenmuc = listchuyenmuc;
    }

    @Override
    public int getCount() {
        return listchuyenmuc.size();
    }

    @Override
    public Object getItem(int position) {
        return listchuyenmuc.get(position); // Trả về đối tượng tại vị trí cụ thể
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về ID của đối tượng
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        ImageView img = convertView.findViewById(R.id.imageviewchuyenmmuc);
        TextView txt = convertView.findViewById(R.id.textviewchuyenmuc);

        chuyenmuc cm = listchuyenmuc.get(position);

        txt.setText(cm.getTenchuyenmuc());

        // Gán trực tiếp hình ảnh từ drawable
        img.setImageResource(cm.getHinhanhchuyenmuc());

        return convertView;
    }

}
