package com.example.truynch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.truynch.Main_ThemChapter;
import com.example.truynch.R;
import com.example.truynch.model.Chapter;

import java.util.List;

public class ChapterAdapter extends ArrayAdapter<Chapter> {

    public ChapterAdapter(@NonNull Context context, int resource, @NonNull List<Chapter> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_chapter, parent, false);
        }

        Chapter chapter = getItem(position);
        TextView textChapter = convertView.findViewById(R.id.TextChapter);
        textChapter.setText("Chap " + chapter.getChuong());

        return convertView;
    }
}
