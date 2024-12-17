package com.example.truynch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.adapter.ChapterAdapter;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.Chapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private TextView TextTenTruyenM2, TextTacGiaM2, TextNoiDungSoLuoc;
    private ListView listViewChapters;
    private ImageView ImageTruyen;
    private ArrayList<Chapter> chapterList;
    private ChapterAdapter chapterAdapter;
    private DatabaseTruyenChu databaseTruyenChu;
    private String idTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextTenTruyenM2 = findViewById(R.id.TextTenTruyenM2);
        TextTacGiaM2 = findViewById(R.id.TextTacGiaM2);
        TextNoiDungSoLuoc = findViewById(R.id.TextNoiDungSoLuoc);
        listViewChapters = findViewById(R.id.listviewNew);
        ImageTruyen = findViewById(R.id.image_TruyenM2);

        chapterList = new ArrayList<>();
        databaseTruyenChu = new DatabaseTruyenChu(this);

        // Nhận ID truyện từ MainActivity
        idTruyen = getIntent().getStringExtra("ID_TRUYEN");

        // Load dữ liệu truyện
        loadTruyenData(idTruyen);

        // Load danh sách chương
        loadChapters(idTruyen);

        chapterAdapter = new ChapterAdapter(this, R.layout.row_chapter, chapterList);
        listViewChapters.setAdapter(chapterAdapter);

        // Trong MainActivity2
        listViewChapters.setOnItemClickListener((adapterView, view, position, id) -> {
            Chapter selectedChapter = chapterList.get(position);

            // Tạo một intent để chuyển tới NoiDungTruyen
            Intent intent = new Intent(MainActivity2.this, NoiDungTruyen.class);

            // Truyền thông tin chương được chọn qua intent
            intent.putExtra("TRUYEN_ID", selectedChapter.getIdTruyen());
            intent.putExtra("CHUONG_ID", selectedChapter.getChuong());
            intent.putExtra("TEN_TRUYEN", TextTenTruyenM2.getText().toString());
            startActivity(intent);
        });


    }

    private void loadTruyenData(String idTruyen) {
        Cursor cursor = databaseTruyenChu.getTruyenById(idTruyen);
        if (cursor != null && cursor.moveToFirst()) {
            TextTenTruyenM2.setText(cursor.getString(1)); // Tên truyện
            TextTacGiaM2.setText(cursor.getString(3)); // Tác giả
            TextNoiDungSoLuoc.setText(cursor.getString(2)); // Nội dung sơ lược

            // Lấy đường dẫn hình ảnh và sử dụng Picasso để tải hình ảnh
            String imagePath = cursor.getString(4); // Giả sử cột 4 là đường dẫn hình ảnh
            if (imagePath != null && !imagePath.isEmpty()) {
                Picasso.get()
                        .load(imagePath)
                        .placeholder(R.drawable.no_image) // Hình ảnh placeholder
                        .into(ImageTruyen);
                //tìm hiểu phương thức này
            }
        } else {
            Toast.makeText(this, "Không tìm thấy truyện", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadChapters(String idTruyen) {
        Cursor cursor = databaseTruyenChu.getChaptersById(idTruyen);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Chapter chapter = new Chapter(
                        cursor.getString(0), // ID truyện
                        cursor.getString(1), // Chương
                        cursor.getString(2)  // Nội dung chương
                );
                chapterList.add(chapter);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Không có chương nào", Toast.LENGTH_SHORT).show();
        }
    }
}
