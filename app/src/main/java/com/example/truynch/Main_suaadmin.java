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

public class Main_suaadmin extends AppCompatActivity {
    EditText edituser, editpass, editemail, editpq;
    Button btneditadmin;
    DatabaseTruyenChu databaseTruyenChu;
    int idtk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_suaadmin);

        edituser = findViewById(R.id.btnsua_user_admin);
        editpass = findViewById(R.id.btnsua_pass_admin);
        editemail = findViewById(R.id.btnsua_email_admin);
        editpq = findViewById(R.id.btnsua_pq_admin);
        btneditadmin = findViewById(R.id.btn_sua_admin);

        databaseTruyenChu = new DatabaseTruyenChu(this);

        Intent intent = getIntent();
        idtk = intent.getIntExtra("Id", 0);
        loadtkInfo(idtk);

        btneditadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittk();
            }
        });
    }

    private void loadtkInfo(int id) {
        Log.d("DEBUG", "ID khách: " + id); // Kiểm tra ID khách
        Cursor cursor = databaseTruyenChu.getTKById(id); // Lấy dữ liệu theo ID
        if (cursor != null && cursor.moveToFirst()) {
            edituser.setText(cursor.getString(1)); // Tên tài khoản
            editpass.setText(cursor.getString(2)); // Mật khẩu
            editemail.setText(cursor.getString(3)); // Email
            editpq.setText(String.valueOf(cursor.getInt(4))); // Phân quyền
            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin khách hàng.", Toast.LENGTH_SHORT).show();
        }
    }

    private void edittk() {
        String tenKhach = edituser.getText().toString();
        String matKhau = editpass.getText().toString();
        String email = editemail.getText().toString();
        String phanquyen = editpq.getText().toString();

        if (tenKhach.isEmpty() || matKhau.isEmpty() || email.isEmpty() || phanquyen.isEmpty()) {
            Toast.makeText(Main_suaadmin.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = databaseTruyenChu.updateTaiKhoan(idtk, tenKhach, matKhau, email, Integer.parseInt(phanquyen));
        if (isUpdated) {
            Toast.makeText(Main_suaadmin.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(Main_suaadmin.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
