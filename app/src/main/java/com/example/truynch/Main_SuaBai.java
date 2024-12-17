package com.example.truynch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.database.DatabaseTruyenChu;

public class Main_SuaBai extends AppCompatActivity {
    EditText edittentruyen, editnoidung, edittacgia, edithinhanh, edittrangthai;
    Button btnSua, btnhuy;
    private String idTruyen;
    DatabaseTruyenChu databaseTruyenChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sua_bai);

        edittentruyen = findViewById(R.id.edittentruyen);
        editnoidung = findViewById(R.id.editnoidung);
        edittacgia = findViewById(R.id.edittacgia);
        edithinhanh = findViewById(R.id.editanh);
        edittrangthai = findViewById(R.id.edittrangthai);

        btnSua = findViewById(R.id.btnsua_edit);
        btnhuy = findViewById(R.id.btnhuy_edit);

        databaseTruyenChu = new DatabaseTruyenChu(this);

        // Lấy ID truyện từ Intent và gán cho biến cấp lớp
        idTruyen = getIntent().getStringExtra("ID_TRUYEN");
        loadTruyenInfo(idTruyen);

        // Gắn sự kiện cho nút "Sửa"
        btnSua.setOnClickListener(view -> editTruyen());
    }

    private void editTruyen() {
        // Lấy dữ liệu từ các EditText
        String tenTruyen = edittentruyen.getText().toString();
        String noiDung = editnoidung.getText().toString();
        String tacGia = edittacgia.getText().toString();
        String trangThai = edittrangthai.getText().toString();
        String hinhAnh = edithinhanh.getText().toString();

        // Kiểm tra xem các trường có trống không
        if (tenTruyen.isEmpty() || noiDung.isEmpty() || tacGia.isEmpty() || trangThai.isEmpty()) {
            Toast.makeText(Main_SuaBai.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi hàm EditTruyen trong DatabaseTruyenChu để cập nhật dữ liệu
        boolean isUpdated = databaseTruyenChu.EditTruyen(idTruyen, tenTruyen, noiDung, tacGia, trangThai, hinhAnh);

        // Kiểm tra kết quả
        if (isUpdated) {
            Toast.makeText(Main_SuaBai.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            // Gửi kết quả về Main_DangBai
            setResult(RESULT_OK);
            finish();  // Kết thúc activity và quay lại Main_DangBai
        } else {
            Toast.makeText(Main_SuaBai.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm để load thông tin truyện từ Database và hiển thị lên các EditText
    private void loadTruyenInfo(String idTruyen) {
        Cursor cursor = databaseTruyenChu.getTruyenById(idTruyen);

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy thông tin từ cursor và gán cho các EditText
            edittentruyen.setText(cursor.getString(1));
            editnoidung.setText(cursor.getString(2));
            edittacgia.setText(cursor.getString(3));
            edittrangthai.setText(cursor.getString(5));
            edithinhanh.setText(cursor.getString(4));
        } else {
            Log.e("Main_SuaBai", "Lỗi khi tải truyện với ID: " + idTruyen);
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
