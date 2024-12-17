package com.example.truynch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.database.DatabaseTruyenChu;

public class Main_suakhach extends AppCompatActivity {
    EditText edittenk, editpk, editek;
    Button btnsuakhach;
    DatabaseTruyenChu databaseTruyenChu;
    int idkh; // Khai báo biến để lưu ID khách hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_suakhach);

        edittenk = findViewById(R.id.dialog_Tenkhach);
        editpk = findViewById(R.id.dialog_MatKhaukhach);
        editek = findViewById(R.id.dialog_Gmailkhach);
        btnsuakhach = findViewById(R.id.button4);

        databaseTruyenChu = new DatabaseTruyenChu(this);
        Intent intent = getIntent();
        idkh = intent.getIntExtra("Id", 0); // Lưu ID khách hàng vào biến
        loadKhachInfo(idkh); // Sử dụng biến idkh

        btnsuakhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKhach();
            }
        });
    }

    private void loadKhachInfo(int idKhach) { // Chuyển sang kiểu int
        Cursor cursor = databaseTruyenChu.getKhachById(String.valueOf(idKhach));
        if (cursor != null && cursor.moveToFirst()) {
            edittenk.setText(cursor.getString(1)); // Tên tài khoản
            editpk.setText(cursor.getString(2)); // Mật khẩu
            editek.setText(cursor.getString(3)); // Email
            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin khách hàng.", Toast.LENGTH_SHORT).show();
        }
    }

    private void editKhach() {
        // Lấy dữ liệu từ các EditText
        String tenKhach = edittenk.getText().toString();
        String matKhau = editpk.getText().toString();
        String email = editek.getText().toString();

        // Kiểm tra xem các trường có trống không
        if (tenKhach.isEmpty() || matKhau.isEmpty() || email.isEmpty()) {
            Toast.makeText(Main_suakhach.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi hàm updateKhach trong DatabaseTruyenChu để cập nhật dữ liệu
        boolean isUpdated = databaseTruyenChu.updateKhach(idkh, tenKhach, matKhau, email); // Thêm idkh vào đây

        // Kiểm tra kết quả
        if (isUpdated) {
            Toast.makeText(Main_suakhach.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish(); // Quay lại activity trước
        } else {
            Toast.makeText(Main_suakhach.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
