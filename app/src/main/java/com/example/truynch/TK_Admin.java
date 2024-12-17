package com.example.truynch;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.adapter.AdapterTaiKhoan;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.TaiKhoan;

import java.util.ArrayList;

public class TK_Admin extends AppCompatActivity {
    ListView listtk;
    ArrayList<TaiKhoan> taiKhoans;
    AdapterTaiKhoan adaptertk;
    DatabaseTruyenChu databaseTruyenChu;
    TextView textTenAdmin, textPassAdmin, textGmailAdmin; // Thêm các TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tk_admin);

        listtk = findViewById(R.id.listtk);
        Button btn = findViewById(R.id.btnadd_user);

        // Khởi tạo danh sách tài khoản và adapter
        taiKhoans = new ArrayList<>();
        databaseTruyenChu = new DatabaseTruyenChu(this);  // Khởi tạo đối tượng Database

        // Kết nối các TextView với layout
        textTenAdmin = findViewById(R.id.text_TenAdmin);
        textPassAdmin = findViewById(R.id.text_PassAdmin);
        textGmailAdmin = findViewById(R.id.Text_GmailAdmin);

        loadAdminData(); // Tải dữ liệu tài khoản admin
        loadData(); // Tải dữ liệu tài khoản

        adaptertk = new AdapterTaiKhoan(this, taiKhoans);  // Tạo adapter với danh sách tài khoản
        listtk.setAdapter(adaptertk);  // Gắn adapter vào ListView
        btn = findViewById(R.id.btnadd_user);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TK_Admin.this, DangKy.class);
                startActivity(intent);
            }
        });

        listtk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaiKhoan selectedTaiKhoan = taiKhoans.get(i);
                showOptionsDialog(selectedTaiKhoan);
                return true;
            }
        });
    }

    private void showOptionsDialog(TaiKhoan taiKhoan) {
        String[] options = {"Sửa", "Xóa", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn tùy chọn")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Chuyển đến Activity sửa tài khoản
                            Intent intent = new Intent(TK_Admin.this, Main_suaadmin.class);
                            intent.putExtra("Id", taiKhoan.getmId()); // Gửi ID của tài khoản được chọn
                            startActivity(intent);
                        } else if (which == 1) {
                            deleteTaiKhoan(taiKhoan);
                        }
                    }
                })
                .show();
    }

    private void deleteTaiKhoan(TaiKhoan taiKhoan) {
        // Bây giờ xóa tài khoản
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản: " + taiKhoan.getmTenTaiKhoan() + "?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gọi phương thức xóa tài khoản
                        databaseTruyenChu.deleteTaiKhoan(taiKhoan.getmId());

                        // Xóa tài khoản khỏi danh sách và thông báo adapter cập nhật
                        taiKhoans.remove(taiKhoan);
                        adaptertk.notifyDataSetChanged();

                        // Hiển thị thông báo
                        Toast.makeText(TK_Admin.this, "Đã xóa tài khoản: " + taiKhoan.getmTenTaiKhoan(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void loadData() {
        Cursor cursor = databaseTruyenChu.getDataTaiKhoan();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaiKhoan taikhoan = new TaiKhoan(
                        cursor.getString(1), // Tên tài khoản
                        cursor.getString(2), // Email
                        cursor.getString(3), // Mật khẩu
                        cursor.getInt(4) // Quyền
                );
                taikhoan.setmId(cursor.getInt(0)); // ID
                taiKhoans.add(taikhoan); // Thêm vào danh sách
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
    // Phương thức để tải thông tin tài khoản admin
    private void loadAdminData() {
        TaiKhoan admin = databaseTruyenChu.getAdminAccount(); // Giả sử bạn đã định nghĩa phương thức này trong DatabaseTruyenChu
        if (admin != null) {
            textTenAdmin.setText("Tên Tài Khoản: " + admin.getmTenTaiKhoan());
            textPassAdmin.setText("Mật Khẩu: " + admin.getmMatKhau());
            textGmailAdmin.setText("Gmail: " + admin.getmEmail());
        } else {
            Toast.makeText(this, "Không tìm thấy tài khoản admin", Toast.LENGTH_SHORT).show();
        }
    }
}
