package com.example.truynch;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.database.DatabaseTruyenChu;

public class NoiDungTruyen extends AppCompatActivity {

    private TextView tenTruyenTextView, noiDungTextView;
    private DatabaseTruyenChu databaseTruyenChu;
    private String chuongId, tenTruyen,truyenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);

        tenTruyenTextView = findViewById(R.id.TenTruyen);
        noiDungTextView = findViewById(R.id.NoiDung);
        databaseTruyenChu = new DatabaseTruyenChu(this);

        // Nhận dữ liệu từ intent
        truyenId = getIntent().getStringExtra("TRUYEN_ID");
        chuongId = getIntent().getStringExtra("CHUONG_ID");
        tenTruyen = getIntent().getStringExtra("TEN_TRUYEN");

        // Hiển thị tên truyện
        tenTruyenTextView.setText(tenTruyen);

        // Lấy nội dung chương từ cơ sở dữ liệu
        loadNoiDungChuong(truyenId,chuongId);
    }

    private void loadNoiDungChuong(String truyenId,String chuongId) {
        Cursor cursor = databaseTruyenChu.getChapterByIdAndTruyen(truyenId, chuongId);
        if (cursor != null && cursor.moveToFirst()) {
            String noiDungChuong = cursor.getString(2); // Lấy nội dung chương
            noiDungTextView.setText(noiDungChuong);
        } else {
            Toast.makeText(this, "Không tìm thấy nội dung chương", Toast.LENGTH_SHORT).show();
        }

    }
}
