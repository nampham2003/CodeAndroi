package com.example.truynch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.truynch.Main_ThemChapter;
import com.example.truynch.R;
import com.example.truynch.model.Chapter;

import java.util.List;

public class Chapter1Adapter extends ArrayAdapter<Chapter> { // Kế thừa từ ArrayAdapter<Chapter>
    public Chapter1Adapter(@NonNull Context context, int resource, @NonNull List<Chapter> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Kiểm tra nếu view hiện tại chưa được tái sử dụng thì tạo mới
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_chapter, parent, false);
        }

        // Lấy dữ liệu của chương tại vị trí hiện tại
        Chapter chapter = getItem(position);

        // Liên kết TextView với layout và hiển thị thông tin chương
        TextView textChapter = convertView.findViewById(R.id.TextChapter);
        textChapter.setText("Chap " + chapter.getChuong());

        convertView.setOnLongClickListener(v -> {
            if (getContext() instanceof Main_ThemChapter) {
                ((Main_ThemChapter) getContext()).deleteChapter(chapter.getIdTruyen(), chapter.getChuong());
            }
            return true; // Trả về true nếu sự kiện được xử lý
        });
        return convertView;
    }
}
