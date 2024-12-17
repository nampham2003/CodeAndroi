package com.example.truynch;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.TaiKhoan;

public class DangKy extends AppCompatActivity {
    private EditText edtDKTaiKhoan, edtDKMatKhau, edtDKEmail;
    private Button btnDKDangKy, btnDKDangNhap;
    private DatabaseTruyenChu databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        AnhXa();

        databaseDocTruyen = new DatabaseTruyenChu(this);

        btnDKDangKy.setOnClickListener(v -> {
            String taikhoan = edtDKTaiKhoan.getText().toString().trim();
            String matkhau = edtDKMatKhau.getText().toString().trim();
            String email = edtDKEmail.getText().toString().trim();

            if (TextUtils.isEmpty(taikhoan) || TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(email)) {
                Toast.makeText(DangKy.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                Log.e("Thông báo : ", "Bạn chưa nhập đầy đủ thông tin");
            } else if (databaseDocTruyen.isTaiKhoanExist(taikhoan)) {
                Toast.makeText(DangKy.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                Log.e("Thông báo : ", "Tài khoản đã tồn tại");
            } else {
                TaiKhoan taikhoan1 = CreateTaiKhoan();
                databaseDocTruyen.addTaiKhoan(taikhoan1);
                Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDKDangNhap.setOnClickListener(v -> finish());
    }

    private TaiKhoan CreateTaiKhoan() {
        String taikhoan = edtDKTaiKhoan.getText().toString().trim();
        String matkhau = edtDKMatKhau.getText().toString().trim();
        String email = edtDKEmail.getText().toString().trim();
        int phanquyen = 1;

        return new TaiKhoan(taikhoan, matkhau, email, phanquyen);
    }

    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        btnDKDangNhap = findViewById(R.id.dkDangNhap);
    }
}
