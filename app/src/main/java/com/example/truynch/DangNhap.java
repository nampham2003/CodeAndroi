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

public class DangNhap extends AppCompatActivity {
    private EditText edtTaiKhoan, edtMatKhau;
    private Button btnDangNhap, btnDangKy;
    private DatabaseTruyenChu databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();

        databaseDocTruyen = new DatabaseTruyenChu(this);

        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });

        btnDangNhap.setOnClickListener(v -> {
            String tentaikhoan = edtTaiKhoan.getText().toString().trim();
            String matkhau = edtMatKhau.getText().toString().trim();

            if (tentaikhoan.isEmpty() || matkhau.isEmpty()) {
                Toast.makeText(DangNhap.this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = null;
            try {
                cursor = databaseDocTruyen.getDataTaiKhoan();
                boolean loggedIn = false;

                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String tentk = cursor.getString(1);
                        String email = cursor.getString(3);

                        Intent intent = new Intent(DangNhap.this, MainActivity.class);
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);
                        startActivity(intent);
                        finish();
                        Log.e("Đăng nhập : ", "Thành công");
                        loggedIn = true;
                        break;
                    }
                }

                if (!loggedIn) {
                    Toast.makeText(DangNhap.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    Log.e("Đăng nhập : ", "Không thành công");
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.taikhoan);
        edtMatKhau = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
    }
}
