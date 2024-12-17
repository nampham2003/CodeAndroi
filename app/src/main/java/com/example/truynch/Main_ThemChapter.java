package com.example.truynch;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.adapter.Chapter1Adapter;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.Chapter;

import java.util.ArrayList;

public class Main_ThemChapter extends AppCompatActivity {

    // Khai báo biến
    private EditText edChapTer, edNoiDungChapter, suachapter, suanoidung;
    private Button btnAddChapter, btnsuachapter, btnxoachapter, btnhuy, btndialogsua, btndialoghuy;
    private DatabaseTruyenChu databaseTruyenChu;
    private String idTruyen;
    private ArrayList<Chapter> chapterList;
    private Chapter1Adapter chapter1Adapter;
    private ListView listViewChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_them_chapter);

        // Khởi tạo cơ sở dữ liệu
        databaseTruyenChu = new DatabaseTruyenChu(this);
        chapterList = new ArrayList<>();
        idTruyen = getIntent().getStringExtra("ID_TRUYEN");

        // Liên kết các view
        edChapTer = findViewById(R.id.edChapTer);
        edNoiDungChapter = findViewById(R.id.edNoiDungChapter);
        btnAddChapter = findViewById(R.id.btn_addchapter);
        listViewChapters = findViewById(R.id.listviewChapter);

        // Thiết lập adapter cho ListView
        chapter1Adapter = new Chapter1Adapter(this, R.layout.row_chapter, chapterList);
        listViewChapters.setAdapter(chapter1Adapter);

        // Tải các chương hiện có
        loadChapters(idTruyen);

        // Xử lý sự kiện khi nhấn nút "Thêm Chapter"
        btnAddChapter.setOnClickListener(view -> addChapter());
    }

    private void addChapter() {
        String chapterName = edChapTer.getText().toString().trim();
        String chapterContent = edNoiDungChapter.getText().toString().trim();

        if (chapterName.isEmpty()) {
            edChapTer.setError("Tên chapter không được để trống");
            edChapTer.requestFocus();
            return;
        }

        if (chapterContent.isEmpty()) {
            edNoiDungChapter.setError("Nội dung chapter không được để trống");
            edNoiDungChapter.requestFocus();
            return;
        }

        boolean isInserted = databaseTruyenChu.addChapter(idTruyen, chapterName, chapterContent);

        if (isInserted) {
            Toast.makeText(Main_ThemChapter.this, "Thêm chương thành công", Toast.LENGTH_SHORT).show();
            loadChapters(idTruyen); // Tải lại các chương để làm mới ListView
            edChapTer.setText(""); // Xóa các trường nhập liệu
            edNoiDungChapter.setText("");
        } else {
            Toast.makeText(Main_ThemChapter.this, "Lỗi khi thêm chương", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadChapters(String idTruyen) {
        Cursor cursor = databaseTruyenChu.getChaptersById(idTruyen);
        chapterList.clear(); // Xóa danh sách trước khi thêm mới

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Chapter chapter = new Chapter(
                        cursor.getString(0), // ID truyện
                        cursor.getString(1), // Chương
                        cursor.getString(2)  // Nội dung chương
                );
                chapterList.add(chapter);
            } while (cursor.moveToNext());
            cursor.close(); // Đóng con trỏ sau khi sử dụng
            chapter1Adapter.notifyDataSetChanged(); // Thông báo cho adapter về sự thay đổi dữ liệu
        } else {
            Toast.makeText(this, "Không có chương nào", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteChapter(String idTruyen, String chuong) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_chapter);
        dialog.setCancelable(false);

        btnsuachapter = dialog.findViewById(R.id.btnedit_chapter);
        btnxoachapter = dialog.findViewById(R.id.btnxoa_chapter);
        btnhuy = dialog.findViewById(R.id.btnhuy_chapter);
        btnsuachapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog1 = new Dialog(Main_ThemChapter.this);
                dialog1.setContentView(R.layout.edit_chapter);
                dialog1.setCancelable(false);

                btndialogsua = dialog1.findViewById(R.id.dialog_sua);
                btndialoghuy = dialog1.findViewById(R.id.dialog_huy);
                suachapter = dialog1.findViewById(R.id.dialog_tenchapter);
                suanoidung = dialog1.findViewById(R.id.dialog_noidung);

                // Gọi loadChapter để đổ dữ liệu chương vào các trường
                loadChapter(idTruyen, chuong); // Thay 'chuong' bằng tên chương cụ thể

                btndialogsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String chapter = suachapter.getText().toString();
                        String noidung = suanoidung.getText().toString();

                        boolean isUpdated = databaseTruyenChu.updateChapter(idTruyen, chapter, noidung);                        if (isUpdated) {
                            Toast.makeText(Main_ThemChapter.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            loadChapters(idTruyen); // Tải lại chương sau khi sửa
                            dialog1.dismiss(); // Đóng dialog
                            //finish(); // Quay trở lại Main_ThemChapter
                        } else {
                            Toast.makeText(Main_ThemChapter.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                btndialoghuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
                dialog1.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8),
                        WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

        btnxoachapter.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(Main_ThemChapter.this);
            alert.setTitle("Thông báo");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setMessage("Bạn có muốn xóa bản ghi này?");
            alert.setPositiveButton("Có", (dialogInterface, i) -> {
                boolean isDeleted = databaseTruyenChu.deleteChapter(idTruyen, chuong);

                if (isDeleted) {
                    Toast.makeText(Main_ThemChapter.this, "Xóa chương thành công", Toast.LENGTH_SHORT).show();
                    loadChapters(idTruyen); // Tải lại danh sách chương
                } else {
                    Toast.makeText(Main_ThemChapter.this, "Lỗi khi xóa chương", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
            alert.show();
        });
        btnhuy.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
        dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 1.0),
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void loadChapter(String idTruyen, String chuong) {
        Cursor cursor = databaseTruyenChu.getChapterByIdAndTruyen(idTruyen, chuong);

        if (cursor != null && cursor.moveToFirst()) {
            suachapter.setText(cursor.getString(1)); // Chapter number
            suanoidung.setText(cursor.getString(2)); // Chapter content
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
