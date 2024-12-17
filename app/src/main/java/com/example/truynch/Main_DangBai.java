package com.example.truynch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truynch.adapter.TruyenAdapter;
import com.example.truynch.database.DatabaseTruyenChu;
import com.example.truynch.model.Truyen;

import java.util.ArrayList;

public class Main_DangBai extends AppCompatActivity {

    ArrayList<Truyen> truyens; // Danh sách tất cả truyện
    TruyenAdapter adapter; // Sử dụng adapter tùy chỉnh
    ArrayList<Truyen> searchResults; // Danh sách truyện tìm kiếm

    Button btnaddTruyen, btnedittruyen, btnxoatruyen, btncancel;
    EditText editTextSearch;
    ListView listViewResults;
    DatabaseTruyenChu databaseTruyenChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_bai);

        btnaddTruyen = findViewById(R.id.btnThemTruyenMoi);
        editTextSearch = findViewById(R.id.timkiemMT);
        listViewResults = findViewById(R.id.listviewtimkiemMT);
        databaseTruyenChu = new DatabaseTruyenChu(this);

        initList();

        btnaddTruyen.setOnClickListener(view -> {
            Intent intent = new Intent(Main_DangBai.this, Main_ThemTruyen.class);
            themTruyenLauncher.launch(intent);  // Sử dụng launcher để chờ kết quả thêm truyện
        });

        // Xử lý sự kiện tìm kiếm
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Xử lý sự kiện click vào một truyện
        listViewResults.setOnItemClickListener((adapterView, view, i, l) -> {
            Truyen selectedTruyen = searchResults.get(i); // Sử dụng kết quả tìm kiếm khi chọn item
            Intent intent = new Intent(Main_DangBai.this, Main_ThemChapter.class);
            intent.putExtra("ID_TRUYEN", selectedTruyen.getIdTruyen());
            startActivity(intent);
        });

        // Xử lý sự kiện long click để xóa hoặc sửa truyện
        listViewResults.setOnItemLongClickListener((adapterView, view, i, l) -> {
            xacnhanxoa(i);
            return true;
        });
    }

    // Lọc danh sách truyện theo từ khóa tìm kiếm
    private void filter(String text) {
        searchResults.clear();
        ArrayList<Truyen> filteredList = new ArrayList<>();
        for (Truyen item : truyens) {
            if (item.getTenTruyen().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        searchResults.addAll(filteredList);
        adapter.filterList(filteredList); // Cập nhật danh sách trong adapter
    }

    // Khởi tạo danh sách truyện từ database
    private void initList() {
        truyens = new ArrayList<>();
        searchResults = new ArrayList<>();
        Cursor cursor = databaseTruyenChu.getDataTruyen1();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String tentruyen = cursor.getString(1);
                String tacgia = cursor.getString(3);
                String trangthai = cursor.getString(5);
                String hinhanh = cursor.getString(4);
                String noidungsoluoc = cursor.getString(2);
                int id_tk = cursor.getInt(6);

                Truyen truyen = new Truyen(id, tentruyen, noidungsoluoc, tacgia, trangthai, hinhanh, id_tk);
                truyens.add(truyen);
                searchResults.add(truyen); // Thêm vào danh sách tìm kiếm ban đầu
            }
            cursor.close();
        }

        // Thiết lập adapter ngoài vòng lặp
        adapter = new TruyenAdapter(getApplicationContext(), truyens);
        listViewResults.setAdapter(adapter);
    }

    // Xác nhận xóa hoặc chỉnh sửa truyện
    private void xacnhanxoa(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        btnedittruyen = dialog.findViewById(R.id.button);
        btnxoatruyen = dialog.findViewById(R.id.button2);
        btncancel = dialog.findViewById(R.id.button3);

        btnedittruyen.setOnClickListener(view -> {
            Truyen selectedTruyen = searchResults.get(position);
            String idTruyen = selectedTruyen.getIdTruyen(); // Lấy id truyện

            // Mở màn hình sửa truyện với id truyền vào
            Intent intent = new Intent(Main_DangBai.this, Main_SuaBai.class);
            intent.putExtra("ID_TRUYEN", idTruyen);
            suaTruyenLauncher.launch(intent);  // Sử dụng launcher để chờ kết quả sửa truyện
            dialog.dismiss();
        });

        btnxoatruyen.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(Main_DangBai.this);
            alert.setTitle("Thông báo");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setMessage("Bạn có muốn xóa bản ghi này?");
            alert.setPositiveButton("Có", (dialogInterface, i) -> {
                // Xóa truyện từ database
                Truyen truyen = searchResults.get(position);
                databaseTruyenChu.deleteTruyen(truyen.getIdTruyen());

                // Xóa trên giao diện
                searchResults.remove(position);
                truyens.remove(truyen); // Cũng xóa khỏi danh sách chính
                adapter.notifyDataSetChanged();
                Toast.makeText(Main_DangBai.this, "Đã xóa truyện", Toast.LENGTH_SHORT).show();
            });

            alert.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
            alert.show();
        });

        btncancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    // Sử dụng ActivityResultLauncher để nhận kết quả khi thêm truyện
    private final ActivityResultLauncher<Intent> themTruyenLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Cập nhật lại danh sách sau khi thêm truyện
                    initList();
                    Toast.makeText(Main_DangBai.this, "Đã thêm truyện mới", Toast.LENGTH_SHORT).show();
                }
            });

    // Sử dụng ActivityResultLauncher để nhận kết quả khi sửa truyện
    private final ActivityResultLauncher<Intent> suaTruyenLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Cập nhật lại danh sách sau khi sửa truyện
                    initList();
                    Toast.makeText(Main_DangBai.this, "Đã cập nhật truyện", Toast.LENGTH_SHORT).show();
                }
            });
}
