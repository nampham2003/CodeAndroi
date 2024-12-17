package com.example.truynch;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.TaiKhoan;

public class TK_Khach extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT = 1;
    DatabaseTruyenChu databaseTruyenChu;
    TextView txttk, txtmk, txtemail;
    Button btnedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tk_khach);

        // Khởi tạo cơ sở dữ liệu
        databaseTruyenChu = new DatabaseTruyenChu(this);

        txttk = findViewById(R.id.text_TenKH);
        txtmk = findViewById(R.id.text_Pass);
        txtemail = findViewById(R.id.Text_Gmail);
        btnedit = findViewById(R.id.btnedit_tkkh);

        Loaddata();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("Id", -1); // Sử dụng -1 để kiểm tra nếu ID không tồn tại
                if (id != -1) {
                    Intent intent = new Intent(TK_Khach.this, Main_suakhach.class);
                    intent.putExtra("Id", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(TK_Khach.this, "ID không hợp lệ.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            // Cập nhật lại thông tin sau khi chỉnh sửa
            Loaddata(); // Gọi hàm để tải lại thông tin khách hàng
        }
    }


    private void Loaddata() {
        Cursor cursor = databaseTruyenChu.getDataTaiKhoan();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaiKhoan taikhoan = new TaiKhoan(
                        cursor.getString(1),  // Tên tài khoản
                        cursor.getString(2),  // Email
                        cursor.getString(3),  // Mật khẩu
                        cursor.getInt(4)     // ID
                );

                // Đặt dữ liệu vào TextView
                txttk.setText(taikhoan.getmTenTaiKhoan());
                txtmk.setText(taikhoan.getmMatKhau());
                txtemail.setText(taikhoan.getmEmail());

            } while (cursor.moveToNext());
            cursor.close();  // Đóng con trỏ sau khi đọc xong
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        databaseTruyenChu.close(); // Đóng cơ sở dữ liệu khi Activity bị hủy
        super.onDestroy();
    }
}
